package javascool;

/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

// Used to define the gui

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

// Used to manipulate the image
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Dimension;
//import javax.imageio.ImageIO;
//import org.javascool.macros.Macros;
//import java.io.IOException;

// Used to define a click
import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/** Panneau pour le tracé d'images pixeliques.
*
* @see <a href="IconOutput.java.html">source code</a>
* @serial exclude
*/
public class IconOutput extends JPanel {
 private static final long serialVersionUID = 1L;
 // @bean
 public IconOutput() {
   setBackground(Color.GRAY);
   setPreferredSize(new Dimension(550, 550));
   reset(550, 550);
 }
 /** Routine interne de tracé, ne pas utiliser. */
 @Override
 public void paint(Graphics g) {
   super.paint(g);
   setBounds();
   g.setPaintMode();
   for(int j = 0; j < height; j++)
     for(int i = 0; i < width; i++) {
 int ij = i + j * width;
 if (0 <= ij && ij < image.length) {
   g.setColor(image[ij]);
   g.fillRect(i0 + i * dij, j0 + j * dij, dij, dij);
 }
     }
   Graphics2D g2d = (Graphics2D) g;
   paint2D(g2d);
 }
 /** Cette routine est appellée à chaque tracé et permet de définir un tracé spécifique au dessus de l'image affichée. 
  * - Pour utiliser cette foncctionnalité, il faut définir: <pre>
  * class MyIconInput extends IconInput {
  *   public void paint2D(Graphics2D g) {
  *     // Ici ajouter les g.drawLine g.fillOval g.drawRect g.fillRect souhaité.
  *   }
  * }</pre>
  * @param g2d L'environnement graphique 2D à utiliser pour peindre.
  */
 public void paint2D(Graphics2D g2d) {
 }
 private void setBounds() {
   int di = width > 0 && getWidth() >= width && zoom ? getWidth() / width : 1;
   int dj = height > 0 && getHeight() >= height && zoom ? getHeight() / height : 1;
   dij = di < dj ? di : dj;
   i0 = (getWidth() - width * dij) / 2;
   j0 = (getHeight() - height * dij) / 2;
 }
 /**  Efface et initialize l'image.
  * @param width Taille horizontale de l'image.
  * @param height Taille verticale de l'image.
  * @param zoom Ajuste automatiquement la taille de l'image au display si true (par défaut), sinon fixe 1 pixel de l'image à 1 pixel de l'affichage.
  * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
  */
 public final IconOutput reset(int width, int height, boolean zoom) {
   if(width > 550 || height > 550 || width * height > 550 * 550)
     throw new IllegalArgumentException("L'image est trop grande ("+width+", "+height+") !");
   this.zoom = zoom;
   /*
   if (width <= 0)
     width = 300;
   if (height <= 0)
     height = 300;
   if(width % 2 == 0)
     width++;
   if(height % 2 == 0)
     height++;
   */
   image = new Color[(this.width = width) * (this.height = height)];
   for(int ij = 0; ij < this.width * this.height; ij++)
     image[ij] = Color.WHITE;
   repaint(0, 0, getWidth(), getHeight());
   return this;
 }
 /**
  * @see #reset(int, int, boolean)
  */
 public final IconOutput reset(int width, int height) {
   return reset(width, height, true);
 }
 /** Initialize l'image à partir d'un fichier.
  * @param location L'URL (Universal Resource Location) de l'image.
  * @param zoom Ajuste automatiquement la taille de l'image au display si true (par défaut), sinon fixe 1 pixel de l'image à 1 pixel de l'affichage.
  * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
  *
 public IconOutput reset(String location, boolean zoom) throws IOException {
   // Fait 2//3 essais sur l'URL si besoin
   for (int n = 0; n < 3; n++) {
     BufferedImage img = ImageIO.read(Macros.getResourceURL(location));
     if(img != null)
 return reset(img, zoom);
   }
   throw new IOException("Unable to load the image " + location);
 }
 /**
  * @see #reset(String, boolean)
  *
 public final IconOutput reset(String location)  throws IOException {
   return reset(location, true);
 }*/
 /** Initialize l'image à partir d'une image en mémoire.
  * @param img L'image qui va initialiser le tracé.
  * @param zoom Ajuste automatiquement la taille de l'image au display si true (par défaut), sinon fixe 1 pixel de l'image à 1 pixel de l'affichage.
  * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
  */
 public IconOutput reset(BufferedImage img, boolean zoom) {
   reset(img.getWidth(), img.getHeight(), zoom);
   for(int j = 0; j <  img.getHeight(); j++)
     for(int i = 0; i < img.getWidth(); i++)
 image[i + width * j] = new Color(img.getRGB(i, j));
   repaint(0, 0, getWidth(), getHeight());
   return this;
 }
 /**
  * @see #reset(BufferedImage, boolean)
  */
 public final IconOutput reset(BufferedImage img) {
   return reset(img, true);
 }
 /** Renvoie les dimensions de l'image. */
 public Dimension getDimension() {
   return new Dimension(width, height);
 }
 /** Renvoie une image dans laquelle le contenu de l'affichage est copié.
  * @return Le contenu de l'affichage sous forme d'image.
  */
 public BufferedImage getImage() {
   BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
   for(int j = 0; j < img.getHeight(); j++)
     for(int i = 0; i < img.getWidth(); i++)
 img.setRGB(i, j, image[i + width * j].getRGB());
   return img;
 }
 /** Définit la valeur d'un pixel.
  * @param x Abscisse du pixel, dans {0, width{.
  * @param y Ordonnée du pixel, dans {0, height{.
  * @param  c Couleur: "black" (default), "blue", "cyan", "gray", "green", "magenta", "orange", "pink", "red", "white", "yellow".
  * @return La valeur true si le pixel est dans les limites de l'image, false sinon.
  */
 public boolean set(int x, int y, String c) {
   return set(x, y, getColor(c));
 }
 /** Définit la valeur d'un pixel.
  * @param x Abscisse du pixel, dans {0, width{.
  * @param y Ordonnée du pixel, dans {0, height{.
  * @param v L'intensité en niveau de gris du pixel de 0 (noir) à 255 (blanc).
  * @return La valeur true si le pixel est dans les limites de l'image, false sinon.
  */
 public boolean set(int x, int y, int v) {
   v = v < 0 ? 0 : v > 255 ? 255 : v;
   return set(x, y, new Color(v, v, v));
 }
 /** Définit la valeur d'un pixel.
  * @param x Abscisse du pixel, dans {0, width{.
  * @param y Ordonnée du pixel, dans {0, height{.
  * @param c L'intensité en couleur du pixel.
  * @return La valeur true si le pixel est dans les limites de l'image, false sinon.
  */
 public boolean set(int x, int y, Color c) {
   if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
     setBounds();
     int ij = x + y * width;
     image[ij] = c;
     repaint(i0 + x * dij, j0 + y * dij, dij, dij);
     return true;
   } else
     return false;
 }
 /** Renvoie la valeur d'un pixel.
  * @param x Abscisse du pixel, dans {0, width{.
  * @param y Ordonnée du pixel, dans {0, height{.
  * @return L'intensite du pixel entre 0 et 255 ou 0 si le pixel n'est pas dans l'image.
  */
 public int getIntensity(int x, int y) {
   if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
     Color c = image[x + y * width];
     return (c.getRed() + c.getGreen() + c.getBlue()) / 3;
   } else
     return 0;
 }
 /**  Renvoie la valeur d'un pixel.
  * @param x Abscisse du pixel, dans {0, width{.
  * @param y Ordonnée du pixel, dans {0, height{.
  * @return La couleur du pixel ou "undefined" si le pixel n'est pas dans l'image.
  */
 public String getColor(int x, int y) {
   if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
     Color c = image[x + y * width];
     return colors.containsKey(c) ? colors.get(c) : c.toString();
   } else
     return "undefined";
 }
 /**  Renvoie la valeur d'un pixel.
  * @param x Abscisse du pixel, dans {0, width{.
  * @param y Ordonnée du pixel, dans {0, height{.
  * @return La couleur du pixel ou black si le pixel n'est pas dans l'image.
  */
 public Color getPixelColor(int x, int y) {
   if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
     Color c = image[x + y * width];
     return c;
   } else
     return Color.BLACK;
 }
 private Color image[];
 private int width, height, i0, j0, dij;
 boolean zoom = true;

 private static HashMap<Color, String> colors = new HashMap<Color, String>();
 private static Color getColor(String color) {
   try { return (Color) Class.forName("java.awt.Color").getField(color).get(null);
   } catch(Exception e) {
     return Color.BLACK;
   }
 }
 private static void putColors(String color) {
   colors.put(getColor(color), color);
 }
 static {
   putColors("black");
   putColors("blue");
   putColors("cyan");
   putColors("gray");
   putColors("green");
   putColors("magenta");
   putColors("orange");
   putColors("pink");
   putColors("red");
   putColors("white");
   putColors("yellow");
 }

 /** Renvoie la position horizontale du dernier clic de souris dans l'image. */
 public int getClicX() {
   return clicX;
 }
 /** Renvoie la position verticale du dernier clic de souris dans l'image. */
 public int getClicY() {
   return clicY;
 }
 private int clicX = 0, clicY = 0;
 {
   addMouseListener(new MouseListener() {
 private static final long serialVersionUID = 1L;
 @Override
 public void mouseReleased(MouseEvent e) {
   // x = i0 + i * dij, y = j0 + j * dij
   clicX = (e.getX() - i0) / dij;
   clicY = (e.getY() - j0) / dij;
   if(runnable != null)
     new Thread(runnable).start();
 }
 @Override
   public void mousePressed(MouseEvent e) {}
 @Override
   public void mouseClicked(MouseEvent e) {}
 @Override
   public void mouseEntered(MouseEvent e) {}
 @Override
   public void mouseExited(MouseEvent e) {}
     });
 }

 /** Définit une portion de code appellée à chaque clic de souris.
  * @param runnable La portion de code à appeler, ou null si il n'y en a pas.
  * @return Cet objet, permettant de définir la construction <tt>new CurveOutput().setRunnable(..)</tt>.
  */
 public IconOutput setRunnable(Runnable runnable) {
   this.runnable = runnable;
   return this;
 }
 private Runnable runnable = null;
}
	
