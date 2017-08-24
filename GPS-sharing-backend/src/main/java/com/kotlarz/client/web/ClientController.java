package com.kotlarz.client.web;

import com.google.inject.Inject;
import com.kotlarz.client.domain.Client;
import com.kotlarz.client.service.ClientService;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.web.annotation.RestMapping;

@Component
public class ClientController {
	ClientService clientService;

	@Inject
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	//@RestMapping(mapping = "client")
	public Client createNew() {
		System.out.println();
		return clientService.create();
	}

}
