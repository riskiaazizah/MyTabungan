package mytabungan.utils;

public class ValidationUtil {
    public static boolean isEmpty(String input){
        return input == null || input.trim().isEmpty();
    }
    public static boolean isValidEmail(String email){
        return !isEmpty(email) && email.contains("@") && email.contains(".");
    }
    public static boolean isValidPassword(String password){
        return !isEmpty(password) && password.length() >= 8;
    }
    public static boolean isValidUsername(String username){
        return !isEmpty(username) && username.length() >= 3 && username.length() <= 20;
    }
}