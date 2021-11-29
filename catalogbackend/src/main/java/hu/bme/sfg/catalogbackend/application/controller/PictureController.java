package hu.bme.sfg.catalogbackend.application.controller;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.service.CaffService;
import hu.bme.sfg.catalogbackend.application.service.PictureHandlerService;
import hu.bme.sfg.catalogbackend.domain.Caff;
import hu.bme.sfg.catalogbackend.domain.CaffFile;
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
    private CaffService caffService;

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

    @PostMapping("/upload")
    public ResponseEntity<Caff> uploadCaff(@RequestParam("fileKey") MultipartFile file) throws ParseException, IOException {
        System.out.println("CAFF file upload started: " + file.getName() + " size: " + file.getSize());
        CaffFile caffFile = new CaffFile();
        caffFile.setData(file.getBytes());

        return ResponseEntity.status(HttpStatus.OK).body(caffService.uploadCaff(caffFile));

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
