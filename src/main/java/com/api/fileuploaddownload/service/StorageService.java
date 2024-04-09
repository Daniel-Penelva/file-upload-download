package com.api.fileuploaddownload.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.fileuploaddownload.entity.FileData;
import com.api.fileuploaddownload.entity.ImageData;
import com.api.fileuploaddownload.repository.FileDataRepository;
import com.api.fileuploaddownload.repository.StorageRepository;
import com.api.fileuploaddownload.util.ImageUtils;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    // Caminho onde vai fazer o download do arquivo
    private final String FOLDER_PATH = "C:/Projetos Spring Boot no VSCode/API-upload-download/MyFiles";


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


    /* Este método é usada para salvar um arquivo no sistema de arquivos do servidor. A função recebe um parâmetro MultipartFile chamado file, 
    que representa o arquivo a ser salvo.*/
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {

        String filePath=FOLDER_PATH+file.getOriginalFilename();

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }


    /* Este método é usada para ler um arquivo do sistema de arquivos do servidor e retornar seu conteúdo como um array de bytes. A função 
    recebe um parâmetro String chamado fileName, que representa o nome do arquivo a ser lido.*/
    public byte[] downloadImageFromFileSystem(String fileName)throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }


}
