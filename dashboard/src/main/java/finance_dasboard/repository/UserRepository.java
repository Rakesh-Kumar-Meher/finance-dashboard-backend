package finance_dasboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import finance_dasboard.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
}
