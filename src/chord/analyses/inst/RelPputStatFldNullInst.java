package chord.analyses.inst;

import joeq.Class.jq_Class;
import joeq.Class.jq_Field;
import joeq.Class.jq_Method;
import joeq.Compiler.Quad.Operand;
import joeq.Compiler.Quad.Operator;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.Operator.Putstatic;
import chord.program.visitors.IHeapInstVisitor;
import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(
	name = "PputStatFldNullInst",
	sign = "P0,F0:P0_F0"
)

public class RelPputStatFldNullInst extends ProgramRel implements IHeapInstVisitor {

	@Override
	public void visitHeapInst(Quad q) {
		Operator op = q.getOperator();
		if (op instanceof Putstatic) {
			jq_Field f = Putstatic.getField(q).getField();
			if (f.getType().isReferenceType()) {
				Operand rx = Putstatic.getSrc(q);
				if(Operand.Util.isNullConstant(rx)) {					
					add(q, f);
				}
			}
		}
	}

	@Override
	public void visit(jq_Method m) {}

	@Override
	public void visit(jq_Class c) {}

}
