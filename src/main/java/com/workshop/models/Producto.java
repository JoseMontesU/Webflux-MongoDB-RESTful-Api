package com.workshop.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "productos")
public class Producto {
    @Id
    private String id;
    private String nombre;
    private Double precio;
    private Date createDate;

    public String getId() {
        return id;
    }

    public Producto() {
    }

    public Producto(String id, String nombre, Double precio, Date createDate) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.createDate = createDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
