package me.whothefuckis.model;

import me.whothefuckis.utility.Length;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class DirTable {
    /*
    Properties
    ====================================================================================================================
    * */

    /*public*/
    public static final String BYTE = "BYTE";
    public static final String RESB = "RESB";
    public static final String WORD = "WORD";
    public static final String RESW = "RESW";
    public static final String START = "START";
    public static final String BASE = "BASE";
    public static final String NOBASE = "NOBASE";
    public static final String END = "END";
    public static final String LTORG = "LTORG";
    public static final String EXTREF = "EXTREF";
    public static final String EXTDEF = "EXTDEF";
    public static final String ORG = "ORG";
    public static final String EQU = "EQU";
    public static final String CSECT = "CSECT";

    /*private*/
    private static final HashMap<String, Directive> directiveTable = new HashMap<>();

    /*
    Cashing
    ====================================================================================================================
    * */
    static {
        load();
    }

    /*
    Constructors
    ====================================================================================================================
    * */
    /**
     * Returns the directive table.*/
    public static HashMap<String, Directive> get() {
        return directiveTable;
    }

    /**
     * Returns a Directive object from the directive table.
     *
     * @param  directive the name of the directive to be retrieved.
     * */
    public static Directive getDirective(@NotNull String directive) {
        return directiveTable.get(directive);
    }

    /**
     * Checks whether the directive name exists in the directive table.
     *
     * @param  directive the name of the directive to be retrieved.
     * */
    public static boolean contains(@NotNull String directive) {
        return directiveTable.containsKey(directive.toUpperCase());
    }

    private static void load() {
        directiveTable.put(BYTE, new Directive(BYTE, Length.VARIABLE, true));
        directiveTable.put(RESB, new Directive(BYTE, Length.VARIABLE, true));
        directiveTable.put(WORD, new Directive(BYTE, Length.THREE, true));
        directiveTable.put(RESW, new Directive(BYTE, Length.VARIABLE, true));
        directiveTable.put(START, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(BASE, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(NOBASE, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(END, new Directive(BYTE, Length.NONE, false));
        directiveTable.put(LTORG, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(EXTREF, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(EXTDEF, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(ORG, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(EQU, new Directive(BYTE, Length.NONE, true));
        directiveTable.put(CSECT, new Directive(BYTE, Length.NONE, true));
    }


}
