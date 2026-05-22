package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import warehouse.model.WarehouseData;
import warehouse.model.ProductData;
import warehouse.repository.WarehouseRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private WarehouseRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        // Lager 1 erstellen und befüllen
        WarehouseData wh1 = new WarehouseData("1", "Hauptlager Linz", 4020, "Linz", "Austria");
        wh1.getProductData().add(new ProductData("00-443175","Bio Orangensaft Sonne","Getraenk", 2500));
        wh1.getProductData().add(new ProductData("00-871895","Bio Apfelsaft Gold","Getraenk", 3420));
        wh1.getProductData().add(new ProductData("01-926885","Ariel Waschmittel Color","Waschmittel", 478));
        wh1.getProductData().add(new ProductData("02-234811","Mampfi Katzenfutter Rind","Tierfutter", 1324));
        repository.save(wh1);

        // Lager 2 erstellen und befüllen
        WarehouseData wh2 = new WarehouseData("2", "Filiale Wien", 1010, "Wien", "Austria");
        wh2.getProductData().add(new ProductData("03-893173","Saugstauberbeutel Ingres","Reinigung", 7390));
        repository.save(wh2);

        System.out.println();
        System.out.println("WarehouseData found with findAll():");
        System.out.println("-------------------------------");
        for (WarehouseData wh : repository.findAll()) {
            System.out.println("Lager: " + wh.getWarehouseName() + " (" + wh.getWarehouseCity() + ")");
            for (ProductData p : wh.getProductData()) {
                System.out.println("  -> " + p);
            }
        }
        System.out.println();
    }
}