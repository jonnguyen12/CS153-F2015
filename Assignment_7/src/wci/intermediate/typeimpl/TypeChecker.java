package wci.intermediate.typeimpl;

import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;
import wci.intermediate.typeimpl.*;

import static wci.intermediate.typeimpl.TypeFormImpl.*;
import static wci.intermediate.typeimpl.TypeKeyImpl.*;

/**
 * <h1>TypeChecker</h1>
 *
 * <p>Perform type checking.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class TypeChecker
{

    /**
     * Check if a type specification is integer.
     * @param type the type specification to check.
     * @return true if integer, else false.
     */
    /*
    public static boolean isInteger(TypeSpec type)
    {
        return (type != null) && (type.baseType() == Predefined.integerType);
    }*/

    /**
     * Check if both type specifications are integer.
     * @param type1 the first type specification to check.
     * @param type2 the second type specification to check.
     * @return true if both are integer, else false.
     */
    /*
    public static boolean areBothInteger(TypeSpec type1, TypeSpec type2)
    {
        return isInteger(type1) && isInteger(type2);
    }*/

    /**
     * Check if two type specifications are assignment compatible.
     * @param targetType the target type specification.
     * @param valueType the value type specification.
     * @return true if the value can be assigned to the target, else false.
     */
    public static boolean areAssignmentCompatible(TypeSpec targetType,
                                                  TypeSpec valueType)
    {
        if ((targetType == null) || (valueType == null)) {
            return false;
        }

        targetType = targetType.baseType();
        valueType  = valueType.baseType();

        boolean compatible = false;

        // Identical types.
        if (targetType == valueType) {
            compatible = true;
        }

/*
        // real := integer
        else if (isReal(targetType) && isInteger(valueType)) {
            compatible = true;
        }
        // string := string
        else {
            compatible =
                targetType.isPascalString() && valueType.isPascalString();
        }
*/

        return compatible;
    }

    /**
     * Check if two type specifications are comparison compatible.
     * @param type1 the first type specification to check.
     * @param type2 the second type specification to check.
     * @return true if the types can be compared to each other, else false.
     */
    public static boolean areComparisonCompatible(TypeSpec type1,
                                                  TypeSpec type2)
    {
        if ((type1 == null) || (type2 == null)) {
            return false;
        }

        type1 = type1.baseType();
        type2 = type2.baseType();
        TypeForm form = type1.getForm();

        boolean compatible = false;

        // Two identical scalar or enumeration types.
        if ((type1 == type2) && (form == SCALAR)) {
            compatible = true;
        }

/*
        // Two strings.
        else {
            compatible = type1.isPascalString() && type2.isPascalString();
        }
*/
        return compatible;
    }
}