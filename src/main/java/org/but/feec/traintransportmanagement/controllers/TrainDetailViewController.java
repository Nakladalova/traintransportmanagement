package org.but.feec.traintransportmanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.but.feec.traintransportmanagement.api.TrainDetailView;

public class TrainDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(TrainDetailViewController.class);

    @FXML
    private TextField trainIdTextField;

    @FXML
    private TextField trainNameTextField;

    @FXML
    private TextField trainSpeedTextField;

    @FXML
    private TextField trainTypeTextField;

    @FXML
    private TextField depoNameTextField;

    @FXML
    private TextField depoSizeTextField;


    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        trainIdTextField.setEditable(false);
        trainNameTextField.setEditable(false);
        trainSpeedTextField.setEditable(false);
        trainTypeTextField.setEditable(false);
        depoNameTextField.setEditable(false);
        depoSizeTextField.setEditable(false);

        loadTrainsData();

        logger.info("TrainDetailViewController initialized");
    }

    private void loadTrainsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof TrainDetailView) {
            TrainDetailView trainDetailView = (TrainDetailView) stage.getUserData();
            trainIdTextField.setText(String.valueOf(trainDetailView.getTrainId()));
            trainNameTextField.setText(trainDetailView.getTrainName());
            trainSpeedTextField.setText(trainDetailView.getTrainSpeed());
            trainTypeTextField.setText(trainDetailView.getTrainType());
            depoNameTextField.setText(trainDetailView.getDepoName());
            depoSizeTextField.setText(trainDetailView.getDepoSize());

        }
    }

}

