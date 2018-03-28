/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import sqlclass.SimpleDataSource;

/**
 *
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

  public static boolean addAccess(User accedeur, Groupbook groupbook)
          throws SQLException {
    return addElement(1, accedeur, groupbook, acces);
  }

  public static boolean addConteneur(Groupbook parent, Groupbook enfant)
          throws SQLException {
    return addElement(1, parent, enfant, conteneurs);
  }

  public static boolean addContenu(Groupbook dossier, Bookmark bookmark)
          throws SQLException {
    return addElement(1, dossier, bookmark, contenus);
  }

  public static boolean addEtiquette(Bookmark bookmark, Tag tag)
          throws SQLException {
    return addElement(1, bookmark, tag, etiquettes);
  }

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

  public static DBA<User, Groupbook>
          removeAccess(User accedeur, Groupbook groupbook) throws SQLException {
    return removeElement(accedeur, groupbook, acces);
  }

  public static DBA<Groupbook, Groupbook>
          removeConteneur(Groupbook parent, Groupbook enfant) throws SQLException {
    return removeElement(parent, enfant, conteneurs);
  }

  public static DBA<Groupbook, Bookmark>
          removeContenu(Groupbook dossier, Bookmark bookmark) throws SQLException {
    return removeElement(dossier, bookmark, contenus);
  }

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
   * @throws IOException
   */
  public void setAcces(TA_User_GB acces) throws IOException {
    if (acces == null) {
      throw new IOException();
    }
    G_TA.acces = acces;
  }

  public void setConteneurs(TA_GB_GB conteneurs) throws IOException {
    if (conteneurs == null) {
      throw new IOException();
    }
    G_TA.conteneurs = conteneurs;
  }

  public void setContenus(TA_GB_BM contenus) throws IOException {
    if (contenus == null) {
      throw new IOException();
    }
    G_TA.contenus = contenus;
  }

  public void setEtiquettes(TA_BM_Tag etiquettes) throws IOException {
    if (etiquettes == null) {
      throw new IOException();
    }
    G_TA.etiquettes = etiquettes;
  }

  public static TA_User_GB getAcces() {
    return acces;
  }

  public static TA_GB_GB getConteneurs() {
    return conteneurs;
  }

  public static TA_GB_BM getContenus() {
    return contenus;
  }

  public static TA_BM_Tag getEtiquettes() {
    return etiquettes;
  }
}
