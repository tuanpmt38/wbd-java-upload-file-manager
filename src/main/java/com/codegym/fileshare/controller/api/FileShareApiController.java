package com.codegym.fileshare.controller.api;

import com.codegym.fileshare.model.FileShare;
import com.codegym.fileshare.service.FileShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileShareApiController {

    @Autowired
    private FileShareService fileshareService;

    //API
    @RequestMapping(value = "/fileShare/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileShare> getFileShare(@PathVariable("id")Long id){
        FileShare fileShare = fileshareService.findById(id);
        return new ResponseEntity<FileShare>(fileShare, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.PUT)
    public ResponseEntity<FileShare> changStatus(@PathVariable("id") long id) {

        FileShare file = fileshareService.findById(id);
        file.setStatus(!file.isStatus());
        fileshareService.save(file);
        return new ResponseEntity<>(file, HttpStatus.OK);

    }

}
