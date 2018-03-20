/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe qui représente la table d'association (TA) entre 
 * les Groupbooks conteneurs (parent) et ceux contenus (enfant).
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_GB_GB extends ArrayList<DBTA<Groupbook, Groupbook>> {
  
  /**
   * Modifie l'identifiant des enregistrements ayant comme 
   * identifiant celui de l'objet Groupbook.
   * (remplace un dossier par un autre... est-ce utile?)
   * @param id Identifiant du Groupbook parent à changer dans la TA
   * @param gb Objet Groupbook d'ont on veut utiliser comme nouveau identifant
   * @return Le nombre d'enregistrements modifiés
   * @throws java.lang.Exception 
   */
  public int setParent(int id, Groupbook gb) throws Exception {
    if (id == gb.getId()) throw new Exception();
    int modifie = 0;
    
    for (DBTA<Groupbook, Groupbook> ta : this) {
      if (id == ta.getLeft()) {
        ta.setLeft(gb);
        modifie++;
      }
    }
    return modifie;
  }
  
  /**
   * Modifie l'identifiant des enregistrements ayant comme
   * identifiant celui de l'objet Groupbook.
   * @param id Identifiant du Groupbook enfant à changer dans la TA
   * @param gb Objet Groupbook d'ont on veut utiliser comme nouveau identifiant
   * @return Le nombre d'enregistrements modifiés
   * @throws java.lang.Exception 
   */
  public int setChild(int id, Groupbook gb) throws Exception {
    if (id == gb.getId()) throw new Exception();
    int modifie = 0;
    
    for (DBTA<Groupbook, Groupbook> ta : this) {
      if (id == ta.getRight()) {
        ta.setRight(gb);
        modifie++;
      }
    }
    return modifie;
  }
  
  /**
   * Obtien la liste des Groupbooks (dossiers) qui contiennent un autre spécifié
   * @param child Dossier enfant qu'on veut obtenir ses emplacements
   * @param gbs Liste des dossiers dans lesquels on fait la recherche
   * @return Liste des dossiers parents à celui spécifié
   */
  public ArrayList<Groupbook> getParents(
          Groupbook child, ArrayList<Groupbook> gbs) {
    return Recherche.getParentGroupbooks(child, this, gbs);
  }
  
  /**
   * Obtien la liste des Groupbooks (dossiers) qui sont contenus dans celui spécifié
   * @param parent Dossier parent (racide)
   * @param gbs Liste des dossiers dans lesquels on fait la recherche
   * @return Liste des dossiers enfants à celui spécifié
   */
  public ArrayList<Groupbook> getChilds(
          Groupbook parent, ArrayList<Groupbook> gbs) {
    return Recherche.getChildGroupbooks(parent, this, gbs);
  }
  
  /**
   * Obtien la liste des Groupbooks (dossiers) qui 
   * sont contenus dans celui spécifié récursivement
   * @param parent Dossier parent (racine))
   * @param gbs Liste des dossiers dans lesquels on fait la recherche
   * @return Liste complète des dossiers parents à celui spécifié
   */
  public ArrayList<Groupbook> getAllChilds(
          Groupbook parent, ArrayList<Groupbook> gbs) {
    ArrayList<Groupbook> result = getChilds(parent, gbs);
    /*for 
    return Recherche.getChildGroupbooks(parent, this, gbs);*/
    return null;
  }
  
  private ArrayList<Groupbook> recursiveChild(ArrayList<Groupbook> result,
          Groupbook parent, ArrayList<Groupbook> childs) {
    //result.addAll(recursiveChild(getChilds(parent, childs)));
    return result;
  }
}
