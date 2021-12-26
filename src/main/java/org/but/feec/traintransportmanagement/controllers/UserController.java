package org.but.feec.traintransportmanagement.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/*
import org.but.feec.traintransportmanagement.api.TrainBasicView;
import org.but.feec.traintransportmanagement.api.UserView;
import org.but.feec.traintransportmanagement.data.UserRepository;
import org.but.feec.traintransportmanagement.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @FXML
    private TableView<UserView> systemUserTableView;
    @FXML
    private TableColumn<UserView, Long> userId;
    @FXML
    private TableColumn<UserView, String> userSurname;
    @FXML
    private TableColumn<UserView, String> userEmail;
    @FXML
    private TableColumn<UserView, String> userPassword;

    private UserRepository userRepository;
    private UserService userService;

    public UserController() {
    }

    @FXML
    public void initialize() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);

        userId.setCellValueFactory(new PropertyValueFactory<UserView, Long>("userId"));
        userSurname.setCellValueFactory(new PropertyValueFactory<UserView, String>("surname"));
        userEmail.setCellValueFactory(new PropertyValueFactory<UserView, String>("email"));
        userPassword.setCellValueFactory(new PropertyValueFactory<UserView, String>("password"));
        //systemTrainTableView.getSortOrder().add(trainsId);

        ObservableList<UserView> observableUserList = initializeUserData();
        systemUserTableView.setItems(observableUserList);

        logger.info("User initialized");
    }

    private ObservableList<UserView> initializeUserData() {
        List<UserView> users = userService.getUserView(userId);
        return FXCollections.observableArrayList(users);
    }
*/



