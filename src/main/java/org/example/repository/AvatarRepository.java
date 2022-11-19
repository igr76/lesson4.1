package org.example.repository;

import org.example.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    @Query(value = "SELECT * FROM avatar LIMIT 10 ", nativeQuery = true)
    List<Avatar> findAll();

}
