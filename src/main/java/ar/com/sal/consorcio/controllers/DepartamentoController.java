package ar.com.sal.consorcio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.sal.consorcio.entities.Departamento;
import ar.com.sal.consorcio.entities.Edificio;
import ar.com.sal.consorcio.repositories.DepartamentoRepository;
import ar.com.sal.consorcio.repositories.EdificioRepository;

@Controller
public class DepartamentoController {
    @Autowired
    private EdificioRepository edificioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Departamento departamentoActual;
    private Edificio edificioActual;

    @GetMapping("/departamento")
    public String getDepartamento(@RequestParam(name = "idDepartamento", defaultValue = "0", required = false) int idDepartamento,
                                    Model model){
        // Detalle departamento
        if (idDepartamento > 0) {
            departamentoActual = departamentoRepository.findById(idDepartamento).get();
            edificioActual = edificioRepository.findById(departamentoActual.getIdEdificio()).get();
            model.addAttribute("departamentoActual", departamentoActual);
            model.addAttribute("edificioActual", edificioActual);
        } else {
            model.addAttribute("departamentoActual", new Departamento());
            model.addAttribute("edificioActual", new Edificio());
        }
        return "departamento";
    }
}
