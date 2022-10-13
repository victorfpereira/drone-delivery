package br.com.dronedelivery.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.dronedelivery.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DroneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroneDTO.class);
        DroneDTO droneDTO1 = new DroneDTO();
        droneDTO1.setId(UUID.randomUUID());
        DroneDTO droneDTO2 = new DroneDTO();
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
        droneDTO2.setId(droneDTO1.getId());
        assertThat(droneDTO1).isEqualTo(droneDTO2);
        droneDTO2.setId(UUID.randomUUID());
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
        droneDTO1.setId(null);
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
    }
}
