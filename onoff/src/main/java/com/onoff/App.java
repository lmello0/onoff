package com.onoff;

/**
 * Hello world!
 */
public final class App {
    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        CLI cli = new CLI();
        while(true){
            cli.menuChooser();
        }
    }
}
