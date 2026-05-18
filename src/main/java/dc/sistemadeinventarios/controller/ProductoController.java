package dc.sistemadeinventarios.controller;

import dc.sistemadeinventarios.model.Producto;
import dc.sistemadeinventarios.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        List<Producto> productos = productoService.listarProductos();
        logger.info("Productos obtenidos: ");
        productos.forEach(producto -> logger.info(producto.toString()));
        return productos;
    }
}
