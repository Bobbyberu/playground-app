package com.playground.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class StorageService {

    @Value("${playground.imagesdir}")
    public static String imageDir;

    /**
     * Create a file on the server from a multipart File
     *
     * @param file File to store on the server
     * @param filePath Destination File path
     * @throws IOException
     */
    public void store(MultipartFile file, String filePath) throws IOException {
        File convFile = new File(filePath);
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
    }

    /**
     *  Retrieve a file on the server
     * @param filePath Resource path
     * @return byte array of image file
     * @throws IOException
     */
    public byte[] retrieve(String filePath) throws IOException {
        InputStream is = this.getClass().getResourceAsStream(filePath);

        BufferedImage img = ImageIO.read(is);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(img, FilenameUtils.getExtension(filePath), bao);

        return bao.toByteArray();
    }
}
