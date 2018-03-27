/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import sqlclass.SimpleDataSource;

/**
 *
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <A>
 * @param <L>
 * @param <R>
 */
public class TABase<L extends DBField, R extends DBField, A extends DBA<L, R>> extends ArrayList<A> {

  private static String nomTable;
  private static Pair<String>[] elements;
  private static ArrayList<Pair<Integer>> idModifications;
  private static ArrayList<Pair<Integer>> modifications;
  private static ArrayList<Pair<Integer>> effacements;
  private Connection conn;

  /**
   *
   * @param nomTable
   * @param elements
   * @throws IOException
   */
  protected static void constructor(String nomTable, Pair<String>[] elements) throws IOException {
    if (elements.length < 3) {
      throw new IOException();
    }
    TABase.nomTable = nomTable;
    TABase.elements = elements;
    TABase.idModifications = new ArrayList<>();
    TABase.modifications = new ArrayList<>();
  }

  /**
   *
   * @param nomTable
   * @param colId
   * @param champId
   * @param colLeft
   * @param champLeft
   * @param colRight
   * @param champRight
   */
  protected static void constructor(String nomTable,
          int colId, String champId,
          int colLeft, String champLeft,
          int colRight, String champRight) {
    TABase.nomTable = nomTable;
    TABase.elements = new Pair[3];
    elements[0] = new Pair(colId, champId);
    elements[1] = new Pair(colLeft, champLeft);
    elements[2] = new Pair(colRight, champRight);
    TABase.idModifications = new ArrayList<>();
    TABase.modifications = new ArrayList<>();
  }

  /**
   *
   * @param nomTable
   * @param elements
   * @throws IOException
   */
  public TABase(String nomTable, Pair<String>[] elements) throws IOException {
    this.conn = null;
    TABase.constructor(nomTable, elements);
  }

  /**
   *
   * @param nomTable
   * @param colId
   * @param champId
   * @param colLeft
   * @param champLeft
   * @param colRight
   * @param champRight
   */
  public TABase(String nomTable,
          int colId, String champId,
          int colLeft, String champLeft,
          int colRight, String champRight) {
    this.conn = null;
    TABase.constructor(nomTable,
            colId, champId,
            colLeft, champLeft,
            colRight, champRight);
  }

  /**
   *
   */
  public TABase() {
    this.conn = null;
    TABase.constructor("table",
            1, "id",
            2, "left",
            3, "right");
  }

  /**
   *
   * @return
   */
  public String getNomTable() {
    return nomTable;
  }

  /**
   *
   * @return
   */
  public Pair<String>[] getElements() {
    return elements;
  }

  /**
   *
   * @return
   */
  public int getColId() {
    return elements[0].id;
  }

  /**
   *
   * @return
   */
  public String getChampId() {
    return (String) elements[0].value;
  }

  /**
   *
   * @return
   */
  public int getColLeft() {
    return elements[1].id;
  }

  /**
   *
   * @return
   */
  public String getChampLeft() {
    return elements[1].value;
  }

  /**
   *
   * @return
   */
  public int getColRight() {
    return elements[2].id;
  }

  /**
   *
   * @return
   */
  public String getChampRight() {
    return elements[2].value;
  }

  /**
   *
   * @param n
   * @return
   * @throws IOException
   */
  public int getColN(int n) throws IOException {
    if (n < 0 || n >= elements.length) {
      throw new IOException();
    }
    return elements[n].id;
  }

  /**
   *
   * @param n
   * @return
   * @throws IOException
   */
  public String getChampN(int n) throws IOException {
    if (n < 0 || n >= elements.length) {
      throw new IOException();
    }
    return elements[n].value;
  }

  /**
   *
   * @return
   */
  public ArrayList<Pair<Integer>> getModifications() {
    return idModifications;
  }

  /**
   *
   * @param e
   * @return
   */
  @Override
  public boolean add(A e) {
    if (!contains(e)) {
      // Trouver une manière de vérifier dans a BD
      // seullement si elle a eu des modifications 
      // en lien avec une des deux FK.
      modifications.add(e.getPair());
      return super.add(e);
    }
    return false;
  }

  /**
   *
   * @param e
   * @return
   */
  /*@Override
  public boolean remove(A e) {
    if (contains(e)) {
      modifications.add(e.getPair());
      return super.remove(e);
    }
    return false;
  }*/
  /**
   *
   * @param <L>
   * @param left
   * @return
   */
  public <L extends DBField> boolean modificationsContainsLeft(L left) {
    return modificationsContainsLeft(left.getId());
  }

  /**
   *
   * @param leftId
   * @return
   */
  public boolean modificationsContainsLeft(int leftId) {
    // Doit être réimplémentée avec l'objet Pair<Integer> ou DBA
    return idModifications.contains(leftId);
  }

  /**
   *
   * @param <R>
   * @param right
   * @return
   */
  public <R extends DBField> boolean modificationsContainsRight(R right) {
    return modificationsContainsRight(right.getId());
  }

  /**
   *
   * @param rightId
   * @return
   */
  public boolean modificationsContainsRight(int rightId) {
    return idModifications.contains(rightId);
  }

  /**
   * Modifie l'identifiant des enregistrements ayant comm identifiant l'objet L.
   *
   * @param id Identifiant du L à changer dans la TA
   * @param left Objet L d'ont on veut utiliser comme nouveau identifiant
   * @return Le nombre d'enregistrements modifiés
   */
  public int modifyLeftIds(int id, L left) {
    return TABase.this.modifyLeftIds(id, left.getId());
  }

  /**
   * Modifie l'identifiant des enregistrements ayant comm identifiant l'objet L.
   *
   * @param id Identifiant du L à changer dans la TA
   * @param leftId Nouvel identifiant de l'objet L d'ont on veut metre à jour
   * @return Le nombre d'enregistrements modifiés
   */
  public int modifyLeftIds(int id, int leftId) {
    int count = 0;
    int i = 0;
    for (DBA<L, R> ta : this) {
      if (id == ta.getLeft()) {
        ta.setLeft(leftId);
        idModifications.add(new Pair(i, leftId));
        count++;
      }
      i++;
    }
    return count;
  }

  /**
   * Modifie l'identifiant des enregistrements ayant comm identifiant l'objet R.
   *
   * @param id Identifiant du R à changer dans la TA
   * @param right Objet R d'ont on veut utiliser comme nouveau identifiant
   * @return Le nombre d'enregistrements modifiés
   */
  public int modifyRightIds(int id, R right) {
    return TABase.this.modifyRightIds(id, right.getId());
  }

  /**
   * Modifie l'identifiant des enregistrements ayant comm identifiant l'objet R.
   *
   * @param id Identifiant du R à changer dans la TA
   * @param rightId Nouvel identifiant de l'objet R d'ont on veut metre à jour
   * @return Le nombre d'enregistrements modifiés
   */
  public int modifyRightIds(int id, int rightId) {
    int count = 0;
    int i = 0;
    for (DBA<L, R> ta : this) {
      if (id == ta.getRight()) {
        ta.setRight(rightId);
        idModifications.add(new Pair(i, rightId));
        count++;
      }
      i++;
    }
    return count;
  }

  /**
   *
   * @param right
   * @param lefts
   * @return
   */
  public ArrayList<L> getLefts(R right, ArrayList<L> lefts) {
    return Recherche.getLefts(right, this, lefts);
  }

  /**
   *
   * @param left
   * @param rights
   * @return
   */
  public ArrayList<R> getRights(L left, ArrayList<R> rights) {
    return Recherche.getRights(left, this, rights);
  }

  /**
   *
   * @return @throws SQLException
   */
  public boolean updateIdDB() throws SQLException {
    if (idModifications.isEmpty()) {
      return false;
    }
    conn = SimpleDataSource.getConnection();
    try {
      PreparedStatement statLeft = conn.prepareStatement(
              "UPDATE " + getNomTable() + " SET ? = ? WHERE " + getChampId() + " = ?");
      PreparedStatement statRight = statLeft;
      statLeft.setString(1, getChampId());
      statRight.setString(1, getChampRight());

      PreparedStatement selectLeft = conn.prepareStatement(
              "SELECT " + getChampId() + " FROM " + getNomTable() + " WHERE " + getChampId() + " = ?");
      PreparedStatement selectRight = selectLeft;

      for (Pair i : idModifications) {
        statLeft.setInt(2, i.id);
        statLeft.executeUpdate();
      }
      idModifications.clear();
    } catch (SQLException e) {
      conn.rollback();
      return false;
    } finally {
      conn.close();
    }
    return true;
  }

  /**
   *
   * @return @throws SQLException
   */
  public boolean updateDB() throws SQLException {
    if (!idModifications.isEmpty()) {
      updateIdDB();
    }

    conn = SimpleDataSource.getConnection();
    try {
      conn.setAutoCommit(false);
      PreparedStatement statLeft = conn.prepareStatement(
              "UPDATE " + getNomTable() + " SET ? = ? WHERE " + getChampId() + " = ?");

      PreparedStatement statRight = statLeft;
      statLeft.setString(1, getChampLeft());
      statRight.setString(1, getChampRight());

      DBA<L, R> mod;
      for (Pair i : modifications) {
        mod = this.get(i.id);
        statLeft.setInt(1, mod.getLeft());
        statLeft.executeUpdate();
        statRight.setInt(2, mod.getRight());
        statRight.executeUpdate();
        conn.commit();
      }
      modifications.clear();
    } catch (SQLException e) {
      conn.rollback();
      return false;
    } finally {
      conn.setAutoCommit(true);
      conn.close();
    }
    return true;
  }

  /**
   *
   * @return @throws SQLException
   */
  public int loadFromDB() throws SQLException {
    if (!modifications.isEmpty()) {
      updateDB();
    } else if (!idModifications.isEmpty()) {
      updateIdDB();
    }

    clear();
    DBA ta;

    conn = SimpleDataSource.getConnection();
    try {
      Statement stat = conn.createStatement();
      ResultSet rs = stat.executeQuery("SELECT * FROM " + getNomTable());
      while (rs.next()) {
        ta = new DBA(rs.getInt(getColId()), rs.getInt(getColLeft()), rs.getInt(getColRight()));
        this.add((A) ta);
      }
    } catch (SQLException e) {
      clear();
    } finally {
      conn.close();
    }
    return this.size();
  }
}
