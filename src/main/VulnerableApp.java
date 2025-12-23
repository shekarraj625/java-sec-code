import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class VulnerableApp {

    // ❌ Hardcoded credentials (Security Hotspot)
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "password123";

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        // ❌ SQL Injection vulnerability
        String query = "SELECT * FROM users WHERE username = '" + username + "'";

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", DB_USER, DB_PASS);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            System.out.println("User found: " + rs.getString("username"));
        }

        // ❌ Command Injection vulnerability
        System.out.print("Enter OS command: ");
        String cmd = scanner.nextLine();
        Runtime.getRuntime().exec(cmd);

        // ❌ Insecure file handling (Path Traversal)
        System.out.print("Enter file name to read: ");
        String fileName = scanner.nextLine();
        File file = new File("data/" + fileName);

        BufferedReader br = new BufferedReader(new FileReader(file));
        System.out.println(br.readLine());

        // ❌ Use of weak cryptography (MD5)
        String password = "secret";
        String hash = org.apache.commons.codec.digest.DigestUtils.md5Hex(password);
        System.out.println("Password hash: " + hash);

        br.close();
        scanner.close();
        conn.close();
    }
}
