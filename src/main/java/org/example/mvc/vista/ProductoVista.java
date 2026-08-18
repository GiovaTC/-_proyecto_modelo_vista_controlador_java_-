package org.example.mvc.vista;

import org.example.mvc.modelo.Producto;

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
        System.out.print("ingrese el ID del PRODUCTO: ");

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
            System.out.println("Producto no encontrado. ");

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
            System.out.println("No existen productos!");

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

