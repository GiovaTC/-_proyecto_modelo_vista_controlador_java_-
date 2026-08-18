package org.example.mvc.controlador;

// el controlador conecta la vista con el modelo/DAO.

import org.example.mvc.dao.ProductoDAO;
import org.example.mvc.modelo.Producto;
import org.example.mvc.vista.ProductoVista;

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
