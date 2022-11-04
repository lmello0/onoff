package com.onoff;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class KnownHosts {
    private InputStream jsonFile = null;
    private JSONObject devices = null;
    private String path = "known_hosts/known_hosts.json";
    final private String macRegex = "((\\d\\w|\\w\\d|\\d{2}|\\w{2})\\:){5}((\\d\\w|\\w\\d|\\d{2}|\\w{2}))";
    final private String ipRegex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";

    public KnownHosts() {
        try {
            jsonFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONTokener tokener = new JSONTokener(jsonFile);
        devices = new JSONObject(tokener);
    }

    public String getIp(String attribute) {
        String attributeFinder = "name";

        if (attribute.matches(macRegex)) {
            attributeFinder = "mac";
        }
        

        JSONArray devicesArray = devices.getJSONArray("devices");
        for (int i = 0; i < devicesArray.length(); i++) {
            if (devicesArray.getJSONObject(i).getJSONObject("device").getString(attributeFinder).equals(attribute)){
                return devicesArray.getJSONObject(i).getJSONObject("device").getString("ip");
            }
        }

        return "Not found ip with the given attribute";
    }

    public String getMac(String attribute) {
        String attributeFinder = "name";

        if (attribute.matches(ipRegex)) {
            attributeFinder = "ip";
        }

        JSONArray devicesArray = devices.getJSONArray("devices");
        for (int i = 0; i < devicesArray.length(); i++) {
            if (devicesArray.getJSONObject(i).getJSONObject("device").getString(attributeFinder).equals(attribute)){
                return devicesArray.getJSONObject(i).getJSONObject("device").getString("mac");
            }
        }

        return "Not found MAC with the given attribute!";
    }

    public void addDeviceObject(String name, String ip, String mac){
        JSONObject deviceInfo = new JSONObject();
        deviceInfo.put("name", name);
        deviceInfo.put("ip", ip);
        deviceInfo.put("mac", mac);

        JSONObject device = new JSONObject();
        device.put("device", deviceInfo);

        JSONArray deviceArray = devices.getJSONArray("devices");
        deviceArray.put(device);

        devices.put("devices", deviceArray);

        writeJsonToFile(devices);
    }

    private void writeJsonToFile(JSONObject json){
        
        try {
            FileWriter file = new FileWriter(path);
            file.write(json.toString());

            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeviceObject(String deviceName) {
        JSONArray devicesArray = devices.getJSONArray("devices");
        for (int i = 0; i < devicesArray.length(); i++) {
            if (devicesArray.getJSONObject(i).getJSONObject("device").getString("name").equals(deviceName)){
                devicesArray.remove(i);

                devices.put("devices", devicesArray);
                writeJsonToFile(devices);
            }
        }
    }

    public String printDevices() {
        JSONArray devicesArray = devices.getJSONArray("devices");

        String devicesString = new String();
        String deviceLine = new String();
        devicesString = "|--------------------------------|\n|        - DISPOSITIVOS -        |\n|--------------------------------|\n";

        int cont = 1;
        int lineLength = 32;

        for (int i = 0; i < devicesArray.length(); i++) {
            JSONObject deviceJson = devicesArray.getJSONObject(i);

            deviceLine = "| " + cont + ". " + deviceJson.getJSONObject("device").getString("name");
            while (deviceLine.length() <= lineLength) {
                deviceLine = deviceLine + " ";
            }
            deviceLine = deviceLine + "|\n";
            
            deviceLine = deviceLine + "|      - ip: " + deviceJson.getJSONObject("device").getString("ip");
            while(deviceLine.length() <= ((lineLength+2)*2)-1) {
                    deviceLine = deviceLine + " ";
            }
            deviceLine = deviceLine + "|\n";

            deviceLine = deviceLine + "|      - mac: " + deviceJson.getJSONObject("device").getString("mac");
            while(deviceLine.length() <= ((lineLength+2)*3)) {
                deviceLine = deviceLine + " ";
            }
            deviceLine = deviceLine + "|\n|                                |\n";

            devicesString = devicesString + deviceLine;

            cont += 1;
        }

        devicesString = devicesString + "|--------------------------------|\n";


        return devicesString;
    }
}