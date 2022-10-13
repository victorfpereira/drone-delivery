package br.com.dronedelivery.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.dronedelivery.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AgendamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agendamento.class);
        Agendamento agendamento1 = new Agendamento();
        agendamento1.setId(UUID.randomUUID());
        Agendamento agendamento2 = new Agendamento();
        agendamento2.setId(agendamento1.getId());
        assertThat(agendamento1).isEqualTo(agendamento2);
        agendamento2.setId(UUID.randomUUID());
        assertThat(agendamento1).isNotEqualTo(agendamento2);
        agendamento1.setId(null);
        assertThat(agendamento1).isNotEqualTo(agendamento2);
    }
}
