package ru.varnavskii.wholesalecompany.util;

public final class ExceptionHandler {
    private ExceptionHandler() {};

    public static String getMessage(Exception e) {
        String errorMessage = e.getCause().getMessage();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("ОШИБКА: (.+)");
        java.util.regex.Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            errorMessage = matcher.group(1).trim();
        }
        return errorMessage;
    }
}
