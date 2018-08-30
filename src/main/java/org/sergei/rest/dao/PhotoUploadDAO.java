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

@Repository
public class PhotoUploadDAO {
    private static final String SQL_SAVE_FILE = "INSERT INTO photos(customer_id, file_name, file_url, file_type, file_size) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_FILE_NAME_BY_CUST_ID = "SELECT file_name FROM photos WHERE customer_id = ?";

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

    public String findFileNameByCustomerId(Long customerId) {
        return jdbcTemplate.queryForObject(SQL_FIND_FILE_NAME_BY_CUST_ID, new Object[]{customerId}, String.class);
    }

    private static final class PhotoUploadRowMapper implements RowMapper<PhotoUploadResponse> {
        @Override
        public PhotoUploadResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();

            photoUploadResponse.setPhotoId(rs.getLong("photo_id"));
            photoUploadResponse.setCustomerId(rs.getLong("customer_id"));
            photoUploadResponse.setFileName(rs.getString("file_name"));
            photoUploadResponse.setFileUrl(rs.getString("file_url"));
            photoUploadResponse.setFileType(rs.getString("file_name"));
            photoUploadResponse.setFileSize(rs.getLong("file_size"));

            return photoUploadResponse;
        }
    }
}
