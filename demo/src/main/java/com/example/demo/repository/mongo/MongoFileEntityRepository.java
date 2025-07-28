package com.example.demo.repository.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.FileEntity;

// No need of this bcz upto 16 MB max you can store

public interface MongoFileEntityRepository extends MongoRepository<FileEntity,ObjectId>{

}
