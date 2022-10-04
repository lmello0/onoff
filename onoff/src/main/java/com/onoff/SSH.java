package com.onoff;

import java.io.ByteArrayOutputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSH {
    public void shutdown(String ip, String username, String password) throws InterruptedException, JSchException {
        Session session = null;
        ChannelExec channel = null;
        
        try{
            session = new JSch().getSession(username, ip);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            
            channel.setCommand("shutdown now");
            
            ByteArrayOutputStream response = new ByteArrayOutputStream();
            channel.setOutputStream(response);

            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = new String(response.toByteArray());
            System.out.println(responseString);
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }
}
