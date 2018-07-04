package com.codegym.fileshare.service;

import com.codegym.fileshare.model.FileShare;

public interface FileShareService {

    Iterable<FileShare> findAll();

    FileShare findById(Long id);

    void save(FileShare fileShare);

    void remove(Long id);
}
