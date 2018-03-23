/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerclass;

import applicationclass.DBField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Classe à retirer si elle n'est pas utile au final
 * @author olivi
 * @param <F>
 */


public class TableList<F extends DBField> implements ObservableList<F> {
  private ArrayList<Integer> list = new ArrayList<>(); 
//  private ListChangeListener<F> lcl = new ListChangeListener<>() {
//    @Override
//    public void onChanged(ListChangeListener.Change<? extends E> c) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//  };
  
  @Override
  public void addListener(ListChangeListener listener) {
    
  }

  @Override
  public void removeListener(ListChangeListener listener) {
  }

  @Override
  public boolean setAll(Collection col) {
    return list.addAll(col);
  }

  @Override
  public void remove(int from, int to) {
    for (int i = from; i <= to; i++) {
      list.remove(from);
    }
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public Object[] toArray(Object[] ts) {
    return list.toArray(ts);
  }

  @Override
  public boolean remove(Object o) {
    return list.remove(o);
  }

  @Override
  public boolean containsAll(Collection clctn) {
    return list.containsAll(clctn);
  }

  @Override
  public boolean addAll(Collection clctn) {
    return list.addAll(clctn);
  }

  @Override
  public boolean addAll(int i, Collection clctn) {
    return list.addAll(i, clctn);
  }

  @Override
  public boolean removeAll(Collection clctn) {
    return list.removeAll(clctn);
  }

  @Override
  public boolean retainAll(Collection clctn) {
    return list.retainAll(clctn);
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public int indexOf(Object o) {
    return list.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator listIterator(int i) {
    return list.listIterator(i);
  }

  @Override
  public List subList(int i, int i1) {
    return list.subList(i, i1);
  }

  @Override
  public void addListener(InvalidationListener listener) {
  }

  @Override
  public void removeListener(InvalidationListener listener) {
  }

  @Override
  public boolean addAll(F... elements) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean setAll(F... elements) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean removeAll(F... elements) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean retainAll(F... elements) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean add(F e) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public F set(int i, F e) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void add(int i, F e) {
  }

  @Override
  public F get(int i) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public F remove(int i) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
}
