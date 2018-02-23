package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.BakeryUser;
import cz.vut.fit.pis.bakery.bakery.model.Role;
import cz.vut.fit.pis.bakery.bakery.model.UsersOrder;
import cz.vut.fit.pis.bakery.bakery.repository.RoleRepository;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }



    @GetMapping("/")
    public List<BakeryUser> users(){
        return (List<BakeryUser>) userRepository.findAll();
    }

    @PostMapping("/")
    public BakeryUser createUser(@Valid @RequestBody BakeryUser bakeryUser){

        bakeryUser.setPassword(bCryptPasswordEncoder.encode(bakeryUser.getPassword()));
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
//        bakeryUser.setRoles(bakeryUserDetails.getRoles());
        bakeryUser.setPassword(bakeryUserDetails.getPassword());

        List<Role> roles = new ArrayList<>();

        for (Role r:
             bakeryUser.getRoles()) {

            roles.add(roleRepository.findOne(r.getName()));
        }

        bakeryUser.setRoles(roles);

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

    @GetMapping("/{id}/orders")
    public List<UsersOrder> getUsersOrders(@PathVariable(value = "id") Long id){
        return userRepository.findOne(id).getUsersOrders();
    }
}