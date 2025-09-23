package co.com.crediya.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constantes {
    public final String TRAZA_AUTH = "TRAZA_AUTH - ";
    public final String URL_REGISTER_USER = "/api/v1/usuarios";
    public final String URL_USER_BY_ROLE_NAME = "/api/v1/usuarios/rol";

    @UtilityClass
    public class MensajesExcepciones{
        public final String PARAMETRO_ROL_OBLIGATORIO = "El parámetro 'rol' es obligatorio";
    }

    @UtilityClass
    public class QueryParams{
        public final String ROL = "rol";
    }

    @UtilityClass
    public class MensajesLogger{
        public final String NUMERO_USUARIOS_POR_ROL = "El número de usuarios por rol {} es {}";
    }
}
