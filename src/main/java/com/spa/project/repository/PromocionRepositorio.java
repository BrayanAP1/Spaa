package com.spa.project.repository;

import com.spa.project.model.Promocion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromocionRepositorio extends MongoRepository<Promocion, String> {
}
