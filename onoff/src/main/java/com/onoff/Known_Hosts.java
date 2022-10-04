package com.onoff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Known_Hosts {
    private File jsonFile = new File("known_hosts/known_hosts.json");
    private JSONArray devices;

    public Known_Hosts() {
        try (FileReader reader = new FileReader(jsonFile)) {
            devices = (JSONArray) new JSONParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public String get_ip(String device_name) {
        for(int i = 0; i < devices.size(); i++) {
            JSONObject device = parseDeviceObject((JSONObject) devices.get(i));
            
            if (device.get("name").equals(device_name)) {
                return (String) device.get("ip");
            }
        }

        return "Not found ip with the given device name!";
    }

    public String get_mac(String device_name) {
        for(int i = 0; i < devices.size(); i++) {
            JSONObject device = parseDeviceObject((JSONObject) devices.get(i));
            
            if (device.get("name").equals(device_name)) {
                return (String) device.get("mac");
            }
        }

        return "Not found MAC with the given device name!";
    }

    private JSONObject parseDeviceObject(JSONObject device) {
        return (JSONObject) device.get("device");

    }

    public static void main(String[] args) {
        Known_Hosts known_hosts = new Known_Hosts();

        System.out.println(known_hosts.get_mac("lmello-ubuntuhomeserver"));
    }
}
