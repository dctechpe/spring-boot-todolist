package pe.dcalma.springboot.todolist.controller;


import pe.dcalma.springboot.todolist.authentication.ManagerUserSesion;
import pe.dcalma.springboot.todolist.exception.TareaNotFoundException;
import pe.dcalma.springboot.todolist.exception.UsuarioNotFoundException;
import pe.dcalma.springboot.todolist.model.Tarea;
import pe.dcalma.springboot.todolist.model.Usuario;
import pe.dcalma.springboot.todolist.service.TareaService;
import pe.dcalma.springboot.todolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class TareaController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TareaService tareaService;

    @Autowired
    ManagerUserSesion managerUserSesion;


    @GetMapping("/usuarios/{id}/tareas/nueva")
    public String formNuevaTarea(@PathVariable(value="id") Long idUsuario,
                                 @ModelAttribute TareaData tareaData, Model model,
                                 HttpSession session) {

        managerUserSesion.comprobarUsuarioLogeado(session, idUsuario);

        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }
        model.addAttribute("usuario", usuario);
        return "formNuevaTarea";
    }

    @PostMapping("/usuarios/{id}/tareas/nueva")
    public String nuevaTarea(@PathVariable(value="id") Long idUsuario, @ModelAttribute TareaData tareaData,
                             Model model, RedirectAttributes flash,
                             HttpSession session) {

        managerUserSesion.comprobarUsuarioLogeado(session, idUsuario);

        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }
        tareaService.nuevaTareaUsuario(idUsuario, tareaData.getTitulo());
        flash.addFlashAttribute("mensaje", "Tarea creada correctamente");
        return "redirect:/usuarios/" + idUsuario + "/tareas";
    }

    @GetMapping("/usuarios/{id}/tareas")
    public String listadoTareas(@PathVariable(value="id") Long idUsuario, Model model, HttpSession session) {

        managerUserSesion.comprobarUsuarioLogeado(session, idUsuario);

        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }
        List<Tarea> tareas = tareaService.allTareasUsuario(idUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("tareas", tareas);
        return "listaTareas";
    }

    @GetMapping("/tareas/{id}/editar")
    public String formEditaTarea(@PathVariable(value="id") Long idTarea, @ModelAttribute TareaData tareaData,
                                 Model model, HttpSession session) {

        Tarea tarea = tareaService.findById(idTarea);
        if (tarea == null) {
            throw new TareaNotFoundException();
        }

        managerUserSesion.comprobarUsuarioLogeado(session, tarea.getUsuario().getId());

        model.addAttribute("tarea", tarea);
        tareaData.setTitulo(tarea.getTitulo());
        return "formEditarTarea";
    }

    @PostMapping("/tareas/{id}/editar")
    public String grabaTareaModificada(@PathVariable(value="id") Long idTarea, @ModelAttribute TareaData tareaData,
                                       Model model, RedirectAttributes flash, HttpSession session) {
        Tarea tarea = tareaService.findById(idTarea);
        if (tarea == null) {
            throw new TareaNotFoundException();
        }

        managerUserSesion.comprobarUsuarioLogeado(session, tarea.getUsuario().getId());

        tareaService.modificaTarea(idTarea, tareaData.getTitulo());
        flash.addFlashAttribute("mensaje", "Tarea modificada correctamente");
        return "redirect:/usuarios/" + tarea.getUsuario().getId() + "/tareas";
    }

    @DeleteMapping("/tareas/{id}")
    @ResponseBody
    public String borrarTarea(@PathVariable(value="id") Long idTarea, RedirectAttributes flash, HttpSession session) {
        Tarea tarea = tareaService.findById(idTarea);
        if (tarea == null) {
            throw new TareaNotFoundException();
        }

        managerUserSesion.comprobarUsuarioLogeado(session, tarea.getUsuario().getId());

        tareaService.borraTarea(idTarea);
        flash.addFlashAttribute("mensaje", "Tarea borrada correctamente");
        return "";
    }
}
