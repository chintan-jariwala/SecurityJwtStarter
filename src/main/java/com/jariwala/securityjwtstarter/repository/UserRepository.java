package com.jariwala.securityjwtstarter.repository;

import com.jariwala.securityjwtstarter.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
