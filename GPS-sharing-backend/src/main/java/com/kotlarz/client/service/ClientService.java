package com.kotlarz.client.service;

import com.kotlarz.client.domain.Client;

import java.util.List;

public interface ClientService {
     List<Client> getAll();

     Client getByUuid(String uuid);

     Client create();

     void removeClient(Client client);
}
