package com.api.fileuploaddownload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.fileuploaddownload.entity.FileData;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName(String fileName);
}
