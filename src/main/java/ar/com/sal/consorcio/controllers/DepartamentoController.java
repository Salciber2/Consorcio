package ar.com.sal.consorcio.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Departamento;
import ar.com.sal.consorcio.entities.Edificio;
import ar.com.sal.consorcio.entities.Persona;
import ar.com.sal.consorcio.entities.PersonaDepartamento;
import ar.com.sal.consorcio.enums.Relacion;
import ar.com.sal.consorcio.enums.TipoDepartamento;
import ar.com.sal.consorcio.repositories.DepartamentoRepository;
import ar.com.sal.consorcio.repositories.EdificioRepository;
import ar.com.sal.consorcio.repositories.PersonaDepartamentoRepository;
import ar.com.sal.consorcio.repositories.PersonaRepository;

@Controller
public class DepartamentoController {
    @Autowired
    private EdificioRepository edificioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private PersonaDepartamentoRepository personaDepartamentoRepository;

    private Edificio edificioActual;
    private Departamento departamentoActual;

    private String mensajePersonaDepartamento = "Ingrese una nueva persona...";

    @GetMapping("/departamento")
    public String getDepartamento(@RequestParam(name = "idEdificio", defaultValue = "0", required = true) int idEdificio,
                                    @RequestParam(name = "id", defaultValue = "0", required = true) int id,
                                    @RequestParam(name = "buscarPersona", defaultValue = "", required = false) String buscarPersona,
                                    Model model){

        model.addAttribute("edificio", new Edificio());
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("tiposDepartamentos", TipoDepartamento.values());
        model.addAttribute("persona", new Persona());
        model.addAttribute("mensajePersonaDepartamento", mensajePersonaDepartamento);
        model.addAttribute("relaciones", Relacion.values());

        edificioActual = edificioRepository.findById(idEdificio).get();
        departamentoActual = departamentoRepository.findById(id).get();

        if(edificioActual != null && departamentoActual != null) {
            model.addAttribute("edificio", edificioActual);
            model.addAttribute("departamento", departamentoActual);
        }

        List<Persona> listaPersonas = ((List<Persona>) personaRepository.findByActivo(true))
                                            .stream()
                                            .filter(e -> e.getNombre().toLowerCase().contains(buscarPersona.toLowerCase())
                                                            || e.getApellido().toLowerCase().contains(buscarPersona.toLowerCase()))
                                            .toList();
        model.addAttribute("personas", listaPersonas);

        List<PersonaDepartamento> listaPersonasDepartamentos = ((List<PersonaDepartamento>) personaDepartamentoRepository
                                                                    .findByActivoAndIdDepartamento(true, departamentoActual.getId()));

        listaPersonasDepartamentos.forEach(pd -> {
            pd.setPersona(personaRepository.findById(pd.getIdPersona()).get());
        });
        model.addAttribute("personasAsociadas", listaPersonasDepartamentos);

        return "departamento";
    }

    @PostMapping("/personasDepartamentosSave")
    public String personasDepartamentosSave(@ModelAttribute PersonaDepartamento personaDepartamento){
        try {
            PersonaDepartamento personaDepartamentoExistente = personaDepartamentoRepository
                .findByIdDepartamentoAndIdPersona(personaDepartamento.getIdDepartamento(), personaDepartamento.getIdPersona());
            if(personaDepartamentoExistente != null) {
                personaDepartamentoExistente.setActivo(true);
                personaDepartamentoExistente.setRelacion(personaDepartamento.getRelacion());
                personaDepartamentoRepository.save(personaDepartamentoExistente);
            } else {
                personaDepartamentoRepository.save(personaDepartamento);
            if (personaDepartamento.getId() > 0) {
                mensajePersonaDepartamento = "Se vinculó la persona con el id: " + personaDepartamento.getId();
                } else {
                    mensajePersonaDepartamento = "No se pudo vincular la persona";
                }
            }
        } catch (Exception e) {
            mensajePersonaDepartamento = "Error al vincular la persona";
        }

        return "redirect:departamento?idEdificio=" + edificioActual.getId() + "&id=" + departamentoActual.getId();
    }

    @PostMapping("/personasDepartamentosRemove")
    public String personasDepartamentosRemove(@RequestParam(name = "idBorrarPersonaDepartamento", defaultValue = "0", required = false) int idBorrarPersonaDepartamento){
        try {
            PersonaDepartamento personaDepartamento = personaDepartamentoRepository.findById(idBorrarPersonaDepartamento).get();
            if(personaDepartamento.isActivo()) {                
                personaDepartamento.setActivo(false);
                personaDepartamentoRepository.save(personaDepartamento);
                mensajePersonaDepartamento = "La relación con id: " + idBorrarPersonaDepartamento + " fue eliminada";
            } else {
                mensajePersonaDepartamento = "La relación con id: " + idBorrarPersonaDepartamento + " ya está inactiva";

            }
        } catch (Exception e) {
            mensajePersonaDepartamento = "No se pudo borrar la relación con id: " + idBorrarPersonaDepartamento;
        }

        return "redirect:departamento?idEdificio=" + edificioActual.getId() + "&id=" + departamentoActual.getId();
    }
}