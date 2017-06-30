package chord.analyses.lval;

import java.util.List;

import joeq.Class.jq_Field;
import joeq.Class.jq_Type;

public abstract class Lvalue {
  public static int maxLength = 2;
  public abstract jq_Type getType();
  public abstract List<jq_Field> getAP();
  public abstract boolean isStaic();
}
