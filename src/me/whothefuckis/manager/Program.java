package me.whothefuckis.manager;


import me.whothefuckis.model.Instruction;
import java.util.ArrayList;
import static me.whothefuckis.utility.Utility.*;


public final class Program {

    private static String name;
    private static String startAddress;
    private static ArrayList<String> instructionsList = new ArrayList<>();

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Program.name = name;
    }

    public static String getStartAddress() {
        return startAddress;
    }

    public static void setStartAddress(String startAddress) {
        Program.startAddress = extendLength(removeHexadecimalNotation(startAddress), 6);
    }

    public static String getEndAddress() {
        return extendLength(removeHexadecimalNotation(
                LocationCounter.getInstance().getHexAddresses().get(
                        LocationCounter.getInstance().getHexAddresses().size() - 1)), 6);
    }

    public static String getFirstExecutableInstructionAddress() {
        ArrayList<Instruction> instructionsList = Parser.getInstance().getParsedInstructions();
        ArrayList<Integer> addressesList = LocationCounter.getInstance().getAddresses();
        for (int i = 0; i < addressesList.size(); i++)
            if (Validator.isOperation(instructionsList.get(i).getMnemonic()))
                if (!addressesList.get(i).equals(addressesList.get(i + 1)))
                    return extendLength(SysConverter.Decimal.toHexadecimal(addressesList.get(i)), 6);
        return null;
    }

    public static boolean hasBaseDirective() {
        return Parser.getInstance().hasBaseDirective();
    }

    public static int getBaseRegisterValue() {
        return Parser.getInstance().getBaseRegisterValue();
    }

    public static String getObjectCodeLength() {
        int startAddress = SysConverter.Hexadecimal.toDecimal(getStartAddress());
        int endAddress = SysConverter.Hexadecimal.toDecimal(getEndAddress());
        return extendLength(SysConverter.Decimal.toHexadecimal(endAddress - startAddress), 6);
    }

    /**
     * @return ArrayList containing all instructions of the given program in
     * it's original form {String}
     */
    public static ArrayList<String> getInstructionsList() {
        return instructionsList;
    }

    public static int getProgramCount() {
        return instructionsList.size();
    }

    public static boolean hasError() {
        return false;
    }
}