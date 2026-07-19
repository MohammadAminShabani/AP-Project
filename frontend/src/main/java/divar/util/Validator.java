package divar.util;

public class Validator {

    private Validator(){}

    public static boolean isEmpty(String value){

        return value == null || value.trim().isEmpty();
    }

    public static boolean validPhone(String phone){

        if (phone == null)
            return false;

        return phone.matches("^09\\d{9}$");    }

    public static boolean validEmail(String email){

        if(email==null || email.isBlank())
            return true;

        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    public static boolean validPassword(String password){

        return password != null && password.length() >= 6;
    }
}