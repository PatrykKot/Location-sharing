package com.kotlarz.client.domain;

import com.google.common.base.Objects;
import org.dizitart.no2.objects.Id;

import java.util.Date;
import java.util.UUID;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equal(uuid, client.uuid) &&
                Objects.equal(created, client.created);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, created);
    }
}
