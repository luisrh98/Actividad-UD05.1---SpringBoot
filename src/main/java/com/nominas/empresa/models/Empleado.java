package com.nominas.empresa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "empleados")
public class Empleado implements Persona {

    @Id
    @NotNull
    private String dni;

    
    private String nombre;

    
    @Pattern(regexp = "^[FfMm]$")
    private String sexo;

    
    @Min(value = 1)
    @Max(value = 10)
    private Integer categoria;

    
    @Min(value = 0)
    private Integer anyos;
    
    @OneToOne(mappedBy = "empleado")
    private Nomina nomina;

    public Empleado() {
    }

    public Empleado(String nombre, String dni, String sexo, Integer categoria, Integer anyos) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
        this.categoria = categoria;
        this.anyos = anyos;
    }

    public Empleado(String nombre, String dni, String sexo) {
        this(nombre, dni, sexo, 1, 0);
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getDni() {
        return dni;
    }
    
    @Override
    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String getSexo() {
        return sexo;
    }

    @Override
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getAnyos() {
        return anyos;
    }

    public void setAnyos(Integer anyos) {
        this.anyos = anyos;
    }
}