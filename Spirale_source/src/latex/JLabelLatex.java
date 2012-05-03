package latex;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;


public class JLabelLatex extends JLabel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    String latex ;
	
    public JLabel JLatex(String chaine, int taillePolice){
	latex = latex + " ";
	TeXFormula formula = new TeXFormula(chaine);
	TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, taillePolice);
	BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);
	return new JLabel(icon);  
    }
}
