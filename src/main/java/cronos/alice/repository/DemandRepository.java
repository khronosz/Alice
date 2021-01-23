package cronos.alice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cronos.alice.model.entity.Demand;

public interface DemandRepository extends JpaRepository<Demand, Long>, CustomizedDemandRepository {

    Optional<Demand> findByName(String name);
}
