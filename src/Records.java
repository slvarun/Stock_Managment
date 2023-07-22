import java.io.*;
import java.util.*;

class Records {
    private String recordFile;
    private String productFile;
    private String warehouseFile;

    Records(String recordFile, String productFile, String warehouseFile) {
        this.recordFile = recordFile;
        this.productFile = productFile;
        this.warehouseFile = warehouseFile;
    }

    public void add(Supplier supplier, Product product) {
        addRecordEntry(supplier, product);
        updateWarehouse(product);
    }

    private void addRecordEntry(Supplier supplier, Product product) {
        try (FileWriter writer = new FileWriter(recordFile, true)) {
            int negative_price = product.quantity * product.price * -1;

            StringBuilder sb = new StringBuilder();
            sb.append(supplier.name).append(",");
            sb.append(product.tran_id).append(",");
            sb.append(product.pname).append(",");
            sb.append(product.id).append(",");
            sb.append(product.price).append(",");
            sb.append(product.quantity).append(",");
            sb.append(product.in_date).append(",");
            sb.append("PURCHASED").append(",");
            sb.append(negative_price).append("\n");

            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateWarehouse(Product product) {
        List<String> lines = new ArrayList<>();
        int newQuantity = product.quantity;
        int totalPrice = product.quantity * product.price;

        try (BufferedReader br = new BufferedReader(new FileReader(warehouseFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values[1].equals(String.valueOf(product.id)) && values[0].equals(product.pname)) {
                    int existingQuantity = Integer.parseInt(values[3]);
                    int existingPrice = Integer.parseInt(values[2]);
                    totalPrice += existingPrice * existingQuantity;
                    newQuantity += existingQuantity;
                } else {
                    lines.add(line);
                }
            }

            String newValues = product.pname + "," + product.id + "," + product.price + ","
                    + newQuantity + "," + totalPrice;

            lines.add(newValues);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(warehouseFile, false)) {
            for (String modifiedLine : lines) {
                writer.write(modifiedLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
