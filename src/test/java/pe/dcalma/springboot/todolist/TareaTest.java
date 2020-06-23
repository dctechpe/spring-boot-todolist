package pe.dcalma.springboot.todolist;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.dcalma.springboot.todolist.model.Tarea;
import pe.dcalma.springboot.todolist.model.TareaRepository;
import pe.dcalma.springboot.todolist.model.Usuario;
import pe.dcalma.springboot.todolist.model.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TareaTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TareaRepository tareaRepository;

    @Test
    public void crearTarea() throws Exception {
        // GIVEN
        Usuario usuario = new Usuario("dcalma@gmail.com");

        // WHEN

        Tarea tarea = new Tarea(usuario, "Práctica 1 de React");

        // THEN

        assertThat(tarea.getTitulo()).isEqualTo("Práctica 1 de React");
        assertThat(tarea.getUsuario()).isEqualTo(usuario);
    }

    @Test
    public void comprobarIgualdadSinId() {
        // GIVEN

        Usuario usuario = new Usuario("dcalma@gmail.com");
        Tarea tarea1 = new Tarea(usuario, "Práctica 1 de React");
        Tarea tarea2 = new Tarea(usuario, "Práctica 1 de React");
        Tarea tarea3 = new Tarea(usuario, "Pagar el servicios");

        // THEN

        assertThat(tarea1).isEqualTo(tarea2);
        assertThat(tarea1).isNotEqualTo(tarea3);
    }

    @Test
    @Transactional
    public void crearTareaEnBaseDatos() {
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        Usuario usuario = usuarioRepository.findById(1L).orElse(null);
        Tarea tarea = new Tarea(usuario, "Práctica 1 de MADS");

        // WHEN

        tareaRepository.save(tarea);

        // THEN

        assertThat(tarea.getId()).isNotNull();
        assertThat(tarea.getUsuario()).isEqualTo(usuario);
        assertThat(tarea.getTitulo()).isEqualTo("Práctica 1 de MADS");
    }


    @Test
    @Transactional
    public void crearTareaEnBaseDatosConUsuarioNoBDLanzaExcepcion() {
        // GIVEN
        // Creamos un usuario sin ID y, por tanto, sin estar en gestionado
        // por JPA
        Usuario usuario = new Usuario("dcalma@gmail.com");
        Tarea tarea = new Tarea(usuario, "Práctica 1 de React");

        // WHEN
        assertThrows(Exception.class, () -> {
            tareaRepository.save(tarea);
        });

        // THEN
        // Se lanza una excepción (capturada en el test)
    }

    @Test
    @Transactional(readOnly = true)
    public void unUsuarioTieneUnaListaDeTareas() {
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        Usuario usuario = usuarioRepository.findById(1L).orElse(null);

        // WHEN
        List<Tarea> tareas = usuario.getTareas();

        // THEN

        assertThat(tareas).hasSize(2);
    }

    @Test
    @Transactional
    public void unaTareaNuevaSeAñadeALaListaDeTareas() {
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        Usuario usuario = usuarioRepository.findById(1L).orElse(null);

        // WHEN

        List<Tarea> tareas = usuario.getTareas();
        Tarea tarea = new Tarea(usuario, "Práctica 1 de React");
        tareaRepository.save(tarea);

        // THEN

        assertThat(usuario.getTareas()).hasSize(3);
        assertThat(tareas).isEqualTo(usuario.getTareas());
        assertThat(usuario.getTareas()).contains(tarea);
    }
}

