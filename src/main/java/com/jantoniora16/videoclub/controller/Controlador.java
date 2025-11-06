package com.jantoniora16.videoclub.controller;


import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// Importamos modelos y servicios
import com.jantoniora16.videoclub.VideoclubApplication;
import com.jantoniora16.videoclub.modelo.Alquiler;
import com.jantoniora16.videoclub.modelo.Cliente;
import com.jantoniora16.videoclub.modelo.Pelicula;
import com.jantoniora16.videoclub.servicios.ClienteServicio;
import com.jantoniora16.videoclub.servicios.PeliculaServicio;

@Controller
public class Controlador {
	//Inyectamos solo los servicios
	@Autowired
	   PeliculaServicio peliculaServicio;
	
	@Autowired
	   ClienteServicio clienteServicio;

	private static final Logger log= LoggerFactory.getLogger(VideoclubApplication.class);
	
	@GetMapping("/catalogo")
	public String catalogoPeliculas (@RequestParam (required=false) String pelicula, Model model) {

		try { // Importante trabajar con excepciones 

		   // 1. Llama al nuevo servicio
			List<Pelicula> lista = peliculaServicio.buscarTodasLasPeliculas();

		   // 2. Pasamos a la plantilla la lista de películas
		   
			model.addAttribute("listapelis", lista);
		   
		   } catch (SQLException e) { 
		   model.addAttribute("error", e.getMessage()); // Añadimos el mensaje de error a la plantilla
		   } 

		   return "catalogo"; // Devolvemos la plantilla
	}
	
	@GetMapping("/peliculas")
	public String seleccionPeliculas(@RequestParam (required = false) String titulo, Model model) {
		
		String tituloBusqueda = (titulo != null) ? titulo : "";

		try { // Importante trabajar con excepciones 
			
		   List<Pelicula> lista = peliculaServicio.buscarPeliculasPorTitulo(tituloBusqueda);
           model.addAttribute("listaPelis", lista);
		  
		} catch (SQLException e) { 
		model.addAttribute("error", e.getMessage()); // Añadimos el mensaje de error a la plantilla
		} 

		return "peliculas"; // Devolvemos la plantilla

   }

	@SuppressWarnings("deprecation")
	@GetMapping("/detallepelicula/{id_peli}")
	public String detallePelicula(@PathVariable Integer id_peli, Model model) {
		log.info("Detalle pelicula "+ id_peli);
		
		try {
			Pelicula peli = peliculaServicio.buscarPeliculaPorId(id_peli);
            model.addAttribute("pelicula", peli);
            log.info("Pelicula recuperada " + peli.getTitulo());
			  
		
			} catch (Exception e) {
	            model.addAttribute("error", e.getMessage());
	        }
	        return "detallepeli";
	    }   
		
	@GetMapping("/clientes")
	public String buscarClientes(@RequestParam (required = false) String email, Model model) {

	    model.addAttribute("emailBusqueda", email);

	    if (email != null && !email.isEmpty()) {

	    	try {
                List<Cliente> lista = clienteServicio.buscarClientesPorEmail(email);
                model.addAttribute("listaClientes", lista);
                
            } catch (SQLException e) { 
               model.addAttribute("error", e.getMessage());
            } 
        }
        return "listadoclientes"; // Devolvemos la plantilla
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/cliente/detalle/{id}")
	public String detalleCliente(@PathVariable Integer id, Model model) {
        log.info("Detalle cliente " + id);
        try {
            Cliente cliente = clienteServicio.buscarClientePorId(id);
            List<Alquiler> historial = clienteServicio.buscarHistorialCliente(id);
            
            model.addAttribute("cliente", cliente);
            model.addAttribute("historial", historial);
            log.info("Cliente recuperado " + cliente.getNombre());

        } catch (DataAccessException e) {
            log.error("Error al buscar cliente/historial: " + e.getMessage());
            model.addAttribute("error", "No se encontró el cliente o su historial.");
        }
        return "detallecliente";
    }
}




