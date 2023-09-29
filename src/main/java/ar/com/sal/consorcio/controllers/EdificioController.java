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
    public String getEdificios(Model model) {
        model.addAttribute("mensajeEdificio", mensajeEdificio);
        model.addAttribute("edificio", new Edificio());
        model.addAttribute("likeDireccion",
                            ((List<Edificio>) edificioRepository.findAll())
                                .stream()
                                .filter(Edificio::isActivo));
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
    public String edificiosRemove(@RequestParam(name = "idBorrar", defaultValue = "0", required = false) int idBorrar){
        try {
            Edificio edificio = edificioRepository.findById(idBorrar).get();
            edificio.setActivo(false);
            edificioRepository.save(edificio);
            mensajeEdificio = "El edificio con id: " + idBorrar + " fue eliminado";
        } catch (Exception e) {
            mensajeEdificio = "No se pudo borrar el edificio con id: " + idBorrar;
        }
        return "redirect:edificios";     
    }    
}