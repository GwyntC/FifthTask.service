package com.fifthtask.service.service.repository;

import com.fifthtask.service.service.dto.ProductUpdateDto;
import com.fifthtask.service.service.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    //poollable DataSource (Hikari)
    @Autowired
    private DataSource dataSource;

    public Optional<Product> getProductById(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("select id, model_name,brand_name,country,price,category_id from products where id=?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
            return Optional.ofNullable(mapProduct(rs));
            } else {
              return null;
             }
        } catch (Exception e) {
            throw new RuntimeException("getProductById Error", e);
        }
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        return Product.builder()
                .id(rs.getLong("id"))
                .brandName(rs.getString("brand_name"))
                .modelName(rs.getString("model_name"))
                .country(rs.getString("country"))
                .price(rs.getBigDecimal("price"))
                .categoryId(rs.getLong("category_id"))
                .build();
    }

    public Long createProduct(Product product) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("insert into products (model_name,brand_name,country,price,category_id) values (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getModelName());
            stmt.setString(2, product.getBrandName());
            stmt.setString(3, product.getCountry());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setLong(5, product.getCategoryId());
            stmt.executeUpdate();
            ResultSet pk = stmt.getGeneratedKeys();
            if (pk.next()) {
                return pk.getLong(1);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Create Product Error", e);
        }
    }

    public long updateProduct(long id, ProductUpdateDto dto) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("update products set model_name=?, brand_name=?, " +
                     "country=?, price=?,category_id=? where id=?")) {
            stmt.setString(1, dto.getModelName());
            stmt.setString(2, dto.getBrandName());
            stmt.setString(3, dto.getCountry());
            stmt.setBigDecimal(4, dto.getPrice());
            stmt.setLong(5, dto.getCategoryId());
            stmt.setLong(6, id);
            stmt.executeUpdate();
            ResultSet rs = stmt.getResultSet();
            //check here
            return 200;
        } catch (Exception ex) {
            throw new RuntimeException("Updating product error");
        }
    }

    public int deleteProduct(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("delete from products where id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return 200;
        } catch (Exception ex) {
            throw new RuntimeException("Delete product error");
        }
    }

    public Optional<List<Product>> searchProduct(String brandName, String category,int startPage,int countPages) {// checked
        List<Product> productList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             //Calling procedure because statement is rather long
             PreparedStatement stmt = conn.prepareStatement("{call searchProducts(?,?,?,?)}")) {
            //using brand name fom table products
            //and category from table categories
            stmt.setString(1, brandName);
            stmt.setString(2, category);
            stmt.setLong(3, startPage);
            stmt.setLong(4, countPages);
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                productList.add(mapProduct(rs));
            }
            return Optional.ofNullable(productList);
        } catch (Exception ex) {
            throw new RuntimeException("SearchProduct error");
        }
    }

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement= connection.prepareStatement("SELECT id, model_name,brand_name,country,price,category_id from products where id>30");
            statement.executeQuery();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                productList.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
}
