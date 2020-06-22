package pe.dcalma.springboot.todolist;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.dcalma.springboot.todolist.controller.SaludoController;
import pe.dcalma.springboot.todolist.service.SaludoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SaludoController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaludoService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(service.saluda("Darwin")).thenReturn("Hola Mock Darwin");
        this.mockMvc.perform(get("/saludo/Darwin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hola Mock Darwin")));
    }
}
