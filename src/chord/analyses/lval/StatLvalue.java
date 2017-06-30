package chord.analyses.lval;

import java.util.ArrayList;
import java.util.List;

import joeq.Class.jq_Class;
import joeq.Class.jq_Field;
import joeq.Class.jq_Type;

public class StatLvalue extends Lvalue {  
  private final jq_Field sf;
  private final List<jq_Field> ap;	
  
  public StatLvalue(jq_Field sf, List<jq_Field> ap) throws InvalidLvalue {	
	this.sf = sf;	
	if((ap == null) || (ap.size() < 1))
	  throw new InvalidLvalue();
	this.ap = new ArrayList<jq_Field>(ap);
  }
  
  public StatLvalue(jq_Field sf, jq_Field f) {
    this.sf = sf;   
    this.ap = new ArrayList<jq_Field>();
    this.ap.add(f);
  }
  
  public StatLvalue(jq_Field sf, jq_Field f0, jq_Field f1) {
	this.sf = sf;   
	this.ap = new ArrayList<jq_Field>();
	this.ap.add(f0);
	this.ap.add(f1);
  }
  
  public StatLvalue(StatLvalue lv, jq_Field f) {	  
    this.sf = lv.sf;	
    this.ap = new ArrayList<jq_Field>(lv.ap);
    this.ap.add(f);
  }
  
  public jq_Field getStatF() {
    return sf;
  }
  
  public List<jq_Field> getAP() {
    return ap;
  }
  
  public jq_Class getDeclaringClass() {
    return sf.getDeclaringClass();	  
  }
  
  @Override
  public boolean equals(Object o) {
	if(this == o) return true;
  	if(!(o instanceof StatLvalue)) 
  	  return false;
  	StatLvalue olv = (StatLvalue) o;
  	
  	if(sf.equals(olv.sf) && ap.equals(olv.ap))
  	  return true;
  	return false;  	
  }

  public jq_Type getType() {
	jq_Field f = ap.get(ap.size() - 1);	
	return f.getType();
	
  }
  
  @Override
  public String toString() {	
	StringBuffer sb = new StringBuffer(sf.getName().toString());
	for(jq_Field f : ap) {
	  sb.append(".");
	  if(f == null) sb.append("null");
	  else sb.append(f.getName());	  
	}
	return sb.toString();
  }
  
  @Override
  public int hashCode() {
    int ret = sf.hashCode();
    for(jq_Field f : ap) {
      if(f != null) {
        ret += f.hashCode();
      }
    }
    return ret;
  }

  @Override
  public boolean isStaic() {	
	return true;
  }
}
