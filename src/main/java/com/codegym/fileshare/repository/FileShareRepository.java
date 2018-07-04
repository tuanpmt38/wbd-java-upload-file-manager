package com.codegym.fileshare.repository;

import com.codegym.fileshare.model.FileShare;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileShareRepository extends PagingAndSortingRepository<FileShare, Long> {
}
