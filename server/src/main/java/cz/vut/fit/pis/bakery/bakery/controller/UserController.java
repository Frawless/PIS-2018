package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.User;
import cz.vut.fit.pis.bakery.bakery.model.Role;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @GetMapping("/")
    public List<User> users(){
        return (List<User>) userRepository.findAll();
    }

    /**
     * Create new user.
     * Each user gets 'USER' authority when register.
     * @param User new user credentials
     * @return new created user.
     */
    @PostMapping("/sing-up")
    public ResponseEntity<User> createUser(@Valid @RequestBody User User){
        List<Role> roles = new ArrayList<>();

        User user = userRepository.findByUsername(User.getUsername());

        if (user != null){
            return ResponseEntity.badRequest().build();
        }

        roles.add(roleRepository.findByName("USER"));  // Add default role for each user

        User.setRoles(roles);
        User.setPassword(bCryptPasswordEncoder.encode(User.getPassword())); // Encrypt password
        return ResponseEntity.ok(userRepository.save(User));
    }

    /**
     *∂∂
     * @param username Username
     * @return return certain user.
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE') or #principal.name == #username")
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(Principal principal, @PathVariable(value = "username") String username){
        User user = userRepository.findByUsername(username);

        if (user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }


    /**
     * Update user.
     * @param username Username
     * @param userDetails New information about user
     * @return Updated user
     */
    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #principal.name == #username")
    public ResponseEntity<User> update(Principal principal, @PathVariable(value = "username") String username, @Valid @RequestBody User userDetails){
        User user = userRepository.findByUsername(username);

        if (user == null){
            return ResponseEntity.notFound().build();
        }

        user.setFirstname(userDetails.getFirstname());
        user.setLastname(userDetails.getLastname());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAddress(userDetails.getAddress());

        user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user.
     *
     * @return OK if user exists NotFound otherwise
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id){
//        User user = userRepository.findByUsername(username);
        User user = userRepository.findOne(id);
        if (user == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}/grand/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> grandRole(@PathVariable(name = "username") String username, @RequestBody List<Role> newRoles){
        User user = userRepository.findByUsername(username);

        if (user == null){
            return ResponseEntity.notFound().build();
        }

        if (!newRoles.isEmpty()){

            List<Role> roles = newRoles.stream()
                    .map(Role::getName)
                    .map(roleRepository::findOne)
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }else {
            user.setRoles(new ArrayList<>());
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

}