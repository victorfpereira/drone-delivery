package br.com.dronedelivery.domain.enumeration;

/**
 * The TipoEndereco enumeration.
 */
public enum TipoEndereco {
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial");

    private final String value;

    TipoEndereco(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
