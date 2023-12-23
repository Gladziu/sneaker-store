package com.rafal.IStore.user;

import com.rafal.IStore.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}