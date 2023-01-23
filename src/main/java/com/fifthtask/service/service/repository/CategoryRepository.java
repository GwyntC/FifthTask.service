package com.fifthtask.service.service.repository;

import com.fifthtask.service.service.dbutil.MySqlDBUtil;
import com.fifthtask.service.service.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    @Autowired
    private MySqlDBUtil mySqlDBUtil;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    //poollable DataSource (Hikari)
    @Autowired
    private DataSource dataSource;

    public List<Category> getAll() {
        List<Category> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, name from categories");//here
            while (rs.next()){
                result.add(mapCategory(rs));
            }
            return result;
        } catch (Exception e){
            throw new RuntimeException("Getting all categories Error", e);
        }
    }

    private Category mapCategory(ResultSet rs) throws SQLException {
        return Category.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
