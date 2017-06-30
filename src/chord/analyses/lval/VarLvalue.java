package chord.analyses.lval;

import java.util.List;
import java.util.ArrayList;

import joeq.Compiler.Quad.RegisterFactory.Register;
import joeq.Class.jq_Field;
import joeq.Class.jq_Method;
import joeq.Class.jq_Type;

public class VarLvalue extends Lvalue {  
  private final Register var;
  private final List<jq_Field> ap;
  private jq_Method m; 

  public VarLvalue(jq_Method m, Register var, List<jq_Field> ap) throws InvalidLvalue {
    this.m = m;	  
    this.var = var;
    if((ap == null) || (ap.size() < 1))
      throw new InvalidLvalue();
    this.ap = new ArrayList<jq_Field>(ap);
  }
  
  public VarLvalue(jq_Method m, Register v, jq_Field f) {
	this.var = v;
    this.m = m;	
	this.ap = new ArrayList<jq_Field>();
	this.ap.add(f);
  }
  
  public VarLvalue(jq_Method m, Register v, jq_Field f1, jq_Field f2) {
	this.var = v;
	this.m = m;	
	this.ap = new ArrayList<jq_Field>();
	this.ap.add(f1);
	this.ap.add(f2);
  }

  public VarLvalue(VarLvalue lv, jq_Field f) {	  
    this.var = lv.var;
    this.m = lv.m;
    this.ap = new ArrayList<jq_Field>(lv.ap);
    this.ap.add(f);
  }   
  
  public Register getVar() {
    return var;
  }

  public List<jq_Field> getAP() {
    return ap;
  }

  
  public jq_Type getType() {	
    jq_Field f = ap.get(ap.size() - 1);    
    return f.getType();    
  }
  
  public jq_Method getMethod() {
    return m;	  
  }
  
  @Override
  public boolean equals(Object o) {
	if(this == o) return true;
  	if(!(o instanceof VarLvalue)) 
  	  return false;
  	VarLvalue olv = (VarLvalue) o;
  	
  	if(var.equals(olv.var) && ap.equals(olv.ap))
  	  return true;
  	return false; 	
  }
  
  @Override
  public String toString() {	
    StringBuffer sb = new StringBuffer(var.toString());
    for(jq_Field f : ap) {
      sb.append(".");
      if(f == null) sb.append("null");
      else
      sb.append(f.getName());      
    }
    return sb.toString();		
  }
  
  @Override
  public int hashCode() {
    int ret = var.hashCode();
    for(jq_Field f : ap) {
      if(f != null) {
        ret += f.hashCode();
      }
    }
    return ret;
  }

  @Override
  public boolean isStaic() {	
    return false;
  }
  
}

