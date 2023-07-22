import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        String recordFile = "C:\\Users\\varun\\OneDrive\\Desktop\\java\\stock_management\\stock_management\\src\\record.csv";
        String warehouseFile = "C:\\Users\\varun\\OneDrive\\Desktop\\java\\stock_management\\stock_management\\src\\warehouse.csv";
        String productFile = "C:\\Users\\varun\\OneDrive\\Desktop\\java\\stock_management\\stock_management\\src\\product.csv";
        String supplierfile = "C:\\Users\\varun\\OneDrive\\Desktop\\java\\stock_management\\stock_management\\src\\suppliers.csv";

        Seller varun = new Seller(warehouseFile, recordFile, productFile, supplierfile);

        while (true) {
            System.out.println("1. Buy   2. Sell   3. Monitor   4. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    buyProducts(varun, sc);
                    break;
                case 2:
                    sellProducts(varun, sc);
                    break;
                case 3:
                    monitorOptions(varun, sc);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void buyProducts(Seller seller, Scanner sc) {
        System.out.println("Your suppliers are:");
        seller.print_suppliers();;
        System.out.println("Enter supplier name:");
        String name = sc.next();
        if (seller.supplier_is_exists(name)) {
            seller.buy(name);
        } else {
            System.out.println("Supplier doesn't exist! Create new? [y / n]");
            String c = sc.next();
            if (c.equals("y")) {
                System.out.println("Enter supplier name:");
                String newname = sc.next();
                seller.new_supplier(newname);
            } else {
                System.out.println("Buy operation canceled.");
            }
        }
    }

    private static void sellProducts(Seller seller, Scanner sc) {
        System.out.println("Your products are:");
        seller.printwareHouseDetails();
        System.out.println("Enter Id of the product:");
        String prodName = sc.next();
        System.out.println("Enter quantity to sell:");
        int qty = sc.nextInt();
        System.out.println("Enter Sell Price:");
        int sell_price = sc.nextInt();
        seller.sell_product(prodName, qty, sell_price);
        System.out.println("Current stock is:");
        seller.printwareHouseDetails();
    }

    private static void monitorOptions(Seller seller, Scanner sc) {
        System.out.println("Enter the option:");
        System.out.println("1. View transaction records");
        System.out.println("2. View warehouse details");
        System.out.println("3. View supplier details");

        int option = sc.nextInt();

        switch (option) {
            case 1:
                System.out.println("You selected: View transaction records");
                seller.printRecords();
                break;
            case 2:
                System.out.println("You selected: View warehouse details");
                seller.printwareHouseDetails();
                break;
            case 3:
                System.out.println("You selected: View supplier details");
                seller.print_suppliers();
                break;
            default:
                System.out.println("Invalid option");
        }
    }
}
