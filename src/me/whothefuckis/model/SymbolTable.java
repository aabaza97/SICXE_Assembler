package me.whothefuckis.model;

import me.whothefuckis.manager.LocationCounter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SymbolTable {

    /*
    Properties
    ====================================================================================================================
    * */
    private static final SymbolTable instance = new SymbolTable();
    private HashMap<String, Symbol> symbolTable = new HashMap<>();

    /*
    Constructors
    ====================================================================================================================
    * */
    private SymbolTable(){}


    /*
    Functions
    ====================================================================================================================
    * */
    public static SymbolTable getInstance() {
        return instance;
    }

    /*static let shared: Foo {
        get{
            return (this.shared = Foo());
        }
    }*/


    public HashMap<String, Symbol> get() {
        return symbolTable;
    }

    public void printAll() {
        for (String name: symbolTable.keySet()) {
            String key = name.toString();
            String value = symbolTable.get(name).toString();
            System.out.println(key + " " + value);
        }
    }

    public Symbol getSymbol(@NotNull String label) {
        //TODO: handle @, #,

        return symbolTable.get(label);
    }

    public boolean containsSymbol(@NotNull String label) {
        return symbolTable.containsKey(label.toUpperCase());
    }

    public void update(@NotNull Instruction instruction) {
        String label = instruction.getLabel();
        if (label != null && !instruction.hasError()) {
            if (symbolTable.containsKey(label)) {
               //TODO: Should create a logger file to imply error location.
                return;
            }
            symbolTable.put(
                    label,
                    new Symbol(label, LocationCounter.getInstance().getCurrentAddress())
            );
        }
    }

}
