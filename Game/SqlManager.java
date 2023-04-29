package Game;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SqlManager {
    private static final String CONFIG_FILE = "config.properties";

    public void addData(String name, int score) 
    		throws SQLException, ClassNotFoundException, IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(CONFIG_FILE);
        props.load(in);
        in.close();

        String driver = props.getProperty("driver");
        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.prepareStatement("INSERT INTO person (Name, score) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
        System.out.println("Done!");
    }
}