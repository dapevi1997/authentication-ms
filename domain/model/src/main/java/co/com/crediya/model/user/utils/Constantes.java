package co.com.crediya.model.user.utils;

public final class Constantes {
    private Constantes() {} // Evita instanciación

    public static final String NAME_NULL = "El nombre del usuario no puede ser nulo";
    public static final String NAME_EMPTY = "El nombre del usuario no puede estar vacío";
    public static final String LASTNAME_NULL = "El apellido del usuario no puede ser nulo";
    public static final String LASTNAME_EMPTY = "El apellido del usuario no puede estar vacío";
    public static final String EMAIL_NULL = "El correo electrónico del usuario no puede ser nulo";
    public static final String EMAIL_BAD_FORMAT = "El correo electrónico debe tener formato correcto";
    public static final String DOCUMENT_ID_NULL = "El documento de identidad del usuario no puede ser nulo";
    public static final String DOCUMENT_ID_NOT_NUMBER = "El documento de identidad proporcionado debe ser un valor numérico";
    public static final String PHONE_NULL = "El relefono del usuario no puede ser nulo";
    public static final String BASE_SALARY_NULL = "El salario base del usuario no puede ser nulo";
    public static final String BASE_SALARY_OUT_RANGE = "El salario base del usuario debe estar entre el rango [$0, $15.000.000]";
    public static final String BASE_SALARY_NOT_NUMBER = "No se pudo obtener el valor numérico del salario ingresado";
}
