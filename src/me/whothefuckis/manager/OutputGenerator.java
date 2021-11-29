package me.whothefuckis.manager;


import me.whothefuckis.model.Symbol;
import me.whothefuckis.model.SymbolTable;
import me.whothefuckis.utility.Constants;
import java.util.ArrayList;
import static me.whothefuckis.utility.Utility.*;


public class OutputGenerator {

    private String filePath;
    private String fileName;

    private static ArrayList<String> addressFileLines = new ArrayList<>();
    private static ArrayList<String> symbolFileLines = new ArrayList<>();

    private static ErrorHandler errorHandler = ErrorHandler.getInstance();


    public OutputGenerator(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public static ArrayList<String> getAddressFileLines() {
        return addressFileLines;
    }

    public static ArrayList<String> getSymbolFileLines() {
        return symbolFileLines;
    }

    public void generateAddressFile() {
        LocationCounter locationCounter = LocationCounter.getInstance();
        for (int i = 0; i < locationCounter.getProgramCounter(); i++) {
            addressFileLines.add(locationCounter.getHexAddresses().get(i)
                    + "\t\t"
                    + Program.getInstructionsList().get(i));
        }
    }

    public void generateSymbolFile() {
        SymbolTable symbolTable = SymbolTable.getInstance();
        for (String name : symbolTable.get().keySet()) {
            Symbol symbol = symbolTable.getSymbol(name);
            String string = addHexadecimalNotation(SysConverter.Decimal.toHexadecimal(symbol.getAddress()))
                    + Constants.TAB + symbol.getLabel() + Constants.TAB;
            symbolFileLines.add(string);
        }
    }

    public static void update() {
        addressFileLines.add(
                LocationCounter.getInstance().getCurrentConvertedAddress()
                        + Constants.TAB
                        + Parser.getInstance().getCurrentInstruction());

        if (errorHandler.hasError())
            addressFileLines.add(errorHandler.generate());
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }


    public Terminal terminal = new Terminal();


    public class Terminal {

        private Terminal() { }

        public void showAddressFile() {
            headerMessage("Address File");
            for (String line : addressFileLines)
                System.out.println(line);
        }

        public void showSymbolFile() {
            headerMessage("Symbol Table File");
            for (String line : symbolFileLines)
                System.out.println(line);
        }

        private void headerMessage(String message) {
            System.out.println( message);
        }
    }
}
