package org.example.mvc;

import org.example.mvc.controlador.ProductoControlador;
import org.example.mvc.dao.ProductoDAO;
import org.example.mvc.vista.ProductoVista;

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