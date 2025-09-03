package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.UserEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {


    @Autowired
    UserRepository repo;

    public Page<UserDTO> getAllUsers(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserEntity> usuarios = repo.findAll(pageable);

            return usuarios.map(this::convertirAUsuarioDTO);

        } catch (Exception e) {
            log.error("Error al listar usuarios: " + e.getMessage(), e);
            throw new ExceptionsUsuarioNoEncontrado("Error al listar usuarios: " + e.getMessage());
        }
    }



    public UserDTO insertUser(@Valid UserDTO data){
        try {
            UserEntity userEntity = convertirAUsuarioEntity(data);
            UserEntity usuarioGuardado = repo.save(userEntity);
            return convertirAUsuarioDTO(usuarioGuardado);
        }catch (Exception e){
            log.error("Error al registrar usuarios: " + e.getMessage());
            throw new ExceptionsUsuarioNoEncontrado("Error al registrar el usuario: " + e.getMessage());
        }
    }

    public UserDTO actualizarUsuario(Long id, UserDTO usuario){
        UserEntity usuarioExistente = repo.findById(id).orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Usuario no encontrado"));
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setDui(usuario.getDui());
        usuarioExistente.setIdTipoUsuario(usuario.getIdTipoUsuario());
        usuarioExistente.setUsuario(usuario.getUsuario());
        usuarioExistente.setContrasena(usuario.getContrasena());
        usuarioExistente.setNacimiento(usuario.getNacimiento());
        usuarioExistente.setDireccion(usuario.getDireccion());
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
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setDui(usuario.getDui());
        dto.setIdTipoUsuario(usuario.getIdTipoUsuario());
        dto.setUsuario(usuario.getUsuario());
        dto.setContrasena(usuario.getContrasena());
        dto.setNacimiento(usuario.getNacimiento());
        dto.setDireccion(usuario.getDireccion());
        return dto;
    }

    private UserEntity convertirAUsuarioEntity(UserDTO dto){
        UserEntity usuario= new UserEntity();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setDui(dto.getDui());
        usuario.setIdTipoUsuario(dto.getIdTipoUsuario());
        usuario.setUsuario(dto.getUsuario());
        usuario.setContrasena(dto.getContrasena());
        usuario.setNacimiento(dto.getNacimiento());
        usuario.setDireccion(dto.getDireccion());
        return usuario;
    }


}
