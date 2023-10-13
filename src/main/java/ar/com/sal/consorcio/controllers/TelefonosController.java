package ar.com.sal.consorcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Persona;
import ar.com.sal.consorcio.entities.Telefono;
import ar.com.sal.consorcio.repositories.PersonaRepository;
import ar.com.sal.consorcio.repositories.TelefonoRepository;
import ar.com.sal.consorcio.utilities.Validar;

@Controller
public class TelefonosController {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private TelefonoRepository telefonoRepository;

    private Persona personaActual;

    private String mensajeTelefono = "Ingrese un nuevo teléfono...";

    @GetMapping("/telefonos")
    public String getTelefonos(@RequestParam(name = "idPersona", defaultValue = "0", required = true) int idPersona,
                                    @RequestParam(name = "buscarTelefono", defaultValue = "", required = false) String buscarTelefono,
                                    @RequestParam(name = "buscarTelefonoInactivo", defaultValue = "false", required = false) Boolean buscarTelefonoInactivo,
                                    Model model) {

        model.addAttribute("persona", new Persona());
        model.addAttribute("mensajeTelefono", mensajeTelefono);
        model.addAttribute("telefono", new Telefono());

        List<Telefono> listaTelefonos;
        personaActual = personaRepository.findById(idPersona).get();

        if(personaActual != null) {
            model.addAttribute("persona", personaActual);
            listaTelefonos = ((List<Telefono>) telefonoRepository.findByIdPersonaAndActivo(idPersona, !buscarTelefonoInactivo));

            if (buscarTelefono != "" && Validar.esNumero(buscarTelefono)) {
                int buscarTelefonoInt = Integer.parseInt(buscarTelefono);
                listaTelefonos = listaTelefonos
                                        .stream()
                                        .filter(d -> d.getNumero() == buscarTelefonoInt)
                                        .toList();
            }
            model.addAttribute("telefonos", listaTelefonos);
        }

        return "telefonos";
    }
}
