import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RWDatabase {
    public String connectionUrl = "jdbc:sqlserver://localhost:1433;integratedSecurity=true";
    // Account
    private String email;
    private String name;
    private String postalCode;
    private String address;
    private byte admin;
    // Profile
    private String age;
    public boolean validated = false;

    public void makeConnection(String sqlCode) {
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            System.out.println(sqlCode);
            ResultSet rs = statement.executeQuery(sqlCode);
            while (rs.next()) {
                email = rs.getString("Email");
                name = rs.getString("Name");
                address = rs.getString("Address");
                postalCode = rs.getString("postalCode");
                admin = rs.getByte("Admin");
                validated = true;
                System.out.format("| %24s | %-24s | %-24s | %-24s | %-2s |\n", email, name, address, postalCode, admin);
            }
            System.out.println(String.format("| %24s | %-24s | %-24s | %-24s | %-2s |\n", " ", " ", " ", " ", " ").replace(" ", "-"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void setAccount(String email, String pass, String name, String address, String postalCode, byte admin) {
        String tempCode = "USE [Netflix Statistix Database];" +
                "INSERT INTO Account " +
                "VALUES (" +
                "'" + email + "'" +
                "," + "'" + name + "'" +
                "," + "'" + address + "'" +
                "," + "'" + postalCode + "'" +
                "," + "'" + pass + "'" +
                "," + admin + ")";
        makeConnection(tempCode);
    }

    public void checkAccount(String email, String pass) {
        String tempCode = "USE [Netflix Statistix Database];" +
                "SELECT * FROM Account " +
                "WHERE Email = " + "'" + email + "'" + " AND " +
                "Password = " + "'" + pass + "'";
        makeConnection(tempCode);
    }

    public byte checkAdmin(String email, String pass) {
        String tempCode = "USE [Netflix Statistix Database];" +
                "SELECT * FROM Account " +
                "WHERE Email = " + "'" + email + "'" + " AND " +
                "Password = " + "'" + pass + "'";
        makeConnection(tempCode);
        return admin;
    }

    public void delAccount(String email, String pass) {
        String tempCode = "USE [Netflix Statistix Database];" +
                "DELETE FROM Account " +
                "WHERE Email = " + "'" + email + "'" + " AND " +
                "WHERE Password = " + "'" + pass + "'";
        makeConnection(tempCode);
    }

    public void requestPass(String name) {
        /* TODO */
    }

}
