import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Read_supplier_details {
    private String filePath;

    Read_supplier_details(String filePath) {
        this.filePath = filePath;
    }

    public void printSupplierNames() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 1;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 1) { // Ensure the line contains at least the supplier name
                    System.out.print(index + "." + values[1] + "   ");
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
