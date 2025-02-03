package org.example.utils;

public class CredentialCheckerUtils {

    private CredentialCheckerUtils(){}
    public static boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean checkPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
    public static boolean checkCredentials(String email, String password) {
        return checkEmail(email) && checkPassword(password);
    }
}
