package br.com.dronedelivery.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.dronedelivery.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DroneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drone.class);
        Drone drone1 = new Drone();
        drone1.setId(UUID.randomUUID());
        Drone drone2 = new Drone();
        drone2.setId(drone1.getId());
        assertThat(drone1).isEqualTo(drone2);
        drone2.setId(UUID.randomUUID());
        assertThat(drone1).isNotEqualTo(drone2);
        drone1.setId(null);
        assertThat(drone1).isNotEqualTo(drone2);
    }
}
