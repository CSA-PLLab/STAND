package chord.analyses.inst;

import joeq.Class.jq_Class;
import joeq.Class.jq_Field;
import joeq.Class.jq_Method;
import joeq.Compiler.Quad.Operand;
import joeq.Compiler.Quad.Operator;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.Operand.RegisterOperand;
import joeq.Compiler.Quad.Operator.Putfield;
import joeq.Compiler.Quad.RegisterFactory.Register;
import chord.program.visitors.IHeapInstVisitor;
import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(
	name="PputInstFldNullInst",
	sign="P0,V0,F0:P0_V0_F0"
)

public class RelPputInstFldNullInst extends ProgramRel implements IHeapInstVisitor {

	@Override
	public void visitHeapInst(Quad q) {
		Operator op = q.getOperator();
		if (op instanceof Putfield) {
			jq_Field f = Putfield.getField(q).getField();
			if (f.getType().isReferenceType()) {
				Operand rx = Putfield.getSrc(q);				
				if (Operand.Util.isNullConstant(rx)) {					
					RegisterOperand bo = (RegisterOperand) Putfield.getBase(q);
					Register b = bo.getRegister();					
					add(q, b, f);
				}
			}
		}
		
	}

	@Override
	public void visit(jq_Method m) {}

	@Override
	public void visit(jq_Class c) {}
	

}
