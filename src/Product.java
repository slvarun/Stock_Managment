import java.util.Random;
import java.time.LocalDate;

class Product {
    String pname;
    int id;
    int price;
    int quantity;
    String tran_id;
    LocalDate in_date;
    Random random = new Random();

    Product() {}

    Product(String pname, int id, int price, int quantity, LocalDate in) {
        this.pname = pname;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.tran_id = Integer.toString(random.nextInt(9000) + 1000);
        this.in_date = in;
    }
}
