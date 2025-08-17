package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.ClienteEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepCliente.*;
import BuSmart.APIBuSmart.Models.DTO.ClienteDTO;
import BuSmart.APIBuSmart.Repositories.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> getAllClientes() {
        try {
            List<ClienteEntity> clientes = clienteRepository.findAll();
            if (clientes.isEmpty()) {
                throw new ExcepcionClienteNoRegistrado("No hay clientes registrados en el sistema");
            }
            return clientes.stream()
                    .map(this::convertirAClienteDTO)
                    .collect(Collectors.toList());
        } catch (ExcepcionClienteNoRegistrado e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al listar clientes: " + e.getMessage(), e);
            throw new ExcepcionClienteNoEncontrado("Error al listar clientes: " + e.getMessage());
        }
    }

    public ClienteDTO insertCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByCorreo(clienteDTO.getCorreo())) {
            throw new ExcepcionClienteDuplicado("correo", "El correo ya está registrado");
        }

        try {
            ClienteEntity clienteEntity = convertirAClienteEntity(clienteDTO);
            ClienteEntity clienteGuardado = clienteRepository.save(clienteEntity);
            return convertirAClienteDTO(clienteGuardado);
        } catch (Exception e) {
            log.error("Error al registrar cliente: " + e.getMessage());
            throw new ExcepcionClienteNoRegistrado("Error al registrar el cliente: " + e.getMessage());
        }
    }

    public ClienteDTO updateCliente(int id, ClienteDTO clienteDTO) {
        ClienteEntity clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ExcepcionClienteNoEncontrado("Cliente no encontrado con ID: " + id));

        // Verificar si el correo ya existe en otro cliente
        if (!clienteExistente.getCorreo().equals(clienteDTO.getCorreo())) {
            if (clienteRepository.existsByCorreo(clienteDTO.getCorreo())) {
                throw new ExcepcionClienteDuplicado("correo", "El correo ya está registrado");
            }
        }

        clienteExistente.setNombreCompleto(clienteDTO.getNombreCompleto());
        clienteExistente.setDireccion(clienteDTO.getDireccion());
        clienteExistente.setCorreo(clienteDTO.getCorreo());
        clienteExistente.setContrasenaCliente(clienteDTO.getContrasenaCliente());

        ClienteEntity clienteActualizado = clienteRepository.save(clienteExistente);
        return convertirAClienteDTO(clienteActualizado);
    }

    public boolean deleteCliente(int id) {
        try {
            if (clienteRepository.existsById(id)) {
                clienteRepository.deleteById(id);
                return true;
            } else {
                log.warn("Cliente no encontrado con ID: {}", id);
                throw new ExcepcionClienteNoEncontrado("Cliente no encontrado con ID: " + id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new ExcepcionClienteNoEncontrado("No se encontró cliente con ID: " + id + " para eliminar");
        }
    }

    private ClienteDTO convertirAClienteDTO(ClienteEntity cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombreCompleto(cliente.getNombreCompleto());
        dto.setDireccion(cliente.getDireccion());
        dto.setCorreo(cliente.getCorreo());
        dto.setContrasenaCliente(cliente.getContrasenaCliente());
        return dto;
    }

    private ClienteEntity convertirAClienteEntity(ClienteDTO dto) {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setDireccion(dto.getDireccion());
        cliente.setCorreo(dto.getCorreo());
        cliente.setContrasenaCliente(dto.getContrasenaCliente());
        return cliente;
    }
}