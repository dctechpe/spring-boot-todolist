package pe.dcalma.springboot.todolist.controller;

import pe.dcalma.springboot.todolist.exception.UsuarioNotFoundException;
import pe.dcalma.springboot.todolist.model.Usuario;
import pe.dcalma.springboot.todolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class TareaController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios/{id}/tareas/nueva")
    public String formNuevaTarea(@PathVariable(value="id") Long idUsuario, @ModelAttribute TareaData tareaData, Model model) {
        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }
        model.addAttribute("usuario", usuario);
        return "formNuevaTarea";
    }

    @PostMapping("/usuarios/{id}/tareas/nueva")
    public String nuevaTarea(@PathVariable(value="id") Long idUsuario, @ModelAttribute TareaData tareaData, RedirectAttributes flash) {
        System.out.println("Creada tarea: "+tareaData.getTitulo());
        return "redirect:/usuarios/"+idUsuario+"/tareas";
    }
}