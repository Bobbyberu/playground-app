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

    Path loadPlayground(String filename, boolean defaultImg);

    Path loadUser(String filename, boolean defaultImg);

    Resource loadPlaygroundAsResource(String filename, boolean defaultImg);

    Resource loadUserAsResource(String filename, boolean defaultImg);

    void deleteAll();

    void deleteAllPlayground();

    void deleteAllUser();

}
