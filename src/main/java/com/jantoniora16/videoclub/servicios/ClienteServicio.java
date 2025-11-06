package com.jantoniora16.videoclub.servicios;

// Importamos modelos
import com.jantoniora16.videoclub.modelo.Alquiler;
import com.jantoniora16.videoclub.modelo.Cliente;
import com.jantoniora16.videoclub.VideoclubApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

@Service
public class ClienteServicio {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final Logger log = LoggerFactory.getLogger(VideoclubApplication.class);
    
    // Método para buscar clientes por email

    public List<Cliente> buscarClientesPorEmail(String email) throws SQLException {
        Connection connection = null;
        List<Cliente> lista = new ArrayList<>();

        connection = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT customer_id, first_name, last_name, email FROM customer WHERE email LIKE ?");
        ps.setString(1, "%" + email + "%");
        ResultSet clientesRS = ps.executeQuery();
        
        lista = createClienteList(clientesRS);
        
        ps.close();
        connection.close();
        
        return lista;
    }

    // Método para obtener el detalle de un cliente

    public Cliente buscarClientePorId(Integer id) {
        String sqlCliente = "SELECT c.customer_id, c.first_name, c.last_name, c.email, a.address, ci.city " +
                            "FROM customer c " +
                            "JOIN address a ON c.address_id = a.address_id " +
                            "JOIN city ci ON a.city_id = ci.city_id " +
                            "WHERE c.customer_id = ?";
                            
        Cliente cliente = jdbcTemplate.query(
            sqlCliente,
            (rs, rowNum) -> { 
                Cliente c = new Cliente();
                c.setId(rs.getInt("customer_id"));
                c.setNombre(rs.getString("first_name"));
                c.setApellido(rs.getString("last_name"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("address"));
                c.setCiudad(rs.getString("city"));
                return c;
            }, 
            id
        ).getFirst();
        
        return cliente;
    }
    
    // Método para obtener el historial de un cliente
    public List<Alquiler> buscarHistorialCliente(Integer id) throws DataAccessException {
        String sqlHistorial = "SELECT f.title, r.rental_date, r.return_date " +
                              "FROM rental r " +
                              "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                              "JOIN film f ON i.film_id = f.film_id " +
                              "WHERE r.customer_id = ? " +
                              "ORDER BY r.rental_date DESC";
                              
        List<Alquiler> historial = jdbcTemplate.query(
            sqlHistorial,
            (rs, rowNum) -> new Alquiler(
                rs.getString("title"),
                rs.getTimestamp("rental_date"),
                rs.getTimestamp("return_date")
            ),
            id
        );
        return historial;
    }
    
    // Helper para convertir ResultSet de Clientes a Lista
    public static List createClienteList(ResultSet resultSet) throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        Integer i = 0;
        log.info("createClienteList");
        while (resultSet.next()) {
            Cliente c = new Cliente();
            c.setId(resultSet.getInt("customer_id"));
            c.setNombre(resultSet.getString("first_name"));
            c.setApellido(resultSet.getString("last_name"));
            c.setEmail(resultSet.getString("email"));
            log.info(c.toString());
            lista.add(c);
        }
        log.info("Tamaño de la lista clientes " + lista.size());
        return lista;
    }
}