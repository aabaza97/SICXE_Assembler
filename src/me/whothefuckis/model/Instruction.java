package me.whothefuckis.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Instruction {
    /*
    Properties
    ====================================================================================================================
    * */

    private String label;
    private String mnemonic;
    private String firstOperand;
    private String secondOperand;
    private String comment;
    private int address;
    private boolean indexed;
    private boolean hasError;

    /*
    Constructors
    ====================================================================================================================
    * */
    public Instruction() {}

    public Instruction(@Nullable String label,
                       @NotNull String mnemonic,
                       @Nullable String firstOperand,
                       @Nullable String secondOperand,
                       @Nullable String comment) {
        setLabel(label);
        setMnemonic(mnemonic);
        setFirstOperand(firstOperand);
        setSecondOperand(secondOperand);
        this.comment = comment;
        indexed = checkIndexingAbility();
        hasError = false;
    }


    /*
    Functions
    ====================================================================================================================
    * */

    private boolean checkIndexingAbility() {
        return secondOperand != null && secondOperand.equals(RegisterTable.X);
    }

    @Nullable
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label != null)
            this.label = label;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(@NotNull String mnemonic) {
        this.mnemonic = mnemonic.toUpperCase();
    }

    @Nullable
    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        if (firstOperand != null)
            this.firstOperand = firstOperand.toUpperCase();
    }

    @Nullable
    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        if (secondOperand != null)
            this.secondOperand = secondOperand.toUpperCase();
    }

    public String getComment() {
        return comment;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public boolean hasLabel() {
        return label != null;
    }

    public boolean hasMnemonic() {
        return mnemonic != null;
    }

    public boolean hasOperands() {
        return hasFirstOperand() || hasSecondOperand();
    }

    public boolean hasFirstOperand() {
        return firstOperand != null;
    }

    public boolean hasSecondOperand() {
        return secondOperand != null;
    }

    public boolean hasComment() {
        return comment != null;
    }

    public boolean hasError() {
        return hasError;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void errorFree(boolean error) {
        hasError = !error;
    }

}
