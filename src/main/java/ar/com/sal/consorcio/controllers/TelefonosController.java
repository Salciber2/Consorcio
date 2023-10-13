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
import ar.com.sal.consorcio.entities.Telefono;
import ar.com.sal.consorcio.repositories.PersonaRepository;
import ar.com.sal.consorcio.repositories.TelefonoRepository;

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
            listaTelefonos = ((List<Telefono>) telefonoRepository.findByIdPersonaAndActivo(idPersona, !buscarTelefonoInactivo))
                                .stream()
                                .filter(t -> t.getNumero().contains(buscarTelefono))
                                .toList();

        model.addAttribute("telefonos", listaTelefonos);
        }

        return "telefonos";
    }

    @PostMapping("/telefonosSave")
    public String telefonosSave(@ModelAttribute Telefono telefono){
        try {
            telefonoRepository.save(telefono);
            if (telefono.getId() > 0) {
                mensajeTelefono = "Se guardo el teléfono con el id: " + telefono.getId();
            } else {
                mensajeTelefono = "No se pudo guardar el teléfono";
            }
        } catch (Exception e) {
            mensajeTelefono = "Error al guardar el teléfono";
        }
        return "redirect:telefonos?idPersona=" + personaActual.getId();
    }

    @PostMapping("/telefonosRemove")
    public String telefonosRemove(@RequestParam(name = "idBorrarTelefono", defaultValue = "0", required = false) int idBorrarTelefono){
        try {
            Telefono telefono = telefonoRepository.findById(idBorrarTelefono).get();
            if (telefono.isActivo()) {
                telefono.setActivo(false);
                telefonoRepository.save(telefono);
                mensajeTelefono = "El teléfono con id: " + idBorrarTelefono + " fue eliminado";
            } else {
                mensajeTelefono = "El teléfono con id: " + idBorrarTelefono + " ya está inactivo";
            }
        } catch (Exception e) {
            mensajeTelefono = "No se pudo borrar el teléfono con id: " + idBorrarTelefono;
        }
        return "redirect:telefonos?idPersona=" + personaActual.getId();
    }
}
