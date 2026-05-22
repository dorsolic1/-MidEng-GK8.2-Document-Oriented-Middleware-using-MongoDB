package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warehouse.model.WarehouseData;
import warehouse.model.ProductData;
import warehouse.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WarehouseController {

    @Autowired
    private WarehouseRepository repository;

    // POST /product: Fügt ein neues Produkt zu Lager "1" hinzu
    @PostMapping("/product")
    public WarehouseData addProduct(@RequestBody ProductData product) {
        WarehouseData wh = repository.findById("1").orElse(
                new WarehouseData("1", "Standard Lager", 4020, "Linz", "Austria")
        );
        wh.getProductData().add(product);
        return repository.save(wh);
    }

    // GET /product: Abrufen aller Produkte aus allen Lagern (flache Liste)
    @GetMapping("/product")
    public List<ProductData> getAllProducts() {
        List<ProductData> allProducts = new ArrayList<>();
        for (WarehouseData wh : repository.findAll()) {
            allProducts.addAll(wh.getProductData());
        }
        return allProducts;
    }

    // GET /warehouse/{id}: Daten für ein bestimmtes Lager abrufen
    @GetMapping("/warehouse/{id}")
    public WarehouseData getWarehouse(@PathVariable String id) {
        return repository.findById(id).orElse(null);
    }

    // GET /warehouse: Alle Lager abrufen
    @GetMapping("/warehouse")
    public List<WarehouseData> getAllWarehouses() {
        return repository.findAll();
    }
}