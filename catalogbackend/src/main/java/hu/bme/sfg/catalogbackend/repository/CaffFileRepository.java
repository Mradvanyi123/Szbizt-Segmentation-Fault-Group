package hu.bme.sfg.catalogbackend.repository;


import hu.bme.sfg.catalogbackend.domain.CaffFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaffFileRepository extends JpaRepository<CaffFile, Long> {
}
