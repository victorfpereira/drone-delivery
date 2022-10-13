package br.com.dronedelivery.domain.enumeration;

/**
 * The TipoTelefone enumeration.
 */
public enum TipoTelefone {
    FIXO("Fixo"),
    CELULAR("Celular");

    private final String value;

    TipoTelefone(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
