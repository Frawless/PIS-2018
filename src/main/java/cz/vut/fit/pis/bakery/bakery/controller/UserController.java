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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        Role updateMe = new Role(); // Allow user update only own page
        updateMe.setName("UPDATE_" + bakeryUser.getUsername().toUpperCase());

        roles.add(updateMe);

        bakeryUser.setRoles(roles);
        bakeryUser.setPassword(bCryptPasswordEncoder.encode(bakeryUser.getPassword())); // Encrypt password
        return ResponseEntity.ok(userRepository.save(bakeryUser));
    }

    /**
     *
     * @param username Username
     * @return return certain user.
     */
    @GetMapping("/{username}")
    public ResponseEntity<BakeryUser> getUser(@PathVariable(value = "username") String username){
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
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('UPDATE_{username}')")
    public ResponseEntity<BakeryUser> update(@PathVariable(value = "username") String username, @Valid @RequestBody BakeryUser bakeryUserDetails){
        BakeryUser bakeryUser = userRepository.findByUsername(username);

        if (bakeryUser == null){
            return ResponseEntity.notFound().build();
        }

        List<Role> roles = new ArrayList<>();

        for (Role r:
                bakeryUser.getRoles()) {
            roles.add(roleRepository.findOne(r.getName()));
        }

        Role userRole = roleRepository.findOne("USER");
        if (!roles.contains(userRole)){
            roles.add(userRole);    // It is not possible to remove 'USER' role.
        }
        Role updateMe = roleRepository.findOne("UPDATE_" + username.toUpperCase());
        if (!roles.contains(updateMe)){
            roles.add(updateMe);
        }

        bakeryUser.setName(bakeryUserDetails.getName());
        bakeryUser.setSurname(bakeryUserDetails.getSurname());
        bakeryUser.setEmail(bakeryUserDetails.getEmail());
        bakeryUser.setPhoneNumber(bakeryUserDetails.getPhoneNumber());
        bakeryUser.setRoles(roles);
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
    @GetMapping("/{username}/orders")
    public List<UsersOrder> getUsersOrders(@PathVariable(value = "username") String username){
        return userRepository.findByUsername(username).getUsersOrders();
    }
}