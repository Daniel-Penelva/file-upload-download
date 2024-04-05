package com.api.fileuploaddownload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.fileuploaddownload.entity.ImageData;

public interface StorageRepository extends JpaRepository<ImageData,Long>{
    
}
