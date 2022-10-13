package br.com.dronedelivery.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.dronedelivery.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PedidoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pedido.class);
        Pedido pedido1 = new Pedido();
        pedido1.setId(UUID.randomUUID());
        Pedido pedido2 = new Pedido();
        pedido2.setId(pedido1.getId());
        assertThat(pedido1).isEqualTo(pedido2);
        pedido2.setId(UUID.randomUUID());
        assertThat(pedido1).isNotEqualTo(pedido2);
        pedido1.setId(null);
        assertThat(pedido1).isNotEqualTo(pedido2);
    }
}
