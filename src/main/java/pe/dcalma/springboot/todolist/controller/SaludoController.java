package pe.dcalma.springboot.todolist.controller;

import pe.dcalma.springboot.todolist.service.SaludoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SaludoController {

    @Autowired
    SaludoService service;

    @RequestMapping("/saludo/{nombre}")
    public @ResponseBody String saludo(@PathVariable(value="nombre") String nombre) {
        return service.saluda(nombre);
    }

}
