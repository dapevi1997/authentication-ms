package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.DOCUMENT_ID_NOT_NUMBER;
import static co.com.crediya.model.user.utils.Constantes.DOCUMENT_ID_NULL;

public class DocumentId {
    private Long documentoIdentidadUsuario;

    public DocumentId(String documentoIdentidadUsuario) throws ConstructionDomainException {
        // Verificar que no venga nulo
        ValidationFieldDomain.validateNotNull(documentoIdentidadUsuario, DOCUMENT_ID_NULL);
        // Validación que sea un valor numérico
        this.documentoIdentidadUsuario = ValidationFieldDomain.validateToLong(documentoIdentidadUsuario, DOCUMENT_ID_NOT_NUMBER);
    }

    public Long getDocumentoIdentidadUsuario() {
        return documentoIdentidadUsuario;
    }

    public void setDocumentoIdentidadUsuario(Long documentoIdentidadUsuario) {
        this.documentoIdentidadUsuario = documentoIdentidadUsuario;
    }
}
