/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import sqlclass.SimpleDataSource;

/**
 *
 * @author olivi
 */
public class GestionnaireTA {
  
  private static TA_User_GB acces;
  private static TA_GB_GB conteneurs;
  private static TA_GB_BM contenus;
  private static TA_BM_Tag etiquettes;
  private static Connection conn;

  /**
   * CONSTRUCTEURS DE LA CLASSE
   ****************************/
  
  /**
   * Constructeur
   * @param acces
   * @param conteneurs
   * @param contenus
   * @param etiquettes 
   */
  public GestionnaireTA(TA_User_GB acces, TA_GB_GB conteneurs, 
          TA_GB_BM contenus, TA_BM_Tag etiquettes) {
    GestionnaireTA.acces = acces;
    GestionnaireTA.conteneurs = conteneurs;
    GestionnaireTA.contenus = contenus;
    GestionnaireTA.etiquettes = etiquettes;
    GestionnaireTA.conn = null;
  }
  public GestionnaireTA() {
    GestionnaireTA.acces = new TA_User_GB();
    GestionnaireTA.conteneurs = new TA_GB_GB();
    GestionnaireTA.contenus = new TA_GB_BM();
    GestionnaireTA.etiquettes = new TA_BM_Tag();
    GestionnaireTA.conn = null;
  }
  
  /**
   * MÉTHODES DE LA CLASSE
   ***********************/
  
  private static <L extends DBField, R extends DBField> 
        boolean addElement(L left, R right, TABase<L, R> ta) 
          throws SQLException {
    conn = SimpleDataSource.getConnection();
    boolean result = false;
    try {
      result = ta.add(new DBTA(left.getId(), right.getId()));
    }
    finally {
      conn.close();
    }
    return result;
  }

  public static boolean addAccess(User accedeur, Groupbook groupbook) 
          throws SQLException {
    return addElement(accedeur, groupbook, acces);
  }
  
  public static boolean addConteneur(Groupbook parent, Groupbook enfant) 
          throws SQLException {
    return addElement(parent, enfant, conteneurs);
  }
  
  public static boolean addContenu(Groupbook dossier, Bookmark bookmark) 
          throws SQLException {
    return addElement(dossier, bookmark, contenus);
  }
  
  public static boolean addEtiquette(Bookmark bookmark, Tag tag) 
          throws SQLException {
    return addElement(bookmark, tag, etiquettes);
  }
  
  private static <L extends DBField, R extends DBField>
        DBTA<L, R> removeElement(L left, R right, TABase<L, R> ta) 
          throws SQLException {
    conn = SimpleDataSource.getConnection();
    DBTA<L, R> result = null;
    try {
      if (Recherche.contains(ta, left, right)) {
        result = ta.remove(Recherche.getPosition(ta, left, right));
      }
    }
    finally {
      conn.close();
    }
    return result;
  }
  
  public static DBTA<User, Groupbook> 
        removeAccess(User accedeur, Groupbook groupbook) throws SQLException {
     return removeElement(accedeur, groupbook, acces);
  }
  
  public static DBTA<Groupbook, Groupbook> 
        removeConteneur(Groupbook parent, Groupbook enfant) throws SQLException {
    return removeElement(parent, enfant, conteneurs);
  }
  
  public static DBTA<Groupbook, Bookmark> 
        removeContenu(Groupbook dossier, Bookmark bookmark) throws SQLException {
    return removeElement(dossier, bookmark, contenus);
  }
  
  public static DBTA<Bookmark, Tag> 
        removeEtiquette(Bookmark bookmark, Tag tag) throws SQLException {
    return removeElement(bookmark, tag, etiquettes);
  }
  
  
  
  /**
   * GETTEURS ET SETTEURS DE BASE
   ******************************/
  
  public void setAcces(TA_User_GB acces) throws IOException {
    if (acces == null) throw new IOException();
    this.acces = acces;
  }

  public void setConteneurs(TA_GB_GB conteneurs) throws IOException {
    if (conteneurs == null) throw new IOException();
    this.conteneurs = conteneurs;
  }

  public void setContenus(TA_GB_BM contenus) throws IOException {
    if (contenus == null) throw new IOException();
    this.contenus = contenus;
  }

  public void setEtiquettes(TA_BM_Tag etiquettes) throws IOException {
    if (etiquettes == null) throw new IOException();
    this.etiquettes = etiquettes;
  }

  
  
  public TA_User_GB getAcces() {
    return acces;
  }

  public TA_GB_GB getConteneurs() {
    return conteneurs;
  }

  public TA_GB_BM getContenus() {
    return contenus;
  }

  public TA_BM_Tag getEtiquettes() {
    return etiquettes;
  }
}
