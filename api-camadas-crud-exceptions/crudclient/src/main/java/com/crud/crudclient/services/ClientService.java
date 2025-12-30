package com.crud.crudclient.services;

import com.crud.crudclient.dto.ClientDTO;
import com.crud.crudclient.entities.Client;
import com.crud.crudclient.repositories.ClientRepository;
import com.crud.crudclient.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {

        Page<Client> result = repository.findAll(pageable);

        return result.map(x -> new ClientDTO(x));
    }

    public ClientDTO findById(Long id) {

        Client result = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")
        );
        ClientDTO dto = new ClientDTO(result);
        return dto;
    }

    public ClientDTO insert(ClientDTO dto) {

        Client entity = new Client();
        copyEntity(dto, entity);
        entity = repository.save(entity);
        return  new ClientDTO(entity);
    }

    public ClientDTO update(Long id, ClientDTO dto) {

        try {
            Client entity = repository.getReferenceById(id);
            copyEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado: id inexistente!");
        }
    }

    public void delete(Long id) {

        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado: id inexistente!");
        }

        repository.deleteById(id);

    }

    public void copyEntity(ClientDTO dto, Client entity) {

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
