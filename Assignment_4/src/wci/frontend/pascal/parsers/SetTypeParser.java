package wci.frontend.pascal.parsers;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.Definition;
import wci.intermediate.SymTabEntry;
import wci.intermediate.TypeFactory;
import wci.intermediate.TypeSpec;
import wci.intermediate.symtabimpl.DefinitionImpl;

import java.util.EnumSet;

import static wci.frontend.pascal.PascalErrorCode.MISSING_OF;
import static wci.frontend.pascal.PascalTokenType.*;
import static wci.intermediate.typeimpl.TypeFormImpl.SET;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;

public class SetTypeParser extends TypeSpecificationParser {

    protected SetTypeParser(PascalParserTD parent) {
        super(parent);
    }

    public TypeSpec parse(Token token)
            throws Exception
    {
        TypeSpec setType = TypeFactory.createType(SET);
        token = nextToken();
        if(token.getType() != OF) {
            errorHandler.flag(token, MISSING_OF, this);
        }
        else {
            token = nextToken();
        }

        TypeSpec elementType = parseElementType(token);
        setType.setAttribute(SET_ELEMENT_TYPE, elementType);
        return setType;
    }
    
    
    private TypeSpec parseElementType(Token token)
            throws Exception
    {
        TypeSpecificationParser typeSpecificationParser =
                new TypeSpecificationParser(this);
        return typeSpecificationParser.parse(token);
    }
}