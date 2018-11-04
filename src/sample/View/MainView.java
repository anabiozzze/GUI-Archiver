package sample.View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Controller.Controller;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainView extends Application {
    /* Класс отвечвет за все элементы интерфейса программы и их расположение
    *  Класс не обращается напрямую к классам архивации, делая это через конроллер */

    private Stage stage;
    private Group group;
    private Scene scene;
    BorderPane pane;
    VBox vBox;
    private String initialFile;
    private TextField textField;
    private HBox hb;

    public MainView() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        // задаем размер и цвет окна и основной области, на которой будем располагать элементы
        group = new Group();
        scene = new Scene(group,600, 200);
        pane = new BorderPane();
        pane.setMinSize(600, 200);
        pane.setStyle("-fx-background-color: lightgrey");

        // привяжем размеры основной области к размерам окна программы, чтобы все масштабировалось вместе
        pane.prefHeightProperty().bind(scene.heightProperty());
        pane.prefWidthProperty().bind(scene.widthProperty());

        // пока отключим масшитабирование окна программы
        primaryStage.setResizable(false);

        // присваиваем окну заголовок и запускаем
        primaryStage.setTitle("Archiver MegaJet 3000");
        primaryStage.setScene(scene);
        primaryStage.show();

        // создаем необходимые элементы и добавляем их в видимую область
        showElements();

        // добавляем основную область в группу элементов окна
        group.getChildren().add(pane);

    }

    // метод открывает окно выбора файла для архивации \ разархивации
    private void startChooser() {

        // выбираем файл во всплывающем окне и сразу сохраняем его путь
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Укажите файл для архивации или разархивации");
        initialFile = fileChooser.showOpenDialog(stage).getPath();

        // вместо пустого текстового поля под окном выбора файла создаем новое поле с прописанным путем к файлу
        hb.getChildren().remove(textField);
        textField = new TextField(initialFile); textField.setMinSize(400, 10);
        textField.setStyle("-fx-text-inner-color: lightgrey");
        hb.getChildren().add(textField);

    }

    private void showElements() {

        vBox = new VBox();
        showMenu();

        // по клику кнопки открываем окно выбора файла
        Button button = new Button("Выберите файл");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startChooser();
            }
        });

        // пустое текстовое поле, перед выбором файла пользователем
        textField = new TextField(); textField.setMinSize(400, 10);

        // задаем последовательность "кнопка - поле" с определенным промежутком
        hb = new HBox();
        hb.getChildren().addAll(button, textField);
        hb.setSpacing(10);

        // задаем отступ для элементов основной области, чтобы не прижимались к краям окна
        BorderPane.setMargin(hb, new Insets(40, 10, 10, 10));
        vBox.getChildren().add(hb); vBox.setSpacing(5);
        BorderPane.setMargin(vBox, new Insets(0, 0, 10, 10));
        pane.setTop(vBox);

        // поле с большим комментарием, некликабельное - создаем и добавляем в основную область
        TextArea area = new TextArea("Пожалуйста, выберите нужный файл, директорию или архив. " +
                "Затем нажмите кнопку \"Создать архив\" или \"Извлечь файлы\" - " +
                "в зависимости от того, что вам требуется. Результат операции появится в той же" +
                " директории, что и выбранный файл.");
        area.setDisable(true);
        area.setMinSize(527, 80);
        area.setMaxSize(527, 80);
        area.setStyle("-fx-background-color: lightgrey");

        // разместим текстовое поле слева, настроим отступы и автоперенос текста
        BorderPane.setMargin(area, new Insets(5, 10, 5, 10));
        pane.setLeft(area); area.setWrapText(true);

        // две ключевые кнопки - сперва создадим, затем объединим в последовательность с промежутком
        Button button1 = new Button("Создать архив");
        Button button2 = new Button("Извлечь файлы");

        HBox hBox = new HBox(); hBox.setSpacing(10);
        hBox.getChildren().addAll(button1, button2);

        // зададим действия нашим основным кнопкам
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // сперва выполним создание архива из выбранного файла
                Controller.getZip(initialFile);

                // теперь выведем сообщение об успешном завершении операции
                hBox.getChildren().remove(2, hBox.getChildren().size());
                Text text = new Text("Готово!");
                text.setStyle("-fx-font-size: 22"); text.setFill(Color.GRAY);
                hBox.getChildren().add(text);
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // сперва извлечём файлы из выбранного архива
                Controller.getUnZip(initialFile);

                // теперь выведем сообщение об успешном завершении операции
                hBox.getChildren().remove(2, hBox.getChildren().size());
                Text text = new Text("Готово!");
                text.setStyle("-fx-font-size: 22"); text.setFill(Color.GRAY);
                hBox.getChildren().add(text);
            }
        });

        // разместим наши кнопки снизу, с равномерным отступом от края и добавим всё это в основное поле
        BorderPane.setMargin(hBox, new Insets(10, 10, 10, 10));
        pane.setBottom(hBox);

    }

    public void showMenu() {
        MenuBar bar1 = new MenuBar();
        MenuBar bar2 = new MenuBar();
        MenuBar bar3 = new MenuBar();

        Menu donate = new Menu("Donate");
        Menu help = new Menu("Help");
        Menu menu = new Menu("");

        MenuItem donateItem = new MenuItem("take my money");
        MenuItem about = new MenuItem("about program");
        MenuItem trouble = new MenuItem("help");

        donate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop d = Desktop.getDesktop();
                    d.browse(new URI("https://www.redcross.org/donate/donation.html/"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Group root = new Group();
                Stage stage = new Stage();
                stage.setTitle("about program");

                TextArea area = new TextArea("Программа не является полноценным продуктом и едва ли будет запущена" +
                        " кем-то, кроме её разработчика. Цель программы - отработать применение графического " +
                        "интерфейса на практике. \n\n" +
                        "Тем не менее, программа действительно должна корректно архивировать и распаковывать файлы.");

                area.setWrapText(true);
                area.setDisable(true);
                area.setMinSize(400, 150);
                area.setMaxSize(400, 150);
                area.setStyle("-fx-background-color: lightgrey");

                root.getChildren().add(area);
                root.setStyle("-fx-background-color: lightgrey");

                stage.setScene(new Scene(root, 400, 150));
                stage.setResizable(false);
                stage.show();

            }
        });

        trouble.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Group root = new Group();
                Stage stage = new Stage();
                stage.setTitle("help");

                TextArea area = new TextArea("При возникновении сложностей в работе: \n" +
                        "1. Выключите программу; \n" +
                        "2. Повторно включите программу; \n" +
                        "3. Всё должно работать.");

                area.setDisable(true);
                area.setMinSize(300, 150);
                area.setMaxSize(300, 150);
                area.setStyle("-fx-background-color: lightgrey");

                root.getChildren().add(area);
                root.setStyle("-fx-background-color: lightgrey");

                stage.setScene(new Scene(root, 300, 150));
                stage.setResizable(false);
                stage.show();

            }
        });

        donate.getItems().add(donateItem);
        help.getItems().addAll(about, trouble);

        bar1.getMenus().add(donate); bar1.setStyle("-fx-background-color: gainsboro");
        bar2.getMenus().add(help); bar2.setStyle("-fx-background-color: gainsboro");
        bar3.getMenus().add(menu); bar3.setStyle("-fx-background-color: gainsboro");

        bar3.setMinSize(444, bar1.getHeight());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(bar3, bar1, bar2);
        hBox.setAlignment(Pos.BASELINE_RIGHT);

        vBox.getChildren().add(hBox);


    }

    public static void main(String[] args) {
        launch(args);
    }

}
