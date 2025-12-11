package myapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TodoController {

    @FXML
    private TextField inputField;

    @FXML
    private ListView<String> todoList;

    @FXML
    protected void onAddClick() {
        String text = inputField.getText().trim();

        if (!text.isEmpty()) {
            todoList.getItems().add(text);
            inputField.clear();
        }
    }

    @FXML
    public void initialize() {
        // Кастомные ячейки — каждая задача отображается с CheckBox и кнопкой удаления
        todoList.setCellFactory(list -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();
            private final Button deleteButton = new Button("×");
            private final Region spacer = new Region(); // Разделитель для выравнивания
            private final HBox hbox = new HBox(5, checkBox, spacer, deleteButton);

            {
                // Настройка отображения
                HBox.setHgrow(checkBox, Priority.ALWAYS);
                HBox.setHgrow(spacer, Priority.ALWAYS); // Заставляем разделитель занимать всё пространство

                // Добавляем CSS классы
                deleteButton.getStyleClass().add("delete-button");
                checkBox.getStyleClass().add("todo-checkbox");
                hbox.getStyleClass().add("todo-item-container");

                // Обработчик удаления
                deleteButton.setOnAction(event -> {
                    if (getItem() != null) {
                        int index = getIndex();
                        todoList.getItems().remove(index);
                    }
                });

                // Обработчик CheckBox
                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        checkBox.getStyleClass().add("completed");
                    } else {
                        checkBox.getStyleClass().remove("completed");
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    // Сбрасываем состояние CheckBox
                    checkBox.setSelected(false);
                    checkBox.getStyleClass().remove("completed");
                } else {
                    checkBox.setText(item);
                    // Сбрасываем состояние чекбокса для новой ячейки
                    checkBox.setSelected(false);
                    checkBox.getStyleClass().remove("completed");

                    setGraphic(hbox);
                    setText(null);
                }
            }
        });
    }
}