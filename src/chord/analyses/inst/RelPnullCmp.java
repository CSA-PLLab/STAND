package chord.analyses.inst;

import joeq.Class.jq_Class;
import joeq.Class.jq_Method;
import joeq.Compiler.Quad.Operand;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.Operand.ConditionOperand;
import joeq.Compiler.Quad.Operand.RegisterOperand;
import joeq.Compiler.Quad.Operator.IntIfCmp;
import joeq.Compiler.Quad.RegisterFactory.Register;
import chord.program.visitors.IIfCmpInstVisitor;
import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(
  name = "PnullCmp",
  sign = "P0,K0,V0:P0_K0_V0"
)

public class RelPnullCmp extends ProgramRel implements IIfCmpInstVisitor {

	@Override
	public void visitIfCmpInst(Quad q) {				
		Operand r1 = IntIfCmp.getSrc1(q);
		Operand r2 = IntIfCmp.getSrc2(q);
		ConditionOperand c = IntIfCmp.getCond(q);
		
		Register r;
		
		if((r1 instanceof RegisterOperand) && (Operand.Util.isNullConstant(r2))) 
			r = ((RegisterOperand) r1).getRegister();
		else if((r2 instanceof RegisterOperand) && (Operand.Util.isNullConstant(r1))) 
			r = ((RegisterOperand) r2).getRegister();
		else
			return;
		
		if (c.getCondition() == 0)
			add(q,0,r);		
		else if(c.getCondition() == 1)
			add(q,1,r);
	}

	@Override
	public void visit(jq_Method m) {}

	@Override
	public void visit(jq_Class c) {}

}
