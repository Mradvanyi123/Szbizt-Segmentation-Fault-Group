package hu.bme.sfg.catalogbackend.application.controller;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.service.PictureHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/picture")
public class PictureController {

    private final PictureHandlerService pictureHandlerService;

    @Autowired
    PictureController(PictureHandlerService pictureHandlerService) {
        this.pictureHandlerService = pictureHandlerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PictureDto>> getAllPictures(@RequestParam(required = false) String name) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<PictureDto> createPicture(@Valid @RequestBody PictureDto pictureDto) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDto> postComment(@Valid @RequestBody CommentDto commentDto,
                                                  @PathVariable("id") Long pictureId) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePicture(@PathVariable("id") Long pictureId) {
        return ResponseEntity.noContent().build();
    }
}
