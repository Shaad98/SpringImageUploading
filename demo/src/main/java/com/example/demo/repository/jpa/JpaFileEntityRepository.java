package com.example.demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FileEntity;

public interface JpaFileEntityRepository extends JpaRepository<FileEntity,Integer>{

}
