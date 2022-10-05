package com.onoff;

import java.io.Console;
import java.util.Scanner;
public class CLI {
    private Scanner keyboard;
    private Console console;
    private SSH ssh = new SSH();
    private KnownHosts knownHosts = new KnownHosts();

    public CLI() {
        this.keyboard = new Scanner(System.in);
        this.console = System.console();
    }

    private int menuGui() {
        System.out.println("|--------------------------|");
        System.out.println("|         - MENU -         |");
        System.out.println("|--------------------------|");
        System.out.println("|  1. Turn on device       |");
        System.out.println("|  2. Turn off device      |");
        System.out.println("|  3. Reboot device        |");
        System.out.println("|  4. List device          |");
        System.out.println("|  5. Add device           |");
        System.out.println("|  6. Remove device        |");
        System.out.println("|  7. Exit                 |");
        System.out.println("|--------------------------|");


        System.out.println();
        System.out.print("Choose [1 - 5]: ");
        int option = keyboard.nextInt();

        return option;
    }

    public void menuChooser() {
        int option = menuGui();

        String ip, username, password, mac, deviceName;

        switch(option){
            case 1:
                System.out.println("WIP");
                break;
            case 2:
                System.out.print("IP: ");
                ip = keyboard.next();

                System.out.print("Username: ");
                username = keyboard.next();

                System.out.print("Password: ");
                password = new String(console.readPassword());

                ssh.shutdown(ip, username, password);

                break;
            case 3:
                System.out.println("WIP");
                break;
            case 4:
                // System.out.println(knownHosts.printDevices());
                break;
            case 5:
                System.out.print("Device name: ");
                deviceName = keyboard.next();
                
                System.out.print("IP: ");
                ip = keyboard.next();

                System.out.print("Mac: ");
                mac = keyboard.next();

                break;
            case 6:
                System.out.print("Device name: ");
                deviceName = keyboard.next();

                // knownHosts.deleteDeviceObject(deviceName);
                break;
            case 7:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }
    }
}