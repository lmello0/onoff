package com.onoff;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WakeOnLan {
    private static String[] validateMac(String mac) throws IllegalArgumentException {
        mac = mac.replace(";", ":");
        String newMac = "";

        if (mac.matches("([a-zA-Z0-9]){12}")) {
            for (int i = 0; i < mac.length(); i++) {
                if ((i > 1) && (i % 2 == 0)) {
                    newMac += ":";
                }
                newMac += mac.charAt(i);
            }
        } else {
            newMac = mac;
        }

        final Pattern pattern = Pattern.compile("((([0-9a-fA-F]){2}[-:]){5}([0-9a-fA-F]){2})");
        final Matcher matcher = pattern.matcher(newMac);

        if (matcher.find()) {
            String result = matcher.group();
            return result.split("(\\:|\\-)");
        } else {
            throw new IllegalArgumentException("Invalid MAC address");
        }
    }

    public static String cleanMac(String mac) throws IllegalArgumentException {
        final String[] hex = validateMac(mac);

        StringBuffer sb = new StringBuffer();
        boolean isMixedCase = false;

        for (int i = 0; i < 6; i++){
            sb.append(hex[i]);
        }

        String testMac = sb.toString();

        if((testMac.toLowerCase().equals(testMac) == false) && (testMac.toUpperCase().equals(testMac) == false)) {
			isMixedCase = true;
		}

        sb = new StringBuffer();
        for(int i=0; i<6; i++) {
			if(isMixedCase == true) {
				sb.append(hex[i].toLowerCase());
			}else{
				sb.append(hex[i]);
			}
			if(i < 5) {
				sb.append(":");
			}
		}

        return sb.toString();
    }

    public static String send(String mac, String ip) throws UnknownHostException, SocketException, IOException, IllegalArgumentException {
        final String[] hex = validateMac(mac);

        final byte[] macBytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            macBytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }

        final byte[] bytes = new byte[102];

        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }

        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }

        final InetAddress address = InetAddress.getByName(ip);
        final DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 22);
        final DatagramSocket socket = new DatagramSocket();
        
        socket.send(packet);
        socket.isClosed();
        
        return hex[0] + ":" + hex[1] + ":" + hex[2] + ":" + hex[3] + ":" + hex[4] + ":" + hex[5];
    }

    public static void main(String[] args) {
        String ipStr = "192.168.1.218";
        String macStr = "46:04:07:e9:aa:de";

        try	{
			macStr = WakeOnLan.cleanMac(macStr);
			System.out.println("Sending to: "+ macStr);
			WakeOnLan.send(macStr, ipStr);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println("Failed to send Wake-on-LAN packet:" + e.getMessage());
		}
    }
}
