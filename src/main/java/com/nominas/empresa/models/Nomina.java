package com.nominas.empresa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "nomina")
public class Nomina {

    @Id
    @NotNull
    private String dni; // DNI del empleado como clave primaria

    @NotNull
    private Integer sueldo; // Sueldo calculado

    @OneToOne
    @JoinColumn(name = "dni", referencedColumnName = "dni", insertable = false, updatable = false)
    private Empleado empleado;

    // Constructores
    public Nomina() {
    }

    public Nomina(String dni, Integer sueldo) {
        this.dni = dni;
        this.sueldo = sueldo;
    }
    
    //Getters y Setters
    public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getSueldo() {
		return sueldo;
	}

	public void setSueldo(Integer sueldo) {
		this.sueldo = sueldo;
	}

    
    /**
     * Tabla de sueldos base según la categoría del empleado.
     * Cada índice corresponde a una categoría (1-9).
     */
    public static final int[] SUELDO_BASE = {
        50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000
    };

    
	/**
     * Calcula el sueldo de un empleado en función de su categoría y años de experiencia.
     * 
     * El sueldo base se determina según la categoría del empleado.
     * Además, se suman 5000 unidades monetarias por cada año de experiencia.
     *
     * @param emp El empleado cuyo sueldo se desea calcular.
     * @return El sueldo total del empleado, basado en su categoría y años de experiencia.
     */
    public static int sueldo(Empleado emp) {
        int sueldo = SUELDO_BASE[emp.getCategoria() - 1];
        sueldo += 5000 * emp.getAnyos();
        return sueldo;
    }
}