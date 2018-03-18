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
public class User {
  private int id;
  private String nom;
  private String mdp;
  private ArrayList<GroupBook> bookList;

  public User(int id, String nom, String mdp, ArrayList<GroupBook> bookList) {
    this.id = id;
    this.nom = nom;
    this.mdp = mdp;
    this.bookList = bookList;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  public void setBookList(ArrayList<GroupBook> bookList) {
    this.bookList = bookList;
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public String getMdp() {
    return mdp;
  }

  public ArrayList<GroupBook> getBookList() {
    return bookList;
  }
  
  public void connect() {
    
  }
  
  public void disconnect() {
    
  }
  
  
}
