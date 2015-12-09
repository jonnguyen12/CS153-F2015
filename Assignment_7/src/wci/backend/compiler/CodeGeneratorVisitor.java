package wci.backend.compiler;

import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.Predefined;

import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;

public class CodeGeneratorVisitor
    extends CLikeVisitorAdapter
    implements CLikeParserTreeConstants
{
	//labels for looping and conditions
	int label_count = 0;
	int empty_count = 0;
	int loop_count = 0;
	String label_suffix = "Label";

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


        String typeCode = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec());  
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());           
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
        else if(typeCode2.equals("more")) {
            addend1Node.jjtAccept(this, data);           	
        }
        else if(typeCode.equals("more")) {
            addend0Node.jjtAccept(this, data);           	
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
        //not terminal (more +,-,/,*)
        else {
            addend1Node.jjtAccept(this, data);   
        }
        return data;
    }

    public Object visit(ASTsubtract node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);


        String typeCode = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec());  
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());           
        String type = "";
        if(typeCode.equals("I")) {
        	type = "i";
        }
        else if(typeCode.equals("F")) {
        	type= "f";
        }
        else if(typeCode2.equals("more")) {
            addend1Node.jjtAccept(this, data);           	
        }
        else if(typeCode.equals("more")) {
           addend0Node.jjtAccept(this, data);           	
        }        

        if(type == "i" || type == "f") {
            addend0Node.jjtAccept(this, data);
            addend1Node.jjtAccept(this, data);
	        // Emit the appropriate sub instruction.
	        CodeGenerator.objectFile.println("    " + type + "sub");
	        CodeGenerator.objectFile.flush();
        }
        //not terminal (more +,-,/,*)

        return data;
    }

    public Object visit(ASTmultiply node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);


        String typeCode = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec());  
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());           
        String type = "";
        if(typeCode.equals("I")) {
        	type = "i";
        }
        else if(typeCode.equals("F")) {
        	type= "f";
        }
        else if(typeCode2.equals("more")) {
            addend1Node.jjtAccept(this, data);           	
        }
        else if(typeCode.equals("more")) {
           addend0Node.jjtAccept(this, data);           	
        }        

        if(type == "i" || type == "f") {
            addend0Node.jjtAccept(this, data);
            addend1Node.jjtAccept(this, data);
	        // Emit the appropriate sub instruction.
	        CodeGenerator.objectFile.println("    " + type + "mul");
	        CodeGenerator.objectFile.flush();
        }
        //not terminal (more +,-,/,*)

        return data;
    }

    public Object visit(ASTdivide node, Object data)
    {
        SimpleNode addend0Node = (SimpleNode) node.jjtGetChild(0);
        SimpleNode addend1Node = (SimpleNode) node.jjtGetChild(1);


        String typeCode = TypeCode.typeSpecToTypeCode(addend1Node.getTypeSpec());  
        String typeCode2 = TypeCode.typeSpecToTypeCode(addend0Node.getTypeSpec());           
        String type = "";
        if(typeCode.equals("I")) {
        	type = "i";
        }
        else if(typeCode.equals("F")) {
        	type= "f";
        }
        else if(typeCode2.equals("more")) {
            addend1Node.jjtAccept(this, data);           	
        }
        else if(typeCode.equals("more")) {
           addend0Node.jjtAccept(this, data);           	
        }        

        if(type == "i" || type == "f") {
            addend0Node.jjtAccept(this, data);
            addend1Node.jjtAccept(this, data);
	        // Emit the appropriate sub instruction.
	        CodeGenerator.objectFile.println("    " + type + "div");
	        CodeGenerator.objectFile.flush();
        }
        //not terminal (more +,-,/,*)

        return data;
    }    

    public Object visit(ASTCompound_Statment node, Object data) {
    	System.out.println("VISITED COMPUND STMT");
    	for(int i = 0; i < node.jjtGetNumChildren(); i++) {
    		SimpleNode curr = (SimpleNode) node.jjtGetChild(i);
    		curr.jjtAccept(this, data);
    		System.out.println(i);
    	}
    	System.out.println("EXITED COMPOUND STMT");
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
  
    public Object visit(ASTboolean_op node, Object data) {
    	String boolOpString = "       ";
    	SimpleNode opNode = (SimpleNode) node.jjtGetChild(0);
    	String op = opNode.toString();
    	System.out.println("OP "+op);
        System.out.println(opNode);
    	if(op.equals("less_than")) {
    		// if A < B push -1 on stack   [B,A]
    		//boolOpString = "fcmpl \n";
    		// iflt pops the top int off the operand stack. If the int is less than zero
    		boolOpString += "if_icmplt "+label_suffix + ++label_count;
    	}
    	else if(op.equals("greater_than")) {
    		//boolOpString = "fcmpg \n"; // pushes 1 if A > b
    		// branches if val > 0 
    		boolOpString += "if_icmpgt "+label_suffix + ++label_count;
    	}
    	else if(op.equals("less_than_or_equals")) {
    		//boolOpString = "fcmpl \n";
    		boolOpString += "if_icmple "+label_suffix + ++label_count;
    	}
    	else if(op.equals("greater_than_or_equals")) {
    		//boolOpString = "fcmpg \n";
    		boolOpString += "if_icmpge "+label_suffix+ ++label_count;
    	}
    	else if(op.equals("equality")) {
    		System.out.println("equality op");
    		//boolOpString = "fcmpg \n";
    		boolOpString += "if_icmpeq "+label_suffix+ ++label_count;
    	}
    	else if(op.equals("not_equals")) {
    		//boolOpString = "fcmpg \n";
    		boolOpString += "if_icmpne "+label_suffix+ ++label_count;
    	}
    	CodeGenerator.objectFile.println(boolOpString);

    	//CodeGenerator.objectFile.println("       iconst_0");
    	SimpleNode op_node = (SimpleNode) node;
    	System.out.println("IS WHILE: "+op_node.getAttribute(IS_WHILE));
    	if( ! (boolean) op_node.getAttribute(IS_WHILE)) {
    		int newLabel = label_count+1;
    		CodeGenerator.objectFile.println("       goto " +label_suffix + newLabel);
    	}
    	return data;
    }    
    
    public Object visit(ASTcondition node, Object data) {
    	SimpleNode exp1 = (SimpleNode) node.jjtGetChild(0);
    	SimpleNode op = (SimpleNode) node.jjtGetChild(1);
    	SimpleNode exp2 = (SimpleNode) node.jjtGetChild(2);
    	
    	exp1.jjtAccept(this, data); //generate expression code 
    	// fsore_0
    	CodeGenerator.objectFile.println("       istore_0");
    	exp2.jjtAccept(this, data); // generate expression code
    	//fsore_1
    	CodeGenerator.objectFile.println("       istore_1");
    	//load B
    	CodeGenerator.objectFile.println("       iload_0");
    	//load A
    	CodeGenerator.objectFile.println("       iload_1");
    	op.jjtAccept(this, data); //  [B,A] create a branch if_icmplt L003  ; branch if i < j
    	return data;
    }    
    
    public Object visit(ASTIfStatment node, Object data) {

    	SimpleNode condition = (SimpleNode) node.jjtGetChild(0).jjtGetChild(0);
    	SimpleNode branch1 = (SimpleNode) node.jjtGetChild(0).jjtGetChild(1);
    	condition.jjtAccept(this, data); //parse condition set labels 
    	int preLabel = label_count;
    	CodeGenerator.objectFile.println(label_suffix  + (preLabel) + ":");
    	branch1.jjtAccept(this, data);
    	// skip all the else
    	//CodeGenerator.objectFile.println("       goto " +label_suffix + (1+label_count));

    	
    	//CodeGenerator.objectFile.println(label_suffix +  + (label_count) + ":");

    	//check for else part for now
    	//int numbOfChildren = node.jjtGetNumChildren();
    	//if(numbOfChildren > 1) // not a single if statement
    	//{
    	//	for(int i = 1; i < numbOfChildren; i++ ) {
    	//		SimpleNode ifChild = (SimpleNode) node.jjtGetChild(i);
    	//		ifChild.jjtAccept(this, data);
    	//	}
    	//}
    	CodeGenerator.objectFile.println(label_suffix +  + (++label_count) + ":");

    	return data;
    }   
    
    public Object visit(ASTIf_Body node, Object data) {
    	if(node.jjtGetNumChildren() > 0) {
	    	SimpleNode body = (SimpleNode) node.jjtGetChild(0);
	    	body.jjtAccept(this, data);
    	}   	
    	return data;
    }
    public Object visit(ASTwhileLoop node, Object data) {
    	System.out.println("WHILE has "+node.jjtGetNumChildren());
    	SimpleNode condition = (SimpleNode) node.jjtGetChild(0);
    	
    	CodeGenerator.objectFile.println("loop" + loop_count++ + ": ");

    	((SimpleNode) condition.jjtGetChild(1)).setAttribute(IS_WHILE, true);
    	condition.jjtAccept(this, data);
//    	generate_code_for_while_condition(condition, data);
    	
    	CodeGenerator.objectFile.println("goto "+"Empty"+ empty_count++);
    	CodeGenerator.objectFile.println(label_suffix+(label_count)+":");
    	SimpleNode body = (SimpleNode) node.jjtGetChild(1);
    	body.jjtAccept(this, data);
    	//CodeGenerator.objectFile.println("goto "+label_suffix+label_count);
    	CodeGenerator.objectFile.println("goto loop" + --loop_count);
    	CodeGenerator.objectFile.println("Empty"+ (--empty_count) +":");
    	return data;
    }    
}