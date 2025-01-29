package lk.ijse.gdse.main.cicvetcare.contoller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.main.cicvetcare.dto.CategoryDto;
import lk.ijse.gdse.main.cicvetcare.dto.tm.CategoryTm;
import lk.ijse.gdse.main.cicvetcare.model.CategoryModel;
import lk.ijse.gdse.main.cicvetcare.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private AnchorPane ancCategory;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CategoryTm, String> colCategoryId;

    @FXML
    private TableColumn<CategoryTm, String> colName;

    @FXML
    private Label lblCatId;

    @FXML
    private TableView<CategoryTm> tblCategory;

    @FXML
    private TextField txtName;

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String catId = lblCatId.getText();
        String catName = txtName.getText();
        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = catName.matches(namePattern);
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + " -fx-border-color: red;");
            return;
        }
        if (isValidName){
            CategoryDto categoryDto = new CategoryDto(catId,catName);
            boolean isSaved = categoryModel.saveCategory(categoryDto);
            if (isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category added successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Category not saved").show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String catId = lblCatId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"are you sure ?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.get() == ButtonType.YES && optionalButtonType.isPresent()){
            boolean isDeleted = categoryModel.deleteCategory(catId);
            if (isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category deleted successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Category not deleted").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String catId = lblCatId.getText();
        String catName = txtName.getText();
        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = catName.matches(namePattern);
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + " -fx-border-color: red;");
            return;
        }
        if (isValidName){
            CategoryDto categoryDto = new CategoryDto(catId,catName);
            boolean isUpdated = categoryModel.updateCategory(categoryDto);
            if (isUpdated){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category updated successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Category not updated").show();
            }
        }
    }

    @FXML
    void tblCategoryOnAction(MouseEvent event) {
        CategoryTm categoryTm = (CategoryTm) tblCategory.getSelectionModel().getSelectedItem();
        if (categoryTm != null) {
            lblCatId.setText(categoryTm.getCategoryId());
            txtName.setText(categoryTm.getCategoryName());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextCategoryId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtName.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<CategoryDto> categoryDtos = categoryModel.getAllCategories();
        ObservableList<CategoryTm> categoryTms = FXCollections.observableArrayList();

        for (CategoryDto categoryDto : categoryDtos) {
            CategoryTm categoryTm = new CategoryTm(
                    categoryDto.getCategoryId(),
                    categoryDto.getCategoryName()
            );
            categoryTms.add(categoryTm);
        }
        tblCategory.setItems(categoryTms);
    }

    CategoryModel categoryModel = new CategoryModel();
    private void loadNextCategoryId() throws SQLException {
        String categoryId = categoryModel.getNextCategoryId();
        lblCatId.setText(categoryId);
    }
}
