package wci.intermediate;

import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;

public class TypeSetterVisitor extends CLikeVisitorAdapter
{
    private void setType(SimpleNode node)
    {
        int count = node.jjtGetNumChildren();
        TypeSpec type = Predefined.integerType;
        
        for (int i = 0; (i < count) && (type == Predefined.integerType); ++i) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            TypeSpec childType = child.getTypeSpec();
            
            if (childType == Predefined.realType) {
                type = Predefined.realType;
            }
        }
        
        node.setTypeSpec(type);
    }
    
    public Object visit(ASTAssignment node, Object data)
    {
        Object obj = super.visit(node, data);
        setType(node);
        return obj;
    }

 
}
