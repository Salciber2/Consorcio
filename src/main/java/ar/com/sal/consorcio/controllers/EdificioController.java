package ar.com.sal.consorcio.controllers;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Departamento;
import ar.com.sal.consorcio.entities.Edificio;
import ar.com.sal.consorcio.repositories.DepartamentoRepository;
import ar.com.sal.consorcio.repositories.EdificioRepository;

@Controller
public class EdificioController {
    @Autowired
    private EdificioRepository edificioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Edificio edificioActual;
    String url = "";

    private String mensajeEdificio = "Ingrese un nuevo edificio...";
    private String mensajeDepartamento = "Ingrese un nuevo departamento...";

    @GetMapping("/edificios")
    public String getEdificios(@RequestParam(name = "buscarEdificio", defaultValue = "", required = false) String buscarEdificio,
                                @RequestParam(name = "buscarEdificioInactivo", defaultValue = "false", required = false) Boolean buscarEdificioInactivo,
                                @RequestParam(name = "buscarDepartamentoActivo", defaultValue = "false", required = false) Boolean buscarDepartamentoActivo,
                                @RequestParam(name = "buscarDepartamentoInactivo", defaultValue = "false", required = false) Boolean buscarDepartamentoInactivo,
                                @RequestParam(name = "idEdificio", defaultValue = "0", required = false) int idEdificio,
                                Model model) {
        // Lista edificios
        model.addAttribute("mensajeEdificio", mensajeEdificio);
        model.addAttribute("edificio", new Edificio());
        model.addAttribute("likeDireccion",
                            ((List<Edificio>) edificioRepository.findAll())
                                .stream()                                
                                .filter(e -> e.getDireccion().toLowerCase().contains(buscarEdificio.toLowerCase()))
                                .filter(e -> e.isActivo() != buscarEdificioInactivo));

        // Lista departamentos
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("mensajeDepartamento", mensajeDepartamento);
        if (idEdificio > 0) {
            edificioActual = edificioRepository.findById(idEdificio).get();
            model.addAttribute("idEdificioActual", idEdificio);
            model.addAttribute("direccionEdificioActual", edificioActual.getDireccion());
            Stream<Departamento> listaDepartamentos = ((List<Departamento>) departamentoRepository.findAll())
                                                        .stream()
                                                        .filter(d -> d.getIdEdificio() == idEdificio);
            if(buscarDepartamentoInactivo  && !buscarDepartamentoActivo) {
                listaDepartamentos = listaDepartamentos.filter(d -> !d.isActivo());
            } else if (!buscarDepartamentoInactivo) {
                listaDepartamentos = listaDepartamentos.filter(Departamento::isActivo);
            }
            model.addAttribute("likeEdificio", listaDepartamentos);
        } else {
            model.addAttribute("idEdificioActual", 0);
            model.addAttribute("direccionEdificioActual", "Ningún edificio seleccionado");
        }

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

    @PostMapping("/departamentosSave")
    public String departamentosSave(@ModelAttribute Departamento departamento){        
        if(departamento.getIdEdificio() > 0) {
            url = "?idEdificio=" + departamento.getIdEdificio();
            if(departamento.getNumero() > 0) {
                try {
                    departamentoRepository.save(departamento);
                    if (departamento.getId() > 0) {
                        mensajeDepartamento = "Se guardo el departamento con el id: " + departamento.getId();
                    } else {
                        mensajeDepartamento = "No se pudo guardar el departamento";
                    }
                    
                } catch (Exception e) {
                    System.out.println("Error al guardar el departamento: " + e);
                    mensajeDepartamento = "Error al guardar el departamento";
                }
            } else {
                mensajeDepartamento = "El departamento debe tener un número válido";    
            }
        } else {
            mensajeDepartamento = "Ningún edificio seleccionado";
        }
        return "redirect:edificios" + url;
    }

    @PostMapping("/departamentosRemove")
    public String departamentosRemove(@RequestParam(name = "idBorrarDepartamento", defaultValue = "0", required = false) int idBorrarDepartamento){
        try {
            Departamento departamento = departamentoRepository.findById(idBorrarDepartamento).get();
            if (departamento != null) {
                url = "?idEdificio=" + departamento.getIdEdificio();
                departamento.setActivo(false);
                departamentoRepository.save(departamento);
                mensajeDepartamento = "El departamento con id: " + idBorrarDepartamento + " fue eliminado";
            }            
        } catch (Exception e) {
            mensajeDepartamento = "No se pudo borrar el departamento con id: " + idBorrarDepartamento;
        }
        return "redirect:edificios" + url;
    }
}