package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warehouse.model.ProductData;
import warehouse.repository.WarehouseRepository;

import java.util.List;

@RestController
@RequestMapping("/api") // Alle Endpunkte starten mit /api
public class WarehouseController {

    @Autowired
    private WarehouseRepository repository;

    // POST /product: Fügt ein neues Produkt hinzu
    @PostMapping("/product")
    public ProductData addProduct(@RequestBody ProductData product) {
        return repository.save(product);
    }

    // GET /product: Abrufen aller Produkte (Lagerbestand)
    @GetMapping("/product")
    public List<ProductData> getAllProducts() {
        return repository.findAll();
    }

    // GET /warehouse: Abrufen der Daten für ein bestimmtes Lager
    // (In den Grundlagen reicht es, nach einer ID zu filtern)
    @GetMapping("/warehouse/{id}")
    public List<ProductData> getWarehouse(@PathVariable String id) {
        return repository.findByWarehouseID(id);
    }

    // Einfache Variante für GET /warehouse (alle Daten)
    @GetMapping("/warehouse")
    public List<ProductData> getAllWarehouses() {
        return repository.findAll();
    }
}