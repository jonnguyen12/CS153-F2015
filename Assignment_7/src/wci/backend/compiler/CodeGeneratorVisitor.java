package wci.backend.compiler;

import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.Predefined;

import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;

public class CodeGeneratorVisitor
    extends CLikeVisitorAdapter
    implements CLikeParserTreeConstants
{

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
        CodeGenerator.objectFile.println("    putstatic " + CodeGenerator.PROGRAM_HEADER_CLASS_NAME +
        		                         "/" + fieldName + " " + typeCode);
        CodeGenerator.objectFile.flush();

        return data;
}

public Object visit(ASTidentifier node, Object data) {
	String programName = (String) data;
    SymTabEntry id = (SymTabEntry) node.getAttribute(ID);
    String fieldName = id.getName();
    TypeSpec type = id.getTypeSpec();
    String typeCode = type == Predefined.integerType ? "I" : "F";

    // Emit the appropriate load instruction.
    CodeGenerator.objectFile.println("    getstatic " + CodeGenerator.PROGRAM_HEADER_CLASS_NAME +
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

    public Object visit(ASTadd node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);

        TypeSpec type0 = addend0Node.getTypeSpec();
        TypeSpec type1 = addend1Node.getTypeSpec();

        // Get the addition type.
        TypeSpec type = node.getTypeSpec();
        String typePrefix = (type == Predefined.integerType) ? "i" : "f";

        // Emit code for the first expression
        // with type conversion if necessary.
        addend0Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type0 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit code for the second expression
        // with type conversion if necessary.
        addend1Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type1 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit the appropriate add instruction.
        CodeGenerator.objectFile.println("    " + typePrefix + "add");
        CodeGenerator.objectFile.flush();

        return data;
    }

    public Object visit(ASTsubtract node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);

        TypeSpec type0 = addend0Node.getTypeSpec();
        TypeSpec type1 = addend1Node.getTypeSpec();

        // Get the addition type.
        TypeSpec type = node.getTypeSpec();
        String typePrefix = (type == Predefined.integerType) ? "i" : "f";

        // Emit code for the first expression
        // with type conversion if necessary.
        addend0Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type0 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit code for the second expression
        // with type conversion if necessary.
        addend1Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type1 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit the appropriate add instruction.
        CodeGenerator.objectFile.println("    " + typePrefix + "sub");
        CodeGenerator.objectFile.flush();

        return data;
    }

    public Object visit(ASTmultiply node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);

        TypeSpec type0 = addend0Node.getTypeSpec();
        TypeSpec type1 = addend1Node.getTypeSpec();

        // Get the addition type.
        TypeSpec type = node.getTypeSpec();
        String typePrefix = (type == Predefined.integerType) ? "i" : "f";

        // Emit code for the first expression
        // with type conversion if necessary.
        addend0Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type0 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit code for the second expression
        // with type conversion if necessary.
        addend1Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type1 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit the appropriate add instruction.
        CodeGenerator.objectFile.println("    " + typePrefix + "mul");
        CodeGenerator.objectFile.flush();

        return data;
    }

    public Object visit(ASTdivide node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);

        TypeSpec type0 = addend0Node.getTypeSpec();
        TypeSpec type1 = addend1Node.getTypeSpec();

        // Get the addition type.
        TypeSpec type = node.getTypeSpec();
        String typePrefix = (type == Predefined.integerType) ? "i" : "f";

        // Emit code for the first expression
        // with type conversion if necessary.
        addend0Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type0 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit code for the second expression
        // with type conversion if necessary.
        addend1Node.jjtAccept(this, data);
        if ((type == Predefined.realType) &&
            (type1 == Predefined.integerType))
        {
            CodeGenerator.objectFile.println("    i2f");
            CodeGenerator.objectFile.flush();
        }

        // Emit the appropriate add instruction.
        CodeGenerator.objectFile.println("    " + typePrefix + "div");
        CodeGenerator.objectFile.flush();

        return data;
    }    

    
    public Object visit(ASTprintln node, Object data) {
//      CodeGenerator.objectFile.println("      getstatic java/lang/System/out Ljava/io/PrintStream;");

      SimpleNode nodeToPrint = (SimpleNode) node.jjtGetChild(0);
//     System.out.println (nodeToPrint.toString());
      String typePrefix = TypeCode.typeSpecToTypeCode(nodeToPrint.getTypeSpec());
      SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
      SymTabEntry id = (SymTabEntry) addend0Node.getAttribute(ID);
//      System.out.println( "name " + id.getName());
//      System.out.println( id.getAttribute());
      if(typePrefix.equals("F")) {
    	  
//      	generate_float_print_code(nodeToPrint, data);
    	  	//generate code for printing an identifier to a float
      		if(nodeToPrint.toString().equals("identifier")) {
      			generate_float_print_code(id.getName(), 0f, data);
      		}
      		//generate code for printing number literal
      		else if(nodeToPrint.toString().equals("number")) {
//      			System.out.println("NUMBER!!!!!!!!!!!!");
//      			System.out.println("value "+nodeToPrint.getAttribute(VALUE).toString());
      			float val = Float.parseFloat( nodeToPrint.getAttribute(VALUE).toString() );
      			generate_float_print_code(null, val, data);
      		}
      }
      if(typePrefix.equals("Ljava/lang/String;")) {
    	  System.out.println("FOUND A STRING!!!!!");
    	  if(nodeToPrint.toString().equals("identifier")) {
    			System.out.println("found string var!!!!!");
    			generate_string_print_code(id.getName(), "", data);
    	  }
    	  else if(nodeToPrint.toString().equals("string")) {
    		  System.out.println("found string literal!!!!!");
    		  String val = nodeToPrint.getAttribute(VALUE).toString();
    		  generate_string_print_code(null, val, data);
    	  }
      }

      return data;
  }

    public Object generate_string_print_code(String id, String val, Object data) {
    	if(id != null) {

  		CodeGenerator.objectFile.println("       getstatic    java/lang/System/out Ljava/io/PrintStream;");
  		CodeGenerator.objectFile.println("       getstatic     CLikeProgram/"+id+" Ljava/lang/String;");
  		CodeGenerator.objectFile.println("       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V");
      	}
      	else {
      		CodeGenerator.objectFile.println("       getstatic    java/lang/System/out Ljava/io/PrintStream;");
      		CodeGenerator.objectFile.println("       ldc "+val);
      		CodeGenerator.objectFile.println("       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V");
      	}
      	return data;
    }
    public Object generate_float_print_code(String id, float value, Object data ) {
    	if(id != null) {
    		CodeGenerator.objectFile.println("       getstatic    java/lang/System/out Ljava/io/PrintStream;");
    		CodeGenerator.objectFile.println("       getstatic     CLikeProgram/"+id+" F");
    		CodeGenerator.objectFile.println( "      invokestatic  java/lang/String.valueOf(F)Ljava/lang/String;");
    		CodeGenerator.objectFile.println("       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V");
    	}
    	else {
    		CodeGenerator.objectFile.println("       getstatic    java/lang/System/out Ljava/io/PrintStream;");
    		CodeGenerator.objectFile.println("       ldc "+value);
    		CodeGenerator.objectFile.println( "      invokestatic  java/lang/String.valueOf(F)Ljava/lang/String;");
    		CodeGenerator.objectFile.println("       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V");
    	}
    	return data;
    }    
    
    
    
}
