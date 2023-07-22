import java.util.Scanner;

class Supplier {
    long phone;
    String name;
    String email;

    Supplier() {
    }

    Supplier(long sphone, String name, String email) {
        this.name = name;
        this.phone = sphone;
        this.email = email;
    }

    public void updateDetails(Supplier s) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the field you want to update: (phone, name, email)");
        String option = sc.nextLine();

        if (option.equals("name")) {
            s.name = sc.nextLine();
        } else if (option.equals("phone")) {
            s.phone = sc.nextLong();
        } else if (option.equals("email")) {
            sc.nextLine();
            s.email = sc.nextLine();
        }
    }
}
