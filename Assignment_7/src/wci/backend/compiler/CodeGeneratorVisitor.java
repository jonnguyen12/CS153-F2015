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
        SimpleNode variableNode   = (SimpleNode) node.jjtGetChild(0);
        SimpleNode expressionNode = (SimpleNode) node.jjtGetChild(1);

        expressionNode.jjtAccept(this, data);

        SymTabEntry id = (SymTabEntry) variableNode.getAttribute(ID);
        String fieldName = id.getName();
        TypeSpec type = id.getTypeSpec();
        String typeCode = TypeCode.typeSpecToTypeCode(type);     
        // Emit the appropriate store instruction.
        CodeGenerator.objectFile.println("      putstatic " + CodeGenerator.PROGRAM_HEADER_CLASS_NAME +
                "/" + fieldName + " " + typeCode);
        CodeGenerator.objectFile.flush();

        return data;
    }

public Object visit(ASTidentifier node, Object data) {
    SymTabEntry id = (SymTabEntry) node.getAttribute(ID);
    String fieldName = id.getName();
    TypeSpec typeSpc = id.getTypeSpec(); 
    String typeCode = TypeCode.typeSpecToTypeCode(typeSpc);

    String type = "";
    if(typeCode.equals("I")) {
    	type = "I";
    }
    else if(typeCode.equals("F")) {
    	type= "F";
    }
    else if (typeCode.equals("Ljava/lang/String;")) {
    	type = "Ljava/lang/String;";
    }    
    //String typeCode = type == Predefined.integerType ? "I" : "F";

    // Emit the appropriate load instruction.
    CodeGenerator.objectFile.println("    getstatic " + CodeGenerator.PROGRAM_HEADER_CLASS_NAME +
            "/" + fieldName + " " + type);
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


        String typeCode = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());  
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec());
        if(!typeCode.equals(typeCode2)) {
        	System.err.println("ERROR: TYPEMISSMATCH,TRYING TO MATCH TYPE: "+ typeCode + " to Type " + typeCode2);
        	typeCode = "ERROR";
        
        }               
        String type = "";
        if(typeCode.equals("I")) {
        	type = "i";
        }
        else if(typeCode.equals("F")) {
        	type= "f";
        }
        else if (typeCode.equals("Ljava/lang/String;")) {
        	type = "c";
        }

        if(type == "i" || type == "f") {
            addend0Node.jjtAccept(this, data);
            addend1Node.jjtAccept(this, data);
	        // Emit the appropriate add instruction.
	        CodeGenerator.objectFile.println("    " + type + "add");
	        CodeGenerator.objectFile.flush();
        }
        else if (type == "c") {
        	//String concatination code gen
        	CodeGenerator.objectFile.println("       new java/lang/StringBuilder");
        	CodeGenerator.objectFile.println("       dup");
            addend0Node.jjtAccept(this, data);
        	CodeGenerator.objectFile.println("       invokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;");
        	CodeGenerator.objectFile.println("       invokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V");   
            addend1Node.jjtAccept(this, data);        	
        	CodeGenerator.objectFile.println("       invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;");
        	CodeGenerator.objectFile.println("       invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;");        	
        }
        return data;

    }

    public Object visit(ASTsubtract node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);


        String typeCode = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec());
        if(!typeCode.equals(typeCode2)) {
        	System.err.println("ERROR: TYPEMISSMATCH,TRYING TO MATCH TYPE: "+ typeCode + " to Type " + typeCode2);
        	typeCode = "ERROR";
        
        }        
        String type = "";
        if(typeCode.equals("I")) {
        	type = "i";
        }
        else if(typeCode.equals("F")) {
        	type= "f";
        }
        else {
        	throw new UnsupportedOperationException("Invalid type for Subtraction");
        }
        if(type == "i" || type == "f") {
            addend0Node.jjtAccept(this, data);
            addend1Node.jjtAccept(this, data);
	        // Emit the appropriate add instruction.
	        CodeGenerator.objectFile.println("    " + type + "sub");
	        CodeGenerator.objectFile.flush();
        }
        return data;
    }

    public Object visit(ASTmultiply node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);

        String typeCode = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());   
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec()); 
        //type check
        if(!typeCode.equals(typeCode2)) {
        	System.err.println("ERROR: TYPEMISSMATCH,TRYING TO MATCH TYPE: "+ typeCode + " to Type " + typeCode2);
        	typeCode = "ERROR";
        
        }
        String type = "";
        if(typeCode.equals("I")) {
        	type = "i";
        }
        else if(typeCode.equals("F")) {
        	type= "f";
        }
        else {
        	throw new UnsupportedOperationException("Invalid type for Multiplucation");
        }
        if(type == "i" || type == "f") {
            addend0Node.jjtAccept(this, data);
            addend1Node.jjtAccept(this, data);
	        // Emit the appropriate add instruction.
	        CodeGenerator.objectFile.println("    " + type + "mul");
	        CodeGenerator.objectFile.flush();
        }


        return data;
    }

    public Object visit(ASTdivide node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);

        String typeCode = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());   
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec());
        if(!typeCode.equals(typeCode2)) {
        	System.err.println("ERROR: TYPEMISSMATCH,TRYING TO MATCH TYPE: "+ typeCode + " to Type " + typeCode2);
        	typeCode = "ERROR";
        
        }                 
        String type = "";
        if(typeCode.equals("I")) {
        	type = "i";
        }
        else if(typeCode.equals("F")) {
        	type= "f";
        }
        else {
        	throw new UnsupportedOperationException("Invalid type for Division");
        }

        if(type == "i" || type == "f") {
            addend0Node.jjtAccept(this, data);
            addend1Node.jjtAccept(this, data);
	        // Emit the appropriate add instruction.
	        CodeGenerator.objectFile.println("    " + type + "div");
	        CodeGenerator.objectFile.flush();
        }


        return data;
    }    

    
    public Object visit(ASTprintln node, Object data) {

      SimpleNode nodeToPrint = (SimpleNode) node.jjtGetChild(0);

      String typePrefix = TypeCode.typeSpecToTypeCode(nodeToPrint.getTypeSpec());
      SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
      SymTabEntry id = (SymTabEntry) addend0Node.getAttribute(ID);

      if(typePrefix.equals("F")) {
    	  	//generate code for printing an identifier to a float
      		if(nodeToPrint.toString().equals("identifier")) {
      			genFloatPrint(id.getName(), 0f, data);
      		}
      		//generate code for printing number literal
      		else if(nodeToPrint.toString().equals("realConstant")) {
      			float val = Float.parseFloat( nodeToPrint.getAttribute(VALUE).toString() );
      			genFloatPrint(null, val, data);
      		}
      }
      else if(typePrefix.equals("I")) {
    		if(nodeToPrint.toString().equals("identifier")) {
    			genIntegerPrint(id.getName(), 0, data);
    		}
    		//generate code for printing number literal
    		else if(nodeToPrint.toString().equals("integerConstant")) {

    			int val = Integer.parseInt( nodeToPrint.getAttribute(VALUE).toString() );
    			genIntegerPrint(null, val, data);
    		}
    }      
      
      else if(typePrefix.equals("Ljava/lang/String;")) {
    	  if(nodeToPrint.toString().equals("identifier")) {
    			genStringPrint(id.getName(), "", data);
    	  }
    	  else if(nodeToPrint.toString().equals("String")) {
    		  String val = nodeToPrint.getAttribute(VALUE).toString();
    		  genStringPrint(null, val, data);
    	  }
      }
      else {
    	  throw new IllegalArgumentException("That data type is not supported for printing.");
    	  
      }

      return data;
  }

    public Object genStringPrint(String id, String val, Object data) {
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
    public Object genFloatPrint(String id, float value, Object data ) {
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
    public Object genIntegerPrint(String id, float value, Object data ) {
    	if(id != null) {
    		CodeGenerator.objectFile.println("       getstatic    java/lang/System/out Ljava/io/PrintStream;");
    		CodeGenerator.objectFile.println("       getstatic     CLikeProgram/"+id+" I");
    		CodeGenerator.objectFile.println("       invokevirtual java/io/PrintStream.println(I)V");
    	}
    	else {
    		CodeGenerator.objectFile.println("       getstatic    java/lang/System/out Ljava/io/PrintStream;");
    		CodeGenerator.objectFile.println("       ldc "+value);
    		CodeGenerator.objectFile.println("       invokevirtual java/io/PrintStream.println(I)V");
    	}
    	return data;
    }        
    
    
}