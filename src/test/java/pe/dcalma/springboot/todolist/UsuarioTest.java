package pe.dcalma.springboot.todolist;

import pe.dcalma.springboot.todolist.model.UsuarioRepository;
import pe.dcalma.springboot.todolist.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioTest {

    Logger logger = LoggerFactory.getLogger(UsuarioTest.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void crearUsuario() throws Exception {

        // GIVEN
        Usuario usuario = new Usuario("dcalma@gmail.com");

        // WHEN
        usuario.setNombre("Darwin Calma");
        usuario.setPassword("12345678");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        usuario.setFechaNacimiento(sdf.parse("1975-05-30"));

        // THEN
        assertThat(usuario.getEmail()).isEqualTo("dcalma@gmail.com");
        assertThat(usuario.getNombre()).isEqualTo("Darwin Calma");
        assertThat(usuario.getPassword()).isEqualTo("12345678");
        assertThat(usuario.getFechaNacimiento()).isEqualTo(sdf.parse("1975-05-30"));
    }

    @Test
    @Transactional
    public void crearUsuarioBaseDatos() throws Exception {

        // GIVEN
        Usuario usuario = new Usuario("dcalma@gmail.com");
        usuario.setNombre("Darwin Calma");
        usuario.setPassword("12345678");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        usuario.setFechaNacimiento(sdf.parse("1975-05-30"));

        // WHEN

        usuarioRepository.save(usuario);

        // THEN
        assertThat(usuario.getId()).isNotNull();
        logger.info("Identificador del usuario: " + usuario.getId());
        assertThat(usuario.getEmail()).isEqualTo("dcalma@gmail.com");
        assertThat(usuario.getNombre()).isEqualTo("Darwin Calma");
        assertThat(usuario.getPassword()).isEqualTo("12345678");
        assertThat(usuario.getFechaNacimiento()).isEqualTo(sdf.parse("1975-05-30"));
    }

    @Test
    @Transactional(readOnly = true)
    public void buscarUsuarioEnBaseDatos() {
        // GIVEN
        // En el application.properties se cargan los datos de prueba del fichero datos-test.sql

        // WHEN

        Usuario usuario = usuarioRepository.findById(1L).orElse(null);

        // THEN
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getNombre()).isEqualTo("Darwin Calma");
    }

    @Test
    @Transactional(readOnly = true)
    public void buscarUsuarioPorEmail() {
        // GIVEN
        // Datos cargados de datos-test.sql

        // WHEN
        Usuario usuario = usuarioRepository.findByEmail("dcalma@gmail.com").orElse(null);

        // THEN
        assertThat(usuario.getNombre()).isEqualTo("Darwin Calma");
    }

    @Test
    public void comprobarIgualdadSinId() {
        // GIVEN

        Usuario usuario1 = new Usuario("mariafernandez@gmail.com");
        Usuario usuario2 = new Usuario("mariafernandez@gmail.com");
        Usuario usuario3 = new Usuario("antoniolopez@gmail.com");

        // THEN

        assertThat(usuario1).isEqualTo(usuario1);
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1).isNotEqualTo(usuario3);
    }

    @Test
    public void comprobarIgualdadConId() {
        // GIVEN
        Usuario usuario1 = new Usuario("juangutierrez@gmail.com");
        usuario1.setId(1L);

        Usuario usuario2 = new Usuario("mariafernandez@gmail.com");
        usuario2.setId(1L);

        Usuario usuario3 = new Usuario("antoniolopez@gmail.com");
        usuario3.setId(2L);

        // THEN
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1).isNotEqualTo(usuario3);
    }

}
