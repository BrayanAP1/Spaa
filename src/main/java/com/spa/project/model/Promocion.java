package com.spa.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "promociones")
public class Promocion {

    @Id
    private String id;
    private String servicio1Id;
    private String servicio2Id;
    private double precioPromocional;
    private Date fechaLimite;  // Cambiado de int a Date

    // Getters y Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getServicio1Id() { return servicio1Id; }
    public void setServicio1Id(String servicio1Id) { this.servicio1Id = servicio1Id; }

    public String getServicio2Id() { return servicio2Id; }
    public void setServicio2Id(String servicio2Id) { this.servicio2Id = servicio2Id; }

    public double getPrecioPromocional() { return precioPromocional; }
    public void setPrecioPromocional(double precioPromocional) { this.precioPromocional = precioPromocional; }

    public Date getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(Date fechaLimite) { this.fechaLimite = fechaLimite; }
}