package pe.dcalma.springboot.todolist;

import pe.dcalma.springboot.todolist.controller.TareaController;
import pe.dcalma.springboot.todolist.model.Tarea;
import pe.dcalma.springboot.todolist.model.Usuario;
import pe.dcalma.springboot.todolist.service.UsuarioService;
import pe.dcalma.springboot.todolist.service.TareaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TareaController.class)
public class TareaWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private TareaService tareaService;
    
    @Test
    public void nuevaTareaDevuelveForm() throws Exception {
        Usuario usuario = new Usuario("dcalma@gmail.com");
        usuario.setId(1L);

        when(usuarioService.findById(1L)).thenReturn(usuario);

        this.mockMvc.perform(get("/usuarios/1/tareas/nueva"))
                .andDo(print())
                .andExpect(content().string(containsString("action=\"/usuarios/1/tareas/nueva\"")));
    }

    @Test
    public void nuevaTareaDevuelveNotFound() throws Exception {

        when(usuarioService.findById(1L)).thenReturn(null);

        this.mockMvc.perform(get("/usuarios/1/tareas/nueva"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void editarTareaDevuelveForm() throws Exception {
        Tarea tarea = new Tarea(new Usuario("dcalma@gmail.com"), "Pagar servicios");
        tarea.setId(1L);
        tarea.getUsuario().setId(1L);

        when(tareaService.findById(1L)).thenReturn(tarea);

        this.mockMvc.perform(get("/tareas/1/editar"))
                .andDo(print())
                .andExpect(content().string(allOf(
                        // Contiene la acción para enviar el post a la URL correcta
                        containsString("action=\"/tareas/1/editar\""),
                        // Contiene el texto de la tarea a editar
                        containsString("Pagar servicios"),
                        // Contiene enlace a listar tareas del usuario si se cancela la edición
                        containsString("href=\"/usuarios/1/tareas\""))));
    }
}
