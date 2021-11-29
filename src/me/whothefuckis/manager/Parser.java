package me.whothefuckis.manager;


import me.whothefuckis.model.DirTable;
import me.whothefuckis.model.Instruction;
import me.whothefuckis.model.SymbolTable;
import me.whothefuckis.utility.Constants;
import me.whothefuckis.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.util.StringTokenizer;

import static me.whothefuckis.manager.ParserValidator.*;
import static me.whothefuckis.manager.Validator.*;

public class Parser {

    private static Parser instance = new Parser();
    private int baseRegisterValue;

    private final static int FIRST_OPERAND = 0;
    private final static int SECOND_OPERAND = 1;

    private ArrayList<Instruction> parsedInstructionsList = new ArrayList<>();
    private String currentInstruction;
    private boolean hasBaseDirective = false;

    private LocationCounter locationCounter = LocationCounter.getInstance();
    private SymbolTable symbolTable = SymbolTable.getInstance();
    private ErrorHandler errorHandler = ErrorHandler.getInstance();


    private Parser() {}

    public static Parser getInstance() {
        return instance;
    }

    /**
     * Parses the file specified in the path.
     * Reads it line by line and creates a list of instructions in the same order
     * they appear in the file
     * @return <b>parsedInstructionsList:</b> ArrayList{Instruction}
     */
    public ArrayList<Instruction> parse(@NotNull ArrayList<String> instructions, @NotNull Mode mode) {
        Instruction parsedInstruction;
        for (String instruction : instructions) {
            parsedInstruction = parseInstruction(instruction, mode);
            if (parsedInstruction != null) {
                parsedInstruction.setAddress(locationCounter.getCurrentAddress());
                parsedInstructionsList.add(parsedInstruction);
            }
        }
        return parsedInstructionsList;
    }


    /**
     * Parses a single instruction given in the form of a String into Instruction model
     */
    @Nullable
    public Instruction parseInstruction(@NotNull String instruction, @NotNull Mode mode) {
        errorHandler.setHasError(false);
        if (isComment(instruction)) {
            locationCounter.update();
            OutputGenerator.update();
            return new Instruction();
        }
        Instruction parsedInstruction = null;
        if (mode == Mode.FREE)
            parsedInstruction = parseInstructionFree(instruction);
        else if (mode == Mode.CONSTRAINED)
            parsedInstruction = parseInstructionConstrained(instruction);

        if (parsedInstruction == null)
            return null;

        currentInstruction = parsedInstruction.toString();
        parsedInstruction.errorFree(validateInstruction(parsedInstruction));
        locationCounter.update(parsedInstruction);
        symbolTable.update(parsedInstruction);
        OutputGenerator.update();
        return parsedInstruction;
    }

    /**
     * Parses a single constrained format instruction given in the form of a String into Instruction model
     */
    private Instruction parseInstructionConstrained(@NotNull String instruction){
        String label = determineLabel(instruction);
        String mnemonic = determineMnemonic(instruction);
        String[] operandsList = determineOperands(instruction);
        String comment = determineComment(instruction);

        if (hasBaseDirective)
            baseRegisterValue = Integer.parseInt(operandsList[FIRST_OPERAND]);

        return new Instruction(
                label,
                Objects.requireNonNull(mnemonic),
                operandsList[FIRST_OPERAND],
                operandsList[SECOND_OPERAND],
                comment
        );
    }

    /**
     * Parses a single free format instruction given in the form of a String into Instruction model
     */
    private Instruction parseInstructionFree(@NotNull String instruction) {
        String label = null, mnemonic, operands = null, comment = null;
        String[] operandsList = new String[2];

        Stack<String> instructionElements = new Stack<>();
        String[] tokens = instruction.trim().split("\\s+");

        // adding literals having spaces as one operand
        StringBuilder operand = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].contains(Constants.APOSTROPHE))
                for (int j = i + 1; j < tokens.length; j++) {
                    i++;
                    operand.append(tokens[j - 1]).append(Constants.SPACE);
                    if (tokens[j].contains(Constants.APOSTROPHE)) {
                        i++;
                        operand.append(tokens[j]);
                        instructionElements.push(operand.toString());
                        break;
                    }
                }
            if (i < tokens.length)
                instructionElements.push(tokens[i]);
        }

        switch (instructionElements.size()) {
            case 0:
                return null;
            case 1:
                mnemonic = instructionElements.pop();
                break;
            case 2:
                operands = instructionElements.pop();
                mnemonic = instructionElements.pop();
                break;
            case 3:
                operands = instructionElements.pop();
                mnemonic = instructionElements.pop();
                label = instructionElements.pop();
                break;
            default:
                StringBuilder commentBuilder = new StringBuilder();
                while (instructionElements.size() > 3)
                    commentBuilder.append(instructionElements.pop()).append(Constants.SPACE);
                comment = Utility.reverseWords(commentBuilder.toString());
                operands = instructionElements.pop();
                mnemonic = instructionElements.pop();
                label = instructionElements.pop();
                break;
        }
        if (operands != null) {
            int i = 0;
            StringTokenizer operandsTokenizer = new StringTokenizer(operands, Constants.COMMA);
            while (operandsTokenizer.hasMoreTokens())
                operandsList[i++] = operandsTokenizer.nextToken();
        }
//        if (mnemonic.equals(DirTable.BASE)) {
//            hasBaseDirective = true;
//            baseRegisterValue = Integer.parseInt(operandsList[FIRST_OPERAND]);
//        }
        return new Instruction(label, mnemonic, operandsList[FIRST_OPERAND], operandsList[SECOND_OPERAND], comment);
    }

    private String determineLabel(String instruction) {
        String label = null;
        if (instruction.length() > Range.LABEL[Range.END]) {
            label = instruction.substring(Range.LABEL[Range.START], Range.LABEL[Range.END]);
            if (isBlank(label))
                return null;
        }
        return label;
    }

    private String determineMnemonic(String instruction) {
        String mnemonic = null;
        if (instruction.length() > Range.MNEMONIC[Range.END])
            mnemonic = instruction.substring(Range.MNEMONIC[Range.START], Range.MNEMONIC[Range.END])
                    .replaceAll("\\s", Constants.EMPTY);

        else if (instruction.length() > Range.MNEMONIC[Range.START])
            mnemonic = instruction.substring(Range.MNEMONIC[Range.START])
                    .replaceAll("\\s", Constants.EMPTY);

        if (mnemonic != null && mnemonic.equals(DirTable.BASE))
            hasBaseDirective = true;

        return mnemonic;
    }


    private String[] determineOperands(String instruction) {
        String operands = null;
        if (instruction.length() > Range.OPERANDS[Range.END])
            operands = instruction.substring(Range.MNEMONIC[Range.START], Range.MNEMONIC[Range.END])
                    .replaceAll("\\s", Constants.EMPTY);

        else if (instruction.length() > Range.OPERANDS[Range.START])
            operands = instruction.substring(Range.OPERANDS[Range.START])
                    .replaceAll("\\s", Constants.EMPTY);

        String[] operandsList = new String[2];
        if (operands != null) {
            int i = 0;
            StringTokenizer tokenizer = new StringTokenizer(operands, Constants.COMMA);
            while (tokenizer.hasMoreTokens())
                operandsList[i++] = tokenizer.nextToken();
        }
        return operandsList;
    }


    private String determineComment(String instruction) {
        String comment = null;
        if (instruction.length() > Range.COMMENT[Range.START]) {
            comment = instruction.substring(Range.COMMENT[Range.START]);
            if (isBlank(comment))
                return null;
        }
        return comment;
    }

    /**
     * @return ArrayList containing all parsed instructions of the given program
     */
    public ArrayList<Instruction> getParsedInstructions() {
        return parsedInstructionsList;
    }

    public String getCurrentInstruction() {
        return currentInstruction;
    }

    /**
     * Shows all instructions of the read program in the form of their details.
     */
    public void showParsedInstructions() {
        for (Instruction i : parsedInstructionsList) {
            System.out.println(i.getLabel());
            System.out.println(i.getMnemonic());
            System.out.println(i.getFirstOperand());
            System.out.println(i.getSecondOperand());
            System.out.println(i.getComment());
            System.out.println(SysConverter.Decimal.toHexadecimal(i.getAddress()) + "\n");
        }
    }

    public boolean hasBaseDirective() {
        return hasBaseDirective;
    }

    public int getBaseRegisterValue() {
        return baseRegisterValue;
    }

    /**
     * enum controls parsing mode
     */
    public enum Mode {
        CONSTRAINED, FREE
    }

    /**
     * Utility inner class for constants of instruction format range.
     */
    private static class Range {
        private final static int START = 0;
        private final static int END = 1;
        private final static int[] LABEL = {0, 9};
        private final static int[] MNEMONIC = {9, 16};
        private final static int[] OPERANDS = {17, 36};
        private final static int[] COMMENT = {35, 67};
    }
}