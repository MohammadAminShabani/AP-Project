package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.network.ApiException;
import divar.service.AdminService;
import divar.session.SessionManager;
import divar.util.Constants;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

        String username = SessionManager.getUsername();

        if (username == null || username.isBlank()) {
            username = "مدیر";
        }

        adminLabel.setText("خوش آمدید " + username);

        showUsers();
    }

    // =========================================================
    // USERS
    // =========================================================

    @FXML
    public void showUsers() {

        TableView<UserResponse> table = new TableView<>();

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        table.setPlaceholder(
                new Label("کاربری برای نمایش وجود ندارد")
        );

        TableColumn<UserResponse, String> idCol =
                new TableColumn<>("شناسه");

        idCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(data.getValue().getId())
                )
        );

        TableColumn<UserResponse, String> usernameCol =
                new TableColumn<>("نام کاربری");

        usernameCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(data.getValue().getUsername())
                )
        );

        TableColumn<UserResponse, String> roleCol =
                new TableColumn<>("نقش");

        roleCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(data.getValue().getRole())
                )
        );

        TableColumn<UserResponse, String> statusCol =
                new TableColumn<>("وضعیت");

        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(data.getValue().getStatus())
                )
        );

        TableColumn<UserResponse, Void> actionCol =
                new TableColumn<>("عملیات");

        actionCol.setCellFactory(column ->
                new TableCell<>() {

                    private final Button actionButton =
                            new Button();

                    {
                        actionButton.setOnAction(event -> {

                            UserResponse user =
                                    getTableView()
                                            .getItems()
                                            .get(getIndex());

                            if (user == null) {
                                return;
                            }

                            try {

                                if (isActive(user)) {

                                    adminService.blockUser(
                                            user.getId()
                                    );

                                } else {

                                    adminService.unblockUser(
                                            user.getId()
                                    );
                                }

                                showUsers();

                            } catch (ApiException e) {

                                showError(
                                        "خطا",
                                        e.getMessage()
                                );

                            } catch (
                                    IOException |
                                    InterruptedException e
                            ) {

                                showServerError();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(
                            Void item,
                            boolean empty
                    ) {

                        super.updateItem(item, empty);

                        if (empty) {

                            setGraphic(null);

                            return;
                        }

                        UserResponse user =
                                getTableView()
                                        .getItems()
                                        .get(getIndex());

                        if (user == null) {

                            setGraphic(null);

                            return;
                        }

                        if (isActive(user)) {

                            actionButton.setText(
                                    "مسدود کردن"
                            );

                        } else {

                            actionButton.setText(
                                    "فعال‌سازی"
                            );
                        }

                        setGraphic(actionButton);
                    }
                }
        );

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

            showError(
                    "خطا در دریافت کاربران",
                    e.getMessage()
            );

        } catch (
                IOException |
                InterruptedException e
        ) {

            showServerError();
        }

        BorderPane pane =
                createBasePane(
                        "مدیریت کاربران"
                );

        pane.setCenter(table);

        contentPane.getChildren().setAll(
                pane
        );
    }

    // =========================================================
    // BLOCKED USERS
    // =========================================================

    @FXML
    public void showBlockedUsers() {

        TableView<UserResponse> table =
                new TableView<>();

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        table.setPlaceholder(
                new Label(
                        "کاربر مسدودشده‌ای وجود ندارد"
                )
        );

        TableColumn<UserResponse, String> idCol =
                new TableColumn<>("شناسه");

        idCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(
                                data.getValue().getId()
                        )
                )
        );

        TableColumn<UserResponse, String> usernameCol =
                new TableColumn<>("نام کاربری");

        usernameCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getUsername()
                        )
                )
        );

        TableColumn<UserResponse, String> roleCol =
                new TableColumn<>("نقش");

        roleCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getRole()
                        )
                )
        );

        TableColumn<UserResponse, String> statusCol =
                new TableColumn<>("وضعیت");

        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getStatus()
                        )
                )
        );

        TableColumn<UserResponse, Void> actionCol =
                new TableColumn<>("عملیات");

        actionCol.setCellFactory(column ->
                new TableCell<>() {

                    private final Button button =
                            new Button(
                                    "فعال‌سازی"
                            );

                    {
                        button.setOnAction(event -> {

                            UserResponse user =
                                    getTableView()
                                            .getItems()
                                            .get(getIndex());

                            if (user == null) {
                                return;
                            }

                            try {

                                adminService.unblockUser(
                                        user.getId()
                                );

                                showBlockedUsers();

                            } catch (ApiException e) {

                                showError(
                                        "خطا",
                                        e.getMessage()
                                );

                            } catch (
                                    IOException |
                                    InterruptedException e
                            ) {

                                showServerError();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(
                            Void item,
                            boolean empty
                    ) {

                        super.updateItem(item, empty);

                        setGraphic(
                                empty
                                        ? null
                                        : button
                        );
                    }
                }
        );

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

            List<UserResponse> blockedUsers =
                    users.stream()
                            .filter(user ->
                                    "BLOCKED".equalsIgnoreCase(
                                            user.getStatus()
                                    )
                            )
                            .toList();

            table.setItems(
                    FXCollections.observableArrayList(
                            blockedUsers
                    )
            );

        } catch (ApiException e) {

            showError(
                    "خطا در دریافت کاربران",
                    e.getMessage()
            );

        } catch (
                IOException |
                InterruptedException e
        ) {

            showServerError();
        }

        BorderPane pane =
                createBasePane(
                        "کاربران مسدودشده"
                );

        pane.setCenter(table);

        contentPane.getChildren().setAll(
                pane
        );
    }

    // =========================================================
    // ADVERTISEMENTS
    // =========================================================

    @FXML
    public void showAdvertisements() {

        TableView<AdvertisementResponse> table =
                new TableView<>();

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        table.setPlaceholder(
                new Label(
                        "آگهی‌ای برای نمایش وجود ندارد"
                )
        );

        TableColumn<AdvertisementResponse, String> idCol =
                new TableColumn<>("شناسه");

        idCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(
                                data.getValue().getId()
                        )
                )
        );

        TableColumn<AdvertisementResponse, String> titleCol =
                new TableColumn<>("عنوان");

        titleCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getTitle()
                        )
                )
        );

        TableColumn<AdvertisementResponse, String> ownerCol =
                new TableColumn<>("مالک");

        ownerCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getOwnerName()
                        )
                )
        );

        TableColumn<AdvertisementResponse, String> cityCol =
                new TableColumn<>("شهر");

        cityCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getCity()
                        )
                )
        );

        TableColumn<AdvertisementResponse, String> categoryCol =
                new TableColumn<>("دسته‌بندی");

        categoryCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getCategory()
                        )
                )
        );

        TableColumn<AdvertisementResponse, String> statusCol =
                new TableColumn<>("وضعیت");

        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        safe(
                                data.getValue()
                                        .getStatus()
                        )
                )
        );

        TableColumn<AdvertisementResponse, Void> actionCol =
                new TableColumn<>("عملیات");

        actionCol.setCellFactory(column ->
                new TableCell<>() {

                    private final ComboBox<String> combo =
                            new ComboBox<>();

                    {
                        combo.getItems().addAll(
                                "تأیید آگهی",
                                "رد آگهی",
                                "حذف آگهی"
                        );

                        combo.setPromptText(
                                "انتخاب عملیات"
                        );

                        combo.setOnAction(event -> {

                            AdvertisementResponse ad =
                                    getTableView()
                                            .getItems()
                                            .get(getIndex());

                            if (ad == null) {
                                return;
                            }

                            String selected =
                                    combo.getValue();

                            if (selected == null) {
                                return;
                            }

                            try {

                                switch (selected) {

                                    case "تأیید آگهی" ->

                                            adminService
                                                    .approveAdvertisement(
                                                            ad.getId()
                                                    );

                                    case "رد آگهی" ->

                                            adminService
                                                    .rejectAdvertisement(
                                                            ad.getId()
                                                    );

                                    case "حذف آگهی" -> {

                                        if (
                                                confirmDelete()
                                        ) {

                                            adminService
                                                    .deleteAdvertisement(
                                                            ad.getId()
                                                    );
                                        }
                                    }
                                }

                                showAdvertisements();

                            } catch (ApiException e) {

                                showError(
                                        "خطا در مدیریت آگهی",
                                        e.getMessage()
                                );

                            } catch (
                                    IOException |
                                    InterruptedException e
                            ) {

                                showServerError();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(
                            Void item,
                            boolean empty
                    ) {

                        super.updateItem(item, empty);

                        if (empty) {

                            setGraphic(null);

                            return;
                        }

                        combo.getSelectionModel()
                                .clearSelection();

                        setGraphic(combo);
                    }
                }
        );

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
                    adminService
                            .getAllAdvertisements();

            table.setItems(
                    FXCollections.observableArrayList(
                            advertisements
                    )
            );

        } catch (ApiException e) {

            showError(
                    "خطا در دریافت آگهی‌ها",
                    e.getMessage()
            );

        } catch (
                IOException |
                InterruptedException e
        ) {

            showServerError();
        }

        BorderPane pane =
                createBasePane(
                        "مدیریت آگهی‌ها"
                );

        pane.setCenter(table);

        contentPane.getChildren().setAll(
                pane
        );
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    @FXML
    public void logout() {

        SessionManager.logout();

        SceneManager.loadScene(
                Constants.LOGIN,
                "ورود"
        );
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private BorderPane createBasePane(
            String titleText
    ) {

        BorderPane pane =
                new BorderPane();

        Label title =
                new Label(titleText);

        title.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
        );

        BorderPane.setMargin(
                title,
                new Insets(15)
        );

        pane.setTop(title);

        return pane;
    }

    private boolean isActive(
            UserResponse user
    ) {

        return user.getStatus() != null &&
                user.getStatus()
                        .equalsIgnoreCase(
                                "ACTIVE"
                        );
    }

    private String safe(
            String value
    ) {

        return value == null
                ? "-"
                : value;
    }

    private void showError(
            String header,
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle("خطا");
        alert.setHeaderText(header);
        alert.setContentText(
                message == null
                        ? "خطای نامشخص"
                        : message
        );

        alert.showAndWait();
    }

    private void showServerError() {

        showError(
                "خطا در ارتباط با سرور",
                "ارتباط با Backend برقرار نشد."
        );
    }

    private boolean confirmDelete() {

        Alert alert =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        alert.setTitle("تأیید حذف");
        alert.setHeaderText(
                "حذف آگهی"
        );

        alert.setContentText(
                "آیا از حذف این آگهی مطمئن هستید؟"
        );

        return alert.showAndWait()
                .filter(
                        ButtonType.OK::equals
                )
                .isPresent();
    }
}