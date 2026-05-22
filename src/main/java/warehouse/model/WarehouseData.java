package warehouse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "warehouseData")
public class WarehouseData {

    @Id
    private String warehouseID; // Die ID des Lagers (z.B. "1")
    private String warehouseName;
    private int warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;

    // Hier werden die Produkte direkt im JSON-Dokument eingebettet
    private List<ProductData> productData = new ArrayList<>();

    public WarehouseData() {
    }

    public WarehouseData(String warehouseID, String warehouseName, int warehousePostalCode, String warehouseCity, String warehouseCountry) {
        this.warehouseID = warehouseID;
        this.warehouseName = warehouseName;
        this.warehousePostalCode = warehousePostalCode;
        this.warehouseCity = warehouseCity;
        this.warehouseCountry = warehouseCountry;
    }

    // Getter und Setter
    public String getWarehouseID() { return warehouseID; }
    public void setWarehouseID(String warehouseID) { this.warehouseID = warehouseID; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public int getWarehousePostalCode() { return warehousePostalCode; }
    public void setWarehousePostalCode(int warehousePostalCode) { this.warehousePostalCode = warehousePostalCode; }
    public String getWarehouseCity() { return warehouseCity; }
    public void setWarehouseCity(String warehouseCity) { this.warehouseCity = warehouseCity; }
    public String getWarehouseCountry() { return warehouseCountry; }
    public void setWarehouseCountry(String warehouseCountry) { this.warehouseCountry = warehouseCountry; }
    public List<ProductData> getProductData() { return productData; }
    public void setProductData(List<ProductData> productData) { this.productData = productData; }
}