package chord.analyses;

import java.io.PrintWriter;

import chord.analyses.alias.Ctxt;
import chord.project.Chord;
import chord.project.ClassicProject;
import chord.project.Config;
import chord.project.ITask;
import chord.project.OutDirUtils;
import chord.project.analyses.JavaAnalysis;
import chord.project.analyses.ProgramDom;
import chord.project.analyses.ProgramRel;
import chord.util.ArraySet;
import chord.util.tuple.object.Pair;
import joeq.Compiler.Quad.ControlFlowGraph;
import joeq.Compiler.Quad.Operator;
import joeq.Compiler.Quad.Quad;
import chord.util.tuple.object.Pent;
import joeq.Compiler.Quad.Inst;
import joeq.Compiler.Quad.Operator.Invoke;
import joeq.Compiler.Quad.Operator.MultiNewArray;
import joeq.Compiler.Quad.Operator.New;
import joeq.Compiler.Quad.Operator.NewArray;
import joeq.Class.jq_Method;

/*
 * chord.printrel.dir      directory where all the .txt files containing the rels will be dumped.
 * chord.printrel.suffix   name suffix for all the .txt files containing the rels that will be dumped.
 */

@Chord(
	    name = "printrels-java"
	)
public class PrintRel extends JavaAnalysis {

	public void run() {
		String printDir = null;
		String suffix = "";
		ProgramRel rel;
		printDir = System.getProperty("chord.printrel.dir", Config.outDirName);
		System.out.println("Printing relations in: " + printDir);
		suffix = System.getProperty("chord.printrel.suffix", "");
		String relName = "";
		PrintWriter outFile = null;
		
	    //ProgramDom<?> domAS = (ProgramDom<?>)ClassicProject.g().getTrgt("AS");
	    // ProgramDom<?> domC = (ProgramDom<?>)ClassicProject.g().getTrgt("C");
	    // ProgramDom<?> domP = (ProgramDom<?>)ClassicProject.g().getTrgt("P");
	    // ProgramDom<?> domE = (ProgramDom<?>)ClassicProject.g().getTrgt("E");
		
	    
	    /*relName = "PathEdge_cs";
	    outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	    rel.load();
	    
	    Iterable<Pent<Ctxt, Quad, Pair<Quad, jq_Method>, Pair<Quad, jq_Method>, Pair<Quad, jq_Method>>> tuplesPE = rel.getAry5ValTuples();
		for (Pent<Ctxt, Quad, Pair<Quad, jq_Method>, Pair<Quad, jq_Method>, Pair<Quad, jq_Method>> tup : tuplesPE){
			int c = domC.indexOf(tup.val0);
			int p = domP.indexOf(tup.val1);
			int t = domAS.indexOf(tup.val2);
			int t1 = domAS.indexOf(tup.val3);
			int t2 = domAS.indexOf(tup.val4);
			outFile.println("PathEdge_cs(" + c + "," + p + "," + t + "," + t1 + "," + t2 + "): "
			    + ((Inst)tup.val1).toJavaLocStr());
		}
		rel.close();
		outFile.close();	
*/
		/*
	    relName = "racePairs_cs";
	    outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	    rel.load();
	    Iterable<Pair<Quad, Quad>> tuplesRp = rel.getAry2ValTuples();
		for (Pair<Quad, Quad> t : tuplesRp){
			int idx1 = domE.indexOf(t.val0);
			int idx2 = domE.indexOf(t.val1);
			outFile.print("racePairs_cs(" + idx1 + "," + idx2 + "): ");
			outFile.print(((Quad)t.val0).toJavaLocStr());
			outFile.print("  ");
			outFile.println(((Quad)t.val1).toJavaLocStr());
		}
		rel.close();
		outFile.close();
		*/
		
	    /****	
		relName = "PP";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		Iterable<Pair<Inst, Inst>> tuplesPP = rel.getAry2ValTuples();
		for (Pair<Inst, Inst> t : tuplesPP){
			outFile.print(((Inst)t.val0).toJavaLocStr());
			outFile.print("  ");
			outFile.println(((Inst)t.val1).toJavaLocStr());
		}
		rel.close();
		outFile.close();
	    *****/	
		/*
		relName = "mhp_cs";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	    rel.load();
	    
	    Iterable<chord.util.tuple.object.Quad<Ctxt, Inst, Pair<Quad, jq_Method>, Pair<Quad, jq_Method> >> tuplesMhp = rel.getAry4ValTuples();
		for (chord.util.tuple.object.Quad<Ctxt, Inst, Pair<Quad, jq_Method>, Pair<Quad, jq_Method>> t : tuplesMhp){
			int c = domC.indexOf(t.val0);
			outFile.print(c);
			outFile.print("  ");
			outFile.print(((Inst)t.val1).toJavaLocStr());
			outFile.print("  ");
			int t1 = domAS.indexOf(t.val2);
			outFile.print(t1);
			outFile.print("  ");
			int t2 = domAS.indexOf(t.val3);
			outFile.println(t2);		
		}
		rel.close();
		outFile.close();
		
		relName = "CICM";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	    rel.load();
	    
	    Iterable<chord.util.tuple.object.Quad<Ctxt, Quad, Ctxt, jq_Method>> tuplesCicm = rel.getAry4ValTuples();
		for (chord.util.tuple.object.Quad<Ctxt, Quad, Ctxt, jq_Method> t : tuplesCicm){
			int c = domC.indexOf(t.val0);
			outFile.print(c);
			outFile.print("  ");
			outFile.print(((Inst)t.val1).toJavaLocStr());
			outFile.print("  ");
			int d = domC.indexOf(t.val2);
			outFile.print(d);
			outFile.print("  ");
			outFile.println("M: " + t.val3);
		}
		rel.close();
		outFile.close();
		*/
		
	    /******	
		relName = "threadACH";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	    rel.load();
	    
	    ProgramDom<?> domAS = (ProgramDom<?>)ClassicProject.g().getTrgt("AS");
	    ProgramDom<?> domC = (ProgramDom<?>)ClassicProject.g().getTrgt("C");
	    Iterable<Trio<Pair<Quad, jq_Method>, Ctxt, Inst>> tuplesAch = rel.getAry3ValTuples();
		for (Trio<Pair<Quad, jq_Method>, Ctxt, Inst> t : tuplesAch){
			int t1 = domAS.indexOf(t.val0);
			outFile.print(t1);
			outFile.print("  ");
			int c = domC.indexOf(t.val1);
			outFile.print(c);
			outFile.print("  ");
			outFile.println(((Inst)t.val2).toJavaLocStr());
		}
		rel.close();
		outFile.close();
		
		
		relName = "threadPH_cs";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	    rel.load();
	    
	    ProgramDom<?> domC = (ProgramDom<?>)ClassicProject.g().getTrgt("C");
	    Iterable<chord.util.tuple.object.Quad<Ctxt, Inst, Ctxt, Inst>> tuplesPh = rel.getAry4ValTuples();
		for (chord.util.tuple.object.Quad<Ctxt, Inst, Ctxt, Inst> t : tuplesPh){
			int c = domC.indexOf(t.val0);
			outFile.print(c);
			outFile.print("  ");
			outFile.print(((Inst)t.val1).toJavaLocStr());
			outFile.print("  ");
			c = domC.indexOf(t.val2);
			outFile.print(c);
			outFile.print("  ");
			outFile.println(((Inst)t.val3).toJavaLocStr());
		}
		rel.close();
		outFile.close();
		
		
		relName = "ThreadIMmod";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		Iterable<Pair<Quad, jq_Method>> tuplesTM = rel.getAry2ValTuples();
		for (Pair<Quad, jq_Method> t : tuplesTM){
			outFile.print(((Quad)t.val0).toJavaLocStr());
			outFile.print("  ");
			outFile.println(t.val1.toString());
		}
		rel.close();
		outFile.close();
		
		relName = "TMrun";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		Iterable<Pair<jq_Class, jq_Method>> tuplesTMrun = rel.getAry2ValTuples();
		for (Pair<jq_Class, jq_Method> t : tuplesTMrun){
			outFile.print(t.val0.toString());
			outFile.print("  ");
			outFile.println(t.val1.toString());
		}
		rel.close();
		outFile.close();
*/		
		// relName = "reachableM";
		// outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		// rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		// rel.load();
		// Iterable<jq_Method> tuplesMethods = rel.getAry1ValTuples();
		// for(jq_Method t : tuplesMethods){
		// 	outFile.print("RM " + t + "\n");
		// 	printCFG(t);
		// }
		// rel.close();
		// outFile.close();

		relName = "sVPts";

		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		Iterable<Pair<Quad, Quad>> tuplesMethods1 = rel.getAry2ValTuples();
		for(Pair<Quad, Quad> t : tuplesMethods1){
			outFile.print("sVPts " + t.val0 + "-->" + t.val1 + "\n");
		}
		rel.close();
		outFile.close();
		relName = "gVPts";

		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		Iterable<Pair<Quad, Quad>> tuplesMethods2 = rel.getAry2ValTuples();
		for(Pair<Quad, Quad> t : tuplesMethods2){
			outFile.print("gVPts " + t.val0 + "-->" + t.val1 + "\n");
		}
		rel.close();
		outFile.close();
		relName = "fVPts";

		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		Iterable<Pair<Quad, Quad>> tuplesMethods3 = rel.getAry2ValTuples();
		for(Pair<Quad, Quad> t : tuplesMethods3){
			outFile.print("fVPts " + t.val0 + "-->" + t.val1 + "\n");
		}
		rel.close();
		outFile.close();

		// relName = "PfsSH";

		// outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
		// rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		// rel.load();
		// Iterable<Trio<Quad, Quad, Quad>> tuplesMethods = rel.getAry3ValTuples();
		// for(Trio<Quad, Quad, Quad> t : tuplesMethods){
		// 	outFile.print("PfsSH " + t.val0 + "--" + t.val1 + "--" + t.val2 + "\n");
		// }
		// rel.close();
		// outFile.close();
/*
		relName = "tStartThis";
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		rel.print(printDir);
		
		relName = "tAllocThis";
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		rel.print(printDir);
		
		relName = "tAllocRun";
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		rel.print(printDir);
		
		relName = "tAllocThisRun";
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		rel.print(printDir);
		
		relName = "tAllocThisRunFinal";
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		rel.print(printDir);
		
		relName = "tStartRunType";
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		rel.print(printDir);
		
		relName = "runnable";
		rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
		rel.load();
		rel.print(printDir);
		***/

		/*relName = "simplePH_cs";
		outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	    rel.load();
	    
	    ProgramDom<?> domC = (ProgramDom<?>)ClassicProject.g().getTrgt("C");
	    Iterable<chord.util.tuple.object.Quad<Ctxt, Inst, Ctxt, Inst>> tuplesPh = rel.getAry4ValTuples();
		for (chord.util.tuple.object.Quad<Ctxt, Inst, Ctxt, Inst> t : tuplesPh){
			int c = domC.indexOf(t.val0);
			outFile.print(c);
			outFile.print("  ");
			outFile.print(((Quad)t.val1).toString());
			outFile.print("  ");
			outFile.print(((Inst)t.val1).toJavaLocStr());
			outFile.print("  ");
			c = domC.indexOf(t.val2);
			outFile.print(c);
			outFile.print("  ");
			outFile.print(((Inst)t.val3).toString());
			outFile.print("  ");
			outFile.println(((Inst)t.val3).toJavaLocStr());
		}
		rel.close();
		outFile.close();
		*/
		
		// relName = "reachableM";
	 //    rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	 //    rel.load();
	 //    Iterable<jq_Method> tuplesM = rel.getAry1ValTuples();
		// for (jq_Method m : tuplesM){
		// 	System.out.println("ReachableM: " + m);
		// }
		// rel.close();
		
	    
		// ProgramDom<?> domM = (ProgramDom<?>)ClassicProject.g().getTrgt("M");
		// for (int i = 0; i < domM.size(); i++) {
		// 	jq_Method m = (jq_Method)(domM.get(i));
		// 	// if (m.getDeclaringClass().getName().equals("org.apache.ftpserver.RequestHandler") && 
		// 	// 	!m.isAbstract()) 
		// 	printCFG(m);
		// 	System.out.println("ReachableM: " + m);
		// }
		
	// 	relName = "escM";
	// 	rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	// 	rel.load();
	// 	rel.print(printDir);
		
		
	// 	relName = "LMlift";
	// 	outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	//     rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	//     rel.load();
	//     Iterable<Pair<Quad,jq_Method>> tuplesLMlift = rel.getAry2ValTuples();
	// 	for (Pair<Quad,jq_Method> p : tuplesLMlift){
	// 		jq_Method m = p.val1;
	// 		outFile.println(((Quad)p.val0).toJavaLocStr() + "  " + m.toString());
	// 		printCFG(m);
	// 	}
	// 	rel.close();
	// 	outFile.close();
		
	// 	ProgramDom<?> domI = (ProgramDom<?>)ClassicProject.g().getTrgt("I");
	// 	ProgramDom<?> domH = (ProgramDom<?>)ClassicProject.g().getTrgt("H");
	// 	ProgramDom<?> domM = (ProgramDom<?>)ClassicProject.g().getTrgt("M");
		
	// 	ClassicProject.g().setTrgtDone("CICM");
	// 	ClassicProject.g().runTask("relevant-ctxt-dlog");
		
	// 	relName = "CM";
	// 	outFile = OutDirUtils.newPrintWriter(relName + suffix + ".txt");
	//     rel = (ProgramRel) ClassicProject.g().getTrgt(relName);
	//     rel.load();
	   
	//     Iterable<Pair<Ctxt,jq_Method>> tuplesCM = rel.getAry2ValTuples();
	// 	for (Pair<Ctxt,jq_Method> p : tuplesCM){
	// 		jq_Method m = p.val1;
	// 		Quad[] c = ((Ctxt)(p.val0)).getElems();
	// 		int mIdx = domM.indexOf(m);
	// 		outFile.print(domM.toUniqueString(mIdx) + " ");
	// 		for (int i = 0; i < c.length; i++) {
	// 			Quad q = c[i];
	// 			Operator op = q.getOperator();
	// 			if (op instanceof New || op instanceof NewArray || op instanceof MultiNewArray) {
	// 				int idx = domH.indexOf(q);
	// 				outFile.print(domH.toUniqueString(idx) + " ");
	// 			} else if (op instanceof Invoke) {
	// 				int idx = domI.indexOf(q);
	// 				outFile.print(domI.toUniqueString(idx) + " ");
	// 			} else
	// 				assert false;
	// 		}
	// 		outFile.println("");
	// 	}
	// 	rel.close();
	// 	outFile.close();

		
	}


    private void printCFG(jq_Method m) {
		System.out.println("*************************************************************");
		ControlFlowGraph cfg = m.getCFG();
		System.out.println(cfg.fullDump());
		System.out.println("*************************************************************");
	}
}
