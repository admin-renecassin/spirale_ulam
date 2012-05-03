import java.awt.Color;
import javascool.IconOutput;
import javascool.ImageUtils;

//Image et creation de la spirale
public class Image extends IconOutput{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    String nom;
    int dimensionx;
    int dimensiony;
    long centrex;
    long centrey;
    Suite suite;
    Suite sousSuite;
    Propriete propriete;
    Color couleurSuiteP;
    Color couleurSuiteNP;
    Color couleurSousSuiteP;
    Color couleurSousSuiteNP;
    int debutIntervalleSousSuite;
    int finIntervalleSousSuite;
    long tempsCalcul;

    public Image(String nomFichier, int dimx, int dimy, int cx, int cy, Suite u, Suite v, Propriete p, Color couleurup, Color couleurunp, Color couleurvp, Color couleurvnp, int a, int b, boolean zoom, int tc){
	dimensionx = dimx;
	dimensiony = dimy;
	centrex = cx;
	centrey = cy;
	suite = u;
	sousSuite = v;
	propriete = p;
	couleurSuiteP = couleurup;
	couleurSuiteNP = couleurunp;
	couleurSousSuiteP = couleurvp; 
	couleurSousSuiteNP = couleurvnp; 
	debutIntervalleSousSuite = a;
	finIntervalleSousSuite = b;
	reset(dimensionx,dimensiony, zoom);
	nom = nomFichier;
	tempsCalcul = tc;
    }

    // Change le nom du fichier de l'image
    public void setNom(String nomfichier){
	nom = nomfichier;
    }
	
    // Change les coordonnées du centre de l'image
    public void setCentre(int x, int y){
	centrex = x;
	centrey = y;
    }

    // Change l'intervalle de calcul et de représentation de la sous suite
    public void setIntervalle(int a, int b){
	debutIntervalleSousSuite = a;
	finIntervalleSousSuite = b;
    }
	
    // Définit la suite
    public void setSuite(Suite u){
	suite = u;
    }

    // Définit la sous suite
    public void setSousSuite(Suite v){
	sousSuite = v;
    }
	
    // Définit la propriété
    public void setPropriete(Propriete p){
	propriete = p;
    }
    
    // Définit le temps maximal de calcul
    public void setTempsMaximalCalcul(long t){
    	tempsCalcul = t;
        }
    
	// Crée la spirale
    public void creeSpirale(){
	long nombre;
	long coinx = coinBasGauche(centrex, centrey, dimensionx, dimensiony)[0];
	long coiny = coinBasGauche(centrex, centrey, dimensionx, dimensiony)[1];
	long heureDebut = System.currentTimeMillis();
	long heureFin = heureDebut;
	//reset(dimensionx,dimensiony, true);
	for (int i=0; i < dimensionx; i++){
	    for (int j=0; j < dimensiony; j++){
		if((heureFin - heureDebut) < tempsCalcul){
		    long coordonneex = coinx + i;
		    long coordonneey = coiny + j;
		    nombre = suite.Calcule(coordonneesValeur(coordonneex, coordonneey));
		    if (propriete.propriete(nombre)){
		    	set( i, dimensiony -1  - j , couleurSuiteP);
		    }else {
		    	set( i, dimensiony -1  - j , couleurSuiteNP);
		    }
		    heureFin = System.currentTimeMillis();
			}
	    }
	}
 }
    // Décale l'image en abscisses ou en ordonnées suivant le cas
    public void decale(int cas){
    	long nombre;
    	long coinx = coinBasGauche(centrex, centrey, dimensionx, dimensiony)[0];
    	long coiny = coinBasGauche(centrex, centrey, dimensionx, dimensiony)[1];
    	if (cas == 1){
    		for (int i=0; i < dimensionx - 1 ; i++){
    		    for (int j=0; j < dimensiony; j++){
    		    	set( i, j , getPixelColor(i + 1, j));
    		    }
    		}
    		for (int j=0; j < dimensiony; j++){
    			int i = dimensionx - 1;
    		    long coordonneex = coinx + i + 1;
    		    long coordonneey = coiny + j;
    		    nombre = suite.Calcule(coordonneesValeur(coordonneex, coordonneey));
    		    if (propriete.propriete(nombre)){
    		    	set( i, dimensiony -1  - j , couleurSuiteP);
    		    }else {
    		    	set( i, dimensiony -1  - j , couleurSuiteNP);
    		    }
    		}
    	} 	
    	if (cas == 2){
    		for (int i= dimensionx - 1; i > 0; i--){
    		    for (int j=0; j < dimensiony; j++){
    		    	set( i, j , getPixelColor(i - 1, j));
    		    }
    		}
    		for (int j=0; j < dimensiony; j++){
    			int i = 0;
    		    long coordonneex = coinx + i;
    		    long coordonneey = coiny + j;
    		    nombre = suite.Calcule(coordonneesValeur(coordonneex, coordonneey));
    		    if (propriete.propriete(nombre)){
    		    	set( i, dimensiony -1  - j , couleurSuiteP);
    		    }else {
    		    	set( i, dimensiony -1  - j , couleurSuiteNP);
    		    }
    		}
    	}
    	if (cas == 3){
    		for (int i=0; i < dimensionx; i++){
    		    for (int j=dimensiony - 1; j > 0; j--){
    		    	set( i, j , getPixelColor(i, j - 1));
    		    }
    		}
    		for (int i=0; i < dimensionx; i++){
    			int j = dimensiony - 1;
    		    long coordonneex = coinx + i;
    		    long coordonneey = coiny + j;
    		    nombre = suite.Calcule(coordonneesValeur(coordonneex, coordonneey));
    		    if (propriete.propriete(nombre)){
    		    	set( i, dimensiony -1  - j , couleurSuiteP);
    		    }else {
    		    	set( i, dimensiony -1  - j , couleurSuiteNP);
    		    }
    		}
    	} 	
    	if (cas == 4){
    		for (int i=0; i < dimensionx; i++){
    		    for (int j=0; j < dimensiony - 1; j++){
    		    	set( i, j , getPixelColor(i, j + 1));
    		    }
    		}
    		for (int i=0; i < dimensionx; i++){
    			int j = 0;
    		    long coordonneex = coinx + i;
    		    long coordonneey = coiny + j;
    		    nombre = suite.Calcule(coordonneesValeur(coordonneex, coordonneey));
    		    if (propriete.propriete(nombre)){
    		    	set( i, dimensiony -1  - j , couleurSuiteP);
    		    }else {
    		    	set( i, dimensiony -1  - j , couleurSuiteNP);
    		    }
    		}
    	} 	
    }
	
    // Affiche la sous suite dans la spirale
    public void filtre(){
	int i, j;
	long[] ij = new long[2];
	long nombre;
	long dimMoitiex = dimMoitie(dimensionx, dimensiony)[0];
	long dimMoitiey = dimMoitie(dimensionx, dimensiony)[1];
	for (long k = debutIntervalleSousSuite; k < finIntervalleSousSuite; k++){
	    nombre = sousSuite.Calcule(k);
	    ij = valeurCoordonnees(nombre);
	    i = (int) (ij[0] + (long) dimMoitiex - (long) centrex);
	    j = (int) (ij[1] + (long) dimMoitiey - (long) centrey);
	    if ( 0 <= i && i < dimensionx && 0 <= j && j < dimensiony){
	    if (getPixelColor(i,dimensiony -1  - j) == couleurSuiteP){
	    	set(i, dimensiony -1  - j, couleurSousSuiteP);
	    }else{
	    	set(i, dimensiony -1  - j, couleurSousSuiteNP);
	    }
	    }
	}
    }

    // Efface la sous suite
    public void effaceFiltre(){
	for (int i = 0; i < dimensionx; i++) {
	    for (int j = 0; j < dimensionx; j++){
		if (getPixelColor(i,j) == couleurSousSuiteP){
		    set(i, j, couleurSuiteP);
		}
		if (getPixelColor(i,j) == couleurSousSuiteNP){
		    set(i, j, couleurSuiteNP);
		}				
	    }
	}
    }

    // Donne la fréquence des termes de la suite affichés dans l'image vérifiant la propriété
    public double frequenceSuite(){
	long nombreTotal;
	long nombreVerifiantPropriete;
	nombreTotal = dimensionx * dimensiony;
	nombreVerifiantPropriete = 0;
	for (int i=0; i < dimensionx; i++){
	    for (int j=0; j < dimensiony; j++){
		if (getPixelColor(i, j) == couleurSuiteP || getPixelColor(i, j) == couleurSousSuiteP){
		    nombreVerifiantPropriete = nombreVerifiantPropriete + 1;
		}
	    }
	}
	if (nombreTotal == 0) return 0;
	return (double) nombreVerifiantPropriete/nombreTotal;
    }
    
    // Donne la fréquence des termes de la sous suite affichés dans l'image vérifiant la propriété
    public double frequenceSousSuite(){
	long nombreTotal;
	long nombreVerifiantPropriete;
	nombreTotal = 0;
	nombreVerifiantPropriete = 0;
	for (int i=0; i < dimensionx; i++){
	    for (int j=0; j < dimensiony; j++){
		if (getPixelColor(i, j) == couleurSousSuiteP){
		    nombreVerifiantPropriete = nombreVerifiantPropriete + 1;
		    nombreTotal = nombreTotal + 1;
		}
		if(getPixelColor(i, j) == couleurSousSuiteNP){
		    nombreTotal = nombreTotal + 1;
		}
	    }
	}
	if (nombreTotal == 0) return 0;
	return (double) nombreVerifiantPropriete/nombreTotal;
    }
	
    // Détermine les coordonnées du point en bas à droite de l'image
    public long[] coinBasGauche(long centrex, long centrey, long dimensionx, long dimensiony){
	long[] coin = new long [2];
	if (dimensionx % 2 == 0){
	    coin[0] = centrex - ((long) dimensionx/2 -1);
	}else{
	    coin[0] = centrex - (long) dimensionx/2;
	}
	if (dimensiony % 2 == 0){
	    coin[1] = centrey - ((long) dimensiony/2 -1);
	}else{
	    coin[1] = centrey - (long) dimensiony/2;
	}
	return coin;
    }

    // Détermine les coordonnées du point centre de l'image
    public int[] dimMoitie(int dimensionx, int dimensiony){
	int[] dim = new int [2];
	if (dimensionx % 2 == 0){
	    dim[0] = (int) dimensionx/2 -1;
	}else{
	    dim[0] = (int) dimensionx/2;
	}
	if (dimensiony % 2 == 0){
	    dim[1] = (int) dimensiony/2 -1;
	}else{
	    dim[1] = (int) dimensiony/2;
	}
	return dim;
    }
	


    // Donne la valeur du nombre se trouvant au point de coordonnées (i, j)
    long coordonneesValeur(long i, long j) {
	long valeur = 0;
	if (i > 0 && - i < j && j <= i) {
	    valeur = 4 * i * i - 3 * i + j;
	}
	if (j < 0 && j < i && i <= - j) {
	    valeur = 4 * j * j - 3 * j + i;
	}
	if (i < 0 && i <= j && j < - i) {
	    valeur = 4 * i * i - i - j;
	}
	if (j > 0 && - j <= i && i < j) {
	    valeur = 4 * j * j - j - i;
	}
	return  valeur;
    }

    // Donne les cordonnées (i,j) correspondantes à la valeur du nombre donné. Fonction réciproque de la précédente 
    long[] valeurCoordonnees(long valeur) {
	long[]ij = new long[2];
	long i = 0;
	long j = 0;
	long m = (long) Math.sqrt(valeur);
	long n;
	long difference = valeur - m * m;
	if (valeur > 0) {
	    if (m % 2 == 0) {
		n = (long) m / 2;
		if (0 <= difference && difference < 2 * n) {
	            i = - n;
	            j = n - difference;
		}
		if (2 * n <= difference && difference < 4 * n + 1) {
	            i = difference - 3 * n;
	            j = - n;
		}
	    } else {
		n = (long) (m - 1) / 2;
		if (0 <= difference && difference < 2 * n + 1) {
	            i = n + 1;
	            j = difference - n;
		}
		if (2 * n + 1 <= difference && difference < 4 * n + 3) {
	            i = 3 * n + 2 - difference;
	            j = n + 1;
		}
	    }
	}
	ij[0] = i;
	ij[1] = j;
	return  ij;
    }
    
    // Sauve l'image
    public boolean save() {
	try {
	    ImageUtils.saveImage(nom, getImage());
	    return true;
	} catch(Exception e) {
	    System.out.println("Erreur à la sauvegarde de l'image dans '"+nom+"' : "+e);
	    return false;
	}
    }

}