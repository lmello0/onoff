package com.onoff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public KnownHosts() {
        try {
            jsonFile = new FileInputStream("known_hosts/known_hosts.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONTokener tokener = new JSONTokener(jsonFile);
        devices = new JSONObject(tokener);
    }

    public static void main(String[] args) {
        KnownHosts knownHosts = new KnownHosts();
        System.out.println(knownHosts.devices);
    }

    // public String getIp(String device_name) {
    //     for(int i = 0; i < devices.size(); i++) {
    //         JSONObject device = parseDeviceObject((JSONObject) devices.get(i));
            
    //         if (device.get("name").equals(device_name)) {
    //             return (String) device.get("ip");
    //         }
    //     }

    //     return "Not found ip with the given device name!";
    // }

    // public String getMac(String device_name) {
    //     for(int i = 0; i < devices.size(); i++) {
    //         JSONObject device = parseDeviceObject((JSONObject) devices.get(i));
            
    //         if (device.get("name").equals(device_name)) {
    //             return (String) device.get("mac");
    //         }
    //     }

    //     return "Not found MAC with the given device name!";
    // }

    // private JSONObject parseDeviceObject(JSONObject device) {
    //     return (JSONObject) device.get("device");

    // }

    // public void addDeviceObject(String name, String ip, String mac){
    //     Map<String, String> map = new HashMap<>();
    //     map.put("name", name);
    //     map.put("ip", ip);
    //     map.put("mac", mac);
        
    //     // devices.add(map);
    // }

    // public void deleteDeviceObject(String deviceName) {
    //     for(int i = 0; i < devices.size(); i++){
    //         JSONObject device = parseDeviceObject((JSONObject) devices.get(i));

    //         if (device.get("name").equals(deviceName)) {
    //             devices.remove(devices.get(i));
    //         }
    //     }
    // }

    // public String printDevices() {
    //     String devicesString = new String();
    //     String deviceLine = new String();
    //     devicesString = "|--------------------------------|\n|        - DISPOSITIVOS -        |\n|--------------------------------|\n";

    //     int cont = 1;
    //     int lineLength = 32;

    //     for (int i = 0; i < devices.size(); i++) {
    //         JSONObject deviceJson = parseDeviceObject((JSONObject) devices.get(i));

    //         deviceLine = "| " + cont + ". " + deviceJson.get("name");
    //         while (deviceLine.length() <= lineLength) {
    //             deviceLine = deviceLine + " ";
    //         }
    //         deviceLine = deviceLine + "|\n";
            
    //         deviceLine = deviceLine + "|      - ip: " + deviceJson.get("ip");
    //         while(deviceLine.length() <= ((lineLength+2)*2)-1) {
    //                 deviceLine = deviceLine + " ";
    //         }
    //         deviceLine = deviceLine + "|\n";

    //         deviceLine = deviceLine + "|      - mac: " + deviceJson.get("mac");
    //         while(deviceLine.length() <= ((lineLength+2)*3)) {
    //             deviceLine = deviceLine + " ";
    //         }
    //         deviceLine = deviceLine + "|\n|                                |\n";

    //         devicesString = devicesString + deviceLine;

    //         cont += 1;
    //     }

    //     devicesString = devicesString + "|--------------------------------|\n";


    //     return devicesString;
    // }
}