package com.nominas.empresa.repositories;

import com.nominas.empresa.models.Empleado;
import com.nominas.empresa.models.Nomina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

	// Obtener empleado por DNI
	Optional<Empleado> findByDni(String dni);

	// Buscar empleados por nombre, DNI, sexo, categoría y/o años
	@Query("SELECT e FROM Empleado e " + "WHERE (:nombre IS NULL OR e.nombre LIKE :nombre%) "
			+ "AND (:dni IS NULL OR e.dni LIKE :dni%) " + "AND (:sexo IS NULL OR e.sexo = :sexo) "
			+ "AND (:categoria IS NULL OR e.categoria = :categoria) " + "AND (:anyos IS NULL OR e.anyos = :anyos)")
	List<Empleado> buscarEmpleados(@Param("nombre") String nombre, @Param("dni") String dni, @Param("sexo") String sexo,
			@Param("categoria") Integer categoria, @Param("anyos") Integer anyos);

	// Método para crear o actualizar un empleado junto con su nómina
	@Transactional
	default Empleado saveEmpleadoConNomina(Empleado empleado, NominaRepository nominaRepository) {
		// Validación simple para asegurar que el empleado tiene datos esenciales
		if (empleado.getNombre() == null || empleado.getNombre().isEmpty()) {
			throw new IllegalArgumentException("El nombre del empleado no puede estar vacío.");
		}
		if (empleado.getDni() == null || empleado.getDni().isEmpty()) {
			throw new IllegalArgumentException("El DNI del empleado no puede estar vacío.");
		}

		// Si el empleado no tiene categoría o años de experiencia, usar el constructor
		// con valores predeterminados
		if (empleado.getCategoria() == null || empleado.getAnyos() == null) {
			empleado = new Empleado(empleado.getNombre(), empleado.getDni(), empleado.getSexo());
		}

		// Guardar o actualizar empleado
		Empleado empleadoGuardado = save(empleado); // Guardamos o actualizamos al empleado

		// Calcular el sueldo usando el método estático
		int sueldoCalculado = Nomina.sueldo(empleado); // Asumimos que 'Nomina.sueldo' hace el cálculo

		// Buscar si ya existe una nómina asociada al empleado por su DNI
		Nomina nomina = nominaRepository.findById(empleado.getDni()).orElse(null);

		// Si no existe, crear una nueva nómina
		if (nomina == null) {
			nomina = new Nomina();
			nomina.setDni(empleado.getDni());
		}

		// Actualizar el sueldo en la nómina
		nomina.setSueldo(sueldoCalculado);

		// Guardar o actualizar la nómina en el repositorio
		nominaRepository.save(nomina);

		// Retornar el empleado guardado
		return empleadoGuardado;
	}

}