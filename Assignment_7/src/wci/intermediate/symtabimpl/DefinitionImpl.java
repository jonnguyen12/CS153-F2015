package wci.intermediate.symtabimpl;

import wci.intermediate.Definition;

/**
 * <h1>DefinitionImpl</h1>
 *
 * <p>How a Pascal symbol table entry is defined.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public enum DefinitionImpl implements Definition
{

    CONSTANT, VARIABLE, FUNCTION, UNDEFINED;

    private String text;

    /**
     * Constructor.
     */
    DefinitionImpl()
    {
        this.text = this.toString().toLowerCase();
    }

    /**
     * Constructor.
     * @param text the text for the definition code.
     */
    DefinitionImpl(String text)
    {
        this.text = text;
    }

    /**
     * Getter.
     * @return the text of the definition code.
     */
    public String getText()
    {
        return text;
    }
}