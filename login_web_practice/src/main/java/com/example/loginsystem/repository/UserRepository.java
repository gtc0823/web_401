//package com.example.loginsystem.repository;
//
//import com.example.loginsystem.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUsername(String username);
//}

package com.example.loginsystem.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.example.loginsystem.model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//處理從DB裡獲得的數據
	private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setID(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };
    
    public void register_normal(User user) {
        String sql = "INSERT INTO normaluser (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }
    
    public void register_premium(User user) {
        String sql = "INSERT INTO premiumuser (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    public User login_normal(String username, String password) {
        String sql = "SELECT * FROM normaluser WHERE username = ? AND password = ?";
        
        List<User> users = jdbcTemplate.query(sql, userRowMapper ,username,password);
        return users.isEmpty() ? null : users.get(0);
    }
    
    public User login_premium(String username, String password) {
        String sql = "SELECT * FROM premiumuser WHERE username = ? AND password = ?";
        
        List<User> users = jdbcTemplate.query(sql, userRowMapper ,username,password);
        return users.isEmpty() ? null : users.get(0);
    }
}
