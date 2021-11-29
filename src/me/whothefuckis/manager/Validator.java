package me.whothefuckis.manager;

import me.whothefuckis.model.DirTable;
import me.whothefuckis.model.Instruction;
import me.whothefuckis.model.OpTable;
import org.jetbrains.annotations.NotNull;

public class Validator {

    private Validator() {
    }

    public static boolean isComment(@NotNull String line) {
        return line.startsWith(".");
    }

    public static boolean isBlank(@NotNull String string) {
        return !string.isEmpty() && string.trim().isEmpty();
    }

    public static boolean isMnemonic(String mnemonic) {
        return isDirective(mnemonic) || isOperation(mnemonic);
    }

    public static boolean isDirective(String directive) {
        return (directive != null) && DirTable.contains(directive);
    }

    public static boolean isReservationDirective(String directive) {
        return (directive != null)
                && (directive.equals(DirTable.RESB) || directive.equals(DirTable.RESW));
    }

    public static boolean isDeclartiveDirective(String directive) {
        return (directive != null)
                && (directive.equals(DirTable.BYTE) || directive.equals(DirTable.WORD));
    }

    public static boolean isOperation(String operation) {
        return (operation != null) && OpTable.containsMnemonic(operation);
    }

    public static boolean isManipulativeOperation(String operation) {
        return (operation != null) && OpTable.getManipulativeOperations().contains(operation);
    }

    public static boolean containsNumber(String string) {
        return (string != null) && string.matches(".*\\d.*");
    }

    public static boolean startsWithNumber(String string) {
        return (string != null) && Character.isDigit(string.charAt(0));
    }

    public static boolean isExpression(String string) {
        if (string == null)
            return false;

        char[] chars = string.toCharArray();
        for (char c : chars)
            if (!Character.isDigit(c) && !isOperator(c))
                return false;
        return true;
    }

    private static boolean isOperator(char o) {
        return o == '%' || o == '/' || o == '*' || o == '+' || o == '-';
    }

    public static boolean isNumeric(String string) {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Utility class for object code bits detection.
     * Used to detect n i x b p e bits.
     */
    public static class Operand {

        public static boolean isImmediate(Instruction instruction) {
            return (instruction.getFirstOperand() != null && instruction.getFirstOperand().startsWith("#"))
                    || (instruction.getSecondOperand() != null && instruction.getSecondOperand().startsWith("#"));
        }

        public static boolean isIndirect(Instruction instruction) {
            return (instruction.getFirstOperand() != null && instruction.getFirstOperand().startsWith("@"))
                    || (instruction.getSecondOperand() != null && instruction.getSecondOperand().startsWith("@"));
        }

        public static boolean isDirect(Instruction instruction) {
            return !isImmediate(instruction) && !isIndirect(instruction);
        }

        public static boolean isImmediate(String operand) {
            return operand != null && operand.startsWith("#");
        }

        public static boolean isIndirect(String operand) {
            return operand != null && operand.startsWith("@");
        }

        public static boolean isDirect(String operand) {
            return !isImmediate(operand) && !isIndirect(operand);
        }

        public static boolean isLiteral(String operand) {
            return operand != null && (operand.startsWith("X'") || operand.startsWith("C'")) && operand.endsWith("'");
        }

        public static boolean isAddressSymbol(String string) {
            return (string != null) && string.charAt(0) == '*';
        }
    }
}
