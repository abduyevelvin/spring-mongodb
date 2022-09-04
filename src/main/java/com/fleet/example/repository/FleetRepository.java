package com.fleet.example.repository;

import com.fleet.example.model.Fleet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FleetRepository extends MongoRepository<Fleet, Long> {
}
