package com.sakila.otros;

import java.util.regex.Pattern;

/**
 * Utilidad de validaciones mediante expresiones regulares para campos del sistema Sakila.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Validator {

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+?1[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$");
    private static final Pattern CEDULA_SSN_PATTERN = Pattern.compile("^(\\d{3}-\\d{7}-\\d{1}|\\d{3}-\\d{2}-\\d{4})$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    private Validator() {}

    /**
     * Valida una fecha en formato ISO (YYYY-MM-DD).
     * @param date Cadena de fecha a evaluar.
     * @return true si cumple el formato; false en caso contrario.
     */
    public static boolean isValidDate(String date) {
        return date != null && DATE_PATTERN.matcher(date.trim()).matches();
    }

    /**
     * Valida un número telefónico estándar (10 dígitos con o sin prefijo).
     * @param phone Teléfono a evaluar.
     * @return true si es válido.
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Valida formato de Cédula (000-0000000-0) o SSN (000-00-0000).
     * @param id Documento de identidad.
     * @return true si es válido.
     */
    public static boolean isValidCedulaOrSSN(String id) {
        return id != null && CEDULA_SSN_PATTERN.matcher(id.trim()).matches();
    }

    /**
     * Valida un correo electrónico.
     * @param email Correo electrónico a evaluar.
     * @return true si es válido.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }
}