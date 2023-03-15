package com.workshop.controller;

import com.workshop.models.Producto;
import com.workshop.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Producto>>> lista(){
        return Mono.just(new ResponseEntity<>(productoService.findAll(), HttpStatus.OK)) ;
//        return Mono.just(ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(productoService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> getById(@PathVariable String id){
        return productoService.findById(id).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Producto>> saveProducto(@RequestBody Producto producto){
        if(producto.getCreateDate()==null){
            producto.setCreateDate(new Date());
        }
//        return productoService.save(producto).map(p -> new ResponseEntity<>(p,HttpStatus.CREATED));
        return productoService.save(producto).map(p-> ResponseEntity
                .created(URI.create("/api/productos/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> update(@RequestBody Producto producto, @PathVariable String id){
        return productoService.findById(id).flatMap(p ->{
            p.setNombre(producto.getNombre());
            p.setPrecio(producto.getPrecio());
            return productoService.save(p);
        }).map(p-> ResponseEntity.created(URI.create("/api/productos/".concat(p.getId())))
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return productoService.findById(id).flatMap(p->{
            return productoService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
