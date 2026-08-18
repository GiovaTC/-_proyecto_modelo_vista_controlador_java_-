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
}

