package me.whothefuckis.model;

import java.util.HashMap;

public class RegisterTable {

    public static final String A = "A";
    public static final String X = "X";
    public static final String L = "L";
    public static final String B = "B";
    public static final String S = "S";
    public static final String T = "T";
    public static final String F = "F";
    public static final String PC = "PC";
    public static final String SW = "SW";

    private static final HashMap<String, Register> registers = new HashMap<>();

    public static String getRegisterName(int registerNumber) {
        return switch (registerNumber) {
            case 0 -> "A";
            case 1 -> "X";
            case 2 -> "L";
            case 3 -> "B";
            case 4 -> "S";
            case 5 -> "T";
            case 6 -> "F";
            case 8 -> "PC";
            case 9 -> "SW";
            default -> null;
        };
    }

    private RegisterTable() {
    }

    static {
        load();
    }

    public static boolean contains(String register) {
        return registers.containsKey(register);
    }

    private static void load() {
        registers.put("A", new Register(0));
        registers.put("X", new Register(1));
        registers.put("L", new Register(2));
        registers.put("B", new Register(3));
        registers.put("S", new Register(4));
        registers.put("T", new Register(5));
        registers.put("F", new Register(6));
        registers.put("PC", new Register(8));
        registers.put("SW", new Register(9));
    }

    public static Register getRegister(String registerName) {
        return registers.get(registerName);
    }
}
