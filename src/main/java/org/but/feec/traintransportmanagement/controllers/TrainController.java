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
import java.util.List;

public class TrainController {

    private static final Logger logger = LoggerFactory.getLogger(TrainController.class);

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


    private TrainRepository trainRepository;
    private TrainService trainService;

    public TrainController() {

    }

    @FXML
    public void initialize() {
        trainRepository = new TrainRepository();
        trainService = new TrainService(trainRepository);

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

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        systemTrainTableView.setContextMenu(menu);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    private ObservableList<TrainBasicView> initializeTrainsData() {
        List<TrainBasicView> trains = trainService.getTrainBasicView();
        return FXCollections.observableArrayList(trains);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/vut-logo-eng.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleAddTrainButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/TrainCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Train");
            stage.setScene(scene);

//            Stage stageOld = (Stage) signInButton.getScene().getWindow();
//            stageOld.close();
//
//            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/vut.jpg")));
//            authConfirmDialog();

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    @FXML
    protected void onRefreshButtonClick(ActionEvent event) {
        ObservableList<TrainBasicView> observableTrainsList = initializeTrainsData();
        systemTrainTableView.setItems(observableTrainsList);
        systemTrainTableView.refresh();
        systemTrainTableView.sort();
    }
}
