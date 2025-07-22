package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.UserEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Repositories.UserRepository;
import de.mkammerer.argon2.Argon2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {


    @Autowired
    UserRepository repo;

    public List<UserDTO> getAllUsers() {
        try {
            List<UserEntity> usuarios = repo.findAll();
            return usuarios.stream()
                    .map(this::convertirAUsuarioDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar usuarios: " + e.getMessage(), e);
            throw new ExceptionsUsuarioNoEncontrado("Error al listar usuarios: " + e.getMessage());
        }
    }

    public UserDTO insertUser(UserDTO userDto){
        if (userDto == null || userDto.getContrasena() == null || userDto.getContrasena().isEmpty()){
            throw new IllegalArgumentException("Usuario o contraseña no pueden ser nulos o vacios");
        }
        try {
            UserEntity userEntity = convertirAUsuarioEntity(userDto);
            UserEntity usuarioGuardado = repo.save(userEntity);
            return convertirAUsuarioDTO(usuarioGuardado);
        }catch (Exception e){
            log.error("Error al registrar usuarios: " + e.getMessage());
            throw new ExceptionsUsuarioNoEncontrado("Error al registrar el usuario: " + e.getMessage());
        }
    }

    public UserDTO actualizarUsuario(Long id, UserDTO usuario){
        UserEntity usuarioExistente = repo.findById(id).orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Usuario no encontrado"));
        usuarioExistente.setId(usuario.getIdUsuario());
        usuarioExistente.setUsuario(usuario.getUsuario());
        usuarioExistente.setContrasena(usuario.getContrasena());
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setEdad(usuario.getEdad());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setDUI(usuario.getDUI());

        UserEntity usuarioActualizado = repo.save(usuarioExistente);
        return convertirAUsuarioDTO(usuarioActualizado);
    }

    public boolean removerUsuario(Long id){
        try{
            UserEntity objUsuario = repo.findById(id).orElse(null);
            if (objUsuario != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Usuario no encontrado.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro usuario con ID: " + id + " para eliminar.", 1);
        }
    }

    private UserDTO convertirAUsuarioDTO(UserEntity usuario) {

        UserDTO dto = new UserDTO();

        dto.setIdUsuario(usuario.getId());
        dto.setUsuario(usuario.getUsuario());
        dto.setContrasena(usuario.getContrasena());
        dto.setNombre(usuario.getNombre());
        dto.setEdad(usuario.getEdad());
        dto.setCorreo(usuario.getCorreo());
        dto.setDUI(usuario.getDUI());

        return dto;
    }

    private UserEntity convertirAUsuarioEntity(UserDTO dto){
        UserEntity usuario= new UserEntity();
        usuario.setId(dto.getIdUsuario());
        usuario.setUsuario(dto.getUsuario());
        usuario.setContrasena(dto.getContrasena());
        usuario.setNombre(dto.getNombre());
        usuario.setEdad(dto.getEdad());
        usuario.setDUI(dto.getDUI());
        return usuario;
    }
}
