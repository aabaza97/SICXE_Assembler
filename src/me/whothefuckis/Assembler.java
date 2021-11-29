package me.whothefuckis;

/**
 * Assembler Core Operations*/
public class Assembler {

    /**
     *
     * DirectiveTable
     *  |->> Directives
     * SymbolTable
     *  |->> Symbol
     * RegisterTable
     *  |->> Register
     * OperationsTable
     *  |->>Op
     * Instructions
     * Locator
     * FileLoader
     * Parser
     * PassOne
     * PassTwo
     *
    * */
    private String LABEL = "1234567890", OPCODE = "nay", OPERAND = "stop";
    private String ioFormat = "%9d\t%-9s\t%-9s\t%-9s\t";

    public Assembler() {
        printOutputs();
    }

    private void printOutputs() {
        int num = Integer.parseInt(LABEL);
        String formattedString =  String.format(ioFormat, num, OPCODE, OPERAND, OPERAND);
        System.out.println(formattedString);
    }

    
    void passOne() {

    }
}
