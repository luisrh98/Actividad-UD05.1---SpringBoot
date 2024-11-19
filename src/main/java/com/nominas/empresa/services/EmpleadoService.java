package com.nominas.empresa.services;

import com.nominas.empresa.models.Empleado;
import com.nominas.empresa.repositories.EmpleadoRepository;
import com.nominas.empresa.repositories.NominaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private NominaRepository nominaRepository;

	/**
	 * Buscar todos los empleados.
	 * 
	 * @return Lista de empleados
	 */
	public List<Empleado> buscarTodos() {
		return empleadoRepository.findAll();
	}

	/**
	 * Buscar un empleado por DNI.
	 * 
	 * @param dni El DNI del empleado
	 * @return El empleado encontrado, si existe
	 */
	public Optional<Empleado> buscarPorDni(String dni) {
		return empleadoRepository.findByDni(dni);
	}

	/**
	 * Crear un nuevo empleado.
	 * 
	 * @param empleado El empleado a crear o actualizar
	 * @return El empleado guardado con su nómina
	 */
	@Transactional
	public Empleado crearEmpleado(Empleado empleado) {
		return empleadoRepository.saveEmpleadoConNomina(empleado, nominaRepository); // Deberías pasar un
																						// NominaRepository si lo
																						// necesitas
	}


	/**
	 * Eliminar un empleado por DNI.
	 * 
	 * @param dni El DNI del empleado a eliminar
	 */

	public void eliminarEmpleado(String dni) {
		Optional<Empleado> empleado = empleadoRepository.findByDni(dni);
		if (empleado.isPresent()) {
			empleadoRepository.delete(empleado.get());
		}
	}

	// Lógica de negocio para buscar empleados
	public List<Empleado> buscarEmpleados(String nombre, String dni, String sexo, Integer categoria, Integer anyos) {
		// Convertir cadenas vacías a null
		nombre = (nombre != null && nombre.trim().isEmpty()) ? null : nombre;
		dni = (dni != null && dni.trim().isEmpty()) ? null : dni;
		sexo = (sexo != null && sexo.trim().isEmpty()) ? null : sexo;

		// Llamar al repositorio con los parámetros convertidos
		return empleadoRepository.buscarEmpleados(nombre, dni, sexo, categoria, anyos);
	}
}