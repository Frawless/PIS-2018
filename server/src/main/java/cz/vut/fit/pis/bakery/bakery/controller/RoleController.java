package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.Role;
import cz.vut.fit.pis.bakery.bakery.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     *
     * @return List of all roles
     */
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> roles(){
        return (List<Role>) roleRepository.findAll();
    }

    /**
     * Create new role
     * @param role new role
     * @return New created role
     */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role createRole(@RequestBody Role role){
        return roleRepository.save(role);
    }
}
