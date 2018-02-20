package cz.vut.fit.pis.bakery.bakery.controller;


import cz.vut.fit.pis.bakery.bakery.model.Catalog;
import cz.vut.fit.pis.bakery.bakery.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogRepository catalogRepository;


    @Autowired
    public CatalogController(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }


    @GetMapping("/")
    public List<Catalog> products(){
        return (List<Catalog>) catalogRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Catalog> getProduct(@PathVariable(value = "name") String id){
        Catalog catalog = catalogRepository.findByName(id);
        if (catalog == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(catalog);
    }

    @PostMapping("/")
    public Catalog createProduct(@RequestBody Catalog catalog){
        return catalogRepository.save(catalog);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Catalog> updateProduct(@PathVariable(name = "name") String name, @RequestBody Catalog details){
        Catalog catalog = catalogRepository.findByName(name);

        if (catalog == null){
            return ResponseEntity.notFound().build();
        }

        catalog.setTotalAmount(details.getTotalAmount());
        catalog.setEnergyValue(details.getEnergyValue());

        catalogRepository.save(catalog);

        return ResponseEntity.ok(catalog);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Catalog> delete(@PathVariable(name = "name") String name){
        Catalog catalog = catalogRepository.findByName(name);

        if (catalog == null){
            return ResponseEntity.notFound().build();
        }
        catalogRepository.deleteByName(name);
        return ResponseEntity.ok().build();
    }
}
