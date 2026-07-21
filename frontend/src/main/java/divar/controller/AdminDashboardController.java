package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.network.ApiException;
import divar.service.AdminService;
import divar.util.Constants;
import divar.util.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class AdminDashboardController {

    @FXML
    private Label adminLabel;

    @FXML
    private StackPane contentPane;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {

        adminLabel.setText(
                "خوش آمدید " + SessionManager.getUsername()
        );

        showUsers();
    }

    @FXML
    public void showUsers() {

        TableView<UserResponse> table = new TableView<>();

        TableColumn<UserResponse, String> idCol =
                new TableColumn<>("ID");

        idCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(data.getValue().getId())
                ));

        idCol.setPrefWidth(70);

        TableColumn<UserResponse, String> usernameCol =
                new TableColumn<>("Username");

        usernameCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getUsername()
                ));

        usernameCol.setPrefWidth(180);

        TableColumn<UserResponse, String> roleCol =
                new TableColumn<>("Role");

        roleCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getRole()
                ));

        roleCol.setPrefWidth(120);

        TableColumn<UserResponse, String> statusCol =
                new TableColumn<>("Status");

        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getStatus()
                ));

        statusCol.setPrefWidth(120);

        TableColumn<UserResponse, Void> actionCol =
                new TableColumn<>("Operation");

        actionCol.setPrefWidth(180);

        actionCol.setCellFactory(column -> new TableCell<>() {

            private final Button actionButton =
                    new Button();

            {

                actionButton.setOnAction(event -> {

                    UserResponse user =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    try {

                        if ("ACTIVE".equalsIgnoreCase(
                                user.getStatus())) {

                            adminService.blockUser(
                                    user.getId());

                        } else {

                            adminService.unblockUser(
                                    user.getId());

                        }

                        showUsers();

                    } catch (ApiException e) {

                        Alert alert =
                                new Alert(Alert.AlertType.ERROR);

                        alert.setContentText(
                                e.getMessage());

                        alert.show();

                    } catch (IOException |
                             InterruptedException e) {

                        Alert alert =
                                new Alert(Alert.AlertType.ERROR);

                        alert.setContentText(
                                "خطا در ارتباط با سرور");

                        alert.show();
                    }

                });

            }

            @Override
            protected void updateItem(
                    Void item,
                    boolean empty) {

                super.updateItem(item, empty);

                if (empty) {

                    setGraphic(null);
                    return;
                }

                UserResponse user =
                        getTableView()
                                .getItems()
                                .get(getIndex());

                if ("ACTIVE".equalsIgnoreCase(
                        user.getStatus())) {

                    actionButton.setText("Block");

                } else {

                    actionButton.setText("Unblock");

                }

                setGraphic(actionButton);

            }

        });

        table.getColumns().addAll(
                idCol,
                usernameCol,
                roleCol,
                statusCol,
                actionCol
        );

        try {

            List<UserResponse> users =
                    adminService.getAllUsers();

            table.setItems(
                    FXCollections.observableArrayList(
                            users
                    )
            );

        } catch (ApiException e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(
                    e.getMessage());

            alert.show();

        } catch (IOException |
                 InterruptedException e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(
                    "خطا در ارتباط با سرور");

            alert.show();

        }

        BorderPane pane =
                new BorderPane();

        Label title =
                new Label("مدیریت کاربران");

        title.setStyle(
                "-fx-font-size:18px;" +
                        "-fx-font-weight:bold;"
        );

        BorderPane.setMargin(
                title,
                new Insets(15)
        );

        pane.setTop(title);
        pane.setCenter(table);

        contentPane.getChildren().setAll(
                pane
        );
    }
    @FXML
    public void showBlockedUsers() {

        TableView<UserResponse> table = new TableView<>();

        TableColumn<UserResponse, String> idCol =
                new TableColumn<>("ID");

        idCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(data.getValue().getId())
                ));

        TableColumn<UserResponse, String> usernameCol =
                new TableColumn<>("Username");

        usernameCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getUsername()
                ));

        TableColumn<UserResponse, String> roleCol =
                new TableColumn<>("Role");

        roleCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getRole()
                ));

        TableColumn<UserResponse, Void> actionCol =
                new TableColumn<>("Operation");

        actionCol.setCellFactory(column -> new TableCell<>() {

            private final Button button =
                    new Button("Unblock");

            {

                button.setOnAction(event -> {

                    UserResponse user =
                            getTableView().getItems().get(getIndex());

                    try {

                        adminService.unblockUser(user.getId());

                        showBlockedUsers();

                    } catch (Exception e) {

                        Alert alert =
                                new Alert(Alert.AlertType.ERROR);

                        alert.setContentText(e.getMessage());

                        alert.show();
                    }

                });

            }

            @Override
            protected void updateItem(Void item,
                                      boolean empty) {

                super.updateItem(item, empty);

                if (empty) {

                    setGraphic(null);

                } else {

                    setGraphic(button);

                }

            }

        });

        table.getColumns().addAll(
                idCol,
                usernameCol,
                roleCol,
                actionCol
        );

        try {

            List<UserResponse> users =
                    adminService.getAllUsers();

            table.setItems(
                    FXCollections.observableArrayList(

                            users.stream()

                                    .filter(user ->
                                            "BLOCKED".equalsIgnoreCase(
                                                    user.getStatus()
                                            ))

                                    .toList()

                    )
            );

        } catch (Exception e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(e.getMessage());

            alert.show();

        }

        BorderPane pane =
                new BorderPane();

        Label title =
                new Label("کاربران بلاک شده");

        title.setStyle(
                "-fx-font-size:18px;-fx-font-weight:bold;"
        );

        BorderPane.setMargin(
                title,
                new Insets(15)
        );

        pane.setTop(title);
        pane.setCenter(table);

        contentPane.getChildren().setAll(pane);
    }

    @FXML
    public void logout() {

        SessionManager.logout();

        SceneManager.loadScene(
                Constants.LOGIN,
                "ورود"
        );
    }
    @FXML
    public void showAdvertisements() {

        TableView<AdvertisementResponse> table = new TableView<>();

        TableColumn<AdvertisementResponse, String> idCol =
                new TableColumn<>("ID");

        idCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(data.getValue().getId())
                ));

        idCol.setPrefWidth(70);

        TableColumn<AdvertisementResponse, String> titleCol =
                new TableColumn<>("عنوان");

        titleCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getTitle()
                ));

        titleCol.setPrefWidth(180);

        TableColumn<AdvertisementResponse, String> ownerCol =
                new TableColumn<>("مالک");

        ownerCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getOwnerName()
                ));

        ownerCol.setPrefWidth(150);

        TableColumn<AdvertisementResponse, String> cityCol =
                new TableColumn<>("شهر");

        cityCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getCity()
                ));

        cityCol.setPrefWidth(120);

        TableColumn<AdvertisementResponse, String> categoryCol =
                new TableColumn<>("دسته");

        categoryCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getCategory()
                ));

        categoryCol.setPrefWidth(120);

        TableColumn<AdvertisementResponse, String> statusCol =
                new TableColumn<>("وضعیت");

        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getStatus()
                ));

        statusCol.setPrefWidth(120);

        TableColumn<AdvertisementResponse, Void> actionCol =
                new TableColumn<>("عملیات");

        actionCol.setPrefWidth(180);

        actionCol.setCellFactory(column -> new TableCell<>() {

            private final ComboBox<String> combo =
                    new ComboBox<>();

            {

                combo.getItems().addAll(
                        "Approve",
                        "Reject",
                        "Delete"
                );

                combo.setPromptText("انتخاب");

                combo.setOnAction(event -> {

                    AdvertisementResponse ad =
                            getTableView().getItems().get(getIndex());

                    String action =
                            combo.getValue();

                    if(action == null)
                        return;

                    try {

                        switch (action){

                            case "Approve" ->
                                    adminService.approveAdvertisement(ad.getId());

                            case "Reject" ->
                                    adminService.rejectAdvertisement(ad.getId());

                            case "Delete" ->
                                    adminService.deleteAdvertisement(ad.getId());

                        }

                        showAdvertisements();

                    } catch (ApiException e){

                        Alert alert =
                                new Alert(Alert.AlertType.ERROR);

                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());

                        alert.show();

                    } catch (IOException | InterruptedException e){

                        Alert alert =
                                new Alert(Alert.AlertType.ERROR);

                        alert.setHeaderText(null);
                        alert.setContentText("خطا در ارتباط با سرور");

                        alert.show();
                    }

                });

            }

            @Override
            protected void updateItem(Void item,
                                      boolean empty) {

                super.updateItem(item, empty);

                if(empty){

                    setGraphic(null);

                }else{

                    combo.getSelectionModel().clearSelection();

                    setGraphic(combo);

                }

            }

        });

        table.getColumns().addAll(
                idCol,
                titleCol,
                ownerCol,
                cityCol,
                categoryCol,
                statusCol,
                actionCol
        );

        try {

            List<AdvertisementResponse> advertisements =
                    adminService.getAllAdvertisements();

            table.setItems(
                    FXCollections.observableArrayList(
                            advertisements
                    )
            );

        } catch (ApiException e){

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());

            alert.show();

        } catch (IOException | InterruptedException e){

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);
            alert.setContentText("خطا در ارتباط با سرور");

            alert.show();
        }

        BorderPane pane = new BorderPane();

        Label title = new Label("مدیریت آگهی‌ها");

        title.setStyle("-fx-font-size:18px;" + "-fx-font-weight:bold;");

        BorderPane.setMargin(title, new Insets(15));

        pane.setTop(title);
        pane.setCenter(table);

        contentPane.getChildren().setAll(pane);
    }}