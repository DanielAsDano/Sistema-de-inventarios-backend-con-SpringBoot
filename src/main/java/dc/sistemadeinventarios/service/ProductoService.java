package dc.sistemadeinventarios.service;

import dc.sistemadeinventarios.model.Producto;
import dc.sistemadeinventarios.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    IProductoRepository productoRepository;

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos;
    }

    @Override
    public Producto buscarProductoPorId(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Producto producto) {
        productoRepository.delete(producto);
    }
}
