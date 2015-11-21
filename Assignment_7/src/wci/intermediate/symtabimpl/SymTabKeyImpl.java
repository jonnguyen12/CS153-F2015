package wci.intermediate.symtabimpl;

import wci.intermediate.SymTabKey;

/**
 * <h1>SymTabKeyImpl</h1>
 *
 * <p>Attribute keys for a symbol table entry.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public enum SymTabKeyImpl implements SymTabKey
{
    // Constant.
    CONSTANT_VALUE,

    // Procedure or function.
    FUNCTION_CODE, FUNCTION_SYMTAB, FUNCTION_ICODE,
    FUNCTION_PARMS, FUNCTION_FUNCTIONS,

    // Variable or record field value.
    VARIABLE_VALUE
}