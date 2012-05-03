package analyseur;
import org.nfunk.jep.JEP;

public class parseJep {
    String expression = "n";

    public int evalue(int n){
	JEP jep = new JEP();
	jep.addStandardFunctions();
	jep.addStandardConstants();
	jep.addVariable("n", n);
	jep.parseExpression(expression);
	return (int) jep.getValue();
    }

    public void setExpression(String expr){
	expression = expr;
    }
}
