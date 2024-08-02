package by.ladzislau.gusakov.accountmanagement.repository;

import by.ladzislau.gusakov.accountmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.account " +
            "WHERE u.email = :email")
    Optional<User> findByEmailWithAccount(String email);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.roles r " +
            "WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(String email);
}
