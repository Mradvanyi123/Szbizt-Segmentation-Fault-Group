package hu.bme.sfg.catalogbackend.application.controller;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.service.CaffParserServiceImpl;
import hu.bme.sfg.catalogbackend.application.service.PictureHandlerService;
import hu.bme.sfg.catalogbackend.util.PictureException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/picture")
@AllArgsConstructor
public class PictureController {

    private PictureHandlerService pictureHandlerService;

    @Autowired
    private CaffParserServiceImpl caffService;

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
    public ResponseEntity<PictureDto> createPicture(@RequestParam("name") String fileName, @RequestParam("caffFile") MultipartFile file, Principal principal) {
        try {
            return ResponseEntity.ok(pictureHandlerService.createPicture(fileName, file, principal));
        } catch (PictureException | ParseException | IOException e) {
            return ResponseEntity.badRequest().build();
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
