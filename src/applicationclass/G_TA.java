/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import sqlclass.SimpleDataSource;

/**
 * Classe dédié à la gestion des tables d'associations
 * 
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class G_TA {
  
  private static TA_User_GB acces;
  private static TA_GB_GB conteneurs;
  private static TA_GB_BM contenus;
  private static TA_BM_Tag etiquettes;
  private static Connection conn;

  /**
   * CONSTRUCTEURS DE LA CLASSE *************************
   */
  /**
   * Constructeur
   *
   * @param acces
   * @param conteneurs
   * @param contenus
   * @param etiquettes
   */
  public G_TA(TA_User_GB acces, TA_GB_GB conteneurs,
          TA_GB_BM contenus, TA_BM_Tag etiquettes) {
    G_TA.acces = acces;
    G_TA.conteneurs = conteneurs;
    G_TA.contenus = contenus;
    G_TA.etiquettes = etiquettes;
    G_TA.conn = null;
  }

  /**
   * Constructeur
   */
  public G_TA() {
    G_TA.acces = new TA_User_GB();
    G_TA.conteneurs = new TA_GB_GB();
    G_TA.contenus = new TA_GB_BM();
    G_TA.etiquettes = new TA_BM_Tag();
    G_TA.conn = null;
  }

  /**
   * MÉTHODES DE LA CLASSE ********************
   */
  /**
   * Méthode générique qui permet l'ajout d'une association dans la table correspondante aux éléments
   * 
   * @param <L> Type de l'élément de gauche
   * @param <R> Type de l'élément de droite
   * @param <A> Type de l'association des éléments
   * @param <TA> Type du tableau d'association correspondant
   * @param id Identifiant de l'association
   * @param left Objet associé de la gauche
   * @param right Objet associé de la droite
   * @param ta Tableau d'association correspondant
   * @return Vrai si l'ajout a pu être effectué
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  private static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          boolean addElement(int id, L left, R right, TA ta)
          throws SQLException {
    conn = SimpleDataSource.getConnection();
    boolean result = false;
    try {
      result = ta.add((A) new DBA(id, left.getId(), right.getId()));
    } finally {
      conn.close();
    }
    return result;
  }

  /**
   * Effectur l'ajout d'une association entre un utilisateur et un groupbook
   *
   * @param accedeur L'utilisateur ayant accès au groupbook
   * @param groupbook Groupbook corespondant
   * @return Vrai si l'ajout a pu être effectué
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static boolean addAccess(User accedeur, Groupbook groupbook)
          throws SQLException {
    return addElement(1, accedeur, groupbook, acces);
  }

  /**
   *
   * @param parent
   * @param enfant
   * @return Vrai si l'ajout a pu être effectué
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static boolean addConteneur(Groupbook parent, Groupbook enfant)
          throws SQLException {
    return addElement(1, parent, enfant, conteneurs);
  }

  /**
   *
   * @param dossier
   * @param bookmark
   * @return Vrai si l'ajout a pu être effectué
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static boolean addContenu(Groupbook dossier, Bookmark bookmark)
          throws SQLException {
    return addElement(1, dossier, bookmark, contenus);
  }

  /**
   *
   * @param bookmark
   * @param tag
   * @return Vrai si l'ajout a pu être effectué
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static boolean addEtiquette(Bookmark bookmark, Tag tag)
          throws SQLException {
    return addElement(1, bookmark, tag, etiquettes);
  }

  /**
   * 
   * @param <L>
   * @param <R>
   * @param left
   * @param right
   * @param ta
   * @return Vrai si la suppression a pu être effectuée
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  private static <L extends DBField, R extends DBField>
          DBA<L, R> removeElement(L left, R right, TABase<L, R, ?> ta)
          throws SQLException {
    conn = SimpleDataSource.getConnection();
    DBA<L, R> result = null;
    try {
      if (Recherche.contains(ta, 1, left, right)) {
        result = ta.remove(Recherche.getPosition(ta, 1, left, right));
      }
    } finally {
      conn.close();
    }
    return result;
  }

  /**
   *
   * @param accedeur
   * @param groupbook
   * @return Vrai si la suppression a pu être effectuée
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static DBA<User, Groupbook>
          removeAccess(User accedeur, Groupbook groupbook) throws SQLException {
    return removeElement(accedeur, groupbook, acces);
  }

  /**
   *
   * @param parent
   * @param enfant
   * @return Vrai si la suppression a pu être effectuée
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static DBA<Groupbook, Groupbook>
          removeConteneur(Groupbook parent, Groupbook enfant) throws SQLException {
    return removeElement(parent, enfant, conteneurs);
  }

  /**
   *
   * @param dossier
   * @param bookmark
   * @return Vrai si la suppression a pu être effectuée
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static DBA<Groupbook, Bookmark>
          removeContenu(Groupbook dossier, Bookmark bookmark) throws SQLException {
    return removeElement(dossier, bookmark, contenus);
  }

  /**
   *
   * @param bookmark
   * @param tag
   * @return Vrai si la suppression a pu être effectuée
   * @throws SQLException Une erreur s'est produite avec la BD
   */
  public static DBA<Bookmark, Tag>
          removeEtiquette(Bookmark bookmark, Tag tag) throws SQLException {
    return removeElement(bookmark, tag, etiquettes);
  }

  /**
   * GETTEURS ET SETTEURS DE BASE ***************************
   */
  /**
   *
   * @param acces
   * @throws java.io.IOException La table d'association n'a pas été instanciée
   */
  public void setAcces(TA_User_GB acces) throws IOException {
    if (acces == null) {
      throw new IOException();
    }
    G_TA.acces = acces;
  }

  /**
   *
   * @param conteneurs
   * @throws java.io.IOException La table d'association n'a pas été instanciée
   */
  public void setConteneurs(TA_GB_GB conteneurs) throws IOException {
    if (conteneurs == null) {
      throw new IOException();
    }
    G_TA.conteneurs = conteneurs;
  }

  /**
   *
   * @param contenus
   * @throws java.io.IOException La table d'association n'a pas été instanciée
   */
  public void setContenus(TA_GB_BM contenus) throws IOException {
    if (contenus == null) {
      throw new IOException();
    }
    G_TA.contenus = contenus;
  }

  /**
   *
   * @param etiquettes
   * @throws java.io.IOException La table d'association n'a pas été instanciée
   */
  public void setEtiquettes(TA_BM_Tag etiquettes) throws IOException {
    if (etiquettes == null) {
      throw new IOException();
    }
    G_TA.etiquettes = etiquettes;
  }

  /**
   * 
   * 
   * @return
   */
  public static TA_User_GB getAcces() {
    return acces;
  }

  /**
   * 
   * 
   * @return
   */
  public static TA_GB_GB getConteneurs() {
    return conteneurs;
  }

  /**
   *
   * @return
   */
  public static TA_GB_BM getContenus() {
    return contenus;
  }

  /**
   *
   * @return
   */
  public static TA_BM_Tag getEtiquettes() {
    return etiquettes;
  }
}
