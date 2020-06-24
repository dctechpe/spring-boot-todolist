package pe.dcalma.springboot.todolist;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.dcalma.springboot.todolist.controller.LoginController;
import pe.dcalma.springboot.todolist.service.UsuarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
public class UsuarioWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void servicioLoginUsuarioOK() throws Exception {

        when(usuarioService.login("dcalma@gmail.com", "12345678")).thenReturn(UsuarioService.LoginStatus.LOGIN_OK);

        this.mockMvc.perform(post("/login")
                .param("eMail","dcalma@gmail.com")
                .param("password","12345678"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hola dcalma@gmail.com!!!")));
    }

    @Test
    public void servicioLoginUsuarioNotFound() throws Exception {

        when(usuarioService.login("pepito.perez@gmail.com", "12345678")).thenReturn(UsuarioService.LoginStatus.USER_NOT_FOUND);

        this.mockMvc.perform(post("/login")
                .param("eMail","pepito.perez@gmail.com")
                .param("password","12345678"))
                .andDo(print())
                .andExpect(content().string(containsString("No existe usuario")));
    }

    @Test
    public void servicioLoginUsuarioErrorPassword() throws Exception {

        when(usuarioService.login("dcalma@gmail.com", "000")).thenReturn(UsuarioService.LoginStatus.ERROR_PASSWORD);

        this.mockMvc.perform(post("/login")
                .param("eMail","dcalma@gmail.com")
                .param("password","000"))
                .andDo(print())
                .andExpect(content().string(containsString("Contraseña incorrecta")));
    }

    @Test
    public void servicioLoginRedirectContraseñaIncorrecta() throws Exception {
        this.mockMvc.perform(get("/login")
                .flashAttr("error", "Contraseña incorrecta"))
                .andDo(print())
                .andExpect(content().string(containsString("Contraseña incorrecta")));
    }

    @Test
    public void servicioLoginRedirectUsuarioNotFound() throws Exception {
        this.mockMvc.perform(get("/login")
                .flashAttr("error", "No existe usuario"))
                .andDo(print())
                .andExpect(content().string(containsString("No existe usuario")));
    }

}
