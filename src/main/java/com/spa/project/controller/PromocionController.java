package com.spa.project.controller;

import com.spa.project.model.Promocion;
import com.spa.project.repository.PromocionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@CrossOrigin
public class PromocionController {

    @Autowired
    private PromocionRepositorio promocionRepo;

    @GetMapping
    public List<Promocion> listarPromociones() {
        return promocionRepo.findAll();
    }

    @PostMapping
    public Promocion crearPromocion(@RequestBody Promocion promocion) {
        return promocionRepo.save(promocion);
    }
}
