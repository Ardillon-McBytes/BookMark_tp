/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

/**
 *
 * @author olivi
 * @param <R>
 */
public class Pair<R> {
  public int id;
  public R value;
  
  public Pair(int id, R nom) {
    this.id = id;
    this.value = nom;
  }
}
