package com.nominas.empresa.controller;

import com.nominas.empresa.models.Empleado;
import com.nominas.empresa.models.Nomina;
import com.nominas.empresa.repositories.NominaRepository;
import com.nominas.empresa.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresa")
public class EmpleadoController {
	
    @Autowired
    private NominaRepository nominaRepository;

    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping("/empleados")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.buscarTodos();
        if (empleados.isEmpty()) {
            System.out.println("No se encontraron empleados.");
        }
        model.addAttribute("empleados", empleados);
        return "listarEmpleados"; // Esta vista debe existir en templates/
    }

 // Muestra el formulario de búsqueda y los resultados
    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda(Model model) {
        // Si no hay resultados previos, se agrega una lista vacía
        if (!model.containsAttribute("empleados")) {
            model.addAttribute("empleados", Collections.emptyList());
        }
        return "buscarEmpleados"; // Renderiza la vista del formulario
    }

    @PostMapping("/buscar")
    public String buscarEmpleados(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Integer categoria,
            @RequestParam(required = false) Integer anyos,
            RedirectAttributes redirectAttributes) {

        // Llamar al servicio para realizar la búsqueda
        List<Empleado> empleados = empleadoService.buscarEmpleados(nombre, dni, sexo, categoria, anyos);

        // Agregar los resultados al modelo para que Thymeleaf los muestre
        redirectAttributes.addFlashAttribute("empleados", empleados);

        return "redirect:/empresa/buscar"; // Redirige a la página con los resultados
    }
    
 //Bucar salarios por dni

    @PostMapping("/buscarSalario")
    public String procesarFormularioBusqueda(
            @RequestParam(name = "dni", required = true) String dni,
            RedirectAttributes redirectAttributes) {
        // Busca al empleado por DNI
        Optional<Empleado> empleadoOpt = empleadoService.buscarPorDni(dni);
        Nomina nomina = nominaRepository.findByDni(dni);

        if (empleadoOpt.isPresent() && nomina != null) {
            Empleado empleado = empleadoOpt.get();

            // Agregar atributos del empleado
            redirectAttributes.addFlashAttribute("nombre", empleado.getNombre());
            redirectAttributes.addFlashAttribute("dniEmpleado", empleado.getDni());

            // Agregar atributos de la nómina
            redirectAttributes.addFlashAttribute("sueldo", nomina.getSueldo() + " €");
        } else {
            // Si no se encuentra, agregar un mensaje de error
            redirectAttributes.addFlashAttribute("sueldo", null);
            redirectAttributes.addFlashAttribute("mensajeError", "Empleado o nómina no encontrados.");
        }

        // Redirige a la página de resultados
        return "redirect:/empresa/buscarSalario";
    }

    @GetMapping("/buscarSalario")
    public String mostrarResultados(Model model) {
        // DEPURAR: Imprime los atributos disponibles en el modelo
        System.out.println("Modelo contiene: " + model.asMap());
        return "buscarSalario";
    }
    
    //Crea un empleado

    @GetMapping("/crear")
    public String crearEmpleadoForm(Model model) {
        // Crear un nuevo objeto Empleado y agregarlo al modelo
        model.addAttribute("empleado", new Empleado());
        return "crearEmpleado";
    }

    @PostMapping("/crear")
    public String crearEmpleado(@ModelAttribute Empleado empleado) {
        System.out.println("Empleado recibido: " + empleado.getNombre() + " " + empleado.getDni() + " " + empleado.getSexo() + " " + empleado.getCategoria()+ " " + empleado.getAnyos());
        empleadoService.crearEmpleado(empleado);
        return "redirect:/empresa/empleados";
    }
    
    //Actualiza un empleado

    @PostMapping("/editar")
    public String mostrarFormularioEdicion(@RequestParam String dni, Model model) {
        // Lógica para obtener el empleado por DNI
        Optional<Empleado> empleado = empleadoService.buscarPorDni(dni);
        if (empleado.isPresent()) {
            model.addAttribute("empleado", empleado.get());
            return "actualizarEmpleado"; // Renderiza la vista del formulario de edición
        } else {
            return "redirect:/empresa/empleados"; // Redirige si no se encuentra el empleado
        }
    }

    @PostMapping("/editar/guardar")
    public String editarEmpleado(@ModelAttribute Empleado empleado) {
        // Lógica para guardar el empleado editado
        empleadoService.crearEmpleado(empleado);
        return "redirect:/empresa/empleados"; // Redirige a la lista de empleados
    }




    
    //elimina un empleado


     @PostMapping("/eliminar")
     public String eliminarEmpleado(@RequestParam String dni) {
            // Lógica de eliminación del empleado
    	empleadoService.eliminarEmpleado(dni); // Llama al servicio para eliminar el empleado

        return "redirect:/empresa/empleados"; // Redirige a la lista de empleados después de eliminar
      
     }

}