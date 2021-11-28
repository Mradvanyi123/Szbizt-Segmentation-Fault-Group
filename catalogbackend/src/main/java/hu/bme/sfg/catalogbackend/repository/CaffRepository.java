package hu.bme.sfg.catalogbackend.repository;

import hu.bme.sfg.catalogbackend.domain.Caff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaffRepository extends JpaRepository<Caff, Long> {
	
}
