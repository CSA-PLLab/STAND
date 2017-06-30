package chord.analyses.lval;

import joeq.Class.jq_Field;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.RegisterFactory.Register;
import chord.bddbddb.Rel.QuadIterable;
import chord.bddbddb.Rel.TrioIterable;
import chord.project.Chord;
import chord.project.ClassicProject;
import chord.project.analyses.JavaAnalysis;
import chord.project.analyses.ProgramRel;
import chord.util.tuple.object.Trio;

@Chord(
  name = "tempMap",
  consumes = {"PgetInstFldInst", "PgetStatFldInst"},
  produces = {"tempToVF", "tempToS"},
  namesOfSigns = {"tempToVF", "tempToS"},
  signs = {"V0,V1,F0:V0xV1_F0", "V0,F0:V0_F0"}
)

public class TempMap extends JavaAnalysis {
  @Override
  public void run() {
	ClassicProject project = ClassicProject.g();
	
    ProgramRel gfInstRel = (ProgramRel) project.getTrgt("PgetInstFldInst");
    ProgramRel tvf = (ProgramRel) project.getTrgt("tempToVF");
    
    tvf.zero();
    gfInstRel.load();       
    QuadIterable<Quad,Register,jq_Field,Register> qSet = gfInstRel.getAry4ValTuples();
    for(chord.util.tuple.object.Quad<Quad,Register,jq_Field,Register> q  : qSet) {
      Register r1 = q.val1;
      Register r2 = q.val3;
      jq_Field f = q.val2;
      if(r1.isTemp() && r1.getType().isReferenceType() && !r2.isTemp()) {
    	tvf.add(r1,r2,f);
      }
    }    
    gfInstRel.close();
    tvf.save();
    
    ProgramRel gfStatRel = (ProgramRel) project.getTrgt("PgetStatFldInst");
    ProgramRel ts = (ProgramRel) project.getTrgt("tempToS");
    
    ts.zero();
    gfStatRel.load();
    TrioIterable<Quad,Register,jq_Field> tSet = gfStatRel.getAry3ValTuples();
    for(Trio<Quad,Register,jq_Field> t : tSet) {
      Register r = t.val1;
      if(r.isTemp() && r.getType().isReferenceType())
        ts.add(t.val1, t.val2);	
    }
    gfStatRel.close();
    ts.save();
  }
}
