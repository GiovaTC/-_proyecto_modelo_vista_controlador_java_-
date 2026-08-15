package org.example.mvc.dao;

import org.example.mvc.config.ConexionBD;
import org.example.mvc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO  {

    // INSERTAR
    public boolean insertar(Producto producto) {
        String sql = """
                INSERT INTO productos(nombre, precio, cantidad)
                VALUES (?,?,?)
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCantidad());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {

            System.out.println("Error al insertar producto:");
            System.out.println(e.getMessage());

            return false;
        }

        // LISTAR .
        
    }
}
