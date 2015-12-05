package wci.backend.compiler;

import wci.intermediate.TypeSpec;
import wci.intermediate.symtabimpl.Predefined;

public class TypeCode {
    private static final String INTEGER_TYPECODE = "I";
    private static final String CHAR_TYPECODE = "Ljava/lang/String;";
    private static final String FLOAT_TYPECODE = "F";

    public static String typeSpecToTypeCode(TypeSpec spec) {
        if (spec == Predefined.charType) {
            return CHAR_TYPECODE;
        }
        else if (spec == Predefined.realType) {
            return FLOAT_TYPECODE;
        }
        else if (spec == Predefined.integerType) {
            return INTEGER_TYPECODE;
        }        
        else {
            throw new UnsupportedOperationException("That type is not yet implemented in the code generator!");
        }
    }
}