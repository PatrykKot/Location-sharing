package com.kotlarz.database.web;

import com.google.inject.Inject;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.database.Database;
import com.kotlarz.database.DatabaseCollectionDto;

import java.util.List;

@Component
public class DatabaseController {
	private Database database;

	@Inject
	public DatabaseController(Database database) {
		this.database = database;
	}

	//@RestMapping(value = "database/collections/all")
	public List<DatabaseCollectionDto> getAllCollections() {
		return database.getCollections();
	}

	//@RestMapping(value = "repositories/all")
	public List<DatabaseCollectionDto> getAllRepositories() {
		return database.getRepositories();
	}
}
