package com.kotlarz.database;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Iterables;

public class DatabaseCollectionDto {
	private String name;

	private Set<Object> objects;

	public DatabaseCollectionDto(String name) {
		this.name = name;
		this.objects = new HashSet<>();
	}

	public void addAll(Iterable<?> iterables) {
		Iterables.addAll(objects, iterables);
	}

	public String getName() {
		return name;
	}

	public Set<Object> getObjects() {
		return objects;
	}

}
