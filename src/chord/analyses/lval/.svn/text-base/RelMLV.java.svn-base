package chord.analyses.lval;

import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(
  name = "MLV",
  sign = "M0,LV0:M0_LV0"
)
public class RelMLV extends ProgramRel {
  @Override
  public void fill() {
	DomLV domLV = (DomLV) doms[1];
	for(Lvalue lv : domLV) {
      if(!lv.isStaic())
        add(((VarLvalue) lv).getMethod(), lv);
	}
  }
}
