package chord.analyses.lval;

import chord.project.Chord;
import chord.project.analyses.ProgramRel;

@Chord(name = "VarLV", sign = "LV0")
public class RelVarLV extends ProgramRel {
  @Override
  public void fill() {
    DomLV domLV = (DomLV) doms[0];
    for (Lvalue lv : domLV)
      if (!lv.isStaic())
        add(lv);
  }
}
