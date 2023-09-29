package ar.com.sal.consorcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Edificio;
import ar.com.sal.consorcio.repositories.EdificioRepository;

@Controller
public class EdificioController {
    @Autowired
    private EdificioRepository edificioRepository;

    private String mensajeEdificio = "Ingrese un nuevo edificio...";


    @GetMapping("/edificios")
    public String getEdificios(@RequestParam(name = "buscarEdificio", defaultValue = "", required = false) String buscarEdificio, Model model) {
        model.addAttribute("mensajeEdificio", mensajeEdificio);
        model.addAttribute("edificio", new Edificio());
        model.addAttribute("likeDireccion",
                            ((List<Edificio>) edificioRepository.findAll())
                                .stream()
                                .filter(Edificio::isActivo)
                                .filter(e -> e.getDireccion().toLowerCase().contains(buscarEdificio.toLowerCase())));
        return "edificios";
    }

    @PostMapping("/edificiosSave")
    public String edificiosSave(@ModelAttribute Edificio edificio){
        try {
            edificioRepository.save(edificio);
            if (edificio.getId() > 0) {
                mensajeEdificio = "Se guardo el edificio con el id: " + edificio.getId();
            } else {
                mensajeEdificio = "No se pudo guardar el edificio";
            }
        } catch (Exception e) {
            System.out.println("Error al guardar el edificio: " + e);
            mensajeEdificio = "Error al guardar el edificio";
        }
        return "redirect:edificios";
    }

    @PostMapping("/edificiosRemove")
    public String edificiosRemove(@RequestParam(name = "idBorrarEdificio", defaultValue = "0", required = false) int idBorrarEdificio){
        try {
            Edificio edificio = edificioRepository.findById(idBorrarEdificio).get();
            edificio.setActivo(false);
            edificioRepository.save(edificio);
            mensajeEdificio = "El edificio con id: " + idBorrarEdificio + " fue eliminado";
        } catch (Exception e) {
            mensajeEdificio = "No se pudo borrar el edificio con id: " + idBorrarEdificio;
        }
        return "redirect:edificios";     
    }
}