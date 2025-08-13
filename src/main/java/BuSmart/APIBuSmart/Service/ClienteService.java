package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.ClienteEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepCliente.ExcepcionClienteDuplicado;
import BuSmart.APIBuSmart.Exceptions.ExcepCliente.ExcepcionClienteNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepCliente.ExcepcionClienteNoRegistrado;
import BuSmart.APIBuSmart.Models.DTO.ClienteDTO;
import BuSmart.APIBuSmart.Repositories.ClienteRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository repoCliente;

    @Autowired
    public ClienteService(ClienteRepository repoCliente) {
        this.repoCliente = repoCliente;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerClientes() {
        try {
            List<ClienteEntity> clientes = repoCliente.findAll();

            if (clientes.isEmpty()) {
                log.warn("No se encontraron clientes en la base de datos");
                return List.of(); // Retorna lista vacía, no null
            }

            return clientes.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {
            log.error("Error de acceso a datos en obtenerClientes(): {}", e.getMessage());
            throw new ExcepcionClienteNoEncontrado("Error al consultar clientes en la base de datos");
        } catch (Exception e) {
            log.error("Error inesperado en obtenerClientes(): {}", e.getMessage());
            throw new ExcepcionClienteNoRegistrado("Error interno al recuperar clientes");
        }
    }

    @Transactional
    public ClienteDTO insertarCliente(@Valid ClienteDTO clienteDTO) {
        try {
            // Validar correo duplicado
            if (clienteDTO.getCorreo() != null && repoCliente.existsByCorreo(clienteDTO.getCorreo())) {
                throw new ExcepcionClienteDuplicado("El correo ya está registrado", "correo");
            }

            ClienteEntity clienteEntity = convertirAEntity(clienteDTO);
            ClienteEntity clienteGuardado = repoCliente.save(clienteEntity);

            return convertirADTO(clienteGuardado);

        } catch (ExcepcionClienteDuplicado e) {
            throw e; // Re-lanzamos la excepción personalizada
        } catch (Exception e) {
            log.error("Error al registrar cliente: {}", e.getMessage());
            throw new ExcepcionClienteNoRegistrado("Error al guardar el cliente");
        }
    }

    @Transactional
    public ClienteDTO actualizarCliente(Integer id, @Valid ClienteDTO clienteDTO) {
        try {
            ClienteEntity clienteExistente = repoCliente.findById(id)
                    .orElseThrow(() -> new ExcepcionClienteNoEncontrado("Cliente no encontrado con ID: " + id));

            // Validar si el correo ya existe (excepto para el mismo cliente)
            if (clienteDTO.getCorreo() != null &&
                    !clienteDTO.getCorreo().equals(clienteExistente.getCorreo()) &&
                    repoCliente.existsByCorreo(clienteDTO.getCorreo())) {
                throw new ExcepcionClienteDuplicado("El correo ya está en uso", "correo");
            }

            // Actualizar campos
            clienteExistente.setNombreComplete(clienteDTO.getNombreComplete());
            clienteExistente.setDirection(clienteDTO.getDirection());
            clienteExistente.setCorreo(clienteDTO.getCorreo());

            if (clienteDTO.getContrasenaCliente() != null && !clienteDTO.getContrasenaCliente().isEmpty()) {
                clienteExistente.setContrasenaCliente(clienteDTO.getContrasenaCliente());
            }

            ClienteEntity clienteActualizado = repoCliente.save(clienteExistente);
            return convertirADTO(clienteActualizado);

        } catch (ExcepcionClienteNoEncontrado | ExcepcionClienteDuplicado e) {
            throw e; // Excepciones conocidas
        } catch (Exception e) {
            log.error("Error al actualizar cliente: {}", e.getMessage());
            throw new ExcepcionClienteNoRegistrado("Error al actualizar el cliente");
        }
    }

    @Transactional
    public boolean removerCliente(Integer id) {
        try {
            if (!repoCliente.existsById(id)) {
                log.warn("Intento de eliminar cliente no existente con ID: {}", id);
                return false;
            }

            repoCliente.deleteById(id);
            return true;

        } catch (Exception e) {
            log.error("Error al eliminar cliente con ID {}: {}", id, e.getMessage());
            throw new ExcepcionClienteNoRegistrado("Error al eliminar el cliente");
        }
    }


    private ClienteDTO convertirADTO(ClienteEntity entity) {
        if (entity == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(entity.getIdCliente());
        dto.setNombreComplete(entity.getNombreComplete());
        dto.setDirection(entity.getDirection());
        dto.setCorreo(entity.getCorreo());
        dto.setContrasenaCliente(entity.getContrasenaCliente());
        return dto;
    }

    private ClienteEntity convertirAEntity(@Valid ClienteDTO dto) {
        if (dto == null) return null;

        ClienteEntity entity = new ClienteEntity();
        entity.setIdCliente(dto.getIdCliente());
        entity.setNombreComplete(dto.getNombreComplete());
        entity.setDirection(dto.getDirection());
        entity.setCorreo(dto.getCorreo());
        entity.setContrasenaCliente(dto.getContrasenaCliente());
        return entity;
    }
}