package pe.dcalma.springboot.todolist;

import pe.dcalma.springboot.todolist.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioTest {

    @Test
    public void crearUsuario() throws Exception {
        Usuario usuario = new Usuario("dcalma@gmail.com");
        usuario.setNombre("Darwin Calma");
        usuario.setPassword("12345678");
        assertThat(usuario.getEmail()).isEqualTo("dcalma@gmail.com");
        assertThat(usuario.getNombre()).isEqualTo("Darwin Calma");
        assertThat(usuario.getPassword()).isEqualTo("12345678");
    }
}
