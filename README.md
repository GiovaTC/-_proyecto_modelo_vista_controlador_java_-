# -_proyecto_modelo_vista_controlador_java_- :.

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/ff1f538e-9c7b-40b4-820a-7419b8ee58b2" />

```

proyecto completo MVC en Java 21 + IntelliJ IDEA + Maven + MySQL 8 + JDBC, utilizando una aplicación de consola para administrar productos.

1. Estructura completa del proyecto
GestionProductosMVC/
│
├── pom.xml
│
└── src/
    └── main/
        └── java/
            └── com/
                └── ejemplo/
                    └── mvc/
                        │
                        ├── Main.java
                        │
                        ├── config/
                        │   └── ConexionBD.java
                        │
                        ├── modelo/
                        │   └── Producto.java
                        │
                        ├── dao/
                        │   └── ProductoDAO.java
                        │
                        ├── vista/
                        │   └── ProductoVista.java
                        │
                        └── controlador/
                            └── ProductoControlador.java
2. Base de datos MySQL

Primero crea la base de datos.

CREATE DATABASE productos_mvc;

USE productos_mvc;

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INT NOT NULL
);
Datos de prueba
INSERT INTO productos (nombre, precio, cantidad)
VALUES
('Teclado', 85000, 10),
('Mouse', 45000, 20),
('Monitor', 750000, 5);

Para comprobar:

SELECT * FROM productos;
3. pom.xml

Este proyecto utiliza Maven y el conector JDBC oficial de MySQL.

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ejemplo</groupId>
    <artifactId>GestionProductosMVC</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!-- MySQL JDBC -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.4.0</version>
        </dependency>

    </dependencies>

</project>
4. Modelo: Producto.java

Ubicación:

src/main/java/com/ejemplo/mvc/modelo/Producto.java
package com.ejemplo.mvc.modelo;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto() {
    }

    public Producto(int id, String nombre, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
5. Configuración: ConexionBD.java

Ubicación:

src/main/java/com/ejemplo/mvc/config/ConexionBD.java

Aquí debes colocar tu usuario y contraseña de MySQL.

package com.ejemplo.mvc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/productos_mvc";

    private static final String USUARIO = "root";

    private static final String PASSWORD = "123456";

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

Si tu contraseña de MySQL es diferente, modifica solamente:

private static final String PASSWORD = "123456";
6. DAO: ProductoDAO.java

El DAO es responsable de comunicarse directamente con MySQL.

Ubicación:

src/main/java/com/ejemplo/mvc/dao/ProductoDAO.java
package com.ejemplo.mvc.dao;

import com.ejemplo.mvc.config.ConexionBD;
import com.ejemplo.mvc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // INSERTAR
    public boolean insertar(Producto producto) {

        String sql = """
                INSERT INTO productos (nombre, precio, cantidad)
                VALUES (?, ?, ?)
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

    // LISTAR
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

            while (resultado.next()) {

                Producto producto = new Producto();

                producto.setId(resultado.getInt("id"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                producto.setCantidad(resultado.getInt("cantidad"));

                productos.add(producto);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar productos:");
            System.out.println(e.getMessage());
        }

        return productos;
    }

    // BUSCAR POR ID
    public Producto buscarPorId(int id) {

        String sql = """
                SELECT id, nombre, precio, cantidad
                FROM productos
                WHERE id = ?
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

    // ACTUALIZAR
    public boolean actualizar(Producto producto) {

        String sql = """
                UPDATE productos
                SET nombre = ?, precio = ?, cantidad = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCantidad());
            statement.setInt(4, producto.getId());

            int filas = statement.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar producto:");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM productos
                WHERE id = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas = statement.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar producto:");
            System.out.println(e.getMessage());

            return false;
        }
    }
}
7. Vista: ProductoVista.java

La Vista se encarga de mostrar información y solicitar datos al usuario.

Ubicación:

src/main/java/com/ejemplo/mvc/vista/ProductoVista.java
package com.ejemplo.mvc.vista;

import com.ejemplo.mvc.modelo.Producto;

import java.util.List;
import java.util.Scanner;

public class ProductoVista {

    private final Scanner scanner;

    public ProductoVista() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {

        System.out.println();
        System.out.println("==================================");
        System.out.println("       GESTION DE PRODUCTOS       ");
        System.out.println("==================================");
        System.out.println("1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Salir");
        System.out.println("==================================");
        System.out.print("Seleccione una opcion: ");
    }

    public int leerOpcion() {

        try {

            return Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {

            return -1;
        }
    }

    public Producto solicitarProducto() {

        System.out.println();
        System.out.println("REGISTRAR PRODUCTO");
        System.out.println("------------------");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(scanner.nextLine());

        return new Producto(nombre, precio, cantidad);
    }

    public int solicitarId() {

        System.out.print("Ingrese el ID del producto: ");

        return Integer.parseInt(scanner.nextLine());
    }

    public Producto solicitarDatosActualizacion(int id) {

        System.out.println();
        System.out.println("ACTUALIZAR PRODUCTO");
        System.out.println("-------------------");

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.print("Nueva cantidad: ");
        int cantidad = Integer.parseInt(scanner.nextLine());

        return new Producto(id, nombre, precio, cantidad);
    }

    public void mostrarProducto(Producto producto) {

        if (producto == null) {

            System.out.println();
            System.out.println("Producto no encontrado.");

            return;
        }

        System.out.println();
        System.out.println("PRODUCTO ENCONTRADO");
        System.out.println("-------------------");
        System.out.println("ID:       " + producto.getId());
        System.out.println("Nombre:   " + producto.getNombre());
        System.out.println("Precio:   $" + producto.getPrecio());
        System.out.println("Cantidad: " + producto.getCantidad());
    }

    public void mostrarProductos(List<Producto> productos) {

        System.out.println();
        System.out.println("LISTADO DE PRODUCTOS");
        System.out.println("--------------------");

        if (productos.isEmpty()) {

            System.out.println("No existen productos.");

            return;
        }

        System.out.printf(
                "%-5s %-25s %-15s %-10s%n",
                "ID",
                "NOMBRE",
                "PRECIO",
                "CANTIDAD"
        );

        System.out.println(
                "------------------------------------------------------------"
        );

        for (Producto producto : productos) {

            System.out.printf(
                    "%-5d %-25s $%-14.2f %-10d%n",
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getCantidad()
            );
        }
    }

    public void mostrarMensaje(String mensaje) {

        System.out.println();
        System.out.println(mensaje);
    }

    public void cerrarScanner() {

        scanner.close();
    }
}
8. Controlador: ProductoControlador.java

El controlador conecta la Vista con el Modelo/DAO.

Ubicación:

src/main/java/com/ejemplo/mvc/controlador/ProductoControlador.java
package com.ejemplo.mvc.controlador;

import com.ejemplo.mvc.dao.ProductoDAO;
import com.ejemplo.mvc.modelo.Producto;
import com.ejemplo.mvc.vista.ProductoVista;

import java.util.List;

public class ProductoControlador {

    private final ProductoDAO productoDAO;
    private final ProductoVista productoVista;

    public ProductoControlador(
            ProductoDAO productoDAO,
            ProductoVista productoVista) {

        this.productoDAO = productoDAO;
        this.productoVista = productoVista;
    }

    public void iniciar() {

        int opcion;

        do {

            productoVista.mostrarMenu();

            opcion = productoVista.leerOpcion();

            switch (opcion) {

                case 1:
                    registrarProducto();
                    break;

                case 2:
                    listarProductos();
                    break;

                case 3:
                    buscarProducto();
                    break;

                case 4:
                    actualizarProducto();
                    break;

                case 5:
                    eliminarProducto();
                    break;

                case 6:
                    productoVista.mostrarMensaje(
                            "Aplicacion finalizada."
                    );
                    break;

                default:
                    productoVista.mostrarMensaje(
                            "Opcion no valida."
                    );
            }

        } while (opcion != 6);

        productoVista.cerrarScanner();
    }

    private void registrarProducto() {

        try {

            Producto producto =
                    productoVista.solicitarProducto();

            boolean resultado =
                    productoDAO.insertar(producto);

            if (resultado) {

                productoVista.mostrarMensaje(
                        "Producto registrado correctamente."
                );

            } else {

                productoVista.mostrarMensaje(
                        "No fue posible registrar el producto."
                );
            }

        } catch (NumberFormatException e) {

            productoVista.mostrarMensaje(
                    "Los valores numericos no son validos."
            );
        }
    }

    private void listarProductos() {

        List<Producto> productos =
                productoDAO.listar();

        productoVista.mostrarProductos(productos);
    }

    private void buscarProducto() {

        try {

            int id = productoVista.solicitarId();

            Producto producto =
                    productoDAO.buscarPorId(id);

            productoVista.mostrarProducto(producto);

        } catch (NumberFormatException e) {

            productoVista.mostrarMensaje(
                    "El ID debe ser numerico."
            );
        }
    }

    private void actualizarProducto() {

        try {

            int id = productoVista.solicitarId();

            Producto existente =
                    productoDAO.buscarPorId(id);

            if (existente == null) {

                productoVista.mostrarMensaje(
                        "El producto no existe."
                );

                return;
            }

            Producto producto =
                    productoVista.solicitarDatosActualizacion(id);

            boolean resultado =
                    productoDAO.actualizar(producto);

            if (resultado) {

                productoVista.mostrarMensaje(
                        "Producto actualizado correctamente."
                );

            } else {

                productoVista.mostrarMensaje(
                        "No fue posible actualizar el producto."
                );
            }

        } catch (NumberFormatException e) {

            productoVista.mostrarMensaje(
                    "Los valores ingresados no son validos."
            );
        }
    }

    private void eliminarProducto() {

        try {

            int id = productoVista.solicitarId();

            Producto producto =
                    productoDAO.buscarPorId(id);

            if (producto == null) {

                productoVista.mostrarMensaje(
                        "El producto no existe."
                );

                return;
            }

            boolean resultado =
                    productoDAO.eliminar(id);

            if (resultado) {

                productoVista.mostrarMensaje(
                        "Producto eliminado correctamente."
                );

            } else {

                productoVista.mostrarMensaje(
                        "No fue posible eliminar el producto."
                );
            }

        } catch (NumberFormatException e) {

            productoVista.mostrarMensaje(
                    "El ID debe ser numerico."
            );
        }
    }
}
9. Clase principal: Main.java

Ubicación:

src/main/java/com/ejemplo/mvc/Main.java
package com.ejemplo.mvc;

import com.ejemplo.mvc.controlador.ProductoControlador;
import com.ejemplo.mvc.dao.ProductoDAO;
import com.ejemplo.mvc.vista.ProductoVista;

public class Main {

    public static void main(String[] args) {

        ProductoDAO productoDAO = new ProductoDAO();

        ProductoVista productoVista = new ProductoVista();

        ProductoControlador productoControlador =
                new ProductoControlador(
                        productoDAO,
                        productoVista
                );

        productoControlador.iniciar();
    }
}
10. ¿Cómo funciona MVC en este proyecto?

La separación queda así:

Modelo

Producto.java

Representa la información del producto:

Producto
├── id
├── nombre
├── precio
└── cantidad
Vista

ProductoVista.java

Se encarga exclusivamente de la interacción con el usuario:

Usuario
   ↓
ProductoVista
Controlador

ProductoControlador.java

Controla las operaciones:

Vista
  ↓
Controlador
  ↓
DAO
DAO

ProductoDAO.java

Realiza las operaciones SQL:

INSERT
SELECT
UPDATE
DELETE
Base de datos

MySQL almacena finalmente la información:

productos_mvc
      │
      └── productos
11. Ejecución en IntelliJ IDEA
Paso 1 — Crear el proyecto

En IntelliJ:

File
→ New
→ Project
→ Maven

Selecciona:

JDK: 21

Nombre:

GestionProductosMVC
Paso 2 — Crear los paquetes

Dentro de:

src/main/java

crea:

com.ejemplo.mvc

Y dentro:

config
modelo
dao
vista
controlador
Paso 3 — Crear las clases

Coloca cada clase en su respectivo paquete:

com.ejemplo.mvc
└── Main.java

com.ejemplo.mvc.config
└── ConexionBD.java

com.ejemplo.mvc.modelo
└── Producto.java

com.ejemplo.mvc.dao
└── ProductoDAO.java

com.ejemplo.mvc.vista
└── ProductoVista.java

com.ejemplo.mvc.controlador
└── ProductoControlador.java
Paso 4 — Configurar MySQL

En ConexionBD.java:

private static final String URL =
        "jdbc:mysql://localhost:3306/productos_mvc";

private static final String USUARIO = "root";

private static final String PASSWORD = "123456";

Por ejemplo, si tu MySQL utiliza:

Usuario: root
Contraseña: root

cambia a:

private static final String PASSWORD = "root";
Paso 5 — Cargar Maven

En IntelliJ:

Maven
→ Reload All Maven Projects

Debe descargar automáticamente:

mysql-connector-j
12. Ejecutar

Ejecuta:

Main.java

La consola mostrará:

Conexion exitosa a MySQL.

==================================
       GESTION DE PRODUCTOS
==================================
1. Registrar producto
2. Listar productos
3. Buscar producto
4. Actualizar producto
5. Eliminar producto
6. Salir
==================================
Seleccione una opcion:
13. Registrar producto

Seleccionamos:

1

La aplicación solicita:

REGISTRAR PRODUCTO
------------------
Nombre: Laptop Lenovo
Precio: 2500000
Cantidad: 5

Resultado:

Producto registrado correctamente.

En MySQL:

SELECT * FROM productos;

Obtendremos algo similar a:

+----+---------------+-----------+----------+
| id | nombre        | precio    | cantidad |
+----+---------------+-----------+----------+
|  1 | Teclado       |  85000.00 |       10 |
|  2 | Mouse         |  45000.00 |       20 |
|  3 | Monitor       | 750000.00 |        5 |
|  4 | Laptop Lenovo | 2500000.00|        5 |
+----+---------------+-----------+----------+
14. Listar productos

Seleccionamos:

2

Resultado:

LISTADO DE PRODUCTOS
--------------------
ID    NOMBRE                    PRECIO          CANTIDAD
------------------------------------------------------------
1     Teclado                   $85000.00       10
2     Mouse                     $45000.00       20
3     Monitor                   $750000.00      5
4     Laptop Lenovo             $2500000.00     5
15. Buscar producto

Seleccionamos:

3

Después:

Ingrese el ID del producto: 2

Resultado:

PRODUCTO ENCONTRADO
-------------------
ID:       2
Nombre:   Mouse
Precio:   $45000.0
Cantidad: 20
16. Actualizar producto

Seleccionamos:

4

Ingresamos:

Ingrese el ID del producto: 2

ACTUALIZAR PRODUCTO
-------------------
Nuevo nombre: Mouse Logitech
Nuevo precio: 65000
Nueva cantidad: 15

Resultado:

Producto actualizado correctamente.
17. Eliminar producto

Seleccionamos:

5

Ingresamos:

Ingrese el ID del producto: 2

Resultado:

Producto eliminado correctamente.
18. Resultado final

Con este proyecto tienes una aplicación MVC funcional:

                         APLICACIÓN MVC
                              │
             ┌────────────────┼────────────────┐
             │                │                │
             ▼                ▼                ▼
          MODELO            VISTA        CONTROLADOR
        Producto       ProductoVista   ProductoControlador
             │                │                │
             │                └───────┬────────┘
             │                        │
             │                        ▼
             │                  ProductoDAO
             │                        │
             └────────────────────────┤
                                      ▼
                                   MySQL 8
                                      │
                                      ▼
                              productos_mvc
                                      │
                                      ▼
                                  productos
:. . / .                                  
