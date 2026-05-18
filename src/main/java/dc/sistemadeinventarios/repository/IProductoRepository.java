package dc.sistemadeinventarios.repository;

import dc.sistemadeinventarios.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
}
