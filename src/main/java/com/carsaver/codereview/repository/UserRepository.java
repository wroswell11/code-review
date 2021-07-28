package com.carsaver.codereview.repository;


import org.springframework.data.repository.CrudRepository;
import com.carsaver.codereview.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(long id);

    List<User> findAllByOrderByIdAsc();

    User save(User user);
}
