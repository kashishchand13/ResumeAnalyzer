package com.example.resume_analyzer.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.resume_analyzer.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}