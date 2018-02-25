package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.BakeryUser;
import cz.vut.fit.pis.bakery.bakery.model.Role;
import cz.vut.fit.pis.bakery.bakery.model.UsersOrder;
import cz.vut.fit.pis.bakery.bakery.repository.RoleRepository;
import cz.vut.fit.pis.bakery.bakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller is responsible for elaboration with BackeryUser class.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     *
     * @return List of all users.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public List<BakeryUser> users(){
        return (List<BakeryUser>) userRepository.findAll();
    }

    /**
     * Create new user.
     * Each user gets 'USER' authority when register.
     * @param bakeryUser new user credentials
     * @return new created user.
     */
    @PostMapping("/sing-up")
    public ResponseEntity<BakeryUser> createUser(@Valid @RequestBody BakeryUser bakeryUser){
        List<Role> roles = new ArrayList<>();

        roles.add(roleRepository.findOne("USER"));  // Add default role for each user

        bakeryUser.setRoles(roles);
        bakeryUser.setPassword(bCryptPasswordEncoder.encode(bakeryUser.getPassword())); // Encrypt password
        return ResponseEntity.ok(userRepository.save(bakeryUser));
    }

    /**
     *
     * @param username Username
     * @return return certain user.
     */
    @PreAuthorize("hasAuthority('ADMIN') or #principal.name == #username")
    @GetMapping("/{username}")
    public ResponseEntity<BakeryUser> getUser(Principal principal, @PathVariable(value = "username") String username){
        BakeryUser bakeryUser = userRepository.findByUsername(username);

        if (bakeryUser == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(bakeryUser);
    }

    /**
     * Update user.
     * @param username Username
     * @param bakeryUserDetails New information about user
     * @return Updated user
     */
    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #principal.name == #username")
    public ResponseEntity<BakeryUser> update(Principal principal, @PathVariable(value = "username") String username, @Valid @RequestBody BakeryUser bakeryUserDetails){
        BakeryUser bakeryUser = userRepository.findByUsername(username);

        if (bakeryUser == null){
            return ResponseEntity.notFound().build();
        }

        bakeryUser.setName(bakeryUserDetails.getName());
        bakeryUser.setSurname(bakeryUserDetails.getSurname());
        bakeryUser.setEmail(bakeryUserDetails.getEmail());
        bakeryUser.setPhoneNumber(bakeryUserDetails.getPhoneNumber());

        bakeryUser.setPassword(bCryptPasswordEncoder.encode(bakeryUserDetails.getPassword()));

        BakeryUser updatedBakeryUser = userRepository.save(bakeryUser);

        return ResponseEntity.ok(updatedBakeryUser);
    }

    /**
     * Delete user.
     * @param username Username
     * @return OK if user exists NotFound otherwise
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<BakeryUser> deleteUser(@PathVariable(value = "username") String username){
        BakeryUser bakeryUser = userRepository.findByUsername(username);
        if (bakeryUser == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(bakeryUser);
        return ResponseEntity.ok().build();
    }

    /**
     * @param username Username
     * @return List of users's orders
     */
    @PreAuthorize("hasAuthority('ADMIN') or #principal.name == #username")
    @GetMapping("/{username}/orders")
    public List<UsersOrder> getUsersOrders(Principal principal, @PathVariable(value = "username") String username){
        return userRepository.findByUsername(username).getUsersOrders();
    }
}