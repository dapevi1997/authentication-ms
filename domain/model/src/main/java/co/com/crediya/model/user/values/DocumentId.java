package co.com.crediya.model.user.values;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.DOCUMENT_ID_NULL;

public class DocumentId {
    private Long documentoIdentidadUsuario;

    public DocumentId(Long documentoIdentidadUsuario) {
        Objects.requireNonNull(documentoIdentidadUsuario, DOCUMENT_ID_NULL);
        this.documentoIdentidadUsuario = documentoIdentidadUsuario;
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
