package com.nominas.empresa.repositories;

import com.nominas.empresa.models.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NominaRepository extends JpaRepository<Nomina, String> {

    // Obtener sueldo por DNI
    @Query("SELECT n.sueldo FROM Nomina n WHERE n.dni = :dni")
    Double findSueldoByDni(@Param("dni") String dni);

    // Agregar o actualizar sueldo (se puede hacer con save())
    // default Nomina save(Nomina nomina) { return save(nomina); }

    // Opcional: eliminar sueldo por DNI (asociado a empleado)
    void deleteByDni(String dni);
    
 // Método para buscar una nómina por DNI
    Nomina findByDni(String dni);
}