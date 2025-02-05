package lk.ijse.gdse.main.cicvetcare.dao;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.*;



public class DAOFactory {
    private  static DAOFactory daoFactory;

    public DAOFactory() {}

    public static DAOFactory getInstance(){
        return daoFactory==null ? daoFactory = new DAOFactory(): daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,CATEGORY,DELIVERY,DRIVER,EMPLOYEE,INVENTORY,ORDERITEM,ORDERS,PAYMENT,PRODUCT,SHOP,SUPPLIER
        ,USER,VEHICLE

    }
    public  CrudDAO getDAO(DAOTypes daoType){

        switch (daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case  CATEGORY:
                return new CategoryDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case DRIVER:
                return new DriverDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case ORDERITEM:
                return new OrderItemDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case SHOP:
                return new ShopDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            default:
                return null;

        }
    }

}
