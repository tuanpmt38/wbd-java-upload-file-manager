package com.codegym.fileshare.controller;

import com.codegym.fileshare.model.FileShare;
import com.codegym.fileshare.model.FileShareForm;
import com.codegym.fileshare.service.FileShareService;
import com.codegym.fileshare.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileShareController {

    @Autowired
    private FileShareService fileshareService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/create-files")
    public ModelAndView createForm() {

        ModelAndView modelAndView = new ModelAndView("/file/create");
        modelAndView.addObject("fileShareForm", new FileShareForm());
        return modelAndView;
    }

    @PostMapping("/create-files")
    public ModelAndView createFile(@ModelAttribute("fileShareForm") FileShareForm fileShareForm) {
        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = fileShareForm.getFeature().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            fileShareForm.getFeature().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

            FileShare file = new FileShare();

            file.setName(fileShareForm.getName());
            file.setDescription(fileShareForm.getDescription());
            file.setStatus(fileShareForm.isShare());
            file.setFeature(randomName);
            fileshareService.save(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ModelAndView modelAndView = new ModelAndView("/file/create");
        modelAndView.addObject("fileShareForm", fileShareForm);
        modelAndView.addObject("message", "File create successfully");
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView listFileShare() {

        Iterable<FileShare> fileShares = fileshareService.findAll();
        ModelAndView modelAndView = new ModelAndView("/file/list");
        modelAndView.addObject("fileShare", fileShares);
        return modelAndView;
    }

    @GetMapping("/delete-fileShare/{id}")
    public ModelAndView deleteForm(@PathVariable("id") Long id) {

        FileShare fileShare = fileshareService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/file/delete");
        modelAndView.addObject("fileShare", fileShare);
        return modelAndView;
    }

    @PostMapping("/delete-fileShare/{id}")
    public String deleteFileShare(@PathVariable("id") Long id) {

        FileShare fileShare = fileshareService.findById(id);
        StorageUtils.removeFeature(fileShare.getFeature());
        fileshareService.remove(id);
        return "redirect:/list";
    }

    @GetMapping("/edit-fileShare/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id) {

        FileShare fileShare = fileshareService.findById(id);

        FileShareForm fileShareForm = new FileShareForm();

        fileShareForm.setId(fileShare.getId());
        fileShareForm.setName(fileShare.getName());
        fileShareForm.setDescription(fileShare.getDescription());
        fileShareForm.setFeatureUrl(fileShare.getFeature());
        fileShareForm.setShare(fileShare.isStatus());

        ModelAndView modelAndView = new ModelAndView("/file/edit");
        modelAndView.addObject("fileShareForm", fileShareForm);
        return modelAndView;
    }

    @PostMapping("/edit-fileShare/{id}")
    public ModelAndView editFileShare(@PathVariable("id") Long id, @ModelAttribute("fileShareForm") FileShareForm fileShareForm) {
        try {
            FileShare fileShare = fileshareService.findById(id);
            if (!fileShareForm.getFeature().isEmpty()) {

                StorageUtils.removeFeature(fileShare.getFeature());
                String randomCode = UUID.randomUUID().toString();
                String originFileName = fileShareForm.getFeature().getOriginalFilename();
                String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
                fileShareForm.getFeature().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));
                fileShare.setFeature(randomName);
                fileShareForm.setFeatureUrl(randomName);
            }

            fileShare.setId(fileShareForm.getId());
            fileShare.setName(fileShareForm.getName());
            fileShare.setDescription(fileShareForm.getDescription());
            fileShare.setStatus(fileShareForm.isShare());

            fileshareService.save(fileShare);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModelAndView modelAndView = new ModelAndView("/file/edit");
        modelAndView.addObject("fileShareForm", fileShareForm);
        modelAndView.addObject("message", "update file successfully");
        return modelAndView;
    }


    @GetMapping("/sendMail/{id}")
    public ModelAndView formEmail(@PathVariable("id") Long id) {

        FileShare fileShare = fileshareService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/file/sendmail");
        modelAndView.addObject("id", id);
        modelAndView.addObject("fileShare", fileShare);
        return modelAndView;
    }

    @PostMapping(value = "{id}/sendEmail")
    public ModelAndView doSendEmail(@PathVariable("id") Long id,
                                    @RequestParam("recipient") String recipientAddress,
                                    @RequestParam("subject") String subject,
                                    @RequestParam("message") String message) {
        String url = "http://localhost:8080/" + id + "/download-file";
        // creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + url);

        // sends the e-mail
        javaMailSender.send(email);

        ModelAndView modelAndView = new ModelAndView("/file/sendmail");
        modelAndView.addObject("message", " send mail successfully");
        return modelAndView;
    }

    @GetMapping("/download/{id}")
    public ModelAndView shareForm(@PathVariable("id") Long id) {

        FileShare fileShare = fileshareService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/file/download");
        modelAndView.addObject("fileShare", fileShare);
        return modelAndView;
    }

    @GetMapping("/{id}/download-file")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") Long id) throws IOException {

        FileShare fileShare = fileshareService.findById(id);
        File file = new File(StorageUtils.FEATURE_LOCATION + "/" + fileShare.getFeature());


        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + fileShare.getName())
                .contentType(MediaType.MULTIPART_FORM_DATA).contentLength(file.length())
                .body(resource);

    }
}
