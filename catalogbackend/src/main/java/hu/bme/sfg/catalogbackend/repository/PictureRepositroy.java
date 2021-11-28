package hu.bme.sfg.catalogbackend.repository;

import hu.bme.sfg.catalogbackend.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PictureRepositroy extends JpaRepository<Picture, UUID> {
    List<Picture> findByNameContaining(String pictureName);
}
