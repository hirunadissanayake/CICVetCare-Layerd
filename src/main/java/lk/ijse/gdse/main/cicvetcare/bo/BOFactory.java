package lk.ijse.gdse.main.cicvetcare.bo;

import lk.ijse.gdse.main.cicvetcare.bo.custom.impl.*;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.OrderItemDAOImpl;

public class BOFactory {
    private  static BOFactory boFactory;

    public BOFactory() {}

    public static BOFactory getInstance(){
        return boFactory==null ? boFactory = new BOFactory(): boFactory;
    }

    public enum BoType {
        CUSTOMER,CATEGORY,DELIVERY,DRIVER,EMPLOYEE,INVENTORY,ORDERS,PAYMENT,PRODUCT,SHOP,SUPPLIER
        ,USER,VEHICLE

    }
    public SuperBO getBO(BOFactory.BoType boType){

        switch (boType){
            case CUSTOMER:
                return new CustomerBOImpl();
            case  CATEGORY:
                return new CategoryBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case DRIVER:
                return new DriverBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case ORDERS:
                return new OrdersBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case SHOP:
                return new ShopBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case USER:
                return new UserBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            default:
                return null;

        }
    }

}
