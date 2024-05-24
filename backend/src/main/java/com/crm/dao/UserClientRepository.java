package com.crm.dao;

import com.crm.entity.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClientRepository extends JpaRepository<UserClient, Integer> {

}
