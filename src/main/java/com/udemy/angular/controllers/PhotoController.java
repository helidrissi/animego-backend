package com.udemy.angular.controllers;

import com.udemy.angular.entities.AnimeCharacter;
import com.udemy.angular.entities.User;
import com.udemy.angular.repositories.IAnimeCharacter;
import com.udemy.angular.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/photos")
public class PhotoController {

    @Autowired
    private IAnimeCharacter characterRepository;
    @Autowired
    private IUser userRepository;

    @GetMapping("/character/{idCharacter}")
    public ResponseEntity photoCharacter(@PathVariable Long idCharacter) {
        if (idCharacter == null) {
            return ResponseEntity.badRequest().body("Cannot get character photo with null ID");
        }
        AnimeCharacter character = characterRepository.getOne(idCharacter);
        if (character == null) {
            return ResponseEntity.notFound().build();
        }
        if (character.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(new ByteArrayInputStream(character.getPhoto())));
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity photoUser(@PathVariable Long idUser) {
        if (idUser == null) {
            return ResponseEntity.badRequest().body("Cannot get user photo with null ID");
        }
        User user = userRepository.getOne(idUser);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (user.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(new ByteArrayInputStream(user.getPhoto())));
    }
}
