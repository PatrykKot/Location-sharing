package com.kotlarz.database.web;

import java.util.List;

import com.google.inject.Inject;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.database.Database;
import com.kotlarz.database.DatabaseCollectionDto;
import com.kotlarz.web.annotation.RestMapping;

@Component
public class DatabaseController {
	Database database;

	@Inject
	public DatabaseController(Database database) {
		super();
		this.database = database;
	}

	//@RestMapping(mapping = "database/collections/all")
	public List<DatabaseCollectionDto> getAllCollections() {
		return database.getCollections();
	}

	//@RestMapping(mapping = "repositories/all")
	public List<DatabaseCollectionDto> getAllRepositories() {
		return database.getRepositories();
	}
}
