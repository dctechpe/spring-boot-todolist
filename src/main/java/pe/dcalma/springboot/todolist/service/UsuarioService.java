package pe.dcalma.springboot.todolist.service;

import pe.dcalma.springboot.todolist.model.Usuario;
import pe.dcalma.springboot.todolist.model.Usuario.LoginStatus;
import pe.dcalma.springboot.todolist.model.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario.LoginStatus login(String eMail, String password) {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(eMail);
        if (!usuario.isPresent()) {
            return LoginStatus.USER_NOT_FOUND;
        } else if (!usuario.get().getPassword().equals(password)) {
            return LoginStatus.ERROR_PASSWORD;
        } else {
            return LoginStatus.LOGIN_OK;
        }
    }

    public void registrar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
