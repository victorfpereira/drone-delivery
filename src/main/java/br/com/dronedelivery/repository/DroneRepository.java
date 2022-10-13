package br.com.dronedelivery.repository;

import br.com.dronedelivery.domain.Drone;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Drone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroneRepository extends JpaRepository<Drone, UUID> {}
