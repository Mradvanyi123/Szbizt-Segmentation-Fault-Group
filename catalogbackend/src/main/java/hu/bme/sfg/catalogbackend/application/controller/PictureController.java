package hu.bme.sfg.catalogbackend.application.controller;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.service.PictureHandlerService;
import hu.bme.sfg.catalogbackend.util.PictureException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/picture")
@AllArgsConstructor
public class PictureController {

    private PictureHandlerService pictureHandlerService;

    @GetMapping
    public ResponseEntity<List<PictureDto>> getAllPictures(@RequestParam(required = false) String name) {
        if (name == null) {
            return ResponseEntity.ok(pictureHandlerService.getAllPictures());
        } else {
            return ResponseEntity.ok(pictureHandlerService.getAllPicturesWithNameFilter(name));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPicture(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(pictureHandlerService.getPicture(id));
        } catch (PictureException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Picture doesn't exists");
        }
    }

    @PostMapping
    public ResponseEntity<PictureDto> createPicture(@Valid @RequestBody PictureDto pictureDto, Principal principal) {
        try {
            return ResponseEntity.ok(pictureHandlerService.createPicture(pictureDto, principal));
        } catch (PictureException e) {
            return ResponseEntity.badRequest().build(); //TODO
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PictureDto> editPictureName(@PathVariable UUID id, @Valid @RequestBody String newName) {
        try {
            return ResponseEntity.ok(pictureHandlerService.editPictureName(newName, id));
        } catch (PictureException e) {
            return ResponseEntity.badRequest().build(); //TODO
        }
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> postComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("id") UUID pictureId, Principal principal) {
        try {
            return ResponseEntity.ok(pictureHandlerService.postComment(pictureId, commentDto, principal));
        } catch (PictureException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePicture(@PathVariable("id") UUID pictureId) {
        try {
            pictureHandlerService.deletePicture(pictureId);
            return ResponseEntity.ok().build();
        } catch (PictureException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
