package com.example.demo.persistence;

import com.example.demo.model.SimpleRole;
import com.example.demo.model.SimpleUser;
import com.example.demo.model.User;
import com.example.demo.model.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class PostgresqlUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostgresqlUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<User> findUser(String uniqueString) {
        //throw new UnsupportedOperationException("Not implemented.");
        String checkquery = "SELECT COUNT(EMail) FROM Kunde WHERE EMail like ?";
        Integer checkexist = jdbcTemplate.queryForObject(checkquery, Integer.class, uniqueString);
        if (checkexist == null || checkexist == 0) {
            throw new UsernameNotFoundException("Kunde not found");
        } else {
            String query = "SELECT Passwort FROM Kunde WHERE EMail = ?";
            String password = jdbcTemplate.queryForObject(query, String.class, uniqueString);
            return Optional.of(new SimpleUser(uniqueString, "{noop}"+password, Set.of(new SimpleRole("KUNDE"))));
        }
    }
}
