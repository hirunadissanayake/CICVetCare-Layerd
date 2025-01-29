package lk.ijse.gdse.main.cicvetcare.model;


import lk.ijse.gdse.main.cicvetcare.dto.SupplierDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Supplier VALUES(?,?,?,?)",
            supplierDto.getSupplierId(),
            supplierDto.getSupplierName(),
            supplierDto.getSupplierContact(),
            supplierDto.getSupplierAddress()
        );


    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("UPDATE Supplier SET name=?, contact_info = ?, address = ? WHERE supplier_id = ?",
                supplierDto.getSupplierName(),
                supplierDto.getSupplierContact(),
                supplierDto.getSupplierAddress(),
                supplierDto.getSupplierId()

        );
    }

    public boolean deleteSupplier(String supplierId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Supplier WHERE supplier_id = ?",supplierId);
    }

    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();

        while (rst.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);

            // Ensure lastId is not null and starts with "C" to match the expected format
            if (lastId != null && lastId.startsWith("S")) {
                String subString = lastId.substring(1);  // Remove the "C" prefix
                try {
                    int i = Integer.parseInt(subString);  // Parse remaining part as integer
                    int newIndex = i + 1;
                    return String.format("S%04d", newIndex);  // Return formatted ID
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing last ID: " + lastId);
                }
            }
        }
        // Fallback to "S0001" if no IDs exist or lastId is not in expected format
        return "S0001";
    }
}

