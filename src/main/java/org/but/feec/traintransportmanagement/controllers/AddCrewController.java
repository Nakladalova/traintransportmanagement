package org.but.feec.traintransportmanagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.feec.traintransportmanagement.api.TrainBasicView;
import org.but.feec.traintransportmanagement.api.TrainDetailView;
import org.but.feec.traintransportmanagement.config.DataSourceConfig;
import org.but.feec.traintransportmanagement.exceptions.DataAccessException;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class AddCrewController {

    @FXML
    public Button findEngineDriver1Button;
    @FXML
    public Button findEngineDriver2Button;
    @FXML
    public Button addEngineDriversButton;
    @FXML
    public TextField engineDriver1TextField;
    @FXML
    public TextField engineDriver2TextField;
    @FXML
    public TextArea messageBox1;
    @FXML
    public TextArea messageBox2;
    @FXML
    public TextArea resultMessageBox;

    int engineDriver1Id=0;
    int engineDriver2Id=0;
    TrainController trainController;

    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    Long trainId;
    private static final Logger logger = LoggerFactory.getLogger(AddCrewController.class);

    private ValidationSupport validation;

    @FXML
    public void initialize() {
        /*validation = new ValidationSupport();
        validation.registerValidator(engineDriver1TextField, Validator.createEmptyValidator("Engine driver must not be empty."));
        findEngineDriver1Button.disableProperty().bind(validation.invalidProperty());*/

        Stage stage = this.stage;
        if (stage.getUserData() instanceof Long) {
            trainId = (Long)stage.getUserData();
        }

    }


    public void handleFindEngineDriver1Button(ActionEvent actionEvent) {
        String surname=engineDriver1TextField.getText();
        String email= surname+"@seznam.cz";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT e.id " +
                             " FROM public.engine_driver e" +
                             " WHERE e.email=?")
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    engineDriver1Id=resultSet.getInt("id");
                    messageBox1.setText("Engine driver was selected.");
                }
                else{
                    messageBox1.setText("Engine driver was not found.\n Enter new engine driver");
                }
            }
        } catch (SQLException e) {
            messageBox1.setText("Engine driver was not found.");
            throw new DataAccessException("Find engine driver by email failed.", e);
        }

    }

    public void handleFindEngineDriver2Button(ActionEvent actionEvent) {
        String surname=engineDriver2TextField.getText();
        String email= surname+"@seznam.cz";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT e.id " +
                             " FROM public.engine_driver e" +
                             " WHERE e.email=?")
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    engineDriver2Id=resultSet.getInt("id");
                    messageBox2.setText("Engine driver was selected");
                }
                else{
                    messageBox2.setText("Engine driver was not found.\n Enter new engine driver");
                }
            }
        } catch (SQLException e) {
            messageBox2.setText("Engine driver was not found.");
            throw new DataAccessException("Find engine driver by email failed.", e);
        }
    }

    public void handleAddEngineDriversButton(ActionEvent actionEvent) {;
        if((engineDriver1Id==0) || (engineDriver2Id==0)){
            resultMessageBox.setText("Type engine driver's surname and click button find engine driver. Engine drivers were not added.");

        }
        else{

            String changeTrainIdToEngineDriver = "UPDATE public.engine_driver SET train_id=? WHERE id=?";
            try (Connection connection = DataSourceConfig.getConnection();
                 PreparedStatement preparedStatement1 = connection.prepareStatement(changeTrainIdToEngineDriver, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement preparedStatement2 = connection.prepareStatement(changeTrainIdToEngineDriver, Statement.RETURN_GENERATED_KEYS)
            ) {
                preparedStatement1.setLong(1, trainId);
                preparedStatement1.setInt(2, engineDriver1Id);
                preparedStatement2.setLong(1, trainId);
                preparedStatement2.setInt(2, engineDriver2Id);
                try {
                    connection.setAutoCommit(false);
                    int affectedRows = preparedStatement1.executeUpdate();
                    if (affectedRows == 0) {
                        throw new DataAccessException("Updating engine driver 1 failed, no rows affected.");
                    }
                    affectedRows = preparedStatement2.executeUpdate();
                    if (affectedRows == 0) {
                        throw new DataAccessException("Updating engine driver 2 failed, no rows affected.");
                    }
                    resultMessageBox.setText("Engine crew was added to the selected train.");
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                } finally {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DataAccessException("Updating engine driver failed operation on the database failed.");
            }

        }
    }
}

        /*else{
            try (Connection connection = DataSourceConfig.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "UPDATE public.engine_driver " +
                                 " SET train_id= ?" +
                                 " WHERE id= ?")

            ) {
                preparedStatement.setLong(1, trainsId);
                preparedStatement.setLong(2, engineDriver1Id );
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        engineDriver2Id=resultSet.getInt("id");
                        messageBox2.setText("Engine driver was found");
                    }
                    else{
                        messageBox2.setText("Engine driver was not found.\n Enter new engine driver");
                    }
                }
            } catch (SQLException e) {
                messageBox2.setText("Engine driver was not found.");
                throw new DataAccessException("Find engine driver by email failed.", e);
            }


        }*/

/*String insertEngineDriverIdSQL = "UPDATE public.engine_driver SET train_id=? WHERE id=?";
            //String checkIfExists = "SELECT email FROM public.engine_driver t WHERE id = ?";
            try (Connection connection = DataSourceConfig.getConnection();
                 // would be beneficial if I will return the created entity back
                 PreparedStatement preparedStatement = connection.prepareStatement(insertEngineDriverIdSQL, Statement.RETURN_GENERATED_KEYS)) {
                // set prepared statement variables
                preparedStatement.setLong(1, trainId);
                preparedStatement.setInt(2, engineDriver1Id);
                try {
                    connection.setAutoCommit(false);
                    try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                        ps.setLong(1, engineDriver1Id);
                        ps.execute();
                    } catch (SQLException e) {
                        throw new DataAccessException("Engine driver for edit do not exist.");
                    }

                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows == 0) {
                        throw new DataAccessException("Updating engine driver failed, no rows affected.");
                    }
                    resultMessageBox.setText("Engine driver was added");
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                } finally {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DataAccessException("Updating engine driver failed operation on the database failed.");
            }
*/




