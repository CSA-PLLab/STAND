package chord.analyses.lval;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import joeq.Class.jq_Class;
import joeq.Class.jq_Field;
import joeq.Class.jq_Method;
import joeq.Compiler.Quad.RegisterFactory.Register;

import chord.analyses.var.DomV;
import chord.bddbddb.Rel.PairIterable;
import chord.bddbddb.Rel.TrioIterable;
import chord.project.Chord;
import chord.project.ClassicProject;
import chord.project.analyses.ProgramDom;
import chord.project.analyses.ProgramRel;
import chord.util.tuple.object.Pair;
import chord.util.tuple.object.Trio;

@Chord(
	name = "LV",
	consumes = {"VH","FH","HF","HFH"}
)

public class DomLV extends ProgramDom<Lvalue> {
	Set<Trio<jq_Field,jq_Field,StatLvalue>> sfLvRelSet;
	Set<Trio<StatLvalue,jq_Field,StatLvalue>> slvLvRelSet;
	Set<Trio<Register,jq_Field,VarLvalue>> vLvRelSet;
	Set<Trio<VarLvalue,jq_Field,VarLvalue>> vlvLvRelSet;
	Map<Register,Set<Object>> vh;
	Map<jq_Field,Set<Object>> fh;
	Map<Object,Set<jq_Field>> hf;
	Map<Pair<Object,jq_Field>,Set<Object>> hfh;
	private Map<Lvalue,Set<Object>> filvpts;
	//private Program program;
	
	@Override
	public void init() {    
		//program = Program.g();
		sfLvRelSet = new HashSet<Trio<jq_Field,jq_Field,StatLvalue>>();
		slvLvRelSet = new HashSet<Trio<StatLvalue,jq_Field,StatLvalue>>();
		vLvRelSet = new HashSet<Trio<Register,jq_Field,VarLvalue>>();
		vlvLvRelSet = new HashSet<Trio<VarLvalue,jq_Field,VarLvalue>>();
		
		vh = new HashMap<Register,Set<Object>>();
		fh = new HashMap<jq_Field,Set<Object>>();
		hf = new HashMap<Object,Set<jq_Field>>();
		hfh = new HashMap<Pair<Object,jq_Field>,Set<Object>>();
		filvpts = new HashMap<Lvalue,Set<Object>>();
	}
	
	@Override
	public void fill() {
		// fill up vh
		ProgramRel relVH = (ProgramRel) ClassicProject.g().getTrgt("VH");
		relVH.load();
		PairIterable<Register, Object> vhtuples = relVH.getAry2ValTuples();
		for(Pair<Register,Object> p : vhtuples) {
			Register v = p.val0;
			Object h = p.val1;
			Set<Object> hset = vh.get(v);
			if(hset == null) {
				hset = new HashSet<Object>();
				vh.put(v, hset);
			}
			hset.add(h);
		}
		relVH.close();
		
		// fill up fh
		ProgramRel relFH = (ProgramRel) ClassicProject.g().getTrgt("FH");
		relFH.load();
		PairIterable<jq_Field, Object> fhtuples = relFH.getAry2ValTuples();
		for(Pair<jq_Field,Object> p : fhtuples) {
			jq_Field s = p.val0;
			Object h = p.val1;
			Set<Object> hset = vh.get(s);
			if(hset == null) {
				hset = new HashSet<Object>();
				fh.put(s, hset);
			}
			hset.add(h);
		}
		relFH.close();
		
		// fill up hf
		ProgramRel relHF = (ProgramRel) ClassicProject.g().getTrgt("HF");
		relHF.load();
		PairIterable<Object, jq_Field> hftuples = relHF.getAry2ValTuples();
		for(Pair<Object, jq_Field> p : hftuples) {
			Object h = p.val0;
			jq_Field f = p.val1;
			Set<jq_Field> fset = hf.get(h);
			if(fset == null) {
				fset = new HashSet<jq_Field>();
				hf.put(h, fset);
			}
			fset.add(f);
		}
		relHF.close();
		
		// fill up hfh
		ProgramRel relHFH = (ProgramRel) ClassicProject.g().getTrgt("HFH");
		relHFH.load();
		TrioIterable<Object,jq_Field,Object> hfhtuples = relHFH.getAry3ValTuples();
		for(Trio<Object,jq_Field,Object> t : hfhtuples) {
			Object h0 = t.val0;
			jq_Field f = t.val1;
			Object h1 = t.val2;
			Pair<Object,jq_Field> p = new Pair<Object,jq_Field>(h0,f);
			Set<Object> hset = hfh.get(p);
			if(hset  == null) {
				hset = new HashSet<Object>();
				hfh.put(p, hset);
			}
			hset.add(h1);
		}
		relHFH.close();
		
		Set<Lvalue> newlvset = new HashSet<Lvalue>();
		DomV domV = (DomV) ClassicProject.g().getTrgt("V");
		// lvalues of length 1
		for(Register v : vh.keySet()) {
			Set<Object> hset = vh.get(v);
			for(Object h : hset) {
				Set<jq_Field> fset = hf.get(h);
				if(fset != null) {
					for(jq_Field f : fset) {
						if((f == null) || !(f.getType() instanceof jq_Class)) continue; 
						// add lvalue
						jq_Method m = domV.getMethod(v);
						VarLvalue lv = new VarLvalue(m,v,f);
						add(lv);
						newlvset.add(lv);
						
						// add access path relations
						Trio<Register,jq_Field,VarLvalue> apRel = new Trio<Register,jq_Field,VarLvalue>(v,f,lv);
						vLvRelSet.add(apRel);
						
						// add points-to relations
						Pair<Object,jq_Field> p = new Pair<Object,jq_Field>(h,f);
						Set<Object> oset = hfh.get(p);						
						Set<Object> pset = filvpts.get(lv);
						if(pset == null) {
							pset = new HashSet<Object>();
							filvpts.put(lv, pset);
						}
						pset.addAll(oset);
					}
				}
			}
		}
		
		for(jq_Field s : fh.keySet()) {
			Set<Object> hset = fh.get(s);
			for(Object h : hset) {
				Set<jq_Field> fset = hf.get(h);
				if(fset != null) {
					for(jq_Field f : fset) {
						if((f == null) || !(f.getType() instanceof jq_Class)) continue;
						// add lvalue
						StatLvalue lv = new StatLvalue(s,f);
						add(lv);
						newlvset.add(lv);
						
						// add access path relations
						Trio<jq_Field,jq_Field,StatLvalue> apRel = new Trio<jq_Field,jq_Field,StatLvalue>(s,f,lv);
						sfLvRelSet.add(apRel);
						
						// add points-to relations
						Pair<Object,jq_Field> p = new Pair<Object,jq_Field>(h,f);
						Set<Object> oset = hfh.get(p);						
						Set<Object> pset = filvpts.get(lv);
						if(pset == null) {
							pset = new HashSet<Object>();
							filvpts.put(lv, pset);
						}
						pset.addAll(oset);
					}
				}
			}
			
		}
		
		
		for(int k = 1; k < Lvalue.maxLength; k++) {
			Set<Lvalue> nextlvset = new HashSet<Lvalue>(newlvset);
			newlvset.clear();
			for(Lvalue lv : nextlvset) {
				Set<Object> hset = filvpts.remove(lv);
				if(hset != null) {
					for(Object h : hset) {
						Set<jq_Field> fset = hf.get(h);
						if(fset != null) {
							for(jq_Field f : fset) {
								if((f == null) || !(f.getType() instanceof jq_Class)) continue;
								// add lvalue and access path rels
								Lvalue newlv;
								if(lv instanceof VarLvalue) {
									newlv = new VarLvalue((VarLvalue)lv,f);
									vlvLvRelSet.add(new Trio<VarLvalue,jq_Field,VarLvalue>((VarLvalue)lv,f,(VarLvalue)newlv));									
								}
								else {
									newlv = new StatLvalue((StatLvalue)lv,f);
									slvLvRelSet.add(new Trio<StatLvalue,jq_Field,StatLvalue>((StatLvalue)lv,f,(StatLvalue)newlv));
								}
								add(newlv);
								newlvset.add(newlv);
								
								// add points-to rels
								Pair<Object,jq_Field> p = new Pair<Object,jq_Field>(h,f);
								Set<Object> oset = hfh.get(p);						
								Set<Object> pset = filvpts.get(newlv);
								if(pset == null) {
									pset = new HashSet<Object>();
									filvpts.put(newlv, pset);
								}
								pset.addAll(oset);
								
							}
						}
					}
				}
			}
		}
		
		/*
		// variable lvalues
		ProgramRel vt = (ProgramRel) ClassicProject.g().getTrgt("VtypeT");		
		Dom[] doms = vt.getDoms();
	    DomV domV = (DomV) doms[0];
		vt.load();		
		PairIterable<Register, jq_Type> vttuples = vt.getAry2ValTuples();
		for (Pair<Register, jq_Type> p : vttuples) {		
			Register v = (Register) p.val0;
			jq_Method m = domV.getMethod(v);
			jq_Type t = (jq_Type) p.val1;
			if(t instanceof jq_Class) {
				jq_Class cl = (jq_Class) program.getClass(t.toString());
				if(cl != null) {
					jq_Field[] farr = cl.getInstanceFields();
					for(jq_Field f : farr) {
						if(f.getType() instanceof jq_Class) {							
							VarLvalue newlv = new VarLvalue(m, v, f);
							if(add(newlv)) {
								Trio<Register,jq_Field,VarLvalue> apRel = new Trio<Register,jq_Field,VarLvalue>(v, f, newlv);
						  		vLvRelSet.add(apRel);
							}
						}
					}
				}
			}
			    
		}		
		vt.close();
		
		ProgramRel vft = (ProgramRel) ClassicProject.g().getTrgt("VFtypeT");		
		doms = vft.getDoms();
	    domV = (DomV) doms[0];
		vft.load();		
		TrioIterable<Register, jq_Field, jq_Type> vfttuples = vft.getAry3ValTuples();
		for (Trio<Register, jq_Field, jq_Type> p : vfttuples) {		
			Register v = (Register) p.val0;
			jq_Method m = domV.getMethod(v);
			jq_Field f0 = (jq_Field) p.val1;
			jq_Type t = (jq_Type) p.val2;
			if((f0 != null) && (t instanceof jq_Class)) {
				jq_Class cl = (jq_Class) program.getClass(t.toString());
				if(cl != null) {
					jq_Field[] farr = cl.getInstanceFields();
					for(jq_Field f : farr) {
						if(f.getType() instanceof jq_Class) {							
							VarLvalue newlv = new VarLvalue(m, v, f0, f);
							if(add(newlv)) {
								Trio<VarLvalue,jq_Field,VarLvalue> apRel = new Trio<VarLvalue,jq_Field,VarLvalue>(new VarLvalue(m, v, f0), f, newlv);
						  		vlvLvRelSet.add(apRel);
							}
						}
					}
				}
			}
			    
		}		
		vft.close();										
		
		//static lvalues		
		ProgramRel st = (ProgramRel) ClassicProject.g().getTrgt("StypeT");		
		
		st.load();		
		PairIterable<jq_Field, jq_Type> sttuples = st.getAry2ValTuples();
		for (Pair<jq_Field, jq_Type> p : sttuples) {		
			jq_Field v = (jq_Field) p.val0;			
			jq_Type t = (jq_Type) p.val1;
			if(t instanceof jq_Class) {
				jq_Class cl = (jq_Class) program.getClass(t.toString());
				if(cl != null) {
					jq_Field[] farr = cl.getInstanceFields();
					for(jq_Field f : farr) {
						if(f.getType() instanceof jq_Class) {
							StatLvalue newlv = new StatLvalue(v, f);
							if(add(newlv)) {
								Trio<jq_Field,jq_Field,StatLvalue> apRel = new Trio<jq_Field, jq_Field,StatLvalue>(v, f, newlv);
						  		sfLvRelSet.add(apRel);
							}
						}
					}
				}
			}
			    
		}		
		st.close();
		
		ProgramRel sft = (ProgramRel) ClassicProject.g().getTrgt("SFtypeT");				
		sft.load();		
		TrioIterable<jq_Field, jq_Field, jq_Type> sfttuples = sft.getAry3ValTuples();
		for (Trio<jq_Field, jq_Field, jq_Type> p : sfttuples) {		
			jq_Field v = (jq_Field) p.val0;			
			jq_Field f0 = (jq_Field) p.val1;
			jq_Type t = (jq_Type) p.val2;
			if((f0 != null) && (t instanceof jq_Class)) {
				jq_Class cl = (jq_Class) program.getClass(t.toString());
				if(cl != null) {
					jq_Field[] farr = cl.getInstanceFields();
					for(jq_Field f : farr) {
						if(f.getType() instanceof jq_Class) {
							StatLvalue newlv = new StatLvalue(v, f0, f);
							if(add(newlv)) {
								Trio<StatLvalue,jq_Field,StatLvalue> apRel = new Trio<StatLvalue,jq_Field,StatLvalue>(new StatLvalue(v, f0), f, newlv);
						  		slvLvRelSet.add(apRel);
							}
						}
					}
				}
			}
			    
		}		
		sft.close();	
		*/					
	}
		
	  
	public String toUniqueString(Lvalue lv) {
		return lv + "!" + ((lv.isStaic())? ((StatLvalue)lv).getDeclaringClass() : ((VarLvalue)lv).getMethod());
	}

}
