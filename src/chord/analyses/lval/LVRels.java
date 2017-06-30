package chord.analyses.lval;

import java.util.Set;

import joeq.Class.jq_Field;
import joeq.Compiler.Quad.RegisterFactory.Register;

import chord.project.Chord;
import chord.project.ClassicProject;
import chord.project.analyses.JavaAnalysis;
import chord.project.analyses.ProgramRel;
import chord.util.tuple.object.Trio;

@Chord(
  name = "LVRels",
  consumes = {"LV"},
  produces = {"VFLV", "SFLV", "LVFLV"},
  namesOfSigns = {"VFLV", "SFLV", "LVFLV"},
  signs = {"V0,F0,LV0:V0_F0_LV0", "F0,F1,LV0:F0_F1_LV0", "LV0,F0,LV1:LV0_F0_LV1"}
)

public class LVRels extends JavaAnalysis {
  @Override
  public void run() {
    DomLV domLV = (DomLV) ClassicProject.g().getTrgt("LV");
    ProgramRel relVFLV = (ProgramRel) ClassicProject.g().getTrgt("VFLV");
    ProgramRel relSFLV = (ProgramRel) ClassicProject.g().getTrgt("SFLV");
    ProgramRel relLVFLV = (ProgramRel) ClassicProject.g().getTrgt("LVFLV");
    
    relVFLV.zero();
    relSFLV.zero();
    relLVFLV.zero();
    
    Set<Trio<Register,jq_Field,VarLvalue>> vLvRelSet = domLV.vLvRelSet;
    Set<Trio<VarLvalue,jq_Field,VarLvalue>> vlvLvRelSet = domLV.vlvLvRelSet;
    Set<Trio<jq_Field,jq_Field,StatLvalue>> sfLvRelSet = domLV.sfLvRelSet;
    Set<Trio<StatLvalue,jq_Field,StatLvalue>> slvLvRelSet = domLV.slvLvRelSet;
    
    for(Trio<Register, jq_Field, VarLvalue> ar : vLvRelSet) {      
  	  relVFLV.add(ar.val0, ar.val1, ar.val2);     
  	}
    
    for(Trio<jq_Field, jq_Field, StatLvalue> ar : sfLvRelSet) {      
      relSFLV.add(ar.val0, ar.val1, ar.val2);     
    }
    
    for(Trio<VarLvalue, jq_Field, VarLvalue> ar : vlvLvRelSet) {      
      relLVFLV.add(ar.val0, ar.val1, ar.val2);     
    }
    
    for(Trio<StatLvalue, jq_Field, StatLvalue> ar : slvLvRelSet) {      
      relLVFLV.add(ar.val0, ar.val1, ar.val2);     
    }
    
    relVFLV.save();
    relSFLV.save();
    relLVFLV.save();       
  }
}
