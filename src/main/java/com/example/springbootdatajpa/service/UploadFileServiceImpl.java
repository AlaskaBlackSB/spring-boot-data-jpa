package com.example.springbootdatajpa.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService{

    // Carpeta donde se suben las imagenes
    private final static String UPLOADS_FOLDER = "uploads";


    @Override
    public Resource load(String filename) throws MalformedURLException {
        // Obtiene la ruta absoluta del arhivo
        Path pathPhoto = getPath(filename);

        // Crea el recurso
        Resource resource = null;

        // Obtiene el recurso
        resource = new UrlResource(pathPhoto.toUri());

        // Comprueba que el recurso exista y que se pueda leer
        if (!resource.exists() || !resource.isReadable()){
            throw new RuntimeException("Error: No se puede cargar la imagen." + pathPhoto.toString());
        }

        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        // Generamos un id unico
        String newPhotoName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Path absoluto
        Path rootPath = getPath(newPhotoName);

        // Copia la imagen
        Files.copy(file.getInputStream(), rootPath);

        return newPhotoName;
    }

    @Override
    public boolean delete(String filename) {
        /* Elimina la imagen asosicada al usuario del servidor */
        // Busca la imagen en la carpeta uploads
        Path rootPath = getPath(filename);
        File file =  rootPath.toFile();

        // Comprueba que el archivo exista y que se pueda leer
        if (file.exists() && file.canRead()){
            // Elimina el archivo
            if(file.delete()){
                return true;
            }
        }

        return false;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }

    // Metodo que retorna la ruta absoluta de un archivo
    public Path getPath(String filename){
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
