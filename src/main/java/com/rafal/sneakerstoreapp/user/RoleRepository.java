package com.rafal.sneakerstoreapp.user;

import com.rafal.sneakerstoreapp.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}