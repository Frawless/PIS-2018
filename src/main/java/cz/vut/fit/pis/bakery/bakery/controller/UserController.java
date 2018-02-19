package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.User;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<User> users(){
        return userRepository.findAll();
    }

    @PostMapping("/")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long userId){
        User user = userRepository.findOne(userId);

        if (user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable(value = "id") Long id, @Valid @RequestBody User userDetails){
        User user = userRepository.findOne(id);
        if (user == null){
            return ResponseEntity.notFound().build();
        }

        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setMail(userDetails.getMail());
        user.setPhoneNumber(userDetails.getPhoneNumber());

        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id){
        User user = userRepository.findOne(id);
        if (user == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}