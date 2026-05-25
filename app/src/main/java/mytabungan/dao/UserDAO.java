package mytabungan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mytabungan.database.DatabaseConfig;
import mytabungan.models.User;
import mytabungan.utils.PasswordUtil;

public class UserDAO {
    public boolean register(User user){
        String data = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.connect();
        PreparedStatement ps = conn.prepareStatement(data)){
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashedPassword);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String usernameOrEmail, String password){
        String sql = "SELECT * FROM users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConfig.connect();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String hashedPassword = result.getString("password");
                return PasswordUtil.checkPassword(password, hashedPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email){
        String sql = "SELECT id FROM users WHERE email = ?";

        try (Connection conn = DatabaseConfig.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameExists(String username){
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
