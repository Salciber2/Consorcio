package ar.com.sal.consorcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Departamento;
import ar.com.sal.consorcio.entities.Edificio;
import ar.com.sal.consorcio.repositories.DepartamentoRepository;
import ar.com.sal.consorcio.repositories.EdificioRepository;

@Controller
public class DepartamentoController {
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private EdificioRepository edificioRepository;

    private String mensajeDepartamento = "Ingrese un nuevo departamento...";
    private Edificio edificio;

    @GetMapping("/departamentos")
    public String getDepartamentos(@RequestParam(name = "buscarDepartamento", defaultValue = "0", required = false) int buscarDepartamento,
                                    @RequestParam(name = "idEdificio", defaultValue = "0", required = false) int idEdificio,
                                    Model model) {
        edificio = edificioRepository.findById(idEdificio).get();
        model.addAttribute("edificio", edificio);
        model.addAttribute("mensajeDepartamento", mensajeDepartamento);
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("likeEdificio",
                            ((List<Departamento>) departamentoRepository.findAll())
                                .stream()
                                .filter(d -> d.getIdEdificio() == idEdificio)
                                .filter(Departamento::isActivo)
                                .filter(d -> d.getNumero() == buscarDepartamento));
        return "departamentos";
    }
}