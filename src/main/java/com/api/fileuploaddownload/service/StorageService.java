package com.api.fileuploaddownload.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.fileuploaddownload.entity.ImageData;
import com.api.fileuploaddownload.repository.StorageRepository;
import com.api.fileuploaddownload.util.ImageUtils;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    /*Este método aceita um arquivo de imagem, o comprime, salva-o em um repositório de armazenamento e retorna uma mensagem indicando o status 
    do upload. Ele segue uma abordagem de "falha silenciosa", retornando null em caso de falha em vez de lançar uma exceção. Isso pode ser 
    adequado dependendo da lógica de tratamento de erros da aplicação.*/
    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }


    /*Este método downloadImage é responsável por fazer o download de uma imagem armazenada no banco de dados e retornar os dados da imagem como 
    um array de bytes. */
    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

}
