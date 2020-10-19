package Utility;

import controller.Home;
import javafx.scene.Node;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

public class DbConnection {


    public Connection con       = null;
    public Statement  st        = null;
    public ResultSet  resultSet = null;
    public String     errorText = "";
    String DB_CONNECTION_IP = new HardCodedValue().DB_CONNECTION_IP;

    FieldValues values = new FieldValues();


    public DbConnection(Node node) {

        try {
            //     new HomeController().setErrorNotification(node, "");
            if (new File(DB_CONNECTION_IP).exists()) {
                String ipAddress = new FileAccessControl().readfromFileStringBuffer(DB_CONNECTION_IP).toString();
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + ipAddress + ":3306/pms", "root", "root");
                st = con.createStatement();
            }

        } catch (Exception e) {

            values.LogError(" Cause: " + e.getCause() +
                    "\nMassage: " + e.getMessage() +
                    "\nErrorText: " + e.toString() +
                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
            );
            new Home().setErrorNotification(node, "error in Database connection building " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String testConnection(String ipAddress) {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + ipAddress + ":3306/pms", "root", "root");
            st = con.createStatement();

            con.close();

            return "connected";

        } catch (Exception e) {
            values.LogError(" Cause: " + e.getCause() +
                    "\nMassage: " + e.getMessage() +
                    "\nErrorText: " + e.toString() +
                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
            );
            e.printStackTrace();
            return e.getMessage();
        }

    }

//    public User login(String userName, String passWord) {
//        User user = null;
//        String query = "SELECT * FROM USER WHERE username = '" + userName + "'and password = '" + passWord + "'";
//        try {
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//                System.out.println("db active===" + resultSet.getString("active"));
//                user = new User();
//                user.setName(resultSet.getString("name"));
//                user.setId(resultSet.getInt("id"));
//                user.setUserName(resultSet.getString("username"));
//                user.setActive(resultSet.getBoolean("active"));
//                user.setType(resultSet.getString("type"));
//                user.setPhone(resultSet.getString("phone"));
//                user.setPassWord(resultSet.getString("password"));
//                System.out.println("username" + user.getName());
//            }
//            con.close();
//
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//        return user;
//    }
//
//    public boolean updateLoginedUser(User user) {
//
//
//        String query = "UPDATE `pms`.`user` SET `name` = '" + user.getName() + "', `username` = '" + user.getUserName() + "'," +
//                " `password` = '" + user.getPassWord() + "', `phone` = '" + user.getPhone() + "' WHERE (`id` = '" + user.getId() + "');";
//
//        try {
//
//            System.out.println("Updated query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//    }
//
//    public int tableRowCount(String tableName) {
//        String query = "SELECT count(*) FROM pms." + tableName + ";";
//        try {
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//
//                return resultSet.getInt("count(*)");
//            }
//            con.close();
//
//        } catch (Exception e) {
//            new FeildValues().LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace()));
//
//            e.printStackTrace();
//        }
//
//        return 0;
//    }
//
//    public boolean saveProductType(Product_type productType) {
//        String query = "INSERT INTO `pms`.`product_type` (`name`, `type`, `grade`, `model`, `size`, `brand`, `selling_price`," +
//                " `selling_measurment`, `selling_amount_ratio`, `has_broker`, `measure_price`) " +
//                "VALUES ('" + productType.getName() + "', '" + productType.getType() + "', '" + productType.getGrade() + "'," +
//                " '" + productType.getModel() + "', '" + productType.getSize() + "', '" + productType.getBrand() + "', '" +
//                productType.getSellingPrice() + "', '" + productType.getSelling_measurement() + "', '" +
//                productType.getSelling_amount_ratio() + "', " + productType.isHasBroker() + ", '" + productType.getMeaserePrice() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public ObservableList<Product_type> productTypeList(int position) {
//
//        ObservableList<Product_type> productTypes = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT * FROM pms.product_type LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Product_type productType = new Product_type(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getBoolean("has_broker"),
//                        resultSet.getString("measure_price")
//                );
//                productTypes.add(productType);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productTypes;
//
//    }
//
//    public ObservableList<Product_type> searchProductTypeList() {
//
//        ObservableList<Product_type> productTypes = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM pms.product_type";
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Product_type productType = new Product_type(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio")
//                );
//                productTypes.add(productType);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productTypes;
//
//    }
//
//    public boolean deleterecord(String tableName, int id) {
//
//        String query = "delete from pms." + tableName + " where id = " + id;
//        System.out.print(query);
//        try {
//            int excued = st.executeUpdate(query);
//            con.close();
//            if (excued == 1)
//                return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public boolean updateProductType(Product_type productType) {
//
//        String query = "UPDATE `pms`.`product_type` SET `name` = '" + productType.getName() + "', `type` = '" + productType.getType() + "'" +
//                ", `grade` = '" + productType.getGrade() + "', `model` = '" + productType.getModel() + "', `size` = '" + productType.getSize() +
//                "', `brand` = '" + productType.getBrand() + "', `selling_price` = '" + productType.getSellingPrice() +
//                "', `selling_measurment` = '" + productType.getSelling_measurement() + "', `selling_amount_ratio` = '" +
//                productType.getSelling_amount_ratio() + "', `has_broker` = " + productType.isHasBroker() +
//                ", `measure_price` = '" + productType.getMeaserePrice() + "' WHERE (`id` = '" + productType.getId() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public ObservableList<Customer> customerList(int position) {
//
//        ObservableList<Customer> customers = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT * FROM pms.customer LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Customer customer = new Customer(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                customers.add(customer);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return customers;
//
//    }
//
//    public ObservableList<Customer> searchCustomerList() {
//
//        ObservableList<Customer> customersList = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM pms.customer";
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Customer customer = new Customer(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                customersList.add(customer);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return customersList;
//
//    }
//
//    public boolean saveCustomer(Customer customer) {
//
//        String query = "INSERT INTO `pms`.`customer` (`name`, `address`, `phone`, `tin`) VALUES ('" +
//                customer.getName() + "', '" + customer.getAddress() + "', '" + customer.getPhone() + "', '" + customer.getTin() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public int saveCustomerName(Customer customer) {
//
//        String query = "INSERT INTO `pms`.`customer` (`name`) VALUES ('" + customer.getName() + "');";
//
//        int id = 0;
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//
//
//            query = "SELECT * FROM `pms`.`customer` WHERE `name` = '" + customer.getName() + "'";
//            resultSet = st.executeQuery(query);
//            if (resultSet.next())
//                id = resultSet.getInt("id");
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//        }
//
//
//        System.out.println("created id==" + id);
//        return id;
//
//
//    }
//
//    public boolean updateCustomer(Customer customer) {
//
//        String query = "UPDATE `pms`.`customer` SET `name` = '" + customer.getName() + "'," +
//                " `address` = '" + customer.getAddress() + "', `phone` = '" + customer.getPhone() +
//                "', `tin` = '" + customer.getTin() + "' WHERE (`id` = '" + customer.getId() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//        }
//
//
//        return false;
//
//
//    }
//
//    public ObservableList<Supplier> supplierList(int position) {
//
//        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT * FROM pms.supplier LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Supplier supplier = new Supplier(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                suppliers.add(supplier);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return suppliers;
//
//    }
//
//    public ObservableList<Supplier> searchSupplierList() {
//
//        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM pms.supplier";
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Supplier supplier = new Supplier(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                suppliers.add(supplier);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return suppliers;
//
//    }
//
//
//    public boolean saveSupplier(Supplier supplier) {
//
//        String query = "INSERT INTO `pms`.`supplier` (`name`, `address`, `phone`, `tin`) VALUES ('" +
//                supplier.getName() + "', '" + supplier.getAddress() + "', '" + supplier.getPhone() + "', '" + supplier.getTin() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public int saveSupplierName(Supplier supplier) {
//
//        String query = "INSERT INTO `pms`.`supplier` (`name`) VALUES ('" + supplier.getName() + "');";
//
//        int id = 0;
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//
//            query = "SELECT * FROM `pms`.`supplier` WHERE `name` = " + supplier.getName();
//            resultSet = st.executeQuery(query);
//            if (resultSet.next())
//                id = resultSet.getInt("id");
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return id;
//
//
//    }
//
//    public boolean updateSupplier(Supplier supplier) {
//
//        String query = "UPDATE `pms`.`supplier` SET `name` = '" + supplier.getName() + "'," +
//                " `address` = '" + supplier.getAddress() + "', `phone` = '" + supplier.getPhone() +
//                "', `tin` = '" + supplier.getTin() + "' WHERE (`id` = '" + supplier.getId() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public ObservableList<User> userList(int position) {
//
//        ObservableList<User> userList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT * FROM pms.user LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                User user = new User(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("username"),
//                        resultSet.getString("password"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("type"),
//                        resultSet.getBoolean("active"));
//                System.out.print("usernmae ===" + resultSet.getString("username"));
//
//                userList.add(user);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return userList;
//
//    }
//
//    public boolean saveUser(User user) {
//
//        String query = "INSERT INTO `pms`.`user` (`name`, `username`, `password`, `phone`, `type`, `active`) VALUES " +
//                "('" + user.getName() + "', '" + user.getUserName() + "', '" + user.getPassWord() + "', '" + user.getPhone() +
//                "', '" + user.getType() + "', " + user.isActive() + ");";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public boolean updateUser(User user) {
//
//        String query = "UPDATE `pms`.`user` SET `name` = '" + user.getName() + "', `username` = '" + user.getUserName() + "', " +
//                "`password` = '" + user.getPassWord() + "', `phone` = '" + user.getPhone() + "', `type` = '" + user.getType() +
//                "', `active`=" + user.isActive() + " WHERE (`id` = '" + user.getId() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public boolean saveStoreProduct(Product_in_store productInStore) {
//
//        String query = "SELECT * FROM pms.store_product where product_id = " + productInStore.getProductType();
//        System.out.println("qqqqqq===" + query);
//        int excuted = 0;
//        try {
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//                double oldAmount = resultSet.getDouble("amount");
//                System.out.print("old amount==" + oldAmount);
//                double newAmount = oldAmount + productInStore.getAmount();
//                query = "UPDATE `pms`.`store_product` SET `amount` = '" + newAmount + "' WHERE (`id` = '" + resultSet.getInt("id") + "');";
//                excuted = st.executeUpdate(query);
//            } else {
//
//                query = "INSERT INTO `pms`.`store_product` (`product_id`, `amount`) VALUES ('" +
//                        productInStore.getProductType() + "', '" + productInStore.getAmount() + "');\n";
//
//                System.out.println("Insert query==" + query);
//                excuted = st.executeUpdate(query);
//                System.out.println("is Excuted===" + excuted);
//                con.close();
//            }
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public boolean updateStoreProduct(Product_in_store productInStore) {
//
//        try {
//            String query = "UPDATE `pms`.`store_product` SET `amount` = '" + productInStore.getAmount() + "' WHERE (`product_id` = '" + productInStore.getProductType() + "');";
//            int excuted = st.executeUpdate(query);
//
//            con.close();
//
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public ObservableList<Product> storeProductList(int position) {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT s.* , p.*  FROM store_product s join product_type p WHERE s.product_id = p.id LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                double mp = 0;
//                if (resultSet.getString("measure_price") != null)
//                    mp = resultSet.getDouble("measure_price");
//                Product product = new Product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount"),
//                        mp
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public ObservableList<Product> storeProductToMove() {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        String query = "SELECT s.* , p.*  FROM store_product s join product_type p WHERE s.amount > 0 and s.product_id = p.id ";
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                double mp = 0;
//                if (resultSet.getString("measure_price") != null)
//                    mp = resultSet.getDouble("measure_price");
//                Product product = new Product(
//                        resultSet.getInt("id"),
//                        1,
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount"),
//                        mp
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public ObservableList<Product> totalProductList(int position) {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT p.*, name, d.amount, s.amount, (d.amount+s.amount)  FROM pms.product_type p " +
//                "left join pms.despence_product  d on p.id = d.product_id " +
//                "left join pms.store_product  s on p.id = s.product_id  having  (d.amount+s.amount) > 0  LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                double amount = new FeildValues().decimalFormat(resultSet.getDouble("(d.amount+s.amount)"));
//
//                Product product = new Product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        amount,
//                        resultSet.getDouble("measure_price")
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public boolean savePurchasedProduct(Purchased_product purchasedProduct) {
//
//
//        try {
//
//            String supplier = purchasedProduct.getSupplier() + "";
//            if (supplier.equals("0"))
//                supplier = null;
//
//            String query = "INSERT INTO `pms`.`purchase` (`product`, `amount`, `unit_cost_price`, `total_price`, " +
//                    "`date`, `supplier`, `invoice_number`, `purchesed_by`) VALUES " +
//                    "('" + purchasedProduct.getProductid() + "', '" + purchasedProduct.getAmount() + "', '" + purchasedProduct.getUnitPrice() +
//                    "', '" + purchasedProduct.getTotalPrice() + "', '" + purchasedProduct.getdate() + "', " + supplier +
//                    ", '" + purchasedProduct.getInvoiceNumber() + "', '" + purchasedProduct.getPurchasedBy() + "');\n";
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public boolean updatePurchasedProduct(Purchased_product purchasedProduct) {
//
//
//        try {
//
//            String supplier = purchasedProduct.getSupplier() + "";
//            if (supplier.equals("0"))
//                supplier = null;
//
//            String query = "UPDATE `pms`.`purchase` SET `product` = " + purchasedProduct.getProductid() + "," +
//                    " `amount` = '" + purchasedProduct.getAmount() + "', `unit_cost_price` = '" + purchasedProduct.getUnitPrice() + "'," +
//                    " `total_price` = '" + purchasedProduct.getTotalPrice() + "', `invoice_number` = '" + purchasedProduct.getInvoiceNumber() +
//                    "' WHERE (`id` = '" + purchasedProduct.getId() + "');";
//
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public ObservableList<Product> despenceProductList(int position) {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT s.* , p.*  FROM despence_product s join product_type p WHERE s.product_id = p.id LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Product product = new Product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount")
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public boolean updateDespenceProduct(Product_in_despence product_in_despence) {
//
//        try {
//            String query = "UPDATE `pms`.`despence_product` SET `amount` = '" + product_in_despence.getAmount() + "' WHERE (`product_id` = '" + product_in_despence.getProductType() + "');";
//            int excuted = st.executeUpdate(query);
//
//            con.close();
//
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public Product getDespenceProduct(int productId) {
//
//
//        String query = "SELECT s.* , p.*  FROM despence_product s join product_type p WHERE s.product_id = p.id AND s.product_id= " + productId;
//
//        Product product = null;
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//
//
//                product = new Product(
//                        resultSet.getInt("id"), 1,
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount")
//                );
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return product;
//
//    }
//
//    public ObservableList<Product> searchDespenceProductList() {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        String query = "SELECT s.* , p.*  FROM despence_product s join product_type p WHERE s.product_id = p.id and s.amount > 0";
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Product product = new Product(
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount"),
//                        resultSet.getDouble("measure_price"),
//                        resultSet.getBoolean("has_broker")
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public boolean saveDespenceProduct(Product_in_despence productInStore) {
//
//        String query = "SELECT * FROM pms.despence_product where product_id = " + productInStore.getProductType();
//        int excuted = 0;
//
//        System.out.println("q====" + query);
//        try {
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//                double oldAmount = resultSet.getDouble("amount");
//                System.out.print("old amount==" + oldAmount);
//                double newAmount = oldAmount - productInStore.getAmount();
//                double savedAmountdouble = new FeildValues().decimalFormat(newAmount);
//
//                System.out.println("new Amount==" + savedAmountdouble);
//
//                query = "UPDATE `pms`.`despence_product` SET `amount` = '" + newAmount + "' WHERE (`id` = '" + resultSet.getInt("id") + "');";
//                excuted = st.executeUpdate(query);
//                System.out.println("q---" + query);
//            } else {
//
//                query = "INSERT INTO `pms`.`despence_product` (`product_id`, `amount`) VALUES (" +
//                        productInStore.getProductType() + ", '" + productInStore.getAmount() + "');\n";
//
//                System.out.println("Insert query==" + query);
//                excuted = st.executeUpdate(query);
//                System.out.println("is Excuted===" + excuted);
//                con.close();
//            }
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public boolean moveProductToDespence(Product_in_despence productInStore) {
//
//
//        //updating despence table
//        String query = "SELECT * FROM pms.despence_product where product_id = " + productInStore.getProductType();
//        int excuted = 0;
//        try {
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//                double oldAmount = resultSet.getDouble("amount");
//                System.out.print("old amount==" + oldAmount);
//                double newAmount = oldAmount + productInStore.getAmount();
//                query = "UPDATE `pms`.`despence_product` SET `amount` = '" + newAmount + "' WHERE (`id` = '" + resultSet.getInt("id") + "');";
//                excuted = st.executeUpdate(query);
//            } else {
//                query = "INSERT INTO `pms`.`despence_product` (`product_id`, `amount`) VALUES ('" +
//                        productInStore.getProductType() + "', '" + productInStore.getAmount() + "');\n";
//
//                System.out.println("Insert query==" + query);
//                excuted = st.executeUpdate(query);
//                System.out.println("is Excuted===" + excuted);
//            }
//
//
//            // updating Store table
//            query = "SELECT * FROM pms.store_product where product_id = " + productInStore.getProductType();
//
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//                double oldAmount = resultSet.getDouble("amount");
//                System.out.print("old amount==" + oldAmount);
//                double newAmount = oldAmount - productInStore.getAmount();
//                query = "UPDATE `pms`.`store_product` SET `amount` = '" + newAmount + "' WHERE (`id` = '" + resultSet.getInt("id") + "');";
//                excuted = st.executeUpdate(query);
//            } else {
//                query = "INSERT INTO `pms`.`store_product` (`product_id`, `amount`) VALUES ('" +
//                        productInStore.getProductType() + "', '" + productInStore.getAmount() + "');\n";
//
//                System.out.println("Insert query==" + query);
//                excuted = st.executeUpdate(query);
//                System.out.println("is Excuted===" + excuted);
//                con.close();
//            }
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public ObservableList<Purchased_product> purchasedProductList(int position) {
//
//        ObservableList<Purchased_product> purchasedProductList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "select p.*,t.*, s.name,u.name FROM pms.purchase p " +
//                "left join pms.supplier s on p.supplier = s.id " +
//                "left join pms.user u on  p.purchesed_by = u.id " +
//                "left join pms.product_type t on  p.product = t.id order by date desc LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Purchased_product purchasedProduct = new Purchased_product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("product"),
//                        resultSet.getInt("supplier"),
//                        resultSet.getString("t.name"),
//                        resultSet.getDouble("amount"),
//                        resultSet.getDouble("unit_cost_price"),
//                        resultSet.getDouble("total_price"),
//                        resultSet.getString("invoice_number"),
//                        resultSet.getString("date"),
//                        resultSet.getInt("purchesed_by"),
//                        resultSet.getString("s.name"),
//                        resultSet.getString("model"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("u.name")
//                );
//                purchasedProductList.add(purchasedProduct);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return purchasedProductList;
//
//    }
//
//    public boolean saveSelledProduct(Selled_product selledProduct) {
//
//
//        try {
//
//
//            String query = "INSERT INTO `pms`.`selled` (`product`, `amount`, `total_price`, `customer_id`, " +
//                    "`invoice_number`, `date`, `selled_by`, `unit_price`, `broker`, `broker_price`, `broker_payed`) VALUES " +
//                    "(" + selledProduct.getProductid() + ", '" + selledProduct.getAmount() + "', '" + selledProduct.getTotalPrice() +
//                    "', " + selledProduct.getCustomer() + ", '" + selledProduct.getInvoiceNumber() + "', '" + selledProduct.getDate() +
//                    "', " + selledProduct.getSelledBy() + ", '" + selledProduct.getUnitPrice() + "', " + selledProduct.getBroker() +
//                    ", '" + selledProduct.getBrokerPrice() + "', " + selledProduct.isBrokerPayed() + ");";
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public boolean updateSaledProduct(Selled_product selledProduct) {
//
//
//        try {
//            System.out.println("In updateSales");
//
//            String query = "SELECT amount FROM pms.selled where id = " + selledProduct.getId();
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//
//
//                double amount = resultSet.getDouble("amount");
//                if (amount != selledProduct.getAmount()) {
//                    String measure = selledProduct.getSalingMeasure();
//                    System.out.println("in product Type ==" + selledProduct.getSalingMeasure());
//                    System.out.println("in product mtype ==" + selledProduct.getMeasuredAmount());
//                    double deff = amount - selledProduct.getAmount();
//
//                    System.out.println("is the type getted==" + selledProduct.getSalingMeasure());
//                    System.out.println("is the type getted measured Amount==" + selledProduct.getMeasuredAmount());
//
//
//                    if (!measure.isEmpty() && !measure.equalsIgnoreCase("null") &&
//                            selledProduct.getMeasuredAmount() != 0) {
//                        double oldAmount = 1 / selledProduct.getMeasuredAmount() * amount;
//                        double newAmount = (1 / selledProduct.getMeasuredAmount() * selledProduct.getAmount());
//                        deff = oldAmount - newAmount;
//                        System.out.println("in product mAmount ==" + oldAmount);
//                    }
//                    System.out.println("in product deff ==" + deff);
//                    query = "UPDATE `pms`.`despence_product` SET `amount` = (amount+ " + deff + ") WHERE (`product_id` = '" + selledProduct.getProductid() + "');";
//                    System.out.println("in updated amoount query==" + deff);
//                    System.out.println("in updated amoount query==" + query);
//
//                    st.executeUpdate(query);
//                }
//
//            }
//
//
//            query = "UPDATE `pms`.`selled` SET `product` = " + selledProduct.getProductid() +
//                    ", `amount` = '" + selledProduct.getAmount() + "', `total_price` = '" + selledProduct.getTotalPrice() +
//                    "', `customer_id` = '" + selledProduct.getCustomer() + "', `invoice_number` = '" + selledProduct.getInvoiceNumber() +
//                    "', `unit_price` = '" + selledProduct.getUnitPrice() + "', `broker` = '" + selledProduct.getBroker() +
//                    "', `broker_price` = '" + selledProduct.getBrokerPrice() + "', `broker_payed` = " + selledProduct.isBrokerPayed() +
//                    " WHERE (`id` = '" + selledProduct.getId() + "');";
//
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//
//    public ObservableList<Selled_product> selledProductList(int position, String dateQuery) {
//
//        String dateStart = dateQuery + " 00:00:00";
//        String dateEnd = dateQuery + " 24:00:00";
//
//        ObservableList<Selled_product> selledProducts = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "select s.*,t.*, c.name,u.name,b.name FROM pms.selled s " +
//                "left join pms.customer c     on  s.customer_id = c.id " +
//                "left join pms.user u         on  s.selled_by   = u.id " +
//                "left join pms.product_type t on  s.product     = t.id " +
//                "left join pms.broker b       on  s.broker      = b.id where " +
//                " date >= '" + dateStart + "' and date < '" + dateEnd + "'   order by date desc LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Selled_product selledProduct = new Selled_product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("product"),
//                        resultSet.getInt("customer_id"),
//                        resultSet.getDouble("amount"),
//                        resultSet.getString("unit_price"),
//                        resultSet.getString("total_price"),
//                        resultSet.getString("invoice_number"),
//                        resultSet.getString("date"),
//                        resultSet.getString("u.name"),
//                        resultSet.getString("b.name"),
//                        resultSet.getString("t.name"),
//                        resultSet.getString("c.name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("size"),
//                        resultSet.getString("model"),
//                        resultSet.getBoolean("has_broker"),
//                        resultSet.getDouble("broker_price"),
//                        resultSet.getBoolean("broker_payed"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getDouble("selling_amount_ratio")
//
//                );
//
//                selledProducts.add(selledProduct);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return selledProducts;
//
//    }
//
//    public int selledRowCount(String dateQuery) {
//
//        String dateStart = dateQuery + " 00:00:00";
//        String dateEnd = dateQuery + " 24:00:00";
//
//        String query = "SELECT count(*) FROM pms.selled where date >= '" + dateStart + "' and date < '" + dateEnd + "'";
//        try {
//            resultSet = st.executeQuery(query);
//            if (resultSet.next()) {
//
//                return resultSet.getInt("count(*)");
//            }
//            con.close();
//
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//        return 0;
//    }
//
//    public ObservableList<Broker> brokerList(int position) {
//
//        ObservableList<Broker> brokerList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT * FROM pms.broker LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Broker broker = new Broker(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                brokerList.add(broker);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return brokerList;
//
//    }
//
//    public boolean saveBroker(Broker broker) {
//
//        String query = "INSERT INTO `pms`.`broker` (`name`, `address`, `phone`, `tin`) VALUES ('" +
//                broker.getName() + "', '" + broker.getAddress() + "', '" + broker.getPhone() + "', '" + broker.getTin() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public int saveBrokerName(Broker broker) {
//
//        String query = "INSERT INTO `pms`.`broker` (`name`) VALUES ('" + broker.getName() + "');";
//
//        int id = 0;
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//
//
//            query = "SELECT * FROM `pms`.`broker` WHERE `name` = '" + broker.getName() + "'";
//            resultSet = st.executeQuery(query);
//            if (resultSet.next())
//                id = resultSet.getInt("id");
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return id;
//
//
//    }
//
//    public boolean updateBroker(Broker broker) {
//
//        String query = "UPDATE `pms`.`broker` SET `name` = '" + broker.getName() + "'," +
//                " `address` = '" + broker.getAddress() + "', `phone` = '" + broker.getPhone() +
//                "', `tin` = '" + broker.getTin() + "' WHERE (`id` = '" + broker.getId() + "');";
//
//        try {
//            System.out.println("Insert query==" + query);
//            int excuted = st.executeUpdate(query);
//            System.out.println("is Excuted===" + excuted);
//            con.close();
//            if (excuted == 1)
//                return true;
//        } catch (SQLException e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return false;
//
//
//    }
//
//    public ObservableList<Broker> searchBrokerList() {
//
//        ObservableList<Broker> brokerObservableList = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM pms.broker";
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Broker broker = new Broker(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                brokerObservableList.add(broker);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return brokerObservableList;
//
//    }
//
//
//    public JSONArray dashboardPiaCount() {
//
//        JSONArray piaChartData = new JSONArray();
//
//        String query = "select count(*), t.name FROM pms.selled s " +
//                "left join pms.product_type t on s.product = t.id " +
//                "where DATE(s.date) > (NOW() - INTERVAL 30 DAY)   group by s.product HAVING COUNT(s.product) > 2;";
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                System.out.println("count res =" + resultSet.getInt("count(*)"));
//                System.out.println("count res =" + resultSet.getString("t.name"));
//                JSONObject object = new JSONObject();
//                object.put("name", resultSet.getString("t.name"));
//                object.put("value", resultSet.getString("count(*)"));
//                piaChartData.put(object);
//            }
//            con.close();
//
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//        return piaChartData;
//    }
//
//
//    public JSONArray dashboardWeeklyselledReportBarchart() {
//
//        JSONArray piaChartData = new JSONArray();
//
//        String query = "SELECT count(*), day(date) FROM pms.selled  " +
//                "where DATE(date)  > (NOW() - INTERVAL 7 DAY) group by day(date);";
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                JSONObject object = new JSONObject();
//                object.put("name", resultSet.getString("day(date)"));
//                object.put("value", resultSet.getString("count(*)"));
//                piaChartData.put(object);
//            }
//            con.close();
//
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//        return piaChartData;
//    }
//
//
//    public JSONArray dashboardselledIncomeReportBarchart() {
//
//        JSONArray piaChartData = new JSONArray();
//
//        String query = "SELECT    day(date) as DATE, SUM(`total_price`) " +
//                "FROM      selled " +
//                "WHERE DATE(date)  > (NOW() - INTERVAL 7 DAY) " +
//                "GROUP BY  DATE(date)";
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                JSONObject object = new JSONObject();
//                object.put("name", resultSet.getString("DATE"));
//                object.put("value", resultSet.getString("SUM(`total_price`)"));
//                piaChartData.put(object);
//            }
//            con.close();
//
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//        return piaChartData;
//    }
//
//
//    //search from the list
//
//    public ObservableList<Supplier> searchSupplierByName(String qText, int position) {
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//
//        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM pms.supplier WHERE name LIKE  '%" + qText +
//                "%' LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        System.out.println("q=" + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Supplier supplier = new Supplier(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                suppliers.add(supplier);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return suppliers;
//
//    }
//
//    public ObservableList<Customer> searchCustomerByName(String qText, int position) {
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//
//        ObservableList<Customer> customers = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM pms.customer WHERE name LIKE  '%" + qText +
//                "%' LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        System.out.println("q=" + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Customer supplier = new Customer(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                customers.add(supplier);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return customers;
//
//    }
//
//    public ObservableList<Broker> searchBrokerByName(String qText, int position) {
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//
//        ObservableList<Broker> brokers = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM pms.broker WHERE name LIKE  '%" + qText +
//                "%' LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        System.out.println("q=" + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Broker broker = new Broker(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("address"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("tin"));
//                brokers.add(broker);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return brokers;
//
//    }
//
//    public ObservableList<Product_type> searchProductTypByName(String qText, int position) {
//
//        ObservableList<Product_type> productTypes = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT * FROM pms.product_type WHERE name LIKE  '%" + qText +
//                "%' LIMIT " + startlimit + "," + new HardCodedValue().PAGELIMIT;
//        System.out.println("qq==" + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//                Product_type productType = new Product_type(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getBoolean("has_broker"),
//                        resultSet.getString("measure_price")
//                );
//                productTypes.add(productType);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productTypes;
//
//    }
//
//    public ObservableList<Product> searchStoreProductByName(String qText, int position) {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT s.* , p.*  FROM store_product s join product_type p WHERE s.product_id = p.id  and " +
//                " p.name LIKE '%" + qText + "%'  LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Product product = new Product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount")
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public ObservableList<Product> searchDespenceProductByName(String qText, int position) {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT s.* , p.*  FROM despence_product s join product_type p WHERE s.product_id = p.id  and " +
//                " p.name LIKE '%" + qText + "%'  LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Product product = new Product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount")
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public ObservableList<Product> searchTotalProductByName(String qText, int position) {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "SELECT p.*, name, d.amount, s.amount, (d.amount+s.amount)  FROM pms.product_type p " +
//                "left join pms.despence_product  d on p.id = d.product_id " +
//                "left join pms.store_product  s on p.id = s.product_id WHERE name LIKE '%" + qText +
//                "%'   having  (d.amount+s.amount) > 0   LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Product product = new Product(
//                        resultSet.getInt("p.id"),
//                        ++startlimit,
//                        resultSet.getInt("p.id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("selling_price"),
//                        resultSet.getString("selling_measurment"),
//                        resultSet.getString("selling_amount_ratio"),
//                        resultSet.getDouble("amount"),
//                        resultSet.getDouble("measure_price")
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//    public ObservableList<Purchased_product> searchPurchasedProductByName(String qText, int position) {
//
//        ObservableList<Purchased_product> purchasedProductList = FXCollections.observableArrayList();
//
//        position--;
//        int startlimit = position * new HardCodedValue().PAGELIMIT;
//        String query = "select p.*,t.*, s.name,u.name FROM pms.purchase p " +
//                "left join pms.supplier s on p.supplier = s.id " +
//                "left join pms.user u on  p.purchesed_by = u.id " +
//                "left join pms.product_type t on  p.product = t.id  where t.name LIKE '%" + qText + "%'order by date desc LIMIT " +
//                startlimit + "," + new HardCodedValue().PAGELIMIT;
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Purchased_product purchasedProduct = new Purchased_product(
//                        resultSet.getInt("id"),
//                        ++startlimit,
//                        resultSet.getInt("product"),
//                        resultSet.getInt("supplier"),
//                        resultSet.getString("t.name"),
//                        resultSet.getDouble("amount"),
//                        resultSet.getDouble("unit_cost_price"),
//                        resultSet.getDouble("total_price"),
//                        resultSet.getString("invoice_number"),
//                        resultSet.getString("date"),
//                        resultSet.getInt("purchesed_by"),
//                        resultSet.getString("s.name"),
//                        resultSet.getString("model"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getString("u.name")
//                );
//                purchasedProductList.add(purchasedProduct);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return purchasedProductList;
//
//    }
//
//    public ObservableList<Product> storeAlertNotification() {
//
//        ObservableList<Product> productList = FXCollections.observableArrayList();
//
//        String query = "SELECT s.amount , p.*  FROM store_product s join product_type p WHERE s.product_id = p.id  and " +
//                " amount < 5";
//
//        System.out.println("Query = " + query);
//        try {
//            resultSet = st.executeQuery(query);
//            while (resultSet.next()) {
//
//
//                Product product = new Product(
//                        resultSet.getString("name"),
//                        resultSet.getString("type"),
//                        resultSet.getString("grade"),
//                        resultSet.getString("model"),
//                        resultSet.getString("size"),
//                        resultSet.getString("brand"),
//                        resultSet.getDouble("amount")
//                );
//                productList.add(product);
//            }
//
//
//            con.close();
//        } catch (Exception e) {
//            values.LogError(" Cause: " + e.getCause() +
//                    "\nMassage: " + e.getMessage() +
//                    "\nErrorText: " + e.toString() +
//                    "\nLocalizedMessage: " + e.getLocalizedMessage() +
//                    "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
//            );
//            e.printStackTrace();
//        }
//
//
//        return productList;
//
//    }
//
//
}
