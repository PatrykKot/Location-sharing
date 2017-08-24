package com.kotlarz.client.domain;

import java.util.Date;
import java.util.UUID;

import org.dizitart.no2.objects.Id;

public class Client {
	@Id
	private String uuid;

	private Date created;

	public Client() {
		created = new Date();
		this.uuid = UUID.randomUUID().toString();
	}

	public String getUuid() {
		return uuid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Client [uuid=" + uuid + ", created=" + created + "]";
	}

}
