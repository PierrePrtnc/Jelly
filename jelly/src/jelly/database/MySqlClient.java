package jelly.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * MySQL client
 * @author Fobec 2010
 */
public class MySqlClient {

	private String dbURL = "";
    private String user = "";
    private String password = "";
    private String database = "";
    private java.sql.Connection dbConnect = null;
    private java.sql.Statement dbStatement = null;
 
    /**
     * Constructeur
     * @param url
     * @param user
     * @param password
     */
    public MySqlClient(String url, String user, String password, String database) {
        this.dbURL = url;
        this.user = user;
        this.password = password;
        this.database = database;
    }
 
    
    public java.sql.Connection getDbConnect() {
		return dbConnect;
	}
    
    /**
     * Connecter Ã  la base de donnÃ©e
     * @return false en cas d'Ã©chec
     */
    public Boolean connect() {
        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.dbConnect = DriverManager.getConnection("jdbc:mysql://" + this.dbURL + "/" + this.database, this.user, this.password);
            this.dbStatement = this.dbConnect.createStatement();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
 
    /**
     * Executer une requete SQL
     * @param sql
     * @return resultat de la requete
     */
    public ResultSet exec(String sql) {
        try {
            ResultSet rs = this.dbStatement.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
 
    /**
     * Fermer la connexion au serveur de DB
     */
    public void close() {
        try {
            this.dbStatement.close();
            this.dbConnect.close();
            this.dbConnect.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// 
//    /**
//     * Exemple d'utilisation de la class
//     * @param args
//     */
//    public static void main(String[] args) {
//    	MySqlClient mysqlCli = new MySqlClient("remotemysql.com:3306", "EvR1zSObCT", "eMA8QUCWIG", "EvR1zSObCT");
//        if (mysqlCli.connect()) {
//            try {
//                ResultSet rs = mysqlCli.exec("SELECT * FROM user");
//                if (rs != null) {
//                    while (rs.next()) {
//                        System.out.println("Valeur: " + rs.getString(1));
//                    }
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(MySqlClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            System.out.println("Mysql connection failed !!!");
//        }
//        mysqlCli.close();
//    }
}
