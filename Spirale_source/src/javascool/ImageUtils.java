package javascool;
/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

//package org.javascool.proglets.codagePixels;

// Used to load/save images

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.io.OutputStream;
import java.io.File;
//import org.javascool.macros.Macros;

/** Définit comment lire/ecire une image.
 *
 * @see <a href="ImageUtils.java.html">code source</a>
 * @serial exclude
 */
public class ImageUtils {
  private ImageUtils() {}

  /** Charge une image locale ou distante.
   *
   * @param location Une URL (Universal Resource Location) de la forme: <div id="load-format"><table align="center">
   * <tr><td><tt>http:/<i>path-name</i></tt></td><td>pour aller chercher le contenu sur un site web</td></tr>
   * <tr><td><tt>http:/<i>path-name</i>?param_i=value_i&amp;..</tt></td><td>pour le récupérer sous forme de requête HTTP</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour le charger du système de fichier local ou en tant que ressource Java dans le CLASSPATH</td></tr>
   * <tr><td><tt>jar:/<i>jar-path-name</i>!/<i>jar-entry</i></tt></td><td>pour le charger d'une archive
   *  <div>(exemple:<tt>jar:http://javascool.gforge.inria.fr/javascool.jar!/META-INF/MANIFEST.MF</tt>)</div></td></tr>
   * </table></div>
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static BufferedImage loadImage(String location) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(Macros.getResourceURL(location));
    } catch(IOException e) { throw new RuntimeException(e + " when loading: " + location + " : "+Macros.getResourceURL(location));
    }
    if(image == null) throw new RuntimeException("Unable to load: " + location);
    return image;
  }
  /** Ecrit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.
   *
   * @param location @optional<"stdout:"> Une URL (Universal Resource Location) de la forme: <div id="save-format"><table>
   * <tr><td><tt>ftp:/<i>path-name</i></tt></td><td>pour sauver sur un site FTP.</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour sauver dans le système de fichier local (le <tt>file:</tt> est optionnel).</td></tr>
   * <tr><td><tt>mailto:<i>address</i>?subject=<i>subject</i></tt></td><td>pour envoyer un courriel avec le texte en contenu.</td></tr>
   * <tr><td><tt>stdout:/</tt></td><td>pour l'imprimer dans la console.</td></tr>
   * </table></div>
   * @param image L'image à sauvegarder.
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static void saveImage(String location, BufferedImage image) {
    location = Macros.getResourceURL(location).toString();
    try {
      if(location.startsWith("file:"))
        ImageIO.write(image, "png", new File(location.substring(5)));
      else {
        URLConnection connection = new URL(location).openConnection();
        connection.setDoOutput(true);
        OutputStream writer = connection.getOutputStream();
        ImageIO.write(image, "png", writer);
        writer.close();
      }
    } catch(IOException e) { throw new RuntimeException(e + " when saving: " + location);
    }
  }
}
