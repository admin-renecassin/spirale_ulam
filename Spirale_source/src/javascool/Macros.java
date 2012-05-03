package javascool;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


public class Macros {
    // @factory

    private Macros() {
    }
	
	
	/** Ouvre une URL (Universal Resource Location) dans un navigateur extérieur.
     * @param location L'URL à afficher.
     *
     * @throws IllegalArgumentException Si l'URL est mal formée.
     * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
     */
    public static void openURL(String location) {
        try {
          if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new java.net.URI(location));
            System.err.println("Note: Ouverture de " + location + " dans un navigateur externe");
          } else {
              openURL2(location);
          }
        } catch (Throwable th) {
            openURL2(location);
        }
    }
    
    // Procédure de secours pour ouvrir une URL
    private static void openURL2(String location) {
     System.err.println("Note: Ouverture de " + location + " dans un browser navigateur (methode de secours)");
                String url = location;
                String os = System.getProperty("os.name").toLowerCase();
                Runtime rt = Runtime.getRuntime();
                try {
                    if (os.indexOf("win") >= 0) {
                        rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                    } else if (os.indexOf("mac") >= 0) {
                        rt.exec("open " + url);
                    } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                        // Do a best guess on unix until we get a platform independent way
                        // Build a list of browsers to try, in this order.
                        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                            "netscape", "opera", "links", "lynx"};
                        // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                        StringBuilder cmd = new StringBuilder();
                        for (int i = 0; i < browsers.length; i++) {
                            cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
                        }
                        rt.exec(new String[]{"sh", "-c", cmd.toString()});
                    } else {
                        throw new RuntimeException("Erreur (pas d'OS détecté) à l'ouverture dans un navigateur de " + location);
                    }
            } catch (Exception e) {
                throw new RuntimeException("Erreur (" + e + ") à l'ouverture dans un navigateur de " + location);
            }
    }

    
    /** Renvoie une URL (Universal Resource Location) normalisée, dans le cas du système de fichier local ou d'une ressource.
     * <p>La fonction recherche l'existence du fichier:
     * (i) par rapport au répertoire de base qui est donné,
     * (ii) par rapport au dossier de travaul "user.dir",
     * (iii) par rapport à la racine des fichier "user.home",
     * (iv) dans les ressources du CLASSPATH.</p>
     * @param location L'URL à normaliser.
     * @param base     Un répertoire de réference pour la normalisation. Par défaut null.
     * @param reading  Précise si nous sommes en lecture (true) ou écriture (false). Par défaut en lecture.
     * @throws IllegalArgumentException Si l'URL est mal formée.
     */
    public static URL getResourceURL(String location, String base, boolean reading) {
        if (base != null) {
            location = base + "/" + location;
        }
        try {
            // @patch : ceci blinde un bug sur les URL jar
            if (location.matches("jar:[^!]*!.*")) {
                String res = location.replaceFirst("[^!]*!/", "");
                URL url = Thread.currentThread().getContextClassLoader().getResource(res);
                if (url != null) {
                    return url;
                } else {
                    throw new IllegalArgumentException("Unable to find " + res + " from " + location + " as a classpath resource");
                }
            }
            if (location.matches("(ftp|http|https|jar|mailto|stdout):.*")) {
                return new URL(location).toURI().normalize().toURL();
            }
            if (location.startsWith("file:")) {
                location = location.substring(5);
            }
            if (reading) {
                File file = new File(location);
                if (file.exists()) {
                    return new URL("file:" + file.getCanonicalPath());
                }
                file = new File(System.getProperty("user.dir"), location);
                if (file.exists()) {
                    return new URL("file:" + file.getCanonicalPath());
                }
                file = new File(System.getProperty("user.home"), location);
                if (file.exists()) {
                    return new URL("file:" + file.getCanonicalPath());
                }
                URL url = Thread.currentThread().getContextClassLoader().getResource(location);
                if (url != null) {
                    return url;
                }
            }
            return new URL("file:" + location);
        } catch (IOException e) {
            throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
        }
    }

    /**
     * @see #getResourceURL(String, String, boolean)
     */
    public static URL getResourceURL(String location, String base) {
        return getResourceURL(location, base, true);
    }

    /**
     * @see #getResourceURL(String, String, boolean)
     */
    public static URL getResourceURL(String location, boolean reading) {
        return getResourceURL(location, null, reading);
    }
    /**
     * @see #getResourceURL(String, String, boolean)
     */
    public static URL getResourceURL(String location) {
        return getResourceURL(location, null, true);
    }

    
}
