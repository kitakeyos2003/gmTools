package gm.db;

import gm.config.RMIConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBManager {

    private static Logger log = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    private Connection conn;

    /*private static final String DB_USERNAME = "sable";
	private static final String DB_PWD = "sable";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/xj_account?autoReconnect=true";*/
    private String db_username;
    private String db_pwd;
    private String db_url;

    private DBManager() {
        init();
    }

    public static DBManager getInstance() {
        if (null == instance) {
            instance = new DBManager();
        }
        return instance;
    }

    private void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            db_username = RMIConfig.getInstance().getDBUsername();
            db_pwd = RMIConfig.getInstance().getDBPwd();
            db_url = RMIConfig.getInstance().getDBURL();
            log.debug("db_username=" + db_username + "db_pwd=" + db_pwd + "db_url=" + db_url);
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            log.debug("init jdbc Driver error : ", e);
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.debug("init DB Connection error : ", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.debug("init error : ", e);
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void destroy() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }
}
