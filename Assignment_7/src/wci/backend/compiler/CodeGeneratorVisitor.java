package wci.backend.compiler;

import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.Predefined;

import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;

public class CodeGeneratorVisitor
    extends CLikeVisitorAdapter
    implements CLikeParserTreeConstants
{
/*
    public Object visit(ASTAssignment node, Object data) {
    	String programName        = (String) data;
        SimpleNode variableNode   = (SimpleNode) node.jjtGetChild(0);
        SimpleNode expressionNode = (SimpleNode) node.jjtGetChild(1);

        // Emit code for the expression.
        expressionNode.jjtAccept(this, data);
        TypeSpec expressionType = expressionNode.getTypeSpec();

        // Get the assignment target type.
        TypeSpec targetType = node.getTypeSpec();

        // Convert an integer value to float if necessary.
        if ((targetType == Predefined.realType) &&
            (expressionType == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        SymTabEntry id = (SymTabEntry) variableNode.getAttribute(ID);
        String fieldName = id.getName();
        TypeSpec type = id.getTypeSpec();
        String typeCode = type == Predefined.integerType ? "I" : "F";

        // Emit the appropriate store instruction.
        CodeGenerator.objectFile.println("    putstatic " + programName +
        		                         "/" + fieldName + " " + typeCode);
        CodeGenerator.objectFile.flush();

        return data;
}
*/ 
public Object visit(ASTidentifier node, Object data) {
	String programName = (String) data;
    SymTabEntry id = (SymTabEntry) node.getAttribute(ID);
    String fieldName = id.getName();
    TypeSpec type = id.getTypeSpec();
    String typeCode = type == Predefined.integerType ? "I" : "F";

    // Emit the appropriate load instruction.
    CodeGenerator.objectFile.println("    getstatic " + programName +
            "/" + fieldName + " " + typeCode);
    CodeGenerator.objectFile.flush();

    return data;
}

    public Object visit(ASTintegerConstant node, Object data)
    {
        int value = (Integer) node.getAttribute(VALUE);

        // Emit a load constant instruction.
        CodeGenerator.objectFile.println("    ldc " + value);
        CodeGenerator.objectFile.flush();

        return data;
    }

    public Object visit(ASTrealConstant node, Object data)
    {
        float value = (Float) node.getAttribute(VALUE);

        // Emit a load constant instruction.
        CodeGenerator.objectFile.println("    ldc " + value);
        CodeGenerator.objectFile.flush();

        return data;
    }
    
    public Object visit(ASTString node, Object data) {
        String value = (String) node.getAttribute(VALUE);
        CodeGenerator.objectFile.println("      ldc " + value);
        CodeGenerator.objectFile.flush();

        return data;
    }


}
