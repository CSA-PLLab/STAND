package chord.analyses.inst;

import joeq.Class.jq_Class;
import joeq.Class.jq_Method;
import joeq.Compiler.Quad.Operand;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.Operand.RegisterOperand;
import joeq.Compiler.Quad.Operator.Move;
import joeq.Compiler.Quad.RegisterFactory.Register;
import chord.program.visitors.IMoveInstVisitor;
import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(
  name = "PobjNullAsgnInst",
  sign = "P0,V0:P0_V0"
)

public class RelPobjNullAsgnInst extends ProgramRel implements IMoveInstVisitor {
	
	// private jq_Method currMethod;

	@Override
	public void visitMoveInst(Quad q) {
		Operand rx = Move.getSrc(q);
		if(Operand.Util.isNullConstant(rx)) {
			RegisterOperand lo = Move.getDest(q);
			Register l = lo.getRegister();
			add(q, l);
		}
	}

	@Override
	public void visit(jq_Method m) {
		//currMethod = m;
	}

	@Override
	public void visit(jq_Class c) {}
	

}
