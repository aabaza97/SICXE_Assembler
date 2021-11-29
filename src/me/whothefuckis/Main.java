package me.whothefuckis;

import me.whothefuckis.manager.Loader;
import me.whothefuckis.manager.Parser;
import me.whothefuckis.manager.OutputGenerator;
import me.whothefuckis.manager.ObjectCodeGenerator;
import me.whothefuckis.manager.Writer;
import me.whothefuckis.model.SymbolTable;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Loader fileLoader = loadFile();

        parseFile(fileLoader.loadFile(), Parser.Mode.FREE);

        OutputGenerator outputGenerator = generateOutputFile(fileLoader.getFileParentPath(), fileLoader.getFileName());

        ObjectCodeGenerator objectCodeGenerator = generateObjectCode();

        assemble(outputGenerator, objectCodeGenerator);

    }

    private static ObjectCodeGenerator generateObjectCode() {
        ObjectCodeGenerator objectCodeGenerator = new ObjectCodeGenerator();
        objectCodeGenerator.generate();
        objectCodeGenerator.terminal.show();
        return objectCodeGenerator;
    }


    /**
     * Asks the user to enter the Input file.
     * */
    public static Loader loadFile() {
        Loader fileLoader = new Loader();
        fileLoader.openChooserDialogue();
        return fileLoader;
    }

    /**
     * Parses the file and returns
     * */
    public static void parseFile(ArrayList<String> instructionsList, Parser.Mode parsingMode) {
        Parser.getInstance().parse(instructionsList, parsingMode);
        SymbolTable.getInstance().printAll();
    }

    private static OutputGenerator generateOutputFile(String fileParentPath, String fileName) {
        OutputGenerator outputGenerator = new OutputGenerator(fileParentPath, fileName);
        outputGenerator.generateSymbolFile();
        outputGenerator.terminal.showAddressFile();
        outputGenerator.terminal.showSymbolFile();
        return outputGenerator;
    }

    private static void assemble(OutputGenerator outputGenerator, ObjectCodeGenerator objectCodeGenerator) {
        Writer fileWriter = new Writer(outputGenerator);
        fileWriter.writeAddressFile();
        fileWriter.writeSymbolFile();
        fileWriter.writeObjectCodeFile(objectCodeGenerator);
    }

}
