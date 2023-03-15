package com.workshop.repository;

import com.workshop.models.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, String>{

}
