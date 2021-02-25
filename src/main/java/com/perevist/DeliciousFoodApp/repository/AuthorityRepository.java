package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
