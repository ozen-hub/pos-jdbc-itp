import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView<CustomerTM> tblCustomers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;

    public void initialize(){
        loadAllData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void loadAllData() {
        ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
        try{
            for (Customer c: new DatabaseCode().findAll()){
                tmList.add(new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary()));
            }
            tblCustomers.setItems(tmList);
        }catch (ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Customer c= new Customer(Integer.parseInt(txtId.getText()), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()));
        try{
            boolean isSaved = new DatabaseCode().save(c);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
                clear();
                loadAllData();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }catch (ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void getCustomerOnAction(ActionEvent actionEvent) {
        int customerId=Integer.parseInt(txtId.getText());
        try{
            Customer selectedCustomer =new DatabaseCode().find(customerId);
            if(null!=selectedCustomer){
                txtName.setText(selectedCustomer.getName());
                txtAddress.setText(selectedCustomer.getAddress());
                txtSalary.setText(String.valueOf(selectedCustomer.getSalary()));
            }else{
               new Alert(Alert.AlertType.WARNING,"Customer Not Found").show();
            }
        }catch (ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        Customer c= new Customer(Integer.parseInt(txtId.getText()), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()));
        try{
            boolean isUpdated = new DatabaseCode().update(c);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated").show();
                clear();
                loadAllData();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Try again").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clear(){
        txtSalary.clear();
        txtAddress.clear();
        txtName.clear();
        txtId.clear();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        int customerId=Integer.parseInt(txtId.getText());
        try{
            boolean isDeleted =new DatabaseCode().delete(customerId);
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted").show();
                clear();
                loadAllData();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Try again").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
