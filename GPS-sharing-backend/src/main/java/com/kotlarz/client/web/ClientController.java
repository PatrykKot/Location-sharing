package com.kotlarz.client.web;

import com.google.inject.Inject;
import com.kotlarz.client.domain.Client;
import com.kotlarz.client.service.ClientServiceImpl;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.web.annotation.RestMapping;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ClientController {
    private ClientServiceImpl clientService;

    @Inject
    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @RestMapping("client")
    public Client createNew() {
        Client newClient = clientService.create();
        log.debug("Created new client with uuid " + newClient.getUuid());
        return newClient;
    }

}
