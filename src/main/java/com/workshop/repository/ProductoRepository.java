package com.workshop.repository;

import com.workshop.models.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepository extends ReactiveMongoRepository<Producto,String> {

}
