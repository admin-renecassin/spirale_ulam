import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

import latex.JLabelLatex;

public class Fenetre extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    // Dimensisons de l'image et des polices de caractères
    int largeurImage = 160;
    int hauteurImage = 128;
    int taillePolice = 18;
    Font police = new Font("Times New Roman", Font.PLAIN, 16);
   
    // Temps maximal de calcul d'une image, en millisecondes
    int temps = 10000;
    
    // Nom et chemin de l'image à enregistrer
    String cheminFichierImage = "";
    String nomFichier = cheminFichierImage + "image.png";
	
    // Définition des suites, sous suites, couleurs et propriétés
    Suite u = new Suite(1, 0, 1, 0);
    Suite v = new Suite(1, 1, 1, 41);
    Propriete p = new Propriete();
    Color couleuruP = new Color(0, 0, 0);
    Color couleuruNP = new Color(255, 255, 255);
    Color couleurvP = new Color(255, 0, 0);
    Color couleurvNP = new Color(0, 255, 0);
    
    // Bouton pour sauver l'image et nom du fichier
    JButton sauver = new JButton("Sauver l'image");

    // Menu pour le choix du type de suite que l'on veut représenter, une propriété y est automatiquement associée
    String[] typeSuite = { "Entiers naturels", "Suite M", "Suite F", "Perso", "Expression"};
    JComboBox typeSuiteListe = new JComboBox(typeSuite);
   
    // Labels et zone de texte pour le choix des coordonnees du centre de l'image
    JLabel labelCentre = new JLabelLatex().JLatex("\\mbox{Coordonnées du centre : }", taillePolice);
    JLabel labelCentrex = new JLabelLatex().JLatex("x = ", taillePolice);
    JLabel labelCentrey = new JLabelLatex().JLatex("y = ", taillePolice);
    JTextField jtfCentrex = new JTextField("0");
    JTextField jtfCentrey = new JTextField("0");
   
    // Choix des coefficients a, b et c pour afficher la suite des nombres de la forme an^2+bn+c
	JLabel labelSuiteNombreForme = new JLabelLatex().JLatex("\\mbox{Nombres de la forme } an^2+bn+c \\mbox{, où :}", taillePolice);
    JTextField jtfSuiteNombrea = new JTextField("0");
    JTextField jtfSuiteNombreb = new JTextField("1");
    JTextField jtfSuiteNombrec = new JTextField("0");
    JLabel labelSuiteNombrea = new JLabelLatex().JLatex("a = ", taillePolice); 
    JLabel labelSuiteNombreb = new JLabelLatex().JLatex("b = ", taillePolice);
    JLabel labelSuiteNombrec = new JLabelLatex().JLatex("c = ", taillePolice);
    
    // Expression explicite de la suite
	JLabel expressionSuite = new JLabelLatex().JLatex("\\mbox{Expression suite :}", taillePolice);
    JTextField jtfExpressionSuite = new JTextField("n%5");
    
    // Pour le temps maximal de calcul
	JLabel tempsMaximal = new JLabelLatex().JLatex("\\mbox{Temps maximal de calcul (ms) :}", taillePolice);
    JTextField jtfTempsMaximal = new JTextField("10000");
    
    // Définition de l'image
    Image image = new Image(nomFichier, largeurImage, hauteurImage, 0, 0, u, v, p, couleuruP, couleuruNP, couleurvP, couleurvNP, 0, 1000, true, temps);

    // Chaînes de caractères pour afficher les fréquences
    String frequence = "Fréquence = ";
    String frequenceSousSuite = "  Fréquence de la sous suite= ";

    // Label pour la fréquence des nombres vérifiant la propriété choisie avec la suite ainsi que de la sous suite
    JLabel labelFrequence = new JLabel(frequence);
    JLabel labelFrequencePartiel = new JLabel(frequenceSousSuite);

    // Choix des coefficients a, b et c pour afficher la sous suite donnée par les indices an^2+bn+c
    JLabel labelNombreForme = new JLabelLatex().JLatex("\\mbox{Sous suite d'indices } an^2+bn+c", taillePolice); 
    JTextField jtfNombrea = new JTextField("1");
    JTextField jtfNombreb = new JTextField("1");
    JTextField jtfNombrec = new JTextField("41");
    JLabel labelNombrea = new JLabelLatex().JLatex("a = ", taillePolice); 
    JLabel labelNombreb = new JLabelLatex().JLatex("b = ", taillePolice); 
    JLabel labelNombrec = new JLabelLatex().JLatex("c = ", taillePolice); 
	
    // Choix de l'intervalle pour n et les nombres precédents
    JLabel labelVariationNombre1 = new JLabelLatex().JLatex("\\mbox{pour n allant de }", taillePolice); 
    JLabel labelVariationNombre2 = new JLabelLatex().JLatex("\\mbox{à }", taillePolice); 
    JTextField jtfNombreDebutIntervalle = new JTextField("0");
    JTextField jtfNombreFinIntervalle = new JTextField("1000");

    // Pour réinitialiser l'image
    JButton reinitialiser = new JButton("Réinitialiser");

    // Gestion des zones de texte modifiables
    GestionClic gc = new GestionClic();

	
	
	public Fenetre(){
		/*try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(Fenetre.this);
		} catch (InstantiationException e) {
		} catch (ClassNotFoundException e) {
		} catch (UnsupportedLookAndFeelException e) {
		} catch (IllegalAccessException e) {}*/
		
	this.setTitle("Spirales de nombres");
	this.setSize(700,800);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	// Panneau qui contient les éléments précédents
	JPanel panneau = new JPanel();
	panneau.removeAll();
	
	// Disposition verticale et centrale des éléments dans la fenêtre
	panneau.setLayout(new BoxLayout(panneau, BoxLayout.PAGE_AXIS));

	// Prenière ligne d'éléments : haut 1
	JPanel haut1 = new JPanel();
	haut1.setBackground(Color.WHITE);
	
	// Le premier c'est sauver l'image et écoute du bouton		
	sauver.addActionListener(gc);
	haut1.add(sauver);
	panneau.add(haut1);
	
	// Affichage et écoute du choix du type de suite
	typeSuiteListe.addActionListener(gc);
	haut1.add(typeSuiteListe);

	// Affichage et écoute du choix des coordonnées du centre de l'image
	haut1.add(labelCentre);
	haut1.add(labelCentrex);
	jtfCentrex.setFont(police);
	jtfCentrex.setPreferredSize(new Dimension(50, 30));
	jtfCentrex.setForeground(Color.BLUE);
	jtfCentrex.addActionListener(gc);
	jtfCentrex.addKeyListener(new ClavierListener());
	haut1.add(jtfCentrex);
	haut1.add(labelCentrey);
	jtfCentrey.setFont(police);
	jtfCentrey.setPreferredSize(new Dimension(50, 30));
	jtfCentrey.setForeground(Color.BLUE);
	jtfCentrey.addActionListener(gc);
	jtfCentrey.addKeyListener(new ClavierListener());
	haut1.add(jtfCentrey);
	panneau.add(haut1);

	// Choix des coefficients de la suite donnée par an^2+bn+c à placer
	JPanel suiteabc = new JPanel();
	suiteabc.setBackground(Color.WHITE);
	jtfSuiteNombrea.setFont(police);
	jtfSuiteNombrea.setPreferredSize(new Dimension(50, 30));
	jtfSuiteNombrea.setForeground(Color.BLUE);
	jtfSuiteNombreb.setFont(police);
	jtfSuiteNombreb.setPreferredSize(new Dimension(50, 30));
	jtfSuiteNombreb.setForeground(Color.BLUE);
	jtfSuiteNombrec.setFont(police);
	jtfSuiteNombrec.setPreferredSize(new Dimension(50, 30));
	jtfSuiteNombrec.setForeground(Color.BLUE);
         
	jtfSuiteNombrea.addActionListener(gc);
	jtfSuiteNombrea.addKeyListener(new ClavierListener()); 
	jtfSuiteNombreb.addActionListener(gc);
	jtfSuiteNombreb.addKeyListener(new ClavierListener()); 
	jtfSuiteNombrec.addActionListener(gc);
	jtfSuiteNombrec.addKeyListener(new ClavierListener()); 

	suiteabc.add(labelSuiteNombreForme);
	suiteabc.add(labelSuiteNombrea);
	suiteabc.add(jtfSuiteNombrea);
	suiteabc.add(labelSuiteNombreb);
	suiteabc.add(jtfSuiteNombreb);
	suiteabc.add(labelSuiteNombrec);
	suiteabc.add(jtfSuiteNombrec);
	panneau.add(suiteabc);

	// Affichage zone de texte pour l'expression de la suite et le temps maximal de calcul
	JPanel expressionTemps = new JPanel();
	expressionTemps.setBackground(Color.WHITE);
	expressionTemps.add(expressionSuite);
	jtfExpressionSuite.setFont(police);
	jtfExpressionSuite.setPreferredSize(new Dimension(100, 30));
	jtfExpressionSuite.setForeground(Color.BLUE);
	jtfExpressionSuite.addActionListener(gc);
	expressionTemps.add(jtfExpressionSuite);
	expressionTemps.add(tempsMaximal);
	
	jtfTempsMaximal.setFont(police);
	jtfTempsMaximal.setPreferredSize(new Dimension(50, 30));
	jtfTempsMaximal.setForeground(Color.BLUE);
	jtfTempsMaximal.addActionListener(gc);
	expressionTemps.add(jtfTempsMaximal);
	panneau.add(expressionTemps);
	
	// Création et placement de l'image 
	image.creeSpirale();
	image.setBackground(Color.WHITE);
	panneau.add(image);
		
	// Affichage des fréquences de la suite et de la sous suite
	JPanel basFrequence = new JPanel();
	basFrequence.setBackground(Color.WHITE);
	labelFrequence.setText(frequence + image.frequenceSuite());
	basFrequence.add(labelFrequence);
	labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	basFrequence.add(labelFrequencePartiel);
	panneau.add(basFrequence);

	// Label de titre pour les sous suites
	JPanel bas1Titre = new JPanel();
	bas1Titre.setBackground(Color.WHITE);
	bas1Titre.add(labelNombreForme);
	panneau.add(bas1Titre);

	// Choix des coefficients des sous suites données par an^2+bn+c
	JPanel bas1 = new JPanel();
	bas1.setBackground(Color.WHITE);
	jtfNombrea.setFont(police);
	jtfNombrea.setPreferredSize(new Dimension(50, 30));
	jtfNombrea.setForeground(Color.BLUE);
	jtfNombreb.setFont(police);
	jtfNombreb.setPreferredSize(new Dimension(50, 30));
	jtfNombreb.setForeground(Color.BLUE);
	jtfNombrec.setFont(police);
	jtfNombrec.setPreferredSize(new Dimension(50, 30));
	jtfNombrec.setForeground(Color.BLUE);
	jtfNombreDebutIntervalle.setFont(police);
	jtfNombreDebutIntervalle.setPreferredSize(new Dimension(50, 30));
	jtfNombreDebutIntervalle.setForeground(Color.BLUE);
	jtfNombreFinIntervalle.setFont(police);
	jtfNombreFinIntervalle.setPreferredSize(new Dimension(50, 30));
	jtfNombreFinIntervalle.setForeground(Color.BLUE);
          
	jtfNombrea.addActionListener(gc);
	jtfNombrea.addKeyListener(new ClavierListener()); 
	jtfNombreb.addActionListener(gc);
	jtfNombreb.addKeyListener(new ClavierListener()); 
	jtfNombrec.addActionListener(gc);
	jtfNombrec.addKeyListener(new ClavierListener()); 
	jtfNombreDebutIntervalle.addActionListener(gc);
	jtfNombreFinIntervalle.addActionListener(gc);

	bas1.add(labelNombrea);
	bas1.add(jtfNombrea);
	bas1.add(labelNombreb);
	bas1.add(jtfNombreb);
	bas1.add(labelNombrec);
	bas1.add(jtfNombrec);
	bas1.add(labelVariationNombre1);
	bas1.add(jtfNombreDebutIntervalle);
	bas1.add(labelVariationNombre2);
	bas1.add(jtfNombreFinIntervalle);
	panneau.add(bas1);

	// Bouton pour réinitialiser l'image
	JPanel bas2 = new JPanel();
	bas2.setBackground(Color.WHITE);
	bas2.add(reinitialiser);
	reinitialiser.addActionListener(gc);
	panneau.add(bas2);
	
    this.setContentPane(panneau);
	this.setVisible(true);
	
	
	// Exemples de création de vidéos
	/*for (int i=1; i<200; i++){
		image.creeSpirale();
		image.sousSuite.setabc(1, 1, i);
		image.filtre();
		image.sousSuite.setabc(1, 1, -i);
		image.filtre();
		if (i < 10){
		image.setNom(cheminFichierImage + "image-00" + i + ".png");
				}
		if (10 <= i && i < 100){
		image.setNom(cheminFichierImage + "image-0" + i + ".png");
				}					
		if (100 <= i && i < 1000){
		image.setNom(cheminFichierImage + "image-" + i + ".png");
				}					
		image.save();
	}*/

	/*	image.suite.setType(1);
		image.propriete.setType(1);
		image.creeSpirale();
	for (int i=0; i<200; i++){
		image.sousSuite.setabc(0, 1, i);
		image.creeSpirale();
		image.filtre();
		//image.fonctionPartiel.setabc(1, 1, i);
		//image.filtre();
		//image.fonctionPartiel.setabc(1, 1, -i);
		//image.filtre();
		if (i < 100){
		image.setNom(cheminFichierImage + "image-00" + i + ".png");
				}
		if (10 <= i && i < 100){
		image.setNom(cheminFichierImage + "image-0" + i + ".png");
				}					
		if (100 <= i && i < 1000){
		image.setNom(cheminFichierImage + "image-" + i + ".png");
				}					
		image.save();
	}*/

	/*	image.suite.setType(1);
		image.propriete.setType(1);
	for (int i=0; i<200; i++){
		//image.sousSuite.setabc(0, 1, i);
		image.creeSpirale();
		image.fonctionPartiel.setabc(4, i, 0);
		image.filtre();
		//image.fonctionPartiel.setabc(1, 1, -i);
		//image.filtre();
		if (i < 100){
		image.setNom(cheminFichierImage + "image-00" + i + ".png");
				}
		if (10 <= i && i < 100){
		image.setNom(cheminFichierImage + "image-0" + i + ".png");
				}					
		if (100 <= i && i < 1000){
		image.setNom(cheminFichierImage + "image-" + i + ".png");
				}					
		image.save();
	}*/
	
	/*	image.suite.setType(4);
		image.propriete.setType(1);
	for (int i=1; i<400; i++){
		image.creeSpirale();
		image.sousSuite.setabc(0, i, 1);
		jtfSuiteNombrea.setText(Long.toString(image.fonction.a));
		jtfSuiteNombreb.setText(Long.toString(image.fonction.b));
		jtfSuiteNombrec.setText(Long.toString(image.fonction.c));
		if (i < 10){
		image.setNom(cheminFichierImage + "image-00" + i + ".png");
				}
		if (10 <= i && i < 100){
		image.setNom(cheminFichierImage + "image-0" + i + ".png");
				}					
		if (100 <= i && i < 1000){
		image.setNom(cheminFichierImage + "image-" + i + ".png");
				}					
		image.save();
	}*/
	
	/*	image.suite.setType(4);
		image.propriete.setType(1);
	for (int i=1; i<400; i++){
		image.creeSpirale();
		image.sousSuite.setabc(0, i, 0);
		jtfSuiteNombrea.setText(Long.toString(image.fonction.a));
		jtfSuiteNombreb.setText(Long.toString(image.fonction.b));
		jtfSuiteNombrec.setText(Long.toString(image.fonction.c));
		if (i < 10){
		image.setNom(cheminFichierImage + "image-00" + i + ".png");
				}
		if (10 <= i && i < 100){
		image.setNom(cheminFichierImage + "image-0" + i + ".png");
				}					
		if (100 <= i && i < 1000){
		image.setNom(cheminFichierImage + "image-" + i + ".png");
				}					
		image.save();
	}*/

	/*	image.suite.setType(4);
		image.propriete.setType(3);
	for (int i=1; i<400; i++){
		image.creeSpirale();
		//image.sousSuite.setabc(0, 50, 100*i);
		//image.sousSuite.setabc(0, i, 1);  // 0015
		image.sousSuite.setabc(0, 11, 200*i);  // 0016
		jtfSuiteNombrea.setText(Long.toString(image.fonction.a));
		jtfSuiteNombreb.setText(Long.toString(image.fonction.b));
		jtfSuiteNombrec.setText(Long.toString(image.fonction.c));
		if (i < 10){
		image.setNom(cheminFichierImage + "image-00" + i + ".png");
				}
		if (10 <= i && i < 100){
		image.setNom(cheminFichierImage + "image-0" + i + ".png");
				}					
		if (100 <= i && i < 1000){
		image.setNom(cheminFichierImage + "image-" + i + ".png");
				}					
		image.save();
	}*/

	}

    // Gestion des champs de texte
    class GestionClic implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == sauver) {
		image.save(); 
	    } 
	    if (e.getSource() == jtfCentrex || e.getSource() == jtfCentrey) {
		try{
	    	image.setCentre(Integer.parseInt(jtfCentrex.getText()),Integer.parseInt(jtfCentrey.getText()));
	    	image.creeSpirale();
	    	labelFrequence.setText(frequence + image.frequenceSuite());
		} catch (NumberFormatException excep){}
	    }
	    if (e.getSource() == typeSuiteListe) {
		JComboBox cb = (JComboBox)e.getSource();
		String suiteNom = (String)cb.getSelectedItem();
		if (suiteNom == "Entiers naturels"){
		    image.suite.setType(1);
		    image.propriete.setType(1);
		    image.creeSpirale();
		    labelFrequence.setText(frequence + image.frequenceSuite());
		}
		if (suiteNom == "Suite M"){
		    image.suite.setType(2);
		    image.propriete.setType(2);
		    image.creeSpirale();
		    labelFrequence.setText(frequence + image.frequenceSuite());
		}
		if (suiteNom == "Suite F"){
		    image.suite.setType(3);
		    image.propriete.setType(1);
		    image.creeSpirale();
		    labelFrequence.setText(frequence + image.frequenceSuite());
		}
		if (suiteNom == "Perso"){
		    image.suite.setType(4);
		    image.propriete.setType(1);
		    image.creeSpirale();
		    labelFrequence.setText(frequence + image.frequenceSuite());
		}
		if (suiteNom == "Expression"){
		    image.suite.setType(5);
		    image.propriete.setType(1);
		    image.creeSpirale();
		    labelFrequence.setText(frequence + image.frequenceSuite());
		}
	    }

	    if (e.getSource() == jtfSuiteNombrea || e.getSource() == jtfSuiteNombreb || e.getSource() == jtfSuiteNombrec) {
	    	try{
		image.suite.setabc(Integer.parseInt(jtfSuiteNombrea.getText()), Integer.parseInt(jtfSuiteNombreb.getText()), Integer.parseInt(jtfSuiteNombrec.getText()));
		image.creeSpirale();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    	}catch (NumberFormatException excep){}
	    }
	    if (e.getSource() == jtfExpressionSuite) {
			image.suite.setType(5);
			image.propriete.setType(1);
			try{
			image.suite.setExpression(jtfExpressionSuite.getText());
			image.creeSpirale();
			labelFrequence.setText(frequence + image.frequenceSuite());
			labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
			}catch (NumberFormatException excep){}
		    } 
	    if (e.getSource() == jtfTempsMaximal) {
		try{
	    	image.setTempsMaximalCalcul(Integer.parseInt(jtfTempsMaximal.getText()));
			image.creeSpirale();
			labelFrequence.setText(frequence + image.frequenceSuite());
			labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
		}catch (NumberFormatException excep){}
		    } 
	    if (e.getSource() == jtfNombrea || e.getSource() == jtfNombreb || e.getSource() == jtfNombrec || e.getSource() == jtfNombreDebutIntervalle || e.getSource() == jtfNombreFinIntervalle) {
		try{
		image.setIntervalle(Integer.parseInt(jtfNombreDebutIntervalle.getText()), Integer.parseInt(jtfNombreFinIntervalle.getText()));
		image.sousSuite.setabc(Integer.parseInt(jtfNombrea.getText()), Integer.parseInt(jtfNombreb.getText()), Integer.parseInt(jtfNombrec.getText()));
		image.effaceFiltre();
		image.filtre();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
		}catch (NumberFormatException excep){}
	    }
	    if (e.getSource() == reinitialiser){
		image.creeSpirale();
	    }
	}
    }

    // Gestion des flèches du clavier pour augmenter ou diminuer les paramètres
    class ClavierListener implements KeyListener{

	public void keyPressed(KeyEvent event) {
	    try{
	    if (event.getKeyCode() == 37 && (event.getSource() == jtfCentrex || event.getSource() == jtfCentrey)){
		image.setCentre(Integer.parseInt(jtfCentrex.getText()) - 1, Integer.parseInt(jtfCentrey.getText()));
		jtfCentrex.setText(Integer.toString(Integer.parseInt(jtfCentrex.getText()) - 1));
		image.decale(2);
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 39 && (event.getSource() == jtfCentrex || event.getSource() == jtfCentrey)){
		image.decale(1);
		image.setCentre(Integer.parseInt(jtfCentrex.getText()) + 1, Integer.parseInt(jtfCentrey.getText()));
		jtfCentrex.setText(Integer.toString(Integer.parseInt(jtfCentrex.getText()) + 1));
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
		}
	    if (event.getKeyCode() == 38 && (event.getSource() == jtfCentrex || event.getSource() == jtfCentrey)){
		image.setCentre(Integer.parseInt(jtfCentrex.getText()), Integer.parseInt(jtfCentrey.getText()) + 1);
		jtfCentrey.setText(Integer.toString(Integer.parseInt(jtfCentrey.getText()) + 1));
		image.decale(3);
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
		}
		if (event.getKeyCode() == 40 && (event.getSource() == jtfCentrex || event.getSource() == jtfCentrey)){
		image.setCentre(Integer.parseInt(jtfCentrex.getText()), Integer.parseInt(jtfCentrey.getText()) - 1);
		jtfCentrey.setText(Integer.toString(Integer.parseInt(jtfCentrey.getText()) - 1));
		image.decale(4);
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
		}
	    if (event.getKeyCode() == 38 && event.getSource() == jtfSuiteNombrea){
		image.suite.setabc(Integer.parseInt(jtfSuiteNombrea.getText()) + 1, Integer.parseInt(jtfSuiteNombreb.getText()), Integer.parseInt(jtfSuiteNombrec.getText()));
		jtfSuiteNombrea.setText(Integer.toString(Integer.parseInt(jtfSuiteNombrea.getText()) + 1));
		image.creeSpirale();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 40 && event.getSource() == jtfSuiteNombrea){
		image.suite.setabc(Integer.parseInt(jtfSuiteNombrea.getText()) - 1, Integer.parseInt(jtfSuiteNombreb.getText()), Integer.parseInt(jtfSuiteNombrec.getText()));
		jtfSuiteNombrea.setText(Integer.toString(Integer.parseInt(jtfSuiteNombrea.getText()) - 1));
		image.creeSpirale();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 38 && event.getSource() == jtfSuiteNombreb){
		image.suite.setabc(Integer.parseInt(jtfSuiteNombrea.getText()), Integer.parseInt(jtfSuiteNombreb.getText()) + 1, Integer.parseInt(jtfSuiteNombrec.getText()));
		jtfSuiteNombreb.setText(Integer.toString(Integer.parseInt(jtfSuiteNombreb.getText()) + 1));
		image.creeSpirale();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 40 && event.getSource() == jtfSuiteNombreb){
		image.suite.setabc(Integer.parseInt(jtfSuiteNombrea.getText()), Integer.parseInt(jtfSuiteNombreb.getText()) - 1, Integer.parseInt(jtfSuiteNombrec.getText()));
		jtfSuiteNombreb.setText(Integer.toString(Integer.parseInt(jtfSuiteNombreb.getText()) - 1));
		image.creeSpirale();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 38 && event.getSource() == jtfSuiteNombrec){
		image.suite.setabc(Integer.parseInt(jtfSuiteNombrea.getText()), Integer.parseInt(jtfSuiteNombreb.getText()), Integer.parseInt(jtfSuiteNombrec.getText()) + 1);
		jtfSuiteNombrec.setText(Integer.toString(Integer.parseInt(jtfSuiteNombrec.getText()) + 1));
		image.creeSpirale();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 40 && event.getSource() == jtfSuiteNombrec){
		image.suite.setabc(Integer.parseInt(jtfSuiteNombrea.getText()), Integer.parseInt(jtfSuiteNombreb.getText()), Integer.parseInt(jtfSuiteNombrec.getText()) - 1);
		jtfSuiteNombrec.setText(Integer.toString(Integer.parseInt(jtfSuiteNombrec.getText()) - 1));
		image.creeSpirale();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 38 && event.getSource() == jtfNombrea){
		image.effaceFiltre();
		image.sousSuite.setabc(Integer.parseInt(jtfNombrea.getText()) - 1, Integer.parseInt(jtfNombreb.getText()), Integer.parseInt(jtfNombrec.getText()));
		jtfNombrea.setText(Integer.toString(Integer.parseInt(jtfNombrea.getText()) + 1));
		image.filtre();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 40 && event.getSource() == jtfNombrea){
		image.effaceFiltre();
		image.sousSuite.setabc(Integer.parseInt(jtfNombrea.getText()) - 1, Integer.parseInt(jtfNombreb.getText()), Integer.parseInt(jtfNombrec.getText()));
		jtfNombrea.setText(Integer.toString(Integer.parseInt(jtfNombrea.getText()) - 1));
		image.filtre();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 38 && event.getSource() == jtfNombreb){
		image.effaceFiltre();
		image.sousSuite.setabc(Integer.parseInt(jtfNombrea.getText()), Integer.parseInt(jtfNombreb.getText()) + 1, Integer.parseInt(jtfNombrec.getText()));
		jtfNombreb.setText(Integer.toString(Integer.parseInt(jtfNombreb.getText()) + 1));
		image.filtre();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 40 && event.getSource() == jtfNombreb){
		image.effaceFiltre();
		image.sousSuite.setabc(Integer.parseInt(jtfNombrea.getText()), Integer.parseInt(jtfNombreb.getText()) - 1, Integer.parseInt(jtfNombrec.getText()));
		jtfNombreb.setText(Integer.toString(Integer.parseInt(jtfNombreb.getText()) - 1));
		image.filtre();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 38 && event.getSource() == jtfNombrec){
		image.effaceFiltre();
		image.sousSuite.setabc(Integer.parseInt(jtfNombrea.getText()), Integer.parseInt(jtfNombreb.getText()), Integer.parseInt(jtfNombrec.getText()) + 1);
		jtfNombrec.setText(Integer.toString(Integer.parseInt(jtfNombrec.getText()) + 1));
		image.filtre();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    if (event.getKeyCode() == 40 && event.getSource() == jtfNombrec){
		image.effaceFiltre();
		image.sousSuite.setabc(Integer.parseInt(jtfNombrea.getText()), Integer.parseInt(jtfNombreb.getText()), Integer.parseInt(jtfNombrec.getText()) - 1);
		jtfNombrec.setText(Integer.toString(Integer.parseInt(jtfNombrec.getText()) - 1));
		image.filtre();
		labelFrequence.setText(frequence + image.frequenceSuite());
		labelFrequencePartiel.setText(frequenceSousSuite + image.frequenceSousSuite());
	    }
	    }catch (NumberFormatException excep){}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {
	}
    }	
}
