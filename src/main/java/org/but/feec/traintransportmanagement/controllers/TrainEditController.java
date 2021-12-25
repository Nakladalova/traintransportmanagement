package org.but.feec.traintransportmanagement.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.but.feec.traintransportmanagement.api.TrainBasicView;
import org.but.feec.traintransportmanagement.api.TrainEditView;
import org.but.feec.traintransportmanagement.data.TrainRepository;
import org.but.feec.traintransportmanagement.services.TrainService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class TrainEditController {

    private static final Logger logger = LoggerFactory.getLogger(TrainEditController.class);

    @FXML
    public Button editTrainButton;
    @FXML
    public TextField idTextField;
    @FXML
    public TextField trainNameTextField;
    @FXML
    private TextField trainSpeedTextField;
    @FXML
    private TextField trainTypeTextField;

    private TrainService trainService;
    private TrainRepository trainRepository;
    private ValidationSupport validation;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        trainRepository = new TrainRepository();
        trainService = new TrainService(trainRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idTextField, Validator.createEmptyValidator("The id must not be empty."));
        idTextField.setEditable(false);
        validation.registerValidator(trainNameTextField, Validator.createEmptyValidator("Train name must not be empty."));
        validation.registerValidator(trainSpeedTextField, Validator.createEmptyValidator("Train speed must not be empty."));
        validation.registerValidator(trainTypeTextField, Validator.createEmptyValidator("Train type must not be empty."));


        editTrainButton.disableProperty().bind(validation.invalidProperty());

        loadTrainsData();

        logger.info("TrainEditController initialized");
    }

    /**
     * Load passed data from Persons controller. Check this tutorial explaining how to pass the data between controllers: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
     */
    private void loadTrainsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof TrainBasicView) {
            TrainBasicView trainBasicView = (TrainBasicView) stage.getUserData();
            idTextField.setText(String.valueOf(trainBasicView.getId()));
            trainNameTextField.setText(trainBasicView.getTrainName());
            trainSpeedTextField.setText(trainBasicView.getSpeed());
            trainTypeTextField.setText(trainBasicView.getType());
        }
    }

    @FXML
    public void handleEditTrainButton(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        Long id = Long.valueOf(idTextField.getText());
        String trainName = trainNameTextField.getText();
        String trainSpeed = trainSpeedTextField.getText();
        String trainType = trainTypeTextField.getText();

        TrainEditView trainEditView = new TrainEditView();
        trainEditView.setTrainId(id);
        trainEditView.setTrainName(trainName);
        trainEditView.setTrainSpeed(trainSpeed);
        trainEditView.setTrainType(trainType);


        trainService.editTrain(trainEditView);

        trainEditedConfirmationDialog();
    }

    private void trainEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Train Edited Confirmation");
        alert.setHeaderText("Your train was successfully edited.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}
