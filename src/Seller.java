import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

import java.util.*;

class Seller extends Supplier {
    String wareHousefile;
    String recordfile;
    String productfile;
    String supplierfile;

    String bold = "\u001B[1m";

    String reset = "\u001B[0m";

    Scanner sc = new Scanner(System.in);

    Seller(String wareHousefile, String recordfile, String productfile, String supplierfile) {
        this.wareHousefile = wareHousefile;
        this.recordfile = recordfile;
        this.productfile = productfile;
        this.supplierfile = supplierfile;
    }

    ArrayList<Supplier> suppliers = new ArrayList<>();

    public void printRecords() {
        try (BufferedReader br = new BufferedReader(new FileReader(recordfile))) {
            String line;
            int totalProfit = 0;

            System.out.println(bold +
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-15s | %-15s | %-15s | %-10s | %-10s | %-8s | %-15s | %-15s | %-25s |\n",
                    "Supplier Name", "Transaction ID", "Product Name", "Product ID", "Price", "Quantity",
                    "Date Purchased", "Action", "Transaction");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------"
                            + reset);
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.printf("| %-15s | %-15s | %-15s | %-10s | %-10s | %-8s | %-15s | %-15s | %-25s |\n",
                        values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7],
                        values[8]);
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        int transactionProfit = Integer.parseInt(values[8]);
                        totalProfit += transactionProfit;
            }

            System.out.println("Total Profit: " + totalProfit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printwareHouseDetails() {
        try (BufferedReader br = new BufferedReader(new FileReader(wareHousefile))) {
            String line;

            System.out.println(bold +
                    "-----------------------------------------------------------------------------------");
            System.out.printf("| %-20s | %-11s | %-8s | %-8s | %-20s |\n",
                    "Product", "Product ID", "Price", "Quantity", "Total Product Price");
            System.out.println(
                    "-----------------------------------------------------------------------------------" + reset);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.printf("| %-20s | %-11s | %-8s | %-8s | %-20s |\n",
                        values[0], values[1], values[2], values[3], values[4]);
                System.out.println(
                        "-----------------------------------------------------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print_suppliers() {

        try (BufferedReader br = new BufferedReader(new FileReader(supplierfile))) {
            String line;

            System.out.println(bold + "------------------------------------------------------------");
            System.out.printf("| %-12s | %-15s | %-25s |\n", "Phone Number", "Name", "Email");
            System.out.println("------------------------------------------------------------" + reset);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.printf("| %-12s | %-15s | %-25s |\n", values[0], values[1], values[2]);
                System.out.println("------------------------------------------------------------");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sell_product(String pid, int quantity, int sell_price) {

        String line1 = "";
        try (BufferedReader br = new BufferedReader(new FileReader(wareHousefile))) {
            List<String> lines = new ArrayList<>();
            String line;
            LocalDate currentDate = LocalDate.now();
            Random random = new Random();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!String.valueOf(values[1]).equals(pid)) {
                    lines.add(line);
                } else {
                    int existing_quantity = Integer.parseInt(values[3]);
                    if (quantity >= existing_quantity) {
                        System.out.println(
                                "Present stock of product ID " + values[1] + " is " + values[3]
                                        + ". Do you want to sell all?");
                        System.out.println("Do you want to sell the whole stock? [y/n]");
                        char option = sc.next().charAt(0);
                        switch (option) {
                            case 'y':
                                line1 = "Seller," + (random.nextInt(9000) + 1000) + "," + values[0] + ","
                                        + values[1] + "," + quantity + "," + sell_price
                                        + "," + currentDate + "," + "SOLD\t" + "," + quantity * sell_price;
                                break;
                            case 'n':
                                lines.add(line);
                                break;
                        }
                    } else {
                        String edited_line = values[0] + "," + values[1] + "," + values[2] + ","
                                + (existing_quantity - quantity) + ","
                                + (existing_quantity - quantity) * Integer.parseInt(values[2]);
                        lines.add(line);
                        lines.set(lines.size() - 1, edited_line);
                        line1 = "Seller," + (random.nextInt(9000) + 1000) + "," + values[0] + ","
                                + values[1] + "," + quantity + "," + sell_price
                                + "," + currentDate + "," + "SOLD\t" + "," + quantity * sell_price;
                    }
                }
            }

            FileWriter w = new FileWriter(recordfile, true);
            w.write(line1 + "\n");
            w.close();

            try (FileWriter writer = new FileWriter(wareHousefile, false)) {
                for (String modifiedLine : lines) {
                    writer.write(modifiedLine + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void new_supplier(String supplier_name) {

        for (Supplier supplier : suppliers) {
            if (supplier.name.equals(supplier_name)) {
                System.out.println("supplier already exists");
                return;
            }
        }
        System.out.println("Enter supplier phone no. : ");
        long sphone = sc.nextLong();
        System.out.println("Enter Supplier Email : ");
        String semail = sc.next();
        String sname = supplier_name;
        Supplier s = new Supplier(sphone, sname, semail);
        suppliers.add(s);
        try (FileWriter writer = new FileWriter(supplierfile, true)) {
            StringBuilder sb = new StringBuilder();
            sb.append(sphone).append(",");
            sb.append(sname).append(",");
            sb.append(semail).append(",");
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean supplier_is_exists(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(supplierfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equals(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void buy(String name) {

        String supname = null;
        long supphone = 0;
        String supemail = null;

        StockTran tran = new StockTran(recordfile, productfile, wareHousefile);
        try (BufferedReader br = new BufferedReader(new FileReader(supplierfile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String values[] = line.split(",");
                if (values[1].equals(name)) {
                    supname = values[1];
                    supphone = (long) (Double.parseDouble(values[0]));
                    supemail = values[2];
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (supname != null && supemail != null) {
            Supplier s = new Supplier(supphone, supname, supemail);
            tran.buy(s);
        } else {
            System.out.println("Supplier not found");
        }
    }

}
