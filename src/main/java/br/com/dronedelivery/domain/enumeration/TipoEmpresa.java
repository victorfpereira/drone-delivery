package br.com.dronedelivery.domain.enumeration;

/**
 * The TipoEmpresa enumeration.
 */
public enum TipoEmpresa {
    COMERCIO("Comercio"),
    INDUSTRIA("Industria");

    private final String value;

    TipoEmpresa(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
