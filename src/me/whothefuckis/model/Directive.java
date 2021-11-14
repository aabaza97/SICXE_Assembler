package me.whothefuckis.model;


import me.whothefuckis.utility.Length;
import org.jetbrains.annotations.NotNull;

public final class Directive {
    /*
    Properties
    ====================================================================================================================
    * */
    private String directive;
    private Length length;
    private boolean hasOperand;


    /*
    Constructors
    ====================================================================================================================
    * */
    /**
     * Data class modeling a directive in a directive table.
     *
     * @param directive String describing the assembler instruction.
     * @param length A Length Case describing the amount of bytes for the instruction.
     * @param hasOperand A boolean decides whether a directive instruction has an operand.
     *
     * @see DirTable
     * */
    public Directive(@NotNull String directive,
                     @NotNull Length length,
                     boolean hasOperand
    ) {
        setDirective(directive);
        this.length = length;
        this.hasOperand = hasOperand;
    }


    /*
    Functions
    ====================================================================================================================
    * */
    @NotNull
    public String getDirective() {
        return this.directive;
    }

    public void setDirective(@NotNull String directive) {
        this.directive = directive.toUpperCase();
    }

    @NotNull
    public Length getLength() {
        return this.length;
    }

    public boolean hasOperand() {
        return this.hasOperand;
    }
}