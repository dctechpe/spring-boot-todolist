package pe.dcalma.springboot.todolist;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.dcalma.springboot.todolist.model.Tarea;
import pe.dcalma.springboot.todolist.model.Usuario;
import pe.dcalma.springboot.todolist.service.TareaService;
import pe.dcalma.springboot.todolist.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TareaServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TareaService tareaService;


    @Test
    public void testNuevaTareaUsuario() {
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        // WHEN
        Tarea tarea = tareaService.nuevaTareaUsuario(1L, "Práctica 1 de React");

        // THEN

        Usuario usuario = usuarioService.findByEmail("dcalma@gmail.com");
        assertThat(usuario.getTareas()).contains(tarea);
    }

    @Test
    public void testListadoTareas() {
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        Usuario usuario = new Usuario("dcalma@gmail.com");
        usuario.setId(1L);

        Tarea renovarCE = new Tarea(usuario, "Renovar CE");
        renovarCE .setId(1L);

        // WHEN

        List<Tarea> tareas = tareaService.allTareasUsuario(1L);

        // THEN

        assertThat(tareas).contains(renovarCE);
    }

    @Test
    public void testBuscarTarea() {
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        // WHEN

        Tarea renovarCE = tareaService.findById(2L);

        // THEN

        assertThat(renovarCE).isNotNull();
        assertThat(renovarCE.getTitulo()).isEqualTo("Renovar CE");
    }
}
