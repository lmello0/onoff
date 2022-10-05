package com.onoff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.JSONTokener;

public class KnownHosts {
    private InputStream jsonFile = null;
    private JSONObject devices = null;
    private String path = "known_hosts/known_hosts.json"; 

    public KnownHosts() {
        try {
            jsonFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONTokener tokener = new JSONTokener(jsonFile);
        devices = new JSONObject(tokener);
    }

    public String getIp(String device_name) {
        JSONArray devicesArray = devices.getJSONArray("devices");
        for (int i = 0; i < devicesArray.length(); i++) {
            if (devicesArray.getJSONObject(i).getJSONObject("device").getString("name").equals(device_name)){
                return devicesArray.getJSONObject(i).getJSONObject("device").getString("ip");
            }
        }

        return "Not found ip with the given device name!";
    }

    public String getMac(String device_name) {
        JSONArray devicesArray = devices.getJSONArray("devices");
        for (int i = 0; i < devicesArray.length(); i++) {
            if (devicesArray.getJSONObject(i).getJSONObject("device").getString("name").equals(device_name)){
                return devicesArray.getJSONObject(i).getJSONObject("device").getString("mac");
            }
        }

        return "Not found MAC with the given device name!";
    }

    // private JSONObject parseDeviceObject(JSONObject device) {
    //     return (JSONObject) device.get("device");

    // }

    public void addDeviceObject(String name, String ip, String mac){
        System.out.println(devices);
        JSONObject deviceInfo = new JSONObject();
        deviceInfo.put("name", name);
        deviceInfo.put("ip", ip);
        deviceInfo.put("mac", mac);
        
        JSONObject device = new JSONObject();
        device.put("device", deviceInfo);

        devices.put("devices", device);
        System.out.println(devices.toString());

        // writeJsonToFile(devices);
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

    // public void deleteDeviceObject(String deviceName) {
    //     for(int i = 0; i < devices.size(); i++){
    //         JSONObject device = parseDeviceObject((JSONObject) devices.get(i));

    //         if (device.get("name").equals(deviceName)) {
    //             devices.remove(devices.get(i));
    //         }
    //     }
    // }

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