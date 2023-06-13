package com.ispc.gestorstock.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelpers {
    public static boolean isValidEmail(String email){
        String expression = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
