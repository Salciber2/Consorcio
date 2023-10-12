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
import ar.com.sal.consorcio.enums.TipoDepartamento;
import ar.com.sal.consorcio.repositories.DepartamentoRepository;
import ar.com.sal.consorcio.repositories.EdificioRepository;
import ar.com.sal.consorcio.utilities.Validar;

@Controller
public class DepartamentosController {
    @Autowired
    private EdificioRepository edificioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Edificio edificioActual;

    private String mensajeDepartamento = "Ingrese un nuevo departamento...";

    @GetMapping("/departamentos")
    public String getDepartamentos(@RequestParam(name = "idEdificio", defaultValue = "0", required = true) int idEdificio,
                                    @RequestParam(name = "buscarDepartamento", defaultValue = "", required = false) String buscarDepartamento,
                                    @RequestParam(name = "buscarDepartamentoInactivo", defaultValue = "false", required = false) Boolean buscarDepartamentoInactivo,
                                    Model model) {

        model.addAttribute("edificio", new Edificio());
        model.addAttribute("mensajeDepartamento", mensajeDepartamento);
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("tiposDepartamentos", TipoDepartamento.values());

        List<Departamento> listaDepartamentos;
        edificioActual = edificioRepository.findById(idEdificio).get();

        if(edificioActual != null) {
            model.addAttribute("edificio", edificioActual);
            listaDepartamentos = ((List<Departamento>) departamentoRepository.findByIdEdificioAndActivo(idEdificio, !buscarDepartamentoInactivo));

            if (buscarDepartamento != "" && Validar.esNumero(buscarDepartamento)) {
                int buscarDepartamentoInt = Integer.parseInt(buscarDepartamento);
                listaDepartamentos = listaDepartamentos
                                        .stream()
                                        .filter(d -> d.getPiso() == buscarDepartamentoInt || d.getNumero() == buscarDepartamentoInt)
                                        .toList();
            }
            model.addAttribute("departamentos", listaDepartamentos);
        }

        return "departamentos";
    }

    @PostMapping("/departamentosSave")
    public String departamentosSave(@ModelAttribute Departamento departamento){
        try {
            departamentoRepository.save(departamento);
            if (departamento.getId() > 0) {
                mensajeDepartamento = "Se guardo el departamento con el id: " + departamento.getId();
            } else {
                mensajeDepartamento = "No se pudo guardar el departamento";
            }
        } catch (Exception e) {
            mensajeDepartamento = "Error al guardar el departamento";
        }
        return "redirect:departamentos?idEdificio=" + edificioActual.getId();
    }

    @PostMapping("/departamentosRemove")
    public String departamentosRemove(@RequestParam(name = "idBorrarDepartamento", defaultValue = "0", required = false) int idBorrarDepartamento){
        try {
            Departamento departamento = departamentoRepository.findById(idBorrarDepartamento).get();
            if (departamento.isActivo()) {
                departamento.setActivo(false);
                departamentoRepository.save(departamento);
                mensajeDepartamento = "El departamento con id: " + idBorrarDepartamento + " fue eliminado";
            } else {
                mensajeDepartamento = "El departamento con id: " + idBorrarDepartamento + " ya está inactivo";
            }
        } catch (Exception e) {
            mensajeDepartamento = "No se pudo borrar el departamento con id: " + idBorrarDepartamento;
        }
        return "redirect:departamentos?idEdificio=" + edificioActual.getId();
    }
}