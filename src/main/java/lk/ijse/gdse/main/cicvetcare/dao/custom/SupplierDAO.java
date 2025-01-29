package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.SupplierDto;


import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO {
     boolean saveSupplier(SupplierDto supplierDto);
     boolean updateSupplier(SupplierDto supplierDto);
     boolean deleteSupplier(String supplierId);
     ArrayList<SupplierDto> getAllSuppliers();
     String getNextSupplierId();
}
