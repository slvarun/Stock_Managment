import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

class StockTran extends Supplier {
    Scanner sc = new Scanner(System.in);

    String recordfile;
    String productfile;
    String wareHousefile;

    StockTran(String recordfile, String productfile, String wareHousefile) {
        this.recordfile = recordfile;
        this.productfile = productfile;
        this.wareHousefile = wareHousefile;
    }

    public void buy(Supplier s) {
        String name = getProductName();
        int id = getProductID();
        int price = getProductPrice();
        int quantity = getProductQuantity();
        LocalDate date = getProductPurchaseDate();

        Product p = new Product(name, id, price, quantity, date);

        Records records = new Records(recordfile, productfile, wareHousefile);
        records.add(s, p);

        System.out.println("Record updated successfully!");
    }

    private String getProductName() {
        System.out.print("Enter name of the product: ");
        String name = sc.nextLine();
        while (name.length() <= 2) {
            System.err.print("Please enter a valid name (at least 3 characters): ");
            name = sc.nextLine();
        }
        return name;
    }

    private int getProductID() {
        System.out.print("Enter product id: ");
        int id = sc.nextInt();
        while (id < 1) {
            System.err.print("Please enter a valid ID (greater than 0): ");
            id = sc.nextInt();
        }
        sc.nextLine(); // Consume the newline character
        return id;
    }

    private int getProductPrice() {
        System.out.print("Enter the price of the product: ");
        int price = sc.nextInt();
        while (price < 0) {
            System.err.print("Please provide a correct Price for your purchase (non-negative value): ");
            price = sc.nextInt();
        }
        return price;
    }

    private int getProductQuantity() {
        System.out.print("Enter the quantity of the product: ");
        int quantity = sc.nextInt();
        while (quantity < 0) {
            System.err.print("Quantity should be greater than or equal to zero: ");
            quantity = sc.nextInt();
        }
        return quantity;
    }

    private LocalDate getProductPurchaseDate() {
        System.out.print("Enter the date the product is purchased (dd-MM-yyyy): ");
        String userInput = sc.next();
        LocalDate date;
        try {
            date = LocalDate.parse(userInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            System.out.println("You entered: " + date);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please provide a valid date (dd-MM-yyyy)!");
            return getProductPurchaseDate();
        }
        return date;
    }
}
