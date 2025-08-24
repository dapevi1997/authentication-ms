package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.utils.UtilUsers;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.DOCUMENT_ID_NULL;

public class DocumentId {
    private Long documentoIdentidadUsuario;

    public DocumentId(String documentoIdentidadUsuario) throws UserContructionException {
        // Verificar que no venga nulo
        UtilUsers.validateNotNull(documentoIdentidadUsuario, DOCUMENT_ID_NULL);
        // Validación que sea un valor numérico
        this.documentoIdentidadUsuario = UtilUsers.validateToLong(documentoIdentidadUsuario);
    }

    public Long getDocumentoIdentidadUsuario() {
        return documentoIdentidadUsuario;
    }

    public void setDocumentoIdentidadUsuario(Long documentoIdentidadUsuario) {
        this.documentoIdentidadUsuario = documentoIdentidadUsuario;
    }

    @Override
    public String toString() {
        return documentoIdentidadUsuario.toString();
    }
}
