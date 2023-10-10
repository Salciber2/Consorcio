package ar.com.sal.consorcio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private String mensajeDepartamento = "Ingrese un nuevo departamento...";

    @GetMapping("/edificio")
    public String getEdificio(@RequestParam(name = "idEdificio", defaultValue = "0", required = false) int idEdificio,
                                Model model) {
        // Lista edificios
        model.addAttribute("mensajeEdificio", mensajeDepartamento);
        model.addAttribute("edificio", new Edificio());

        Edificio edificioActual = edificioRepository.findById(idEdificio).get();
        if(edificioActual != null) {
            model.addAttribute("edificio", edificioActual);
        }

        return "edificio";
    }
}
