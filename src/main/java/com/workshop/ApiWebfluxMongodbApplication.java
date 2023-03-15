package com.workshop;

import com.workshop.models.Producto;
import com.workshop.repository.ProductoRepository;
import com.workshop.services.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class ApiWebfluxMongodbApplication implements CommandLineRunner {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(ApiWebfluxMongodbApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiWebfluxMongodbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mongoTemplate.dropCollection("productos")
                .subscribe();
        Flux.just(
                        new Producto(null,"tv samsung",152.23,null),
                        new Producto(null,"pantalla samsung",125.23,null),
                        new Producto(null,"telefono samsung",356.23,null)
                )
                .flatMap(producto -> {
                    producto.setCreateDate(new Date());
                    return productoService.save(producto);
                })
                .subscribe(producto -> log.info("Insert: "+producto.getId()+" "+producto.getNombre()));
    }
}
