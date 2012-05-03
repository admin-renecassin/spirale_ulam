public class Propriete {
    int type;
	
    public Propriete(){
	type = 1;
    }

    // Change le type de propriété
    public void setType(int type){
	if (type > 0 && type <5){
	    this.type = type;
	}
    }
	
    // Déterminer si le nombre vérifie la propriété
    public boolean propriete(long n){
	if (this.type == 1){
	    return estPremier(n);
	}
	if (this.type == 2){
	    return !(n == 0);
	}
	if (this.type == 3){
	    return !(n%3 == 0);
	}
	if (this.type == 4){
	    return !(n%4 == 0);
	}
	return false;
    }
	
    // Détermine si le nombre n est premier
    boolean estPremier(long n) {
    long m = Math.abs(n);
	if (m <= 3) return  m == 2 || m == 3;
	if (m % 2 == 0) return  false;
	for (long i = 3; i * i <= m; i += 2) {
	    if (m % i == 0) {
		return  false;
	    }
	}
	return  true;
    }

	
}
