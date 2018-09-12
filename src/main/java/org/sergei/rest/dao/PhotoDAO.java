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
    private static final String SQL_SAVE_FILE = "INSERT INTO photos(customer_id, file_name, file_url, file_type, file_size) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_FILE_NAME_BY_CUST_ID = "SELECT file_name FROM photos WHERE customer_id = ?";
    private static final String SQL_EXISTS_BY_CUSTOMER_ID = "SELECT count(*) FROM orders WHERE customer_id = ?";
    private static final String SQL_DELETE_BY_CUSTOMER_ID = "DELETE FROM photos WHERE customer_id = ?";
    private static final String SQL_FIND_PHOTO_BY_CUSTOMER_ID = "SELECT * FROM photos WHERE customer_id = ?";
    private static final String SQL_FIND_ALL_PHOTOS_BY_CUSTOMER_ID = "SELECT * FROM photos WHERE customer_id = ?";
    private static final String SQL_FIND_PHOTO_BY_CUSTOMER_ID_AND_NAME = "SELECT * FROM photos WHERE customer_id = ? AND file_name = ?";
    private static final String SQL_DELETE_BY_CUSTOMER_ID_AND_FILE_NAME = "DELETE FROM photos WHERE customer_id = ? AND file_name = ?";

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Long customerId, String fileDownloadUri,
                     CommonsMultipartFile commonsMultipartFile) {
        try {
            jdbcTemplate.update(SQL_SAVE_FILE, customerId, commonsMultipartFile.getOriginalFilename(),
                    fileDownloadUri, commonsMultipartFile.getContentType(), commonsMultipartFile.getSize());
            LOGGER.info("Photo meta data was saved");
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public PhotoUploadResponse findPhotoByCustomerId(Long customerId) {
        return jdbcTemplate.queryForObject(SQL_FIND_PHOTO_BY_CUSTOMER_ID, new PhotoUploadResponseRowMapper(), customerId);
    }

    public PhotoUploadResponse findPhotoByCustomerIdAndFileName(Long customerId, String fileName) {
        return jdbcTemplate.queryForObject(SQL_FIND_PHOTO_BY_CUSTOMER_ID_AND_NAME, new PhotoUploadResponseRowMapper(), customerId, fileName);
    }

    public List<PhotoUploadResponse> findAllPhotosByCustomerId(Long customerId) {
        return jdbcTemplate.query(SQL_FIND_ALL_PHOTOS_BY_CUSTOMER_ID, new PhotoUploadResponseRowMapper(), customerId);
    }

    public String findFileNameByCustomerId(Long customerId) {
        return jdbcTemplate.queryForObject(SQL_FIND_FILE_NAME_BY_CUST_ID, new Object[]{customerId}, String.class);
    }

    public boolean existsByCustomerId(Long customerId) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_CUSTOMER_ID, new Object[]{customerId}, Integer.class);
        return count > 0;
    }

    public void deleteFileByCustomerId(Long customerId) {
        try {
            jdbcTemplate.update(SQL_DELETE_BY_CUSTOMER_ID, customerId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void deleteFileByCustomerIdAndFileName(Long customerId, String fileName) {
        try {
            jdbcTemplate.update(SQL_DELETE_BY_CUSTOMER_ID_AND_FILE_NAME, customerId, fileName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static final class PhotoUploadResponseRowMapper implements RowMapper<PhotoUploadResponse> {

        @Override
        public PhotoUploadResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();

            photoUploadResponse.setPhotoId(rs.getLong("photo_id"));
            photoUploadResponse.setCustomerId(rs.getLong("customer_id"));
            photoUploadResponse.setFileName(rs.getString("file_name"));
            photoUploadResponse.setFileUrl(rs.getString("file_url"));
            photoUploadResponse.setFileType(rs.getString("file_type"));
            photoUploadResponse.setFileSize(rs.getLong("file_size"));

            return photoUploadResponse;
        }
    }
}
