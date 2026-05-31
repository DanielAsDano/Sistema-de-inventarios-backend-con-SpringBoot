package dc.sistemadeinventarios.controller;

import dc.sistemadeinventarios.exception.RecursoNoEncontrado;
import dc.sistemadeinventarios.model.Producto;
import dc.sistemadeinventarios.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto){
        logger.info("Producto agregado: " + producto);
        productoService.guardarProducto(producto);
        return producto;
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id){
        Producto producto = productoService.buscarProductoPorId(id);
        logger.info("Producto encontrado: " + producto);
        if (producto != null){
            return ResponseEntity.ok(producto);
        }else {
            throw new RecursoNoEncontrado("no se encontro el producto con el id " + id);
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto productoRecibido){
        Producto producto = productoService.buscarProductoPorId(id);
        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setExistencia(productoRecibido.getExistencia());
        producto.setPrecio(productoRecibido.getPrecio());

        productoService.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable Integer id){
        Producto producto = productoService.buscarProductoPorId(id);
        if (producto == null){
            throw new RecursoNoEncontrado("No se encontro el producto con el id: " + id);
        }
        productoService.eliminarProducto(producto);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}