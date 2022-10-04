package com.onoff;

import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSH {
    public void shutdown(String ip, String username, String password) {
        try{
            JSch jsch = new JSch();

            Session session = jsch.getSession(username, ip, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            String command = "shutdown now";

            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand("sudo -S -p '' " + command);

            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();
            ((ChannelExec)channel).setErrStream(System.err);

            channel.connect();

            out.write((password + "\n").getBytes());
            out.flush();

            byte[] tmp = new byte[1024];
            while(true) {
                while(in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if(i < 0){
                        break;
                    }
                    System.out.println(new String(tmp, 0, i));
                }
                
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }

                try { Thread.sleep(1000); } catch (Exception ee) {}
            }

            channel.disconnect();
            session.disconnect();
        } catch (JSchException jschE) {
            System.out.println("\nError: " + jschE.getCause().getMessage() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
