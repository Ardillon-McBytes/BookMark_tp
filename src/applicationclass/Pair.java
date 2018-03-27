/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

/**
 * 
 * 
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <R>
 */
public class Pair<R> {

  /**
   *
   */
  public int id;

  /**
   *
   */
  public R value;

  /**
   *
   * @param id
   * @param nom
   */
  public Pair(int id, R nom) {
    this.id = id;
    this.value = nom;
  }
}
