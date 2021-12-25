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
import javafx.util.Duration;
import org.but.feec.traintransportmanagement.api.TrainCreateView;
import org.but.feec.traintransportmanagement.data.TrainRepository;
import org.but.feec.traintransportmanagement.services.TrainService;
import org.controlsfx.validation.ValidationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.controlsfx.validation.Validator;

import java.util.Optional;

public class TrainCreateController {

    private static final Logger logger = LoggerFactory.getLogger(TrainCreateController.class);

    @FXML
    public Button newTrainCreateTrain;
    @FXML
    private TextField newDepoId;
    @FXML
    private TextField newTrainName;

    @FXML
    private TextField newTrainSpeed;

    @FXML
    private TextField newTrainType;
    
    private TrainService trainService;
    private TrainRepository trainRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        trainRepository = new TrainRepository();
        trainService = new TrainService(trainRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newDepoId, Validator.createEmptyValidator("Train id must not be empty."));
        validation.registerValidator(newTrainName, Validator.createEmptyValidator("Train name must not be empty."));
        validation.registerValidator(newTrainSpeed, Validator.createEmptyValidator("Train speed name must not be empty."));
        validation.registerValidator(newTrainType, Validator.createEmptyValidator("Train type must not be empty."));

        newTrainCreateTrain.disableProperty().bind(validation.invalidProperty());

        logger.info("TrainCreateController initialized");
    }

    @FXML
    void handleCreateNewTrain(ActionEvent event) {

        String trainName = newTrainName.getText();
        String trainSpeed = newTrainSpeed.getText();
        String trainType = newTrainType.getText();
        String depoId = newDepoId.getText();

        TrainCreateView trainCreateView = new TrainCreateView();
        trainCreateView.setTrainName(trainName);
        trainCreateView.setTrainSpeed(trainSpeed);
        trainCreateView.setTrainType(trainType);
        int depoIdInt = Integer.parseInt(depoId);
        trainCreateView.setDepoId(depoIdInt);

        trainService.createTrain(trainCreateView);

        trainCreatedConfirmationDialog();
    }

    private void trainCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Train Created Confirmation");
        alert.setHeaderText("Your train was successfully created.");

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

