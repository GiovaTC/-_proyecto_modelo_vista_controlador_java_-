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
    }
        // LISTAR .
        public List<Producto> listar() {
            List<Producto> productos = new ArrayList<>();

            String sql = """
                    SELECT id, nombre, precio, cantidad
                    FROM productos
                    ORDER BY id
                    """;

            try (Connection conexion = ConexionBD.obtenerConexion();
                 PreparedStatement statement =
                         conexion.prepareStatement(sql);
                 ResultSet resultado = statement.executeQuery()) {

                while ( resultado.next()) {
                    Producto producto = new Producto();

                    producto.setId(resultado.getInt("id"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setPrecio(resultado.getDouble("precio"));
                    producto.setCantidad(resultado.getInt("cantidad"));

                    productos.add(producto);
                }
            } catch (SQLException e) {
                System.out.println("Error al listar productos: ");
                System.out.println(e.getMessage());
            }

            return productos;
        }

        // BUSCAR POR ID
    public Producto buscarPorId(int id) {

        String sql = """
                SELECT id, nombre, precio, cantidad
                FROM productos
                WHERE id = ?;
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {
                    Producto producto = new Producto();

                    producto.setId(resultado.getInt("id"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setPrecio(resultado.getDouble("precio"));
                    producto.setCantidad(resultado.getInt("cantidad"));

                    return producto;
                }
            }
        } catch (SQLException e) {

            System.out.println("Error al buscar producto:");
            System.out.println(e.getMessage());
        }

        return null;
    }

    // ACTUALIZAR .
}
