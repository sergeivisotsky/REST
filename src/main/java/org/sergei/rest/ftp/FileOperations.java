package org.sergei.rest.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

@Component
public class FileOperations {
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

    // Method to process file download from the server
    public void downloadFile(String remoteFile) {
        try {
            // Connecting to the ftp server
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // Remote file to be downloaded
            File localFile = new File("D:/Program files/servers/apache-tomcat-9.0.10_API/" +
                    "webapps/static/tmp/" + remoteFile);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
            boolean done = ftpClient.retrieveFile(remoteFile, outputStream);
            if (done) {
                LOGGER.info("File downloaded from the server");
            } else {
                LOGGER.error("Failed to download file from the server");
            }
            outputStream.close();
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

    // Method to delete file from the server
    public void deleteFile(String remoteFile) {
        try {
            // Connecting to the ftp server
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean done = ftpClient.deleteFile(remoteFile);
            if (done) {
                LOGGER.info("File deleted from the server");
            } else {
                LOGGER.error("Failed to deleted file from the server");
            }
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
