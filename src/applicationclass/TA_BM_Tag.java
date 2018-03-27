/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import static applicationclass.G_Tag.tag;
import static applicationclass.TA_GB_BM.id_Bm;
import static applicationclass.TA_GB_BM.id_group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sqlclass.SimpleDataSource;

/**
 * Classe d'association entre les marquepage et les tags (Étiquetage des liens)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_BM_Tag extends TABase<Bookmark, Tag, DBA<Bookmark, Tag>> {

  /**
   * 
   */
  public TA_BM_Tag() {
    TABase.constructor("bookmark_tag",
            1, "id",
            2, "id_bookmark",
            3, "id_tag");
  }

  /**
   * 
   * 
   * @param idTag 
   * @return 
   * @throws SQLException 
   */
  static public ArrayList<Bookmark> getBmFromTag(int idTag) throws SQLException {
    ArrayList<Bookmark> list_bm = new ArrayList<Bookmark>();

    Connection conn = SimpleDataSource.getConnection();
    try {

      String query = "SELECT * "
              + "FROM bookmark_tag "
              + "WHERE id_tag = ?";
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, idTag);

      ResultSet rs = ps.executeQuery();

      G_BM G_BM = new G_BM();
      if (rs.next()) {
        G_BM.getBm(rs.getInt(2));
        list_bm.add(G_BM.getBookMark());
      }

    } finally {
      conn.close();

    }

    return list_bm;

  }

  /**
   *
   * @param tag
   * @param bm
   * @throws SQLException
   */
  static public void addTagToBm(int tag, int bm) throws SQLException {

    Connection conn = SimpleDataSource.getConnection();

    try {

      PreparedStatement stat = conn.prepareStatement(
              " INSERT INTO `bookmark_tag` (`id_bookmark`, `id_tag`) "
              + "VALUES ('" + tag + "','"
              + bm + "')");

      stat.executeUpdate();

    } finally {
      conn.close();

    }

  }

}
