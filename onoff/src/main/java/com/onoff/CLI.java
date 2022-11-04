package com.onoff;

import java.io.Console;
import java.io.IOException;
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
        System.out.print("Choose [1 - 7]: ");
        int option = keyboard.nextInt();

        return option;
    }

    private int menuTurnOff() {
        System.out.println("|---------------------------|");
        System.out.println("|      Turn off device      |");
        System.out.println("|---------------------------|");
        System.out.println("|  1. IP                    |");
        System.out.println("|  2. Mac                   |");
        System.out.println("|  3. Name                  |");
        System.out.println("|  4. Back                  |");
        System.out.println("|---------------------------|");

        System.out.println();
        System.out.print("Choose [1 - 4]: ");
        int option = keyboard.nextInt();

        return option;
    }

    private int menuTurnOn() {
        System.out.println("|---------------------------|");
        System.out.println("|      Turn on device       |");
        System.out.println("|---------------------------|");
        System.out.println("|  1. IP                    |");
        System.out.println("|  2. Mac                   |");
        System.out.println("|  3. Name                  |");
        System.out.println("|  4. Back                  |");
        System.out.println("|---------------------------|");

        System.out.println();
        System.out.print("Choose [1 - 4]: ");
        int option = keyboard.nextInt();

        return option;
    }

    public void menuChooser() {
        int option = menuGui();

        String ip = null, mac = null, username = null, password = null, deviceName = null;

        switch(option){
            case 1:
                int menuTurnOn = menuTurnOn();
                switch(menuTurnOn) {
                    case 1:
                        System.out.print("IP: ");
                        ip = keyboard.next();

                        mac = knownHosts.getMac(ip);
                        break;
                    case 2:
                        System.out.print("MAC: ");
                        mac = keyboard.next();
                        break;
                    case 3:
                        System.out.print("Device name: ");
                        deviceName = keyboard.next();

                        mac = knownHosts.getMac(deviceName);
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Invalid option!");
                }

                if (!(ip == null && username == null && password == null)){
                    mac = WakeOnLan.cleanMac(mac);
                    try {
                        WakeOnLan.send(mac, ip);
                    } catch (IllegalArgumentException | IOException e) {
                        e.printStackTrace();
                    }

                    break;
                } else if (menuTurnOn != 4) {
                    System.out.println("Invalid parameters!");
                }

                break;
            case 2:
                int menuTurnOff = menuTurnOff();
                switch(menuTurnOff){
                    case 1:
                        System.out.print("IP: ");
                        ip = keyboard.next();

                        System.out.print("Username: ");
                        username = keyboard.next();

                        System.out.print("Password: ");
                        password = new String(console.readPassword());
                        break;
                    case 2:
                        System.out.print("MAC: ");
                        mac = keyboard.next();

                        System.out.print("Username: ");
                        username = keyboard.next();

                        System.out.print("Password: ");
                        password = new String(console.readPassword());

                        ip = knownHosts.getIp(mac);
                        break;
                    case 3:
                        System.out.print("Device name: ");
                        deviceName = keyboard.next();

                        System.out.print("Username: ");
                        username = keyboard.next();

                        System.out.print("Password: ");
                        password = new String(console.readPassword());

                        ip = knownHosts.getIp(deviceName);
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Invalid option!");
                }

                if (!(ip == null && username == null && password == null)){
                    ssh.shutdown(ip, username, password);
                    break;
                } else if (menuTurnOff != 4) {
                    System.out.println("Invalid parameters!");
                }

                break;
            case 3:
                System.out.println("WIP");
                break;
            case 4:
                System.out.println(knownHosts.printDevices());
                break;
            case 5:
                System.out.print("Device name: ");
                deviceName = keyboard.next();
                
                System.out.print("IP: ");
                ip = keyboard.next();

                System.out.print("Mac: ");
                mac = keyboard.next().replace("-", ":");

                knownHosts.addDeviceObject(deviceName, ip, mac);
                break;
            case 6:
                System.out.print("Device name: ");
                deviceName = keyboard.next();

                knownHosts.deleteDeviceObject(deviceName);
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