package org.sergei.rest.dao;

import org.sergei.rest.model.PhotoUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PhotoDAO {
    private static final String SQL_SAVE_FILE = "INSERT INTO photos(customer_number, file_name, file_url, file_type, file_size) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_FILE_NAME_BY_CUST_NUMBER_FILE_NAME = "SELECT file_name FROM photos WHERE customer_number = ? AND file_name = ?";
    private static final String SQL_EXISTS_BY_CUSTOMER_NUMBER = "SELECT count(*) FROM orders WHERE customer_number = ?";
    private static final String SQL_FIND_ALL_PHOTOS_BY_CUSTOMER_NUMBER = "SELECT * FROM photos WHERE customer_number = ?";
    private static final String SQL_FIND_PHOTO_BY_CUSTOMER_NUMBER_AND_FILE_ID = "SELECT * FROM photos WHERE customer_number = ? AND photo_id = ?";
    private static final String SQL_DELETE_BY_CUSTOMER_NUMBER_AND_FILE_ID = "DELETE FROM photos WHERE customer_number = ? AND photo_id = ?";
    private static final String SQL_EXISTS_BY_PHOTO_ID = "SELECT count(*) FROM photos WHERE photo_id = ?";
    private static final String SQL_EXISTS_BY_PHOTO_NAME = "SELECT count(*) FROM photos WHERE file_name = ?";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save photo meta data
    public void save(Long customerNumber, String fileDownloadUri,
                     CommonsMultipartFile commonsMultipartFile) {
        try {
            jdbcTemplate.update(SQL_SAVE_FILE, customerNumber, commonsMultipartFile.getOriginalFilename(),
                    fileDownloadUri, commonsMultipartFile.getContentType(), commonsMultipartFile.getSize());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Find photo meta data by customer number and file ID
    public PhotoUploadResponse findPhotoMetaByCustomerNumberAndFileId(Long customerId, Long photoId) {
        return jdbcTemplate.queryForObject(SQL_FIND_PHOTO_BY_CUSTOMER_NUMBER_AND_FILE_ID, new PhotoUploadResponseRowMapper(), customerId, photoId);
    }

    // Find all photos by customer number
    public List<PhotoUploadResponse> findAllPhotosByCustomerNumber(Long customerNumber) {
        return jdbcTemplate.query(SQL_FIND_ALL_PHOTOS_BY_CUSTOMER_NUMBER, new PhotoUploadResponseRowMapper(), customerNumber);
    }

    // Find photo by customer number and photo ID
    public String findPhotoByCustomerNumberAndFileName(Long customerNumber, String fileName) {
        return jdbcTemplate.queryForObject(SQL_FIND_FILE_NAME_BY_CUST_NUMBER_FILE_NAME, new Object[]{customerNumber, fileName}, String.class);
    }

    // Checks if photo with this customer number exists
    public boolean existsByCustomerNumber(Long customerNumber) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_CUSTOMER_NUMBER, new Object[]{customerNumber}, Integer.class);
        return count > 0;
    }

    // Checks if photo with this photo ID exists
    public boolean existsByPhotoId(Long photoId) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_PHOTO_ID, new Object[]{photoId}, Integer.class);

        return count > 0;
    }

    // Checks if photo with this photo name exists
    public boolean existsByPhotoName(String photoName) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_PHOTO_NAME, new Object[]{photoName}, Integer.class);

        return count > 0;
    }

    // Delete photo by customer number and photo ID
    public void deleteFileByCustomerNumberAndFileId(Long customerNumber, Long photoId) {
        try {
            jdbcTemplate.update(SQL_DELETE_BY_CUSTOMER_NUMBER_AND_FILE_ID, customerNumber, photoId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static final class PhotoUploadResponseRowMapper implements RowMapper<PhotoUploadResponse> {

        @Override
        public PhotoUploadResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();

            photoUploadResponse.setPhotoId(rs.getLong("photo_id"));
            photoUploadResponse.setCustomerNumber(rs.getLong("customer_number"));
            photoUploadResponse.setFileName(rs.getString("file_name"));
            photoUploadResponse.setFileUrl(rs.getString("file_url"));
            photoUploadResponse.setFileType(rs.getString("file_type"));
            photoUploadResponse.setFileSize(rs.getLong("file_size"));

            return photoUploadResponse;
        }
    }
}
