/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

/**
 *
 * @author olivi
 */
public interface DBField {
  public void setId(int id);
  public void setNom(String nom);
  public void setValue(String value);
  public int getId();
  public String getNom();
  public String getValue();
}
