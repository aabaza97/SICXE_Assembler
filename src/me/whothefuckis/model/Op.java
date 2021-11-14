package me.whothefuckis.model;

import me.whothefuckis.utility.Format;
import me.whothefuckis.utility.OpType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Op {

    /*
    Properties
    ====================================================================================================================
    * */

    private final OpType firstOperandType;
    private final OpType secondOperandType;
    private Format format;
    private String name;
    private int opcode;
    private boolean indexable;



    /*
    Constructors
    ====================================================================================================================
    * */
    public Op(@NotNull String name,
              int opcode,
              @NotNull Format format,
              @NotNull OpType firstOperandType,
              @NotNull OpType secondOperandType
    ) {
        setName(name);
        this.opcode = opcode;
        this.format = format;
        this.firstOperandType = firstOperandType;
        this.secondOperandType = secondOperandType;
        indexable = format == Format.THREE;
    }

    /*
    Functions
    ====================================================================================================================
    * */

    @Nullable
    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name != null)
            this.name = name.toUpperCase();
    }

    public int getOpcode() {
        return opcode;
    }

    public Format getFormat() {
        return format;
    }

    public OpType getFirstOperandType() {
        return firstOperandType;
    }

    public OpType getSecondOperandType() {
        return secondOperandType;
    }

    public boolean hasOperand() {
        return hasFirstOperand() || hasSecondOperand();
    }

    public boolean hasFirstOperand() {
        return firstOperandType == OpType.DONT_CARE || firstOperandType != OpType.NONE;
    }

    public boolean hasSecondOperand() {
        return secondOperandType == OpType.DONT_CARE || secondOperandType != OpType.NONE;
    }

    public boolean isIndexable() {
        return indexable;
    }
}
