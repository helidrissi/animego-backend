package com.udemy.angular.controllers;

import com.udemy.angular.entities.AnimeCharacter;
import com.udemy.angular.entities.User;
import com.udemy.angular.repositories.IAnimeCharacter;
import com.udemy.angular.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/characters")
public class CharactersController {

    @Autowired
    private IAnimeCharacter characterRepository;
    @Autowired
    private IUser userRepository;

    @GetMapping("/")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(characterRepository.findAll());
    }

    @GetMapping("/all/{idUser}")
    public ResponseEntity findAllUserCharacters(@PathVariable Long idUser) {
        if (idUser == null) {
            return ResponseEntity.badRequest().body("Cannot find anime with null user");
        }
        User user = userRepository.getOne(idUser);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<AnimeCharacter> userChars = characterRepository.findByUser(user);
        List<AnimeCharacter> sharedChars = characterRepository.findBySharedAndUserNotLike(true, user);
        userChars.forEach(character -> character.setIdOwner(idUser));
        sharedChars.forEach(character -> character.setIdOwner(-1L));
        userChars.addAll(sharedChars);

        return ResponseEntity.ok(userChars);
    }

    @GetMapping("/{idCharacter}")
    public ResponseEntity findCharacterById(@PathVariable(name = "idCharacter") Long idCharacter) {
        if (idCharacter == null) {
            return ResponseEntity.badRequest().body("Cannot find anime with null ID");
        }
        AnimeCharacter character = characterRepository.getOne(idCharacter);
        if (character == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(character);
    }

    @PostMapping("/")
    public ResponseEntity createCharacter(@RequestBody AnimeCharacter character) {
        if (character == null) {
            return ResponseEntity.badRequest().body("Cannot create character with empty fields");
        }
        return ResponseEntity.ok(characterRepository.save(character));
    }

    @DeleteMapping("/{idCharacter}")
    public ResponseEntity deleteCharacter(@PathVariable(name = "idCharacter") Long idCharacter) {
        if (idCharacter == null) {
            return ResponseEntity.badRequest().body("Cannot remove character with null ID");
        }
        AnimeCharacter character = characterRepository.getOne(idCharacter);
        if (character == null) {
            return  ResponseEntity.notFound().build();
        }
        characterRepository.delete(character);
        return ResponseEntity.ok("Character removed with success");
    }

    @GetMapping("/share/{idCharacter}/{isShared}")
    public ResponseEntity shareCharacter(@PathVariable(name = "idCharacter") Long idCharacter, @PathVariable(name = "isShared") boolean isShared) {
        if (idCharacter == null) {
            return ResponseEntity.badRequest().body("Cannot remove character with null ID");
        }
        AnimeCharacter character = characterRepository.getOne(idCharacter);
        if (character == null) {
            return  ResponseEntity.notFound().build();
        }
        character.setShared(!isShared);
        return ResponseEntity.ok(characterRepository.save(character));
    }
}
