package ar.com.sal.consorcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Edificio;
import ar.com.sal.consorcio.entities.Persona;
import ar.com.sal.consorcio.repositories.DepartamentoRepository;
import ar.com.sal.consorcio.repositories.PersonaRepository;

@Controller
public class PersonaController {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Persona personaActual;
    String url = "";

    private String mensajePersona = "Ingrese una nueva persona...";
    private String mensajeDepartamento = "Asocie un nuevo departamento...";

    @GetMapping("/personas")
    public String getPersonas(@RequestParam(name = "buscarPersona", defaultValue = "", required = false) String buscarPersona,
                                Model model){
        // Lista personas
        model.addAttribute("mensajePersona", mensajePersona);
        model.addAttribute("persona", new Persona());
        model.addAttribute("likeNombre",
                            ((List<Persona>) personaRepository.findAll())
                                .stream()                                
                                .filter(p -> p.getNombre().toLowerCase().contains(buscarPersona.toLowerCase()))
                                .filter(p -> p.isActivo()));

        // Lista departamentos

        return "personas";
    }
}
