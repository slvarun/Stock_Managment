import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Read_products_file {
    private String productFile;

    public Read_products_file(String productFile) {
        this.productFile = productFile;
    }

    public void printDetails() {
        try (BufferedReader br = new BufferedReader(new FileReader(productFile))) {
            String line;

            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Transaction ID | Product     | Product ID | Price   | Quantity | Date Purchased            | Total Product Price |");
            System.out.println("------------------------------------------------------------------------------------------------------------------------");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                printFormattedDetails(values);
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printDetails(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(productFile))) {
            String line;

            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Transaction ID | Product     | Product ID | Price   | Quantity | Date Purchased            | Total Product Price |");
            System.out.println("------------------------------------------------------------------------------------------------------------------------");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(Integer.toString(id))) {
                    printFormattedDetails(values);
                    System.out.println("------------------------------------------------------------------------------------------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printDetails(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(productFile))) {
            String line;

            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Transaction ID | Product     | Product ID | Price   | Quantity | Date Purchased            | Total Product Price |");
            System.out.println("------------------------------------------------------------------------------------------------------------------------");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (name.equals(values[1])) {
                    printFormattedDetails(values);
                    System.out.println("------------------------------------------------------------------------------------------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(productFile))) {
            List<String> lines = new ArrayList<>();
            String line;

            // Read all lines from the file and exclude the line with the specified ID
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!values[0].equals(Integer.toString(id))) {
                    lines.add(line);
                }
            }

            // Write the modified lines back to the file
            try (FileWriter writer = new FileWriter(productFile, false)) {
                for (String modifiedLine : lines) {
                    writer.write(modifiedLine + "\n");
                }
                System.out.println("Product with ID " + id + " deleted successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFormattedDetails(String[] values) {
        System.out.printf("| %-14s | %-11s | %-10s | %-7s | %-8s | %-25s | %-19s |\n",
                values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }
}
