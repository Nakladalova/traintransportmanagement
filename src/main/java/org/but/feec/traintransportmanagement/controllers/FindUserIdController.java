package org.but.feec.traintransportmanagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.controlsfx.validation.Validator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.but.feec.traintransportmanagement.data.UserRepository;
import org.but.feec.traintransportmanagement.services.UserService;
import org.but.feec.traintransportmanagement.config.DataSourceConfig;
import org.but.feec.traintransportmanagement.exceptions.DataAccessException;

public class FindUserIdController {
    private static final Logger logger = LoggerFactory.getLogger(FindUserIdController.class);

    @FXML
    public Button findUserSecureButton;
    @FXML
    public Button findUserSQLInjectionButton;
    @FXML
    private TextField userId;
    @FXML
    private TextField userName;
    @FXML
    private TextArea userNameTextArea;
    @FXML
    public Button findUserSQLInjectionDropTable;

    private ValidationSupport validation;
    private UserRepository userRepository;
    private UserService userService;


    @FXML
    public void initialize() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);

        validation = new ValidationSupport();
        validation.registerValidator(userId, Validator.createEmptyValidator("User id must not be empty."));

        //findUser.disableProperty().bind(validation.invalidProperty());

        logger.info("FindUserController initialized");
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @FXML
    void handleFindUserSecureButton(ActionEvent event) {
        String userIdString = userId.getText();
        int userIdInt=0;
        if(tryParse(userIdString)==null){
            userNameTextArea.setText("error");
        }
        else{
            userIdInt=tryParse(userIdString);;
        }
        //int userIdInt=0;
        /*try{
            userIdInt=Integer.parseInt(userIdString);
        } catch (NumberFormatException e) {
            userNameTextArea.setText("error");
            //e.printStackTrace();
        }*/
        String userSurname;
        StringBuffer s = new StringBuffer();
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT surname" +
                             " FROM public.sql_injection_table1 u" +
                             " WHERE u.id = ?")
        ) {
            preparedStatement.setInt(1, userIdInt);
            String backLash="/";
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userSurname=resultSet.getString("surname");
                    s.append(userSurname);
                    //s.append("/");
                    s.append("\n");
                }
            }
            String userNameFromDatabase=s.toString();
            //userName.setText(userNameFromDatabase);
            userNameTextArea.setText(userNameFromDatabase);

        } catch (SQLException e) {
            userNameTextArea.setText("error");
            throw new DataAccessException("Find user by ID failed.", e);
        }

    }

    @FXML
    void handleFindUserSQLInjectionButton(ActionEvent event) {
        String userIdString = userId.getText();
        String userSurname;
        StringBuffer s = new StringBuffer();
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT surname" +
                             " FROM public.sql_injection_table1 u" +
                             " WHERE u.id ="+userIdString)
        ) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userSurname=resultSet.getString("surname");
                    s.append(userSurname);
                    //s.append("/");
                    s.append("\n");
                }
            }
            String userNameFromDatabase=s.toString();
            //userName.setText(userNameFromDatabase);
            userNameTextArea.setText(userNameFromDatabase);

        } catch (SQLException e) {
            userNameTextArea.setText("error");
            throw new DataAccessException("Find user by ID failed.", e);
        }

    }

    @FXML
    void handleFindUserSQLInjectionDropTableButton(ActionEvent event) {
        String userIdString = userId.getText();
        String userSurname;
        StringBuffer s = new StringBuffer();
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT surname" +
                             " FROM public.sql_injection_table2 u" +
                             " WHERE u.id ="+userIdString)
        ) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userSurname=resultSet.getString("surname");
                    s.append(userSurname);
                    //s.append("/");
                    s.append("\n");
                }
            }
            String userNameFromDatabase=s.toString();
            //userName.setText(userNameFromDatabase);
            userNameTextArea.setText(userNameFromDatabase);

        } catch (SQLException e) {
            userNameTextArea.setText("error");
            throw new DataAccessException("Find user by ID failed.", e);
        }

    }


}
