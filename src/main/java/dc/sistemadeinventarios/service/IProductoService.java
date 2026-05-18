package dc.sistemadeinventarios.service;

import dc.sistemadeinventarios.model.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> listarProductos();
    public Producto buscarProductoPorId(Integer id);
    public void guardarProducto(Producto producto);
    public void eliminarProducto(Producto producto);
}
