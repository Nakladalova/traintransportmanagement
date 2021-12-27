package org.but.feec.traintransportmanagement.controllers;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.but.feec.traintransportmanagement.config.DataSourceConfig;
import org.but.feec.traintransportmanagement.exceptions.DataAccessException;
import org.but.feec.traintransportmanagement.exceptions.ExceptionHandler;
import javafx.stage.Stage;
import org.but.feec.traintransportmanagement.App;
import org.but.feec.traintransportmanagement.api.TrainBasicView;
import org.but.feec.traintransportmanagement.api.TrainDetailView;
import org.but.feec.traintransportmanagement.data.TrainRepository;
import org.but.feec.traintransportmanagement.services.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TrainController {

    private static final Logger logger = LoggerFactory.getLogger(TrainController.class);

    ObservableList<String>columnNamesList = FXCollections.observableArrayList("None","train_name", "speed", "type");
    @FXML
    private TableView<TrainBasicView> systemTrainTableView;
    @FXML
    private TableColumn<TrainBasicView, Long> trainsId;
    @FXML
    private TableColumn<TrainBasicView, String> trainsName;
    @FXML
    private TableColumn<TrainBasicView, String> trainsSpeed;
    @FXML
    private TableColumn<TrainBasicView, String> trainsType;
    @FXML
    private Button refreshButton;
    @FXML
    public Button addTrainButton;
    @FXML
    public Button findUser;
    @FXML
    public ComboBox trainColumnsComboBox;
    @FXML
    public TextField valueTextField;
    @FXML
    public Button filterButton;
    @FXML
    public Button resetFilterButton;
    @FXML
    public Button addCrewButton;

    Long trainId;

    private TrainRepository trainRepository;
    private TrainService trainService;

    public TrainController() {

    }

    @FXML
    public void initialize() {
        trainRepository = new TrainRepository();
        trainService = new TrainService(trainRepository);

        trainColumnsComboBox.getItems().removeAll(trainColumnsComboBox.getItems());
        trainColumnsComboBox.setItems(columnNamesList);
        trainsId.setCellValueFactory(new PropertyValueFactory<TrainBasicView, Long>("id"));
        trainsName.setCellValueFactory(new PropertyValueFactory<TrainBasicView, String>("trainName"));
        trainsSpeed.setCellValueFactory(new PropertyValueFactory<TrainBasicView, String>("speed"));
        trainsType.setCellValueFactory(new PropertyValueFactory<TrainBasicView, String>("type"));
        systemTrainTableView.getSortOrder().add(trainsId);

        ObservableList<TrainBasicView> observableTrainList = initializeTrainsData();
        systemTrainTableView.setItems(observableTrainList);

        initializeTableViewSelection();
        loadIcons();

        logger.info("Application initialized");

    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit train");
        MenuItem detailedView = new MenuItem("Detailed train view");
        MenuItem addDriversCrew = new MenuItem("Add drivers crew");
        edit.setOnAction((ActionEvent event) -> {
            TrainBasicView trainView = systemTrainTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/TrainEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(trainView);
                stage.setTitle("BDS JavaFX Edit Train");

                TrainEditController controller = new TrainEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            TrainBasicView trainView = systemTrainTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/TrainDetailView.fxml"));
                Stage stage = new Stage();

                Long trainId = trainView.getId();
                TrainDetailView trainDetailView = trainService.getTrainDetailView(trainId);

                stage.setUserData(trainDetailView);
                stage.setTitle("BDS JavaFX Train Detailed View");

                TrainDetailViewController controller = new TrainDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        addDriversCrew.setOnAction((ActionEvent event) -> {
            TrainBasicView trainView = systemTrainTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/AddCrew.fxml"));
                Stage stage = new Stage();

                Long trainId = trainView.getId();
                stage.setUserData(trainId);
                stage.setTitle("BDS JavaFX Add Crew");

                AddCrewController controller = new AddCrewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(edit, detailedView, addDriversCrew);
/*
        menu.getItems().add(edit);
        menu.getItems().add(detailedView);
        menu.getItems().add(addDriversCrew);

 */
        systemTrainTableView.setContextMenu(menu);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    private ObservableList<TrainBasicView> initializeTrainsData() {
        String selectedItem= (String)trainColumnsComboBox.getSelectionModel().getSelectedItem();
        String value= valueTextField.getText();
        if (selectedItem == null || selectedItem == "None") {
            selectedItem = "None";
            value = null;
        }
        List<TrainBasicView> trains = trainService.getTrainBasicView(selectedItem, value);
        return FXCollections.observableArrayList(trains);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/vut-logo-eng.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void filterData(){
        ObservableList<TrainBasicView> observableTrainsList = initializeTrainsData();
        systemTrainTableView.setItems(observableTrainsList);
        systemTrainTableView.refresh();
        systemTrainTableView.sort();
    }

    @FXML
    public void handlefilterButton(ActionEvent event) {
        filterData();
    }

    @FXML
    public void handleResetfilterButton(ActionEvent event) {
        trainColumnsComboBox.getSelectionModel().select("None");
        valueTextField.clear();
        filterData();
    }
    public void handleAddCrewButton(ActionEvent actionEvent) {
        TrainBasicView trainView = systemTrainTableView.getSelectionModel().getSelectedItem();
        try {
            trainId = trainView.getId();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/AddCrew.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Add Crew");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public Long getTrainsId(){
        return trainId;
    }

    public void handleAddTrainButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/FindUser.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Find User");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleFindUserButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/FindUserId.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX User");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }


    @FXML
    protected void onRefreshButtonClick(ActionEvent event) {
        filterData();
    }
}
