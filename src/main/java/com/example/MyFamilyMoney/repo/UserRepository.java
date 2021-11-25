package com.example.MyFamilyMoney.repo;

import com.example.MyFamilyMoney.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
