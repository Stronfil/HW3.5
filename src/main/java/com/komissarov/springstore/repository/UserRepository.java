package com.komissarov.springstore.repository;

import com.komissarov.springstore.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByLogin(String login);
}
