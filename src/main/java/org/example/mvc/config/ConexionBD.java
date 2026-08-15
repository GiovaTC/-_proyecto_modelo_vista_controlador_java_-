package org.example.mvc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/productos_mvc";

    private static final String USUARIO = "gtapiero";

    private static final String PASSWORD = "Tapiero123";
    public static Connection obtenerConexion() {

        try {

            Connection conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    PASSWORD
            );

            System.out.println("Conexion exitosa a MySQL.");

            return conexion;

        } catch (SQLException e) {

            System.out.println("Error al conectar con MySQL.");
            System.out.println(e.getMessage());

            return null;
        }
    }
}
