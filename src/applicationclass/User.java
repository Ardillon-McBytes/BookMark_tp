/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe pour les utilisateurs
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class User implements DBField {
  private int id;
  private String nom;
  private String mdp;

  public User(int id, String nom, String mdp, 
          ArrayList<Groupbook> groupBooks, TA_User_GB ta) {
    this.id = id;
    this.nom = nom;
    this.mdp = mdp;
    setGroupBooks(groupBooks, ta);
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public void setValue(String value) {
    setMdp(value);
  }
  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  public void setGroupBooks(ArrayList<Groupbook> groupbooks, TA_User_GB ta) {
    for (Groupbook gb : groupbooks) {
      
    }
    //groupbooks;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getNom() {
    return nom;
  }

   @Override
  public String getValue() {
    return getMdp();
  }
  public String getMdp() {
    return mdp;
  }

  public ArrayList<Groupbook> getGroupbooks(TA_User_GB ta) {
    ArrayList<Groupbook> gbs = new ArrayList<>();
    return gbs;
  }
  
  public void connect() {
    
  }
  
  public void disconnect() {
    
  }

  

 
  
  
}
