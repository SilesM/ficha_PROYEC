/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.fichaqrr.controller;

/**
 *
 * @author MarvinSiles
 */
import com.proyecto.fichaqrr.model.Equipo;
import com.proyecto.fichaqrr.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class EquipoController {

    @Autowired
    private EquipoRepository equipoRepository;

    @GetMapping("/equipo")
    public String mostrarFicha(@RequestParam("id") Long id, Model model) {
        Optional<Equipo> equipoOpt = equipoRepository.findById(id);
        if (equipoOpt.isPresent()) {
            model.addAttribute("equipo", equipoOpt.get());
            return "ficha";
        } else {
            return "equipo_no_encontrado";
        }
    }

    @GetMapping("/imagen/{id}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> obtenerImagen(@PathVariable Long id) {
        Optional<Equipo> equipoOpt = equipoRepository.findById(id);
        if (equipoOpt.isPresent() && equipoOpt.get().getImagen() != null) {
            ByteArrayResource img = new ByteArrayResource(equipoOpt.get().getImagen());
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(img);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        // Página raíz: evita el error en Railway
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Aplicación QR de fichas técnicas funcionando correctamente.";
    }
}
