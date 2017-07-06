package chord.analyses.invk;

import joeq.Class.jq_Method;
import joeq.Compiler.Quad.Operator;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.Operand.ParamListOperand;
import joeq.Compiler.Quad.Operand.RegisterOperand;
import joeq.Compiler.Quad.Operator.Invoke;
import joeq.Compiler.Quad.RegisterFactory.Register;
import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(
	name = "initIV",
	sign = "I0,V0:I0_V0"
)
public class RelInitIV extends ProgramRel {
	public void fill() {
		DomI domI = (DomI) doms[0];
		int numI = domI.size();
		for(int i = 0; i < numI; i++) {
			Quad q = domI.get(i);
			Operator op = q.getOperator();
			if(op instanceof Invoke) {
				jq_Method m = Invoke.getMethod(q).getMethod();
				if(m.getName().toString().equals("<init>")) {
					ParamListOperand l = Invoke.getParamList(q);
					RegisterOperand vo = l.get(0);
					Register v = vo.getRegister();
					if (v.getType().isReferenceType()) {
						add(q, v);
					}
				}
			}
		}
	}

}
