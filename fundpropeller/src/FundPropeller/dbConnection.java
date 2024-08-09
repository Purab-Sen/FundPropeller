package FundPropeller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class dbConnection {

    Connection c;
    Statement s;

    public dbConnection () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/fundpropeller", "root", "");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
