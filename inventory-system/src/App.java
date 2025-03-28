import java.util.*;

public class App {

    static class Product {
        String id;
        String name;
        int quantity;
        int price;

        public Product(String id, String name, int quantity, int price) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
    }

    private static final Map<String, Product> inventory = new LinkedHashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            switch (command) {
                case "ADD_PRODUCT":
                    handleAddProduct(parts.length > 1 ? parts[1] : "");
                    break;
                case "UPDATE_QUANTITY":
                    handleUpdateQuantity(parts.length > 1 ? parts[1] : "");
                    break;
                case "UPDATE_PRICE":
                    handleUpdatePrice(parts.length > 1 ? parts[1] : "");
                    break;
                case "VIEW_PRODUCT":
                    handleViewProduct(parts.length > 1 ? parts[1] : "");
                    break;
                case "REMOVE_PRODUCT":
                    handleRemoveProduct(parts.length > 1 ? parts[1] : "");
                    break;
                case "LIST_PRODUCTS":
                    handleListProducts();
                    break;
                case "SORT_PRODUCTS":
                    handleSortProducts(parts.length > 1 ? parts[1] : "");
                    break;
                case "EXIT":
                    handleExit();
                    return;
                default:
                    System.out.println("REQUEST_PATTERN_INVALID");
                    break;
            }
        }
    }

    private static void handleAddProduct(String params) {
        String[] args = params.split(" ");
        if (args.length != 4) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        String id = args[0];
        String name = args[1];
        int quantity;
        int price;

        try {
            quantity = Integer.parseInt(args[2]);
            price = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        if (inventory.containsKey(id)) {
            System.out.println("PRODUCT_ALREADY_EXISTS");
        } else {
            inventory.put(id, new Product(id, name, quantity, price));
            System.out.println("SUCCESS");
        }
    }

    private static void handleUpdateQuantity(String params) {
        String[] args = params.split(" ");
        if (args.length != 2) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        String id = args[0];
        int quantity;

        try {
            quantity = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        Product product = inventory.get(id);
        if (product == null) {
            System.out.println("PRODUCT_NOT_FOUND");
        } else {
            product.quantity = quantity;
            System.out.println("SUCCESS");
        }
    }

    private static void handleUpdatePrice(String params) {
        String[] args = params.split(" ");
        if (args.length != 2) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        String id = args[0];
        int price;

        try {
            price = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        Product product = inventory.get(id);
        if (product == null) {
            System.out.println("PRODUCT_NOT_FOUND");
        } else {
            product.price = price;
            System.out.println("SUCCESS");
        }
    }

    private static void handleViewProduct(String id) {
        Product product = inventory.get(id);
        if (product == null) {
            System.out.println("PRODUCT_NOT_FOUND");
        } else {
            System.out.println("Product ID: " + product.id);
            System.out.println("Name: " + product.name);
            System.out.println("Quantity: " + product.quantity);
            System.out.println("Price: " + product.price);
        }
    }

    private static void handleRemoveProduct(String id) {
        if (inventory.remove(id) == null) {
            System.out.println("PRODUCT_NOT_FOUND");
        } else {
            System.out.println("SUCCESS");
        }
    }

    private static void handleListProducts() {
        if (inventory.isEmpty()) {
            System.out.println("NO_PRODUCTS_AVAILABLE");
        } else {
            for (Product product : inventory.values()) {
                System.out.println(product.id + ":" + product.name + ":" + product.quantity + ":" + product.price);
            }
        }
    }

    private static void handleSortProducts(String byField) {
        List<Product> productList = new ArrayList<>(inventory.values());

        switch (byField) {
            case "id":
                productList.sort(Comparator.comparing(p -> p.id));
                break;
            case "name":
                productList.sort(Comparator.comparing(p -> p.name));
                break;
            case "quantity":
                productList.sort(Comparator.comparingInt(p -> p.quantity));
                break;
            case "price":
                productList.sort(Comparator.comparingInt(p -> p.price));
                break;
            default:
                System.out.println("INVALID_SORT_FIELD");
                return;
        }

        for (Product product : productList) {
            System.out.println(product.id + ":" + product.name + ":" + product.quantity + ":" + product.price);
        }
    }

    private static void handleExit() {
        int productCount = inventory.size();
        int totalValue = inventory.values().stream()
                .mapToInt(p -> p.quantity * p.price)
                .sum();

        System.out.println("Product Count: " + productCount);
        System.out.println("Total Inventory Value: " + totalValue);
        System.out.println("Goodbye!");
    }
}


