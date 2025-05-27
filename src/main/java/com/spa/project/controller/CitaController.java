package com.spa.project.controller;

import com.spa.project.model.Cita;
import com.spa.project.model.Servicio;
import com.spa.project.model.Especialista;
import com.spa.project.repository.CitaRepository;
import com.spa.project.repository.ServicioRepository;
import com.spa.project.repository.EspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private EspecialistaRepository especialistaRepository;

    // Obtener todas las citas con información completa
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> obtenerTodasLasCitas() {
        List<Cita> citas = citaRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Cita cita : citas) {
            Map<String, Object> datosCita = new HashMap<>();
            datosCita.put("id", cita.getId());
            datosCita.put("fecha", cita.getFecha());
            datosCita.put("hora", cita.getHora());
            datosCita.put("clienteNombre", cita.getClienteNombre());
            datosCita.put("clienteTelefono", cita.getClienteTelefono());

            // Obtener información del servicio
            Optional<Servicio> servicio = servicioRepository.findById(cita.getServicioId());
            datosCita.put("servicioId", cita.getServicioId());
            datosCita.put("servicioNombre", servicio.isPresent() ? servicio.get().getNombre() : "Servicio no encontrado");

            // Obtener información del especialista
            Optional<Especialista> especialista = especialistaRepository.findById(cita.getEspecialistaId());
            datosCita.put("especialistaId", cita.getEspecialistaId());
            datosCita.put("especialistaNombre", especialista.isPresent() ? especialista.get().getNombre() : "Especialista no encontrado");

            resultado.add(datosCita);
        }

        return ResponseEntity.ok(resultado);
    }

    // Obtener una cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable String id) {
        return citaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva cita
    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        try {
            // Validaciones básicas
            if (cita.getClienteNombre() == null || cita.getClienteNombre().isEmpty() ||
                cita.getClienteTelefono() == null || cita.getClienteTelefono().isEmpty() ||
                cita.getServicioId() == null || cita.getServicioId().isEmpty() ||
                cita.getEspecialistaId() == null || cita.getEspecialistaId().isEmpty() ||
                cita.getFecha() == null || cita.getFecha().isEmpty() ||
                cita.getHora() == null || cita.getHora().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Cita nuevaCita = citaRepository.save(cita);
            return ResponseEntity.ok(nuevaCita);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Actualizar una cita existente
    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable String id, @RequestBody Cita citaActualizada) {
        return citaRepository.findById(id)
                .map(citaExistente -> {
                    // Actualizar solo los campos permitidos
                    if (citaActualizada.getFecha() != null) {
                        citaExistente.setFecha(citaActualizada.getFecha());
                    }
                    if (citaActualizada.getHora() != null) {
                        citaExistente.setHora(citaActualizada.getHora());
                    }
                    if (citaActualizada.getClienteNombre() != null) {
                        citaExistente.setClienteNombre(citaActualizada.getClienteNombre());
                    }
                    if (citaActualizada.getClienteTelefono() != null) {
                        citaExistente.setClienteTelefono(citaActualizada.getClienteTelefono());
                    }
                    if (citaActualizada.getServicioId() != null) {
                        citaExistente.setServicioId(citaActualizada.getServicioId());
                    }
                    if (citaActualizada.getEspecialistaId() != null) {
                        citaExistente.setEspecialistaId(citaActualizada.getEspecialistaId());
                    }

                    Cita citaActualizadaDB = citaRepository.save(citaExistente);
                    return ResponseEntity.ok(citaActualizadaDB);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una cita
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable String id) {
        return citaRepository.findById(id)
                .map(cita -> {
                    citaRepository.delete(cita);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener citas por teléfono del cliente
    @GetMapping("/cliente/{telefono}")
    public ResponseEntity<List<Cita>> obtenerCitasPorCliente(@PathVariable String telefono) {
        List<Cita> citas = citaRepository.findByClienteTelefono(telefono);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    // Obtener citas por especialista
    @GetMapping("/especialista/{especialistaId}")
    public ResponseEntity<List<Cita>> obtenerCitasPorEspecialista(@PathVariable String especialistaId) {
        List<Cita> citas = citaRepository.findByEspecialistaId(especialistaId);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }
}