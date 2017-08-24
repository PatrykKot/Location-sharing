package com.kotlarz.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PreDestroy;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.dizitart.no2.objects.ObjectRepository;

import com.kotlarz.component.annotation.Component;

@Component
public class Database {
	private Nitrite database;

	public Database() {
		database = Nitrite.builder().compressed().filePath("database.db").openOrCreate();
	}

	// TODO
	@PreDestroy
	void destroy() {
		database.close();
	}

	public Nitrite getInstance() {
		return database;
	}

	public List<DatabaseCollectionDto> getRepositories() {
		List<DatabaseCollectionDto> repositories = new LinkedList<>();
		for (String repositoryName : database.listRepositories()) {
			try {
				DatabaseCollectionDto repository = new DatabaseCollectionDto(repositoryName);
				ObjectRepository<?> repo = database.getRepository(Class.forName(repositoryName));
				repository.addAll(repo.find());
				repositories.add(repository);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return repositories;
	}

	public List<DatabaseCollectionDto> getCollections() {
		List<DatabaseCollectionDto> collections = new LinkedList<>();
		for (String collectionName : database.listCollectionNames()) {
			DatabaseCollectionDto collection = new DatabaseCollectionDto(collectionName);
			NitriteCollection coll = database.getCollection(collectionName);
			Cursor cursor = coll.find();
			collection.addAll(cursorToList(cursor));
			collections.add(collection);
		}

		return collections;
	}

	private List<Map<String, Object>> cursorToList(Cursor cursor) {
		List<Map<String, Object>> list = new LinkedList<>();
		for (Document document : cursor) {
			list.add(documentToMap(document));
		}
		return list;
	}

	private Map<String, Object> documentToMap(Document document) {
		Map<String, Object> map = new HashMap<>();
		for (Entry<String, Object> entry : document.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

}
