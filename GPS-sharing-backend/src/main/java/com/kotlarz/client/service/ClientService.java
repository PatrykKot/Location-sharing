package com.kotlarz.client.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dizitart.no2.objects.filters.ObjectFilters;

import com.google.inject.Inject;
import com.kotlarz.client.domain.Client;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.database.Database;
import com.kotlarz.repository.AbstractCrudRepository;

@Component
public class ClientService extends AbstractCrudRepository<Client> {

	private static final Logger log = LogManager.getLogger(ClientService.class);

	@Inject
	public ClientService(Database database) {
		this.database = database;
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
}
