import org.nfunk.jep.JEP;

// Suite
public class Suite {
    int type;
    long a;
    long b;
    long c;
    String expression = "n";
	
    public Suite(int type, long a, long b, long c){
    	this.type = type;
    	this.a = a;
    	this.b = b;
    	this.c = c;
    }

    // Change les coefficients a, b et c, pour le choix d'une sous suite d'indices an^2+bn+c
    public void setabc(long a, long b, long c){
    	this.a = a;
    	this.b = b;
    	this.c = c;
    }
	
    // Change le type de suite
    public void setType(int type){
    	if (type > 0 && type <6){
    		this.type = type;
    	}
    }
    
    // Change l'espression de la suite pour le type 5
    public void setExpression(String expression){
    	this.expression = expression;
    }
    
    // Donne le coefficient a
    public long geta(){
    	return this.a;
    }

    // Donne le coefficient b
    public long getb(){
    	return this.b;
    }

    // Donne le coefficient c
    public long getc(){
    	return this.c;
    }
    
    // Évalue la suite 
    public long Calcule(long n){
	long valeurRetour = 0;
	if (this.type == 1){
	    valeurRetour = f(n, this.a, this.b, this.c);
	}
	if (this.type == 2){
	    valeurRetour = suiteM(f(n, this.a, this.b, this.c));
	}
	if (this.type == 3){
	    valeurRetour = suiteF(f(n, this.a, this.b, this.c));
	}
	if (this.type == 4){
		valeurRetour = perso(f(n, this.a, this.b, this.c));
	}
	if (this.type == 5){
		JEP jep = new JEP();
		jep.addStandardFunctions();
		jep.addStandardConstants();
		jep.addVariable("n", f(n, this.a, this.b, this.c));
		jep.parseExpression(this.expression);
		valeurRetour = (int) jep.getValue();
	}
	return valeurRetour;
    }
	
    // Fonction du second degré de coefficients a, b et c
    long f (long n, long a, long b, long c){
	return a*n*n + b*n + c;
    }

    // La suite F
    long suiteF(long n){
	if (n == 0)
	    return 0;
	if (n == 1)
	    return 1;     
	else{
			if (n % 2 == 0){
				return suiteF((long) n/2);
			}else{
				return suiteF(n - 1) + suiteF(n + 1);
			}
		}
	}
	
    // La suite M
    long suiteM (long n) {
	long k;
	if (n == 0)
	    return 0;
	else{
	    k = (long) Math.floor((Math.log(n)) / (Math.log(2)) + 1);
	    if (k % 2 == 0){
		return suiteM((long) Math.pow(2,k)-1-n);
	    }
	    else{
		return 1 - suiteM((long) Math.pow(2,k)-1-n);
	    }
		}
    }

    // Ici on peut personnaliser sa suite !
    long perso (long n){
	return (long) Math.floor(Math.sqrt(n));
	//return (long) Math.floor(Math.log(n)*Math.sqrt(Math.log(n)));
	//return (long) Math.floor(10*Math.cos(n));
	//return (long) Math.floor(n*(Math.log(n) + Math.log(Math.log(n))) - 1);
	//return (long) Math.floor(n*Math.log(2));
	//return (long) Math.floor(n*Math.exp(2));
	//return (long) Math.floor((n*167)/17);
	//return (long) Math.floor(n*Math.PI);
    //return (long) Math.floor(Math.abs(Math.exp(n)*Math.sin(n)));
    }	
}
