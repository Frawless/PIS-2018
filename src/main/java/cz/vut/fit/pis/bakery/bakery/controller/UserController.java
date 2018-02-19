package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.BakeryUser;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public List<BakeryUser> users(){
        return (List<BakeryUser>) userRepository.findAll();
    }

    @PostMapping("/")
    public BakeryUser createUser(@Valid @RequestBody BakeryUser bakeryUser){
        return userRepository.save(bakeryUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BakeryUser> getUser(@PathVariable(value = "id") Long userId){
        BakeryUser bakeryUser = userRepository.findOne(userId);

        if (bakeryUser == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(bakeryUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BakeryUser> update(@PathVariable(value = "id") Long id, @Valid @RequestBody BakeryUser bakeryUserDetails){
        BakeryUser bakeryUser = userRepository.findOne(id);
        if (bakeryUser == null){
            return ResponseEntity.notFound().build();
        }

        bakeryUser.setName(bakeryUserDetails.getName());
        bakeryUser.setSurname(bakeryUserDetails.getSurname());
        bakeryUser.setEmail(bakeryUserDetails.getEmail());
        bakeryUser.setPhoneNumber(bakeryUserDetails.getPhoneNumber());

        BakeryUser updatedBakeryUser = userRepository.save(bakeryUser);

        return ResponseEntity.ok(updatedBakeryUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BakeryUser> deleteUser(@PathVariable(value = "id") Long id){
        BakeryUser bakeryUser = userRepository.findOne(id);
        if (bakeryUser == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(bakeryUser);
        return ResponseEntity.ok().build();
    }
}