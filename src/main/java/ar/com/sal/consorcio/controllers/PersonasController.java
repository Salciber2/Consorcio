package ar.com.sal.consorcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Persona;
import ar.com.sal.consorcio.repositories.PersonaRepository;

@Controller
public class PersonasController {
    @Autowired
    private PersonaRepository personaRepository;

    private String mensajePersona = "Ingrese una nueva persona...";

    @GetMapping("/personas")
    public String getPersonas(@RequestParam(name = "buscarPersona", defaultValue = "", required = false) String buscarPersona,
                                @RequestParam(name = "buscarPersonaInactiva", defaultValue = "false", required = false) Boolean buscarPersonaInactiva,
                                @RequestParam(name = "idPersona", defaultValue = "0", required = false) int idPersona,
                                Model model){
        // Lista personas
        model.addAttribute("mensajePersona", mensajePersona);
        model.addAttribute("persona", new Persona());

        List<Persona> listaPersonas = ((List<Persona>) personaRepository.findByActivo(!buscarPersonaInactiva))
                                            .stream()
                                            .filter(e -> e.getNombre().toLowerCase().contains(buscarPersona.toLowerCase())
                                                            || e.getApellido().toLowerCase().contains(buscarPersona.toLowerCase()))
                                            .toList();
        model.addAttribute("personas", listaPersonas);

        return "personas";
    }

    @PostMapping("/personasSave")
    public String personasSave(@ModelAttribute Persona persona){
        try {
            personaRepository.save(persona);
            if (persona.getId() > 0) {
                mensajePersona = "Se guardo la persona con el id: " + persona.getId();
            } else {
                mensajePersona = "No se pudo guardar la persona";
            }
        } catch (Exception e) {
            mensajePersona = "Error al guardar la persona";
        }
        return "redirect:personas";
    }

    @PostMapping("/personasRemove")
    public String personasRemove(@RequestParam(name = "idBorrarPersona", defaultValue = "0", required = false) int idBorrarPersona){
        try {
            Persona persona = personaRepository.findById(idBorrarPersona).get();
            if(persona.isActivo()) {                
                persona.setActivo(false);
                personaRepository.save(persona);
                mensajePersona = "La persona con id: " + idBorrarPersona + " fue eliminada";
            } else {
                mensajePersona = "La persona con id: " + idBorrarPersona + " ya está inactiva";

            }
        } catch (Exception e) {
            mensajePersona = "No se pudo borrar la persona con id: " + idBorrarPersona;
        }
        return "redirect:personas";
    }
}
