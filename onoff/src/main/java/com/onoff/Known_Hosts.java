package com.onoff;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Known_Hosts {
    private File jsonFile = new File("known_hosts/known_hosts.json");
    private Scanner jsonObject = null;

    public Known_Hosts() throws FileNotFoundException {
        jsonObject = new Scanner(jsonFile);
    }

    public String getJsonString(){
        return jsonObject.toString();
    }

    public static void main(String[] args) {
        try {
            Known_Hosts known_Hosts = new Known_Hosts();
            System.out.println(known_Hosts.getJsonString());  
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
