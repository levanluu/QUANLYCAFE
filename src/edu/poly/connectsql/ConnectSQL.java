package edu.poly.connectsql;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectSQL {
    public Connection connect;
    
    public ConnectSQL(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    + "database=PROJECT_JAVA", "JAVA", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
