import java.util.ArrayList;
import java.util.List;

public class Main {

    public interface Product {
        String getName();
        Double getPrice();
    }

    public static class Electronic implements Product {
        private String name;
        private Double price;

        public Electronic(String name, Double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Double getPrice() {
            return price;
        }
    }

    public static class Accessory implements Product {
        private String name;
        private Double price;

        public Accessory(String name, Double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Double getPrice() {
            return price;
        }
    }

    @FunctionalInterface
    public interface Discountable {
        Double applyDiscount(Product product);
    }

    public static class Store {
        private List<Product> products;

        public Store() {
            this.products = new ArrayList<>();
        }

        public void addProduct(Product product) {
            products.add(product);
        }

        public void applyDiscount(Discountable discountable) {
            for (Product product : products) {
                Double newPrice = discountable.applyDiscount(product);
                System.out.println("Product: " + product.getName() +
                                   ", Original Price: " + product.getPrice() +
                                   ", Discounted Price: " + newPrice);
            }
        }

        public List<Product> getProducts() {
            return products;
        }
    }

    public static void main(String[] args) {
        Store store = new Store();

        store.addProduct(new Electronic("Laptop", 1000.0));
        store.addProduct(new Electronic("Smartphone", 500.0));

        store.addProduct(new Accessory("Mouse", 20.0));
        store.addProduct(new Accessory("Keyboard", 50.0));

        Discountable tenPercentDiscount = product -> product.getPrice() * 0.9;
        System.out.println("Aplicando 10% de descuento:");
        store.applyDiscount(tenPercentDiscount);

        Discountable fiftyDollarsDiscount = product -> product.getPrice() - 50.0;
        System.out.println("\nAplicando descuento fijo de $50:");
        store.applyDiscount(fiftyDollarsDiscount);

        Discountable combinedDiscount = product -> {
            Double priceAfterTenPercent = product.getPrice() * 0.9;
            return priceAfterTenPercent - 20.0;
        };
        System.out.println("\nAplicando descuento combinado (10% + $20):");
        store.applyDiscount(combinedDiscount);

        Discountable variableDiscount = product -> {
            if (product.getPrice() > 500.0) {
                return product.getPrice() * 0.8; 
            } else {
                return product.getPrice() * 0.95; 
            }
        };
        System.out.println("\nAplicando descuento variable basado en el precio:");
        store.applyDiscount(variableDiscount);

        Discountable accessoryOnlyDiscount = product -> {
            if (product instanceof Accessory) {
                return product.getPrice() * 0.85; 
            }
            return product.getPrice(); 
        };
        System.out.println("\nAplicando descuento del 15% solo a los accesorios:");
        store.applyDiscount(accessoryOnlyDiscount);
    }
}
