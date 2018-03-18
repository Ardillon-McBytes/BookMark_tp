/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe pour les dossiers de marquepage
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class GroupBook {
  private String nom;
  private String description;
  private ArrayList<Bookmark> bookmark;
  private ArrayList<GroupBook> bookList;
  private ArrayList<User> partages;

  public GroupBook(String nom, String description, ArrayList<Bookmark> bookmark, 
          ArrayList<GroupBook> bookList, ArrayList<User> partages) {
    this.nom = nom;
    this.description = description;
    this.bookmark = bookmark;
    this.bookList = bookList;
    this.partages = partages;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setBookmark(ArrayList<Bookmark> bookmark) {
    this.bookmark = bookmark;
  }

  public void setBookList(ArrayList<GroupBook> bookList) {
    this.bookList = bookList;
  }

  public void setPartages(ArrayList<User> partages) {
    this.partages = partages;
  }

  public String getNom() {
    return nom;
  }

  public String getDescription() {
    return description;
  }

  public ArrayList<Bookmark> getBookmark() {
    return bookmark;
  }

  public ArrayList<GroupBook> getBookList() {
    return bookList;
  }

  public boolean isPartager() {
    return !partages.isEmpty();
  }
  
  public boolean add(Bookmark book) {
    if (!bookmark.stream().noneMatch((b) -> (b.getId() == book.getId()))) {
      return false;
    }
    bookmark.add(book);
    return true;
  }
}
