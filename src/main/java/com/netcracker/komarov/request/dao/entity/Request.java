package com.netcracker.komarov.request.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "entity_id")
    private long entityId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Request() {
    }

    public Request(long entityId, Status status) {
        this.entityId = entityId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(entityId, request.entityId) &&
                status == request.status;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, entityId, status);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", entityId=" + entityId +
                ", status=" + status +
                '}';
    }
}
