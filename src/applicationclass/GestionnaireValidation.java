/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;


/**
 * 
 * @author olivi
 */
public class GestionnaireValidation {
  
  public static void main(String[] args) {
    String[] courriels = {"mon@email.com", "mon@e.mai.l.com", "m.o.n@email.com", 
      "", "mon.@email.com", "@email.com", "mon@.com", "mon@email.", 
      "mon@email.com.", ".mon@email.com", "mon@com"};
  
    for (String c : courriels) {
      System.out.println(c + " est " + GestionnaireValidation.courriel(c));
    }
    
    String[] noms = {"", "...", "o.o", 
      "Patate", "Bal-Tazard-", "&?%$&*?", "mµnicipalité", "(a)()(", 
      "o-.", "asdjaw.", ".oipoi"};
  
    for (String n : noms) {
      System.out.println(n + " est " + GestionnaireValidation.nom(n));
    }
  }
  
  /**
   * Compte le nombre d'occurence d'une chaine de caractère dans une autre
   * @param element Élement recherché
   * @param text Chaine de caractères
   * @return Nombre de fois qu'on retrouve l'élément dans la chaine de caractère
   */
  private static int nbOccurence(String element, String text) {
    return text.length() - text.replace(element, "").length();
  }
  
  private static char premierChar(String text) {
    return text.charAt(0);
  }
  
  private static char dernierChar(String text) {
    return text.charAt(text.length() - 1);
  }
  
  private static boolean chaineValide(String text, int maximum) {
    return text.length() > 5 && text.length() < maximum;
  }
  
  private static String alphaSeulement(String text) {
    return text.replaceAll("[^A-Za-z]", "");
  }
  
  public static boolean courriel(String adresse) {
    return EmailValidator.getInstance().isValid(adresse);
    
    /*// Méthode avec JavaMail (Library javax.mail.jar)
    boolean result = true;
    try {
      InternetAddress emailAddr = new InternetAddress(adresse);
      emailAddr.validate();
    } catch (AddressException ex) {
      result = false;
    }
    return result;*/
    
    /*if (adresse == null || adresse.isEmpty() || 
            !adresse.contains(".") || 1 != nbOccurence("@", adresse)) {
      return false;
    }
    
    String[] aCommercial = adresse.split("@");
    String[] point = aCommercial[aCommercial.length - 1].split("\\.");
    String debut = alphaSeulement(aCommercial[0]);
    String fin = alphaSeulement(point[point.length - 1]);
    if (dernierChar(debut) != dernierChar(aCommercial[0]) || 
            premierChar()) {
      return false;
    }
    return (aCommercial.length < 2 || point.length < 2 || 
            !(debut.isEmpty() || premierChar(adresse) != premierChar(debut)) && 
            !(fin.isEmpty() || dernierChar(adresse) != dernierChar(fin)));*/
  }
  
  public static boolean nom(String nom) {
    return !(nom == null || nom.isEmpty() || 
            "".equals(alphaSeulement(nom)) || !chaineValide(nom, 20));
  }
  
  public static boolean url(String url) {
    String[] schemes = {"http","https"};
    UrlValidator urlValidator = new UrlValidator(schemes);
    return urlValidator.isValid(url);

    //return !(url == null || url.isEmpty());
    
    /*if (!url.toLowerCase().startsWith("http") || 
            "".equals(alphaSeulement(url))) {
      return false;
    }
    return true;*/
  }
}
