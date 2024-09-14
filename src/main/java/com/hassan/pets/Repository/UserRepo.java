package com.hassan.pets.Repository;

import com.hassan.pets.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {


    @Query("select u from Users u where email like :userEmail ")
    Optional<Users> findByEmail(@Param("userEmail") String userEmail);
}
