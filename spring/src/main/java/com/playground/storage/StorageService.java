package com.playground.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void initAll();

    void initPlayground();

    void initUser();

    void storePlayground(MultipartFile file, String filename);

    void storeUser(MultipartFile file, String filename);

    Stream<Path> loadAllPlayground();

    Stream<Path> loadAllUser();

    Path loadPlayground(String filename);

    Path loadUser(String filename);

    Resource loadPlaygroundAsResource(String filename);

    Resource loadUserAsResource(String filename);

    void deleteAll();

    void deleteAllPlayground();

    void deleteAllUser();

}
