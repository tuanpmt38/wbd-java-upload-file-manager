package com.codegym.fileshare.service.iplm;

import com.codegym.fileshare.model.FileShare;
import com.codegym.fileshare.repository.FileShareRepository;
import com.codegym.fileshare.service.FileShareService;
import org.springframework.beans.factory.annotation.Autowired;

public class FileShareServiceImpl implements FileShareService {

    @Autowired
    private FileShareRepository fileShareRepository;

    @Override
    public Iterable<FileShare> findAll() {
        return fileShareRepository.findAll();
    }

    @Override
    public FileShare findById(Long id) {
        return fileShareRepository.findOne(id);
    }

    @Override
    public void save(FileShare fileShare) {
        fileShareRepository.save(fileShare);
    }

    @Override
    public void remove(Long id) {
        fileShareRepository.delete(id);
    }
}
