package com.playground.storage;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocationPlayground;
    private final Path rootLocationUser;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocationPlayground = Paths.get(properties.getLocationPlayground());
        this.rootLocationUser = Paths.get(properties.getLocationUser());
    }

    @Override
    public void storePlayground(MultipartFile file, String filename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocationPlayground.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public void storeUser(MultipartFile file, String filename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocationUser.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAllPlayground() {
        try {
            return Files.walk(this.rootLocationPlayground, 1)
                    .filter(path -> !path.equals(this.rootLocationPlayground))
                    .map(this.rootLocationPlayground::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Stream<Path> loadAllUser() {
        try {
            return Files.walk(this.rootLocationUser, 1)
                    .filter(path -> !path.equals(this.rootLocationUser))
                    .map(this.rootLocationUser::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path loadPlayground(String filename) {
        return rootLocationPlayground.resolve(filename);
    }

    @Override
    public Path loadUser(String filename) {
        return rootLocationUser.resolve(filename);
    }

    @Override
    public Resource loadPlaygroundAsResource(String filename) {
        try {
            Path file = loadPlayground(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                return null;

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Resource loadUserAsResource(String filename) {
        try {
            Path file = loadUser(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                return null;
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        deleteAllPlayground();
        deleteAllUser();
    }

    @Override
    public void deleteAllPlayground() {
        FileSystemUtils.deleteRecursively(rootLocationPlayground.toFile());
    }

    @Override
    public void deleteAllUser() {
        FileSystemUtils.deleteRecursively(rootLocationUser.toFile());
    }

    @Override
    public void initAll() {
        initPlayground();
        initUser();
    }

    @Override
    public void initPlayground() {
        try {
            Files.createDirectories(rootLocationPlayground);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void initUser() {
        try {
            Files.createDirectories(rootLocationUser);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}

