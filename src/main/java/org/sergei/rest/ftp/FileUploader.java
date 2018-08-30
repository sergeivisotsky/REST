package org.sergei.rest.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUploader {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${ftp.server}")
    private String SERVER;

    @Value("${ftp.port}")
    private int PORT;

    @Value("${ftp.username}")
    private String USERNAME;

    @Value("${ftp.password}")
    private String PASSWORD;

    @Autowired
    private FTPClient ftpClient;

    // Method to perform file upload on the server
    public void uploadFile(CommonsMultipartFile commonsMultipartFile) {
        try {
            // Connecting to the ftp server
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Get local commonsMultipartFile to upload on the server
            File localFile = new File(commonsMultipartFile.getOriginalFilename());
            commonsMultipartFile.transferTo(localFile); // Transfers MultipartFile to a regular file
            String remoteFile = commonsMultipartFile.getOriginalFilename();
            InputStream inputStream = new FileInputStream(localFile);
            boolean done = ftpClient.storeFile(remoteFile, inputStream);

            if (done) {
                LOGGER.info("File uploaded to the server");
            } else {
                LOGGER.error("Failed to upload file to the server");
            }

            inputStream.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
