package wci.util;

import java.util.ArrayList;

import wci.intermediate.*;
import wci.intermediate.symtabimpl.DefinitionImpl;
import wci.intermediate.typeimpl.TypeFormImpl;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;

/**
 * <h1>CrossReferencer</h1>
 *
 * <p>Generate a cross-reference listing.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class CrossReferencer
{
    private static final int NAME_WIDTH = 16;

    private static final String NAME_FORMAT       = "%-" + NAME_WIDTH + "s";
    private static final String NUMBERS_LABEL     = " Line numbers    ";
    private static final String NUMBERS_UNDERLINE = " ------------    ";
    private static final String NUMBER_FORMAT = " %03d";

    private static final int LABEL_WIDTH  = NUMBERS_LABEL.length();
    private static final int INDENT_WIDTH = NAME_WIDTH + LABEL_WIDTH;

    private static final StringBuilder INDENT = new StringBuilder(INDENT_WIDTH);
    static {
        for (int i = 0; i < INDENT_WIDTH; ++i) INDENT.append(" ");
    }

    /**
     * Print the cross-reference table.
     * @param symTabStack the symbol table stack.
     */
    public void print(SymTabStack symTabStack)
    {
        System.out.println("\n===== CROSS-REFERENCE TABLE =====");

        SymTabEntry programId = symTabStack.getProgramId();
        printRoutine(programId);
    }

    /**
     * Print a cross-reference table for a routine.
     * @param routineId the routine identifier's symbol table entry.
     */
    private void printRoutine(SymTabEntry routineId)
    {
        Definition definition = routineId.getDefinition();
        System.out.println("\n*** " + definition.toString() +
                           " " + routineId.getName() + " ***");
        printColumnHeadings();

        // Print the entries in the routine's symbol table.
        SymTab symTab = (SymTab) routineId.getAttribute(FUNCTION_SYMTAB);
        printSymTab(symTab);
/*
        // Print any procedures and functions defined in the routine.
        ArrayList<SymTabEntry> routineIds =
                (ArrayList<SymTabEntry>) routineId.getAttribute(ROUTINE_ROUTINES);
        if (routineIds != null) {
            for (SymTabEntry rtnId : routineIds) {
                printRoutine(rtnId);
            }
        }*/
    }

    /**
     * Print column headings.
     */
    private void printColumnHeadings()
    {
        System.out.println();
        System.out.println(String.format(NAME_FORMAT, "Identifier")
                           + NUMBERS_LABEL +     "Type specification");
        System.out.println(String.format(NAME_FORMAT, "----------")
                           + NUMBERS_UNDERLINE + "------------------");
    }

    /**
     * Print the entries in a symbol table.
     * @param symTab the symbol table.
     */
    private void printSymTab(SymTab symTab)
    {
        // Loop over the sorted list of symbol table entries.
        ArrayList<SymTabEntry> sorted = symTab.sortedEntries();
        for (SymTabEntry entry : sorted) {
            ArrayList<Integer> lineNumbers = entry.getLineNumbers();

            // For each entry, print the identifier name
            // followed by the line numbers.
            System.out.print(String.format(NAME_FORMAT, entry.getName()));
            if (lineNumbers != null) {
                for (Integer lineNumber : lineNumbers) {
                    System.out.print(String.format(NUMBER_FORMAT, lineNumber));
                }
            }

            // Print the symbol table entry.
            System.out.println();
            printEntry(entry);
        }
    }

    /**
     * Print a symbol table entry.
     * @param entry the symbol table entry.
     */
    private void printEntry(SymTabEntry entry)
    {
        Definition definition = entry.getDefinition();
        int nestingLevel = entry.getSymTab().getNestingLevel();
        System.out.println(INDENT + "Defined as: " + definition.getText());
        System.out.println(INDENT + "Scope nesting level: " + nestingLevel);

        // Print the type specification.
        TypeSpec type = entry.getTypeSpec();
        printType(type);

        switch ((DefinitionImpl) definition) {

            case CONSTANT: {
                Object value = entry.getAttribute(CONSTANT_VALUE);
                System.out.println(INDENT + "Value = " + toString(value));

                // Print the type details only if the type is unnamed.
                if (type.getIdentifier() == null) {
                    printTypeDetail(type);
                }

                break;
            }

            case VARIABLE: {

                // Print the type details only if the type is unnamed.
                if (type.getIdentifier() == null) {
                    printTypeDetail(type);
                }

                break;
            }
            
        }
    }

    /**
     * Print a type specification.
     * @param type the type specification.
     */
    private void printType(TypeSpec type)
    {
        if (type != null) {
            TypeForm form = type.getForm();
            SymTabEntry typeId = type.getIdentifier();
            String typeName = typeId != null ? typeId.getName() : "<unnamed>";

            System.out.println(INDENT + "Type form = " + form +
                               ", Type id = " + typeName);
        }
    }

    private static final String ENUM_CONST_FORMAT = "%" + NAME_WIDTH + "s = %s";

    /**
     * Print the details of a type specification.
     * @param type the type specification.
     */
    private void printTypeDetail(TypeSpec type)
    {
        TypeForm form = type.getForm();

        switch ((TypeFormImpl) form) {
            
        }
    }

    /**
     * Convert a value to a string.
     * @param value the value.
     * @return the string.
     */
    private String toString(Object value)
    {
        return value instanceof String ? "'" + (String) value + "'"
                                       : value.toString();
    }
}