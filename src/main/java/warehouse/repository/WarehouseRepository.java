package warehouse.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import warehouse.model.WarehouseData;

public interface WarehouseRepository extends MongoRepository<WarehouseData, String> {
    // Standard-CRUD-Methoden werden von MongoRepository automatisch bereitgestellt
}