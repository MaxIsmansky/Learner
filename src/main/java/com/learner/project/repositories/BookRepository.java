package com.learner.project.repositories;

import com.learner.project.models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author Ismansky Maxim
 * Date: 23.12.2021
 */
public interface BookRepository extends JpaRepository<Books, Long> {

    Iterable<Books> findBooksByUser_id(Long user_id);
}
