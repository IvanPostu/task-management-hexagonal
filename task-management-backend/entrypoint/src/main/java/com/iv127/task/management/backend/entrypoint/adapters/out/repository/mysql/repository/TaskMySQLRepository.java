package com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.repository;


import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.entity.TaskMySQLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMySQLRepository extends JpaRepository<TaskMySQLEntity, Integer>,
        JpaSpecificationExecutor<TaskMySQLEntity> {

}
