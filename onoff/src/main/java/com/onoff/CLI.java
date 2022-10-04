package com.onoff;

import java.io.Console;
import java.util.Scanner;
public class CLI {
    private Scanner keyboard;
    private Console console;
    private SSH ssh = new SSH();

    public CLI() {
        this.keyboard = new Scanner(System.in);
        this.console = System.console();
    }

    private int menuGui() {
        System.out.println("|------------------|");
        System.out.println("|     - MENU -     |");
        System.out.println("|------------------|");
        System.out.println("|  1. Ligar PC     |");
        System.out.println("|  2. Desligar PC  |");
        System.out.println("|  3. Reiniciar PC |");
        System.out.println("|  4. Sair         |");
        System.out.println("|------------------|");


        System.out.println();
        System.out.print("Choose [1 - 4]: ");
        int option = keyboard.nextInt();

        return option;
    }

    public void menuChooser() {
        int option = menuGui();

        switch(option){
            case 1:
                System.out.println("WIP");
                break;
            case 2:
                System.out.print("IP: ");
                String ip = keyboard.next();

                System.out.print("Username: ");
                String username = keyboard.next();

                System.out.print("Password: ");
                String password = new String(console.readPassword());

                ssh.shutdown(ip, username, password);

                break;
            case 3:
                System.out.println("WIP");
                break;
            case 4:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }
    }
}