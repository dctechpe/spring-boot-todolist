package pe.dcalma.springboot.todolist;

import pe.dcalma.springboot.todolist.model.IUsuarioDao;
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
    private IUsuarioDao usuarioDao;

    @Test
    public void crearUsuario() throws Exception {

        // GIVEN
        Usuario usuario = new Usuario("dcalma@gmail.com");
        usuario.setNombre("Darwin Calma");
        usuario.setPassword("12345678");

        // WHEN
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

        usuarioDao.save(usuario);

        // THEN
        assertThat(usuario.getId()).isNotNull();
        logger.info("Identificador del usuario: " + usuario.getId());
        assertThat(usuario.getEmail()).isEqualTo("dcalma@gmail.com");
        assertThat(usuario.getNombre()).isEqualTo("Darwin Calma");
        assertThat(usuario.getPassword()).isEqualTo("12345678");
        assertThat(usuario.getFechaNacimiento()).isEqualTo(sdf.parse("1975-05-30"));
    }

}
