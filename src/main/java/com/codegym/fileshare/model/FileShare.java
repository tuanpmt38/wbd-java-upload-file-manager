package com.codegym.fileshare.model;

import javax.persistence.*;

@Entity
@Table(name ="files")
public class FileShare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private boolean status;
    private String feature;

    public FileShare(){}

    public FileShare(String name, String description, boolean status, String feature) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.feature = feature;
    }

    public FileShare(Long id, String name, String description, boolean status, String feature) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.feature = feature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
