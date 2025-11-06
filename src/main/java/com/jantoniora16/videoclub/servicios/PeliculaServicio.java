package com.jantoniora16.videoclub.servicios;

// Importamos modelos
import com.jantoniora16.videoclub.modelo.Pelicula;
import com.jantoniora16.videoclub.VideoclubApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PeliculaServicio {

    @Autowired // Para inyectar el servicio al controlador
    JdbcTemplate jdbcTemplate;
    
    // Traigo el logger
    private static final Logger log = LoggerFactory.getLogger(VideoclubApplication.class);

    //  Método del catálogo 

    public List<Pelicula> buscarTodasLasPeliculas() throws SQLException {
        
        Connection connection = null; 
        log.info("1. creamos lista ");
        
        connection = jdbcTemplate.getDataSource().getConnection();
        
        Statement st = connection.createStatement(); 
        
        st.execute("select film_id, title, description, length, rating from film"); 
        ResultSet pelis = st.getResultSet(); 

        List<Pelicula> lista = createList(pelis);
        
        st.close();
        connection.close();
        
        return lista;
    }
    
    //** Método para buscar películas por título
     
    public List<Pelicula> buscarPeliculasPorTitulo(String titulo) throws SQLException {
        Connection connection = null;
        List<Pelicula> lista = new ArrayList<>();

        connection = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement("select film_id, title, description, length, rating from film WHERE title LIKE ?");
        ps.setString(1, titulo + "%");
        ResultSet pelis = ps.executeQuery();
        
        lista = createList(pelis);
        log.info("tamaño " + lista.size());
        
        ps.close();
        connection.close();
        
        return lista;
    }

    // Método para obtener el detalle de una película
    public Pelicula buscarPeliculaPorId(Integer idPeli) {
        String sql = "SELECT film_id, title, description, release_year, length, rating FROM film WHERE film_id = ?";
        
        Pelicula peli = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new Pelicula(
                rs.getInt("film_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("length"),
                rs.getString("rating"),
                rs.getInt("release_year"),
                null
            ), 
            idPeli
        ).getFirst();
        
        return peli;
    }

    // Helper para convertir ResultSet de Películas a Lista

    public static List createList(ResultSet resultSet) throws SQLException {
        List<Pelicula> lista = new ArrayList<>();
        Integer i = 0;
        log.info("createList");
        while (resultSet.next()) {
            Pelicula p = new Pelicula();
            p.setId(resultSet.getInt("film_id"));
            p.setTitulo(resultSet.getString("title"));
            p.setDescripcion(resultSet.getString("description"));
            p.setDuracion(resultSet.getInt("length"));
            p.setClasificacion(resultSet.getString("rating"));
            log.info(p.toString());
            lista.add(p);
        }
        log.info("Tamaño de la lista " + lista.size());
        return lista;
    }
}