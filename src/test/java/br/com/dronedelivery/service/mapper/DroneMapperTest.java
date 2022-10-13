package br.com.dronedelivery.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DroneMapperTest {

    private DroneMapper droneMapper;

    @BeforeEach
    public void setUp() {
        droneMapper = new DroneMapperImpl();
    }
}
