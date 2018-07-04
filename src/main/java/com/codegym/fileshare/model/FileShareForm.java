package com.codegym.fileshare.model;

import org.springframework.web.multipart.MultipartFile;

public class FileShareForm {

    private Long id;
    private String name;
    private String description;
    private boolean share;
    private MultipartFile feature;
    private String featureUrl;

    public FileShareForm(){}

    public FileShareForm(Long id, String name, String description, boolean share, MultipartFile feature, String featureUrl) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.share = share;
        this.feature = feature;
        this.featureUrl = featureUrl;
    }

    public FileShareForm(String name, String description, boolean share, MultipartFile feature, String featureUrl) {

        this.name = name;
        this.description = description;
        this.share = share;
        this.feature = feature;
        this.featureUrl = featureUrl;
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

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public MultipartFile getFeature() {
        return feature;
    }

    public void setFeature(MultipartFile feature) {
        this.feature = feature;
    }

    public String getFeatureUrl() {
        return featureUrl;
    }

    public void setFeatureUrl(String featureUrl) {
        this.featureUrl = featureUrl;
    }
}
