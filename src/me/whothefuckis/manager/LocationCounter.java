package me.whothefuckis.manager;

import me.whothefuckis.model.DirTable;
import me.whothefuckis.model.Instruction;
import me.whothefuckis.model.OpTable;
import me.whothefuckis.utility.Utility;

import java.util.ArrayList;
import java.util.Objects;

import static me.whothefuckis.manager.Validator.*;

public class LocationCounter {

    private static LocationCounter instance = new LocationCounter();

    public static LocationCounter getInstance() {
        return instance;
    }

    private LocationCounter() { }

    private final int WORD_LENGTH = 3;

    private boolean enabled = true;
    private int previousAddress = 0;
    private int currentAddress = 0;
    private int programCounter = 0;
    private ArrayList<Integer> addresses = new ArrayList<>();
    private ArrayList<String> convertedAddresses = new ArrayList<>();

    public void set(int address) {
        currentAddress = address;
    }

    public void reset() {
        currentAddress = 0;
        programCounter = 0;
    }

    public void update(Instruction instruction){
        if (!enabled)
            return;

        String mnemonic = instruction.getMnemonic();
        if (mnemonic == null)
            return;

        if (isDirective(mnemonic)) {
            switch (DirTable.getDirective(mnemonic).getLength()) {
                case NONE:
                    switch (mnemonic) {
                        case DirTable.START:
                            currentAddress = SysConverter.Hexadecimal.toDecimal(instruction.getFirstOperand());
                            addAddress(currentAddress);
                            previousAddress = currentAddress;
                            Program.setName(instruction.getLabel());
                            Program.setStartAddress(instruction.getFirstOperand());
                            return;
                        case DirTable.EQU:
                            break;
                    }
                    break;
                case ONE:
                    currentAddress += 1;
                    break;
                case TWO:
                    currentAddress += 2;
                    break;
                case THREE:
                    currentAddress += 3;
                    break;
                case VARIABLE:
                    switch (mnemonic) {
                        case DirTable.RESW:
                            currentAddress +=
                                    (Integer.parseInt(Objects.requireNonNull(instruction.getFirstOperand()))
                                            * WORD_LENGTH);
                            break;
                        case DirTable.RESB:
                            currentAddress +=
                                    (Integer.parseInt(Objects.requireNonNull(instruction.getFirstOperand())));
                            break;
                        case DirTable.BYTE:
                            if (Validator.Operand.isLiteral(instruction.getFirstOperand()))
                                currentAddress += (instruction.getFirstOperand().length() - 3);
                            else
                                currentAddress += 1;
                            break;
                    }
                    break;
            }
            update();
        } else if (isOperation(mnemonic)) {
            switch (Objects.requireNonNull(OpTable.getOperation(mnemonic).getFormat())) {
                case ONE:
                    currentAddress += 1;
                case TWO:
                    currentAddress += 2;
                    break;
                case THREE:
                    currentAddress += 3;
                    break;
                case FOUR:
                    currentAddress += 4;
                    break;
            }
            update();
        } else
            enabled = false;
    }


    public int update() {
        addAddress(previousAddress);
        previousAddress = currentAddress;
        return currentAddress;
    }

    public void addAddress(int address) {
        programCounter++;
        addresses.add(address);
        convertedAddresses
                .add(Utility.addHexadecimalNotation(
                        Utility.extendLength(
                                SysConverter.Decimal.toHexadecimal(address), 4).toUpperCase()));
    }

    public ArrayList<Integer> getAddresses() {
        return addresses;
    }

    public ArrayList<String> getHexAddresses() {
        return convertedAddresses;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public int getCurrentAddress() {
        return addresses.get(programCounter - 1);
    }

    public String getCurrentConvertedAddress() {
        return convertedAddresses.get(programCounter - 1);
    }
}
