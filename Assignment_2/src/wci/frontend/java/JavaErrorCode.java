package wci.frontend.java;

/**
 * Created by phucnguyen on 9/16/15.
 */
/**
 * Created by phucnguyen on 9/16/15.
 */
public enum JavaErrorCode
{
	ALREADY_DEFINED("Variable is already defined"),
	ARRAY_NOT_INITIALIZED("Array may not have been initialized"),
	CANT_BE_DEREFERENCED("Integer cant dereferenced"),
	CANNOT_FIND_SYMBOL("cannot find symbol"),
	CHAR_CANNOT_BE_DEREFERENCED("CHAR CANNOT BE DEREFERENCED"),
	CASE_CONSTANT_REUSED("CASE constant reused"),
	IDENTIFIER_REDEFINED("Redefined identifier"),
    IDENTIFIER_UNDEFINED("Undefined identifier"),
    INCOMPATIBLE_ASSIGNMENT("Incompatible assignment"),
    INCOMPATIBLE_TYPES("Incompatible types"),
    
    INVALID_ASSIGNMENT("Invalid assignment statement"),
    INVALID_CHARACTER("Invalid character"),
    INVALID_CONSTANT("Invalid constant"),
    INVALID_EXPONENT("Invalid exponent"),
    INVALID_EXPRESSION("Invalid expression"),
    INVALID_FIELD("Invalid field"),
    INVALID_FRACTION("Invalid fraction"),
    INVALID_IDENTIFIER_USAGE("Invalid identifier usage"),
    INVALID_INDEX_TYPE("Invalid index type"),
    INVALID_INTEGER("Invalid integer"),
    INVALID_LABLE("Invalid label"),
    INVALID_METHOD("Method not found"),
    INVALID_STATEMENT("Invalid statement"),
    INVALID_STRING("Invalid string"),
    INVALID_TYPE("Invalid type expression"),
    
    
    MISSING_COLON("Missing :"),
    
    MISSING_COMMA("Missing ,"),
   
    MISSING_FOR_CONTROL("Invalid FOR control variable"),
    MISSING_IDENTIFIER("Missing identifier"),
    MISSING_LEFT_BRACE("Missing {"),
    MISSING_LEFT_BRACKET("Missing ["),
    MISSING_LEFT_PAREN("Missing ("),
    MISSING_PERIOD("Missing ."),
    MISSING_RIGHT_BRACE("Missing }"),
    MISSING_RIGHT_BRACKET("Missing ]"),
    MISSING_RIGHT_PAREN("Missing )"),
    MISSING_SEMICOLON("Missing ;"),
    MISSING_TYPE("Missing identifier type"),
    MISSING_VARIABLE("Missing variable"),
    RANGE_INTEGER("Integer literal out of range"),
    RANGE_REAL("Real literal out of range"),
    STACK_OVERFLOW("Stack overflow"),
    UNEXPECTED_EOF("Unexpected end of file"),
    UNEXPECTED_TOKEN("Unexpected token"),
    UNIMPLEMENTED("Unimplemented feature"),
    UNRECOGNIZABLE("Unrecognizable input"),
    WRONG_NUMBER_OF_PARMS("Wrong number of actual parameters"),

    // Fatal errors.
    IO_ERROR(-101, "Object I/O error"),
    TOO_MANY_ERRORS(-102, "Too many syntax errors");

    private int status;      // exit status
    private String message;  // error message

    /**
     * Constructor.
     * @param message the error message.
     */
    JavaErrorCode(String message)
    {
        this.status = 0;
        this.message = message;
    }

    /**
     * Constructor.
     * @param status the exit status.
     * @param message the error message.
     */
    JavaErrorCode(int status, String message)
    {
        this.status = status;
        this.message = message;
    }

    /**
     * Getter.
     * @return the exit status.
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @return the message.
     */
    public String toString()
    {
        return message;
    }
}