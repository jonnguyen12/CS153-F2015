package wci.intermediate;


import wci.frontend.ASTAssignment;
import wci.frontend.ASTDataType;
import wci.frontend.ASTExpression;
import wci.frontend.ASTFunctionCall;
import wci.frontend.ASTFunctionDeclaration;
import wci.frontend.ASTIfStatment;
import wci.frontend.ASTOperators;
import wci.frontend.ASTProgram;
import wci.frontend.ASTSimpleStatement;
import wci.frontend.ASTStatements;
import wci.frontend.ASTString;
import wci.frontend.ASTadd;
import wci.frontend.ASTassignmentType;
import wci.frontend.ASTdivide;
import wci.frontend.ASTforLoop;
import wci.frontend.ASTidentifier;
import wci.frontend.ASTintegerConstant;
import wci.frontend.ASTmultiply;
import wci.frontend.ASTprintln;
import wci.frontend.ASTrealConstant;
import wci.frontend.ASTreturnStatement;
import wci.frontend.ASTsubtract;
import wci.frontend.ASTvariableDeclaration;
import wci.frontend.ASTwhileLoop;
import wci.frontend.CLikeParserVisitor;
import wci.frontend.SimpleNode;

public class CLikeVisitorAdapter implements CLikeParserVisitor
{
    public Object visit(SimpleNode node, Object data)
    {
        return node.childrenAccept(this, data);
    }
    
    public Object visit(ASTAssignment node, Object data)
    {
        return node.childrenAccept(this, data);
    }

	@Override
	public Object visit(ASTProgram node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTStatements node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}


	public Object visit(ASTvariableDeclaration node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTFunctionDeclaration node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTFunctionCall node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTSimpleStatement node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTIfStatment node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTwhileLoop node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTforLoop node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTreturnStatement node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTOperators node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTassignmentType node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTExpression node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTString node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTidentifier node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTDataType node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTintegerConstant node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTrealConstant node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTadd node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTsubtract node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTmultiply node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTdivide node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTprintln node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}


    
 
}
