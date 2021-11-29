package me.whothefuckis.model;

import me.whothefuckis.utility.Format;
import me.whothefuckis.utility.OpType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;

public final class OpTable {

    private static HashMap<String, Op> operationTable = new HashMap<>();
    private static HashSet<String> manipulativeOperations = new HashSet<>();

    private OpTable() {}

    /**
     * Cache op-table before starting program
     */
    static {
        load();
        loadManipulativeOperations();
    }


    public static HashMap<String, Op> get() {
        return operationTable;
    }

    public static HashSet<String> getManipulativeOperations() {
        return manipulativeOperations;
    }

    public static Op getOperation(@NotNull String mnemonic) {
        return operationTable.get(mnemonic);
    }

    public static boolean containsMnemonic(@NotNull String mnemonic) {
        return operationTable.containsKey(mnemonic.toUpperCase());
    }

    /**
     * Load all mnemonics details to op-table.
     * Adds mnemonics in the format of Operation class.
     *
     * @see Op class
     */
    private static void load() {
        operationTable.put("ADD",
                new Op("ADD", 0x18, Format.THREE, OpType.VALUE, OpType.DONT_CARE));
        operationTable.put("+ADD",
                new Op("+ADD", 0x18, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("ADDR",
                new Op("ADDR", 0x90, Format.TWO, OpType.REGISTER, OpType.REGISTER));
        operationTable.put("AND",
                new Op("AND", 0x40, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+AND",
                new Op("+AND", 0x40, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("CLEAR",
                new Op("CLEAR", 0xB4, Format.TWO, OpType.REGISTER, OpType.NONE));
        operationTable.put("COMP",
                new Op("COMP", 0x28, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+COMP",
                new Op("+COMP", 0x28, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("COMPR",
                new Op("COMPR", 0xA0, Format.TWO, OpType.REGISTER, OpType.REGISTER));
        operationTable.put("DIV",
                new Op("DIV", 0x24, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+DIV",
                new Op("+DIV", 0x24, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("DIVR",
                new Op("DIVR", 0x9C, Format.TWO, OpType.REGISTER, OpType.REGISTER));
        operationTable.put("J",
                new Op("J", 0x3C, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+J",
                new Op("+J", 0x3C, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("JEQ",
                new Op("JEQ", 0x30, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+JEQ",
                new Op("+JEQ", 0x30, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("JGT",
                new Op("JGT", 0x34, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+JGT",
                new Op("+JGT", 0x34, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("JLT",
                new Op("JLT", 0x38, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+JLT",
                new Op("+JLT", 0x38, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("JSUB",
                new Op("JSUB", 0x48, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+JSUB",
                new Op("+JSUB", 0x48, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("LDA",
                new Op("LDA", 0x00, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+LDA",
                new Op("+LDA", 0x00, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("LDB",
                new Op("LDB", 0x68, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+LDB",
                new Op("+LDB", 0x68, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("LDCH",
                new Op("LDCH", 0x50, Format.THREE, OpType.VALUE, OpType.DONT_CARE));
        operationTable.put("+LDCH",
                new Op("+LDCH", 0x50, Format.FOUR, OpType.VALUE, OpType.DONT_CARE));
        operationTable.put("LDL",
                new Op("LDL", 0x08, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+LDL",
                new Op("+LDL", 0x08, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("LDS",
                new Op("LDS", 0x6C, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+LDS",
                new Op("+LDS", 0x6C, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("LDT",
                new Op("LDT", 0x74, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+LDT",
                new Op("+LDT", 0x74, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("LDX",
                new Op("LDX", 0x04, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+LDX",
                new Op("+LDX", 0x04, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("MUL",
                new Op("MUL", 0x20, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+MUL",
                new Op("+MUL", 0x20, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("MULR",
                new Op("MULR", 0x98, Format.TWO, OpType.REGISTER, OpType.REGISTER));
        operationTable.put("OR",
                new Op("OR", 0x44, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+OR",
                new Op("+OR", 0x44, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("RD",
                new Op("RD", 0xD8, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+RD",
                new Op("+RD", 0xD8, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("RMO",
                new Op("RMO", 0xAC, Format.TWO, OpType.REGISTER, OpType.REGISTER));
        operationTable.put("RSUB",
                new Op("RSUB", 0x4C, Format.THREE, OpType.NONE, OpType.NONE));
        operationTable.put("+RSUB",
                new Op("+RSUB", 0x4C, Format.FOUR, OpType.NONE, OpType.NONE));
        operationTable.put("SHIFTL",
                new Op("SHIFTL", 0xA4, Format.TWO, OpType.REGISTER, OpType.VALUE));
        operationTable.put("SHIFTR",
                new Op("SHIFTR", 0xA8, Format.TWO, OpType.REGISTER, OpType.VALUE));
        operationTable.put("STA",
                new Op("STA", 0x0C, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+STA",
                new Op("+STA", 0x0C, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("STB",
                new Op("STB", 0x78, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+STB",
                new Op("+STB", 0x78, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("STCH",
                new Op("STCH", 0x54, Format.THREE, OpType.VALUE, OpType.DONT_CARE));
        operationTable.put("+STCH",
                new Op("+STCH", 0x54, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("STL",
                new Op("STL", 0x14, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+STL",
                new Op("+STL", 0x14, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("STS",
                new Op("STS", 0x7C, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+STS",
                new Op("+STS", 0x7C, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("STT",
                new Op("STT", 0x84, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+STT",
                new Op("+STT", 0x84, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("STX",
                new Op("STX", 0x10, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+STX",
                new Op("+STX", 0x10, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("SUB",
                new Op("SUB", 0x1C, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+SUB",
                new Op("+SUB", 0x1C, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("SUBR",
                new Op("SUBR", 0x94, Format.TWO, OpType.REGISTER, OpType.REGISTER));
        operationTable.put("TD",
                new Op("TD", 0xE0, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+TD",
                new Op("+TD", 0xE0, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("TIX",
                new Op("TIX", 0x2C, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+TIX",
                new Op("+TIX", 0x2C, Format.FOUR, OpType.VALUE, OpType.NONE));
        operationTable.put("TIXR",
                new Op("TIXR", 0xB8, Format.TWO, OpType.REGISTER, OpType.NONE));
        operationTable.put("WD",
                new Op("WD", 0xDC, Format.THREE, OpType.VALUE, OpType.NONE));
        operationTable.put("+WD",
                new Op("+WD", 0xDC, Format.FOUR, OpType.VALUE, OpType.NONE));
    }

    /**
     * Load all mnemonics of operations that may be manipulative to
     * program counter.
     */
    private static void loadManipulativeOperations() {
        manipulativeOperations.add("J");
        manipulativeOperations.add("+J");
        manipulativeOperations.add("JLT");
        manipulativeOperations.add("+JLT");
        manipulativeOperations.add("JGT");
        manipulativeOperations.add("+JGT");
        manipulativeOperations.add("JEQ");
        manipulativeOperations.add("+JEQ");
        manipulativeOperations.add("JSUB");
        manipulativeOperations.add("+JSUB");
        manipulativeOperations.add("RSUB");
        manipulativeOperations.add("+RSUB");
    }


}