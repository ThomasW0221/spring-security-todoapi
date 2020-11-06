package io.twinterf.todowithjwtauth.repository;

import io.twinterf.todowithjwtauth.models.UserDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDao, String> {
}
