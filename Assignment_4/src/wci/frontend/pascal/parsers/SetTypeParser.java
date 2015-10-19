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

    // Synchronization set for Set type
    static final EnumSet<PascalTokenType> SET_START_SET = EnumSet.of(SEMICOLON);


//    public TypeSpec parse(Token token)
//            throws Exception {
//        TypeSpec setType = TypeFactory.createType(SET);
//        token = nextToken();
//
//        if (token.getType() != OF) {
//            errorHandler.flag(token, MISSING_OF, this);
//        } else {
//            token = nextToken();
//        }
//
//        TypeSpec elementType = parseElementType(token);
//        setType.setAttribute(SET_ELEMENT_TYPE, elementType);
//        return setType;
//    }
//
//
//    private TypeSpec parseElementType(Token token)
//            throws Exception {
//        TypeSpecificationParser typeSpecificationParser =
//                new TypeSpecificationParser(this);
//        return typeSpecificationParser.parse(token);
//    }

    public TypeSpec parse(Token token ) throws Exception {
        TypeSpec setType = TypeFactory.createType(SET);
        token = nextToken();

        if (token.getType() == PascalTokenType.OF) {
            token = nextToken();


            switch ((PascalTokenType) token.getType()) {
                case IDENTIFIER:
                    String name = token.getText().toLowerCase();
                    SymTabEntry id = symTabStack.lookup(name);

                    if (id != null) {
                        Definition definition = id.getDefinition();
                        if (definition == DefinitionImpl.TYPE) {
                            id.appendLineNumber(token.getLineNumber());
                            token = nextToken();
                            setType.setAttribute(BASE_TYPE, id.getTypeSpec());

                            return setType;
                        } else {
                            token = synchronize(SET_START_SET);
                            return null;
                        }
                    } else {
                        token = synchronize(SET_START_SET);
                        return null;
                    }
                case INTEGER:
                    SubrangeTypeParser subrangeTypeParser = new SubrangeTypeParser(this);
                    TypeSpec subrange = subrangeTypeParser.parse(token);
                    setType.setAttribute(NAMELESS_SET_TYPE, subrange);
                    return setType;
                case LEFT_PAREN:
                    EnumerationTypeParser enumerationTypeParser = new EnumerationTypeParser(this);
                    TypeSpec enumeration = enumerationTypeParser.parse(token);
                    setType.setAttribute(NAMELESS_SET_TYPE, enumeration);
                    return setType;
                default:
                    synchronize(SET_START_SET);
                    return null;
            }
        }
        else {
            token = synchronize(SET_START_SET);
            errorHandler.flag(token, MISSING_OF, this);
            return null;
        }
    }
}