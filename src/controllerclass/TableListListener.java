/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerclass;

import applicationclass.DBField;
import javafx.collections.ListChangeListener;

/**
 * Classe à retirer si elle n'est pas utile au final
 * @author Olivier Lemay Dostie
 * @serial 1.0
 * @param <F> 
 */
public class TableListListener<F extends DBField> implements ListChangeListener<F> {

  @Override
  public void onChanged(Change<? extends F> c) {
    while (c.next()) {
      if (c.wasPermutated()) {
        for (int i = c.getFrom(); i < c.getTo(); ++i) {
          //permutate
        }
      } else if (c.wasUpdated()) {
        //update item
      } else {
        for (F remitem : c.getRemoved()) {
          //remitem.remove(Outer.this);
        }
        for (F additem : c.getAddedSubList()) {
          //additem.add(Outer.this);    
        }
      }
    }
  }
}
