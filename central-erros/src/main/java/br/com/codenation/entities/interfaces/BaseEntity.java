package br.com.codenation.entities.interfaces;

public interface BaseEntity<ID> {
    ID getId();
    void setId(ID id);
}
