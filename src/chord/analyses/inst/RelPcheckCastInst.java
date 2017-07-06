package chord.analyses.inst;

import joeq.Class.jq_Class;
import joeq.Class.jq_Method;
import joeq.Compiler.Quad.Operand;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.Operand.RegisterOperand;
import joeq.Compiler.Quad.Operator.CheckCast;
import joeq.Compiler.Quad.RegisterFactory.Register;
import chord.program.visitors.ICastInstVisitor;
import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(
	name = "PcheckCastInst",
	sign = "P0,V0,V1:P0_V0xV1"
)

public class RelPcheckCastInst extends ProgramRel implements ICastInstVisitor {

	@Override
	public void visitCastInst(Quad q) {
		Operand rx = CheckCast.getSrc(q);
		if (rx instanceof RegisterOperand) {
			RegisterOperand ro = (RegisterOperand) rx;
			if (ro.getType().isReferenceType()) {
				Register r = ro.getRegister();
				RegisterOperand lo = CheckCast.getDest(q);
				Register l = lo.getRegister();
				add(q, l, r);
			}
		}		
	}

	@Override
	public void visit(jq_Method m) {}

	@Override
	public void visit(jq_Class c) {}
}
