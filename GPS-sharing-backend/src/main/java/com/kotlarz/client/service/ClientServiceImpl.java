package com.kotlarz.client.service;

import com.google.inject.Inject;
import com.kotlarz.client.domain.Client;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.database.Database;
import com.kotlarz.repository.AbstractCrudRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.LinkedList;
import java.util.List;

@Component
public class ClientServiceImpl extends AbstractCrudRepository<Client> implements ClientService {

    private static final Logger log = LogManager.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl() {
        log.info("Creating");
    }

    @Inject
    public ClientServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = repository.find().toList();
        if (clients == null) {
            clients = new LinkedList<>();
        }

        return clients;
    }

    public Client getByUuid(String uuid) {
        Client client = read(ObjectFilters.eq("uuid", uuid)).firstOrDefault();
        return client;
    }

    public Client create() {
        log.debug("Creating new client");
        Client newClient = new Client();
        create(newClient);
        log.debug("Client created: " + newClient);
        return newClient;
    }

    @Override
    public void removeClient(Client client) {
        delete(client);
    }
}
