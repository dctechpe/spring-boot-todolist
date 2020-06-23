package pe.dcalma.springboot.todolist;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.dcalma.springboot.todolist.model.Tarea;
import pe.dcalma.springboot.todolist.model.Usuario;
import pe.dcalma.springboot.todolist.model.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TareaTest {

    @Autowired
    UsuarioRepository usuarioRepository;

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
}

