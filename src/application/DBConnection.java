package application;

import java.sql.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//TODO: clean up and comment code 

/**
 * class for provide all nessesary methods
 * for database interaction with the local
 * sqlite db
 * @author chris
 *
 */
public class DBConnection {

	/**
	 * create the table for the items
	 */
	public static void createTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/bauteil-db.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            
            String sql = "CREATE TABLE IF NOT EXISTS bauteile" +
                    "(id             INTEGER    PRIMARY KEY    AUTOINCREMENT   NOT NULL," +
                    " name           TEXT   NOT NULL," +
                    " menge            INT    NOT NULL," +
                    " ort        CHAR(50)," +
                    " notes         text)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
}
	/**
	 * Fetches all Bauteil entries from db
	 * @return ArrayList<Bauteil>
	 */
	public static ObservableList<Bauteil> selectFromDB() {
		ObservableList<Bauteil> bauteilListe = FXCollections.observableArrayList();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/bauteil-db.db");
            c.setAutoCommit(false);
            
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery(" SELECT * FROM bauteile;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int menge = rs.getInt("menge");
                String ort = rs.getString("ort");
                String notes = rs.getString("notes");
                bauteilListe.add(new Bauteil(id,name,menge,ort,notes));
                
                System.out.print("id: " + id);
                System.out.print(" name: " + name);
                System.out.print(" ort: " + ort);
                System.out.print(" notes: " + notes);
                System.out.println();
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        	return bauteilListe;
	}
	
	public static void updateIntoTable(Bauteil b) {
        Connection c = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/bauteil-db.db");
            c.setAutoCommit(false);
            
            String sql = "UPDATE bauteile set "
            		+"name = ?, "
            		+"menge = ?,"
            		+"ort = ?,"
            		+"notes = ? WHERE id = ?;";
            
            stmt = c.prepareStatement(sql);
            
            int id = b.getId();
            String name = b.getName();
            int menge = b.getMenge();
            String ort = b.getOrt();
            String notes = b.getOrt();
            
            stmt.setString(1, name);
            stmt.setInt(2, menge);
            stmt.setString(3, ort);
            stmt.setString(4, notes);
            stmt.setInt(5, id);
            
            stmt.execute();
            
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
	}
	
	public static  void insertIntoTable(Bauteil b) {
        Connection c = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/bauteil-db.db");
            c.setAutoCommit(false);
            //#################################################
            String name = b.getName();
            int menge = b.getMenge();
            String ort = b.getOrt();
            String notes = b.getNotes();
            
            
            String sql = "INSERT INTO bauteile (name, menge, ort, notes)" +
                    "VALUES (?, ?, ?, ?);";
            stmt = c.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, menge);
            stmt.setString(3, ort);
            stmt.setString(4, notes);
            
            stmt.execute();


            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
	}
	public static  int getLastRow() {
        Connection c = null;
        PreparedStatement stmt = null;
        int idValue = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/bauteil-db.db");
            c.setAutoCommit(false);
            
            
            String sql = "SELECT MAX(id) AS LAST FROM bauteile;";
            stmt = c.prepareStatement(sql);
            ResultSet rs1 = stmt.executeQuery();
            String maxId = rs1.getString("LAST");
             idValue = (Integer.parseInt(maxId))+1;
            //stmt.execute();


            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return idValue;
	}
	
	public static void delteBauteil(Bauteil b) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/bauteil-db.db");
            String sql = "DELETE FROM bauteile WHERE id = ?;";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, b.getId());
            
            
            stmt.execute();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
      }
}
