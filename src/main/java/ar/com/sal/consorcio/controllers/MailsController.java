package ar.com.sal.consorcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Mail;
import ar.com.sal.consorcio.entities.Persona;
import ar.com.sal.consorcio.entities.Telefono;
import ar.com.sal.consorcio.repositories.MailRepository;
import ar.com.sal.consorcio.repositories.PersonaRepository;

@Controller
public class MailsController {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private MailRepository mailRepository;

    private Persona personaActual;

    private String mensajeMail = "Ingrese un nuevo mail...";

    @GetMapping("/mails")
    public String getMails(@RequestParam(name = "idPersona", defaultValue = "0", required = true) int idPersona,
                                    @RequestParam(name = "buscarMail", defaultValue = "", required = false) String buscarMail,
                                    @RequestParam(name = "buscarMailInactivo", defaultValue = "false", required = false) Boolean buscarMailInactivo,
                                    Model model) {

        model.addAttribute("persona", new Persona());
        model.addAttribute("mensajeMail", mensajeMail);
        model.addAttribute("mail", new Mail());

        List<Mail> listaMails;
        personaActual = personaRepository.findById(idPersona).get();

        if(personaActual != null) {
            model.addAttribute("persona", personaActual);
            listaMails = ((List<Mail>) mailRepository.findByIdPersonaAndActivo(idPersona, !buscarMailInactivo))
                                .stream()
                                .filter(t -> t.getDireccion().contains(buscarMail))
                                .toList();

        model.addAttribute("mails", listaMails);
        }

        return "mails";
    }

    @PostMapping("/mailsSave")
    public String mailsSave(@ModelAttribute Mail mail){
        try {
            mailRepository.save(mail);
            if (mail.getId() > 0) {
                mensajeMail = "Se guardo el mail con el id: " + mail.getId();
            } else {
                mensajeMail = "No se pudo guardar el mail";
            }
        } catch (Exception e) {
            mensajeMail = "Error al guardar el mail";
        }
        return "redirect:mails?idPersona=" + personaActual.getId();
    }

    @PostMapping("/mailsRemove")
    public String mailsRemove(@RequestParam(name = "idBorrarMail", defaultValue = "0", required = false) int idBorrarMail){
        try {
            Mail mail = mailRepository.findById(idBorrarMail).get();
            if (mail.isActivo()) {
                mail.setActivo(false);
                mailRepository.save(mail);
                mensajeMail = "El mail con id: " + idBorrarMail + " fue eliminado";
            } else {
                mensajeMail = "El mail con id: " + idBorrarMail + " ya está inactivo";
            }
        } catch (Exception e) {
            mensajeMail = "No se pudo borrar el mail con id: " + idBorrarMail;
        }
        return "redirect:mails?idPersona=" + personaActual.getId();
    }
    
}
