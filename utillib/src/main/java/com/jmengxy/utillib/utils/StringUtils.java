package com.jmengxy.utillib.utils;

/**
 * Created by jiemeng on 26/12/2017.
 */

public class StringUtils {
    public static final String MASK_SYMBOL = "****";

    public static String trimLeft(String s) {
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        return s.substring(i);
    }

    public static String trimRight(String s) {
        int i = s.length() - 1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0, i + 1);
    }

    public static String trimRedundanceWhiteSpaces(String s) {
        StringBuffer sb = new StringBuffer();
        String trimmed = s.trim();
        boolean flag = false;
        for (int i = 0; i < trimmed.length(); i++) {
            if (Character.isWhitespace(trimmed.charAt(i))) {
                if (flag) {
                    continue;
                }

                sb.append(' ');
                flag = true;
            } else {
                sb.append(trimmed.charAt(i));
                flag = false;
            }
        }

        return sb.toString();
    }

    public static String trimAllWhiteSpaces(String s) {
        StringBuffer sb = new StringBuffer();
        String trimmed = s.trim();
        for (int i = 0; i < trimmed.length(); i++) {
            if (!Character.isWhitespace(trimmed.charAt(i))) {
                sb.append(trimmed.charAt(i));
            }
        }

        return sb.toString();
    }

    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    public static String getMaskedString(String s) {
        String maskedStr = "";
        if (!s.isEmpty()) {
            maskedStr = MASK_SYMBOL;
        }
        if (s.length() >= MASK_SYMBOL.length()) {
            maskedStr += s.substring(MASK_SYMBOL.length());
        }

        return maskedStr;
    }

    public static String join(String delimiter, String... strings) {
        StringBuffer buffer = new StringBuffer();
        for (String s : strings) {
            if (!isEmpty(s)) {
                if (buffer.length() != 0) {
                    buffer.append(delimiter);
                }

                buffer.append(s);
            }
        }

        return buffer.toString();
    }

    public static boolean checkPassword(CharSequence password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasAlpha = false;
        boolean hasDigit = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isAlphabetic(password.charAt(i))) {
                hasAlpha = true;
            }

            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            }
        }

        return hasAlpha && hasDigit;
    }

    public static int getDigitCount(CharSequence s) {
        int digitCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                digitCount++;
            }
        }

        return digitCount;
    }

    public static String formatMonthYearForDisplay(int month, int year) {
        return String.format("%02d/%s", month, Integer.toString(year).substring(2));
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static String toFeeFormat(String fee) {
        return toFeeFormat(Float.parseFloat(fee));
    }

    public static String toFeeFormat(float fee) {
        return String.format("%.2f", fee);
    }

    public static String maskBankCard(String bankCardNumber) {
        String substring = bankCardNumber.substring(12);
        return "**** **** **** " + substring;
    }
}
