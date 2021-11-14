package me.whothefuckis.model;

public class Register {
    /*
    Properties
    ====================================================================================================================
   * */
    private final int address;
    private int value;


    /*
    Constructors
    ====================================================================================================================
    * */
    /**
     * Data class modeling each register row in the register table.
     *
     * @param address Integer resembling the address location of the register in the machine.
     *
     * @see RegisterTable
     * */
    public Register(int address) {
        this.address = address;
    }


    /*
    Functions
    ====================================================================================================================
    * */
    public int getAddress() {
        return address;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
