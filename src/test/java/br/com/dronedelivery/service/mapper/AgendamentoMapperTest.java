package br.com.dronedelivery.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgendamentoMapperTest {

    private AgendamentoMapper agendamentoMapper;

    @BeforeEach
    public void setUp() {
        agendamentoMapper = new AgendamentoMapperImpl();
    }
}
