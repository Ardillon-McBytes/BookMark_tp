/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package sqlclass;

/**
 *
 * @author moi
 */
import sqlclass.SimpleDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Executes all SQL statements from a file or the console.
 */
public class ExecSQL {

  /**
   *
   * @param args
   * @throws SQLException
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static void main(String[] args)
            throws SQLException, IOException, ClassNotFoundException {
        if (args.length == 0) {
            System.out.println(
                    "Usage: java -classpath driver_class_path"
                    + File.pathSeparator
                    + ". ExecSQL propertiesFile [SQLcommandFile]");
            return;
        }

        SimpleDataSource.init(args[0]);

        Scanner in;
        if (args.length > 1) {
            in = new Scanner(new File(args[1]));
        } else {
            in = new Scanner(System.in);
        }

        Connection conn = SimpleDataSource.getConnection();
        try {
            Statement stat = conn.createStatement();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                try {
                    boolean hasResultSet = stat.execute(line);
                    if (hasResultSet) {
                        ResultSet result = stat.getResultSet();
                        showResultSet(result);
                        result.close();
                    }
                } catch (SQLException ex) {
                    while (ex != null) {
                        System.err.println("SQLState: " + ex.getSQLState());
                        System.err.println(" Code: " + ex.getErrorCode());
                        System.err.println(" Message: " + ex.getMessage());
                        ex = ex.getNextException();
                    }

                }
            }
        } finally {
            conn.close();
        }
    }

    /**
     * Prints a result set.
     *
     * @param result the result set
   * @throws java.sql.SQLException
     */
    public static void showResultSet(ResultSet result)
            throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) {
                System.out.print(", ");
            }
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();

        while (result.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    System.out.print(", ");
                }
                System.out.print(result.getString(i));
            }
            System.out.println();
        }
    }
}
