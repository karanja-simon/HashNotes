/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.utils;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author RESEARCH2
 */
@SuppressWarnings("FieldMayBeFinal")
public class DatabaseConnection {

    private static Connection con;
    private static String dbHost;
    private static String dbUsername;
    private static String dbPassword;
    private static NetworkServerControl server = null;

    public static void initDatabaseConnection() {
        File file = new File("db");
        //System.out.println(file.getAbsolutePath());
        DatabaseConnection.dbHost = "jdbc:derby://localhost:1527/" + file.getAbsolutePath().replace("\\", "/") + "/hashnotes";
        DatabaseConnection.dbUsername = "hashnotes";
        DatabaseConnection.dbPassword = "hashnotes";
    }

    public static boolean startDerbyServer() {
        try {
            server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
            server.start(null);
        } catch (UnknownHostException e) {
            MyLogger.log(DatabaseConnection.class, e.getMessage());
            return false;
        } catch (Exception e) {
            MyLogger.log(DatabaseConnection.class, e.getMessage());
            return false;
        }
        return true;
    }

    public static final boolean setConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            con = DriverManager.getConnection(dbHost, dbUsername, dbPassword);
            //System.out.println("connection ok");
        } catch (SQLException | ClassNotFoundException SQL_Err) {
            MyLogger.log(DatabaseConnection.class, SQL_Err.getMessage());
           // System.out.println(SQL_Err);
            return false;
        }

        return true;
    }

    public static Connection getConnection() {
        initDatabaseConnection();
        startDerbyServer();
        setConnection();
        return con;
    }

    public static NetworkServerControl getServer() {
        return server;
    }

}
