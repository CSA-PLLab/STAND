package chord.analyses.print;

import joeq.Class.jq_Method;
import joeq.Compiler.Quad.Quad;

import chord.project.Chord;
import chord.project.ClassicProject;
import chord.project.analyses.JavaAnalysis;
import chord.analyses.method.DomM;
import chord.bddbddb.Rel.PairIterable;
import chord.util.tuple.object.Pair;
import chord.project.analyses.ProgramRel;


@Chord(
  name = "print-java",
  consumes = {"MP"}
)

public class PrintP extends JavaAnalysis {
  @Override 
  public void run() {
    ProgramRel relMP = (ProgramRel) ClassicProject.g().getTrgt("MP");
    relMP.load();
    PairIterable<jq_Method, Quad> tuples = relMP.getAry2ValTuples();	
    for (Pair<jq_Method, Quad> p : tuples) {		
			jq_Method m = (jq_Method) p.val0;	
			Quad quad = (Quad) p.val1;
      System.out.println(m + " -> " + quad);
		}		
		relMP.close();

    System.out.println("Printed");
  }
}
