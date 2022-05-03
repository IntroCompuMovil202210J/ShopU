package com.example.shopu.utils;

import android.text.TextUtils;

public class UserFormVerifier {
    public static boolean validateEmailFormat(String email) {
        return email.matches("^[a-z0-9]+(?!.*(?:\\+{2,}|\\-{2,}|\\.{2,}))(?:[\\.+\\-]{0,1}[a-z0-9])*@javeriana\\.edu\\.co$");
    }

    public static boolean validateIfEmailEmpty(String email) {
        return TextUtils.isEmpty(email);
    }

    public static boolean validatePasswordFormat(String password) {
        return true;
    }

    public static boolean validateIfPasswordEmpty(String password) {
        return TextUtils.isEmpty(password);
    }
}
