package me.whothefuckis;

import java.io.*;
import java.util.Scanner;


public class Main {

    public static PrintStream stream = System.out;
    public static String filePath = "";

    /**
     * Just a testing variable... blabblaalansdfalk*/
    static String line = "";

    public static void main(String[] args) {
        BufferedReader bufferedReader;
        FileReader reader;

        Main.getFile();

        try {
            reader = new FileReader(filePath);
            bufferedReader = new BufferedReader(reader);

            line = bufferedReader.readLine();
            stream.println(line.split("\t")[1]);
        } catch (Exception e) {

        }
    }



    /**
     * Asks the user to enter the Input file's absolute path.
     * */
    public static void getFile() {
        Scanner scanner = new Scanner(System.in);
        stream.println("Enter your file path:");
        stream.flush();
        filePath = scanner.nextLine();

        while (filePath.isEmpty()) {
            stream.println("Please enter a valid file path");
            filePath = scanner.nextLine();
        }
//        stream.println("You entered a file in path:  \""+ filePath + "\"");
    }

}
