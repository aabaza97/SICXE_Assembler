package me.whothefuckis.model;

import org.jetbrains.annotations.NotNull;

public class Symbol {

    /*
    Properties
    ====================================================================================================================
    * */
    private String label;
    private int address;


    /*
    Constructors
    ====================================================================================================================
    * */
    /**
     * Data class modeling each symbol row in the symbol table.
     *
     * @param label String describing the symbol.
     * @param address Integer resembling the address location of the symbol in the program.
     *
     * @see SymbolTable
     * */
    public Symbol(@NotNull String label, int address) {
        this.label = label;
        this.address = address;
    }

    /*
    Functions
    ====================================================================================================================
    * */
    @NotNull
    public String getLabel() {
        return label;
    }

    public int getAddress() {
        return address;
    }

}
