package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Optional;

    /**
     */
    public class OfficeHourQueue extends Application {

        private Label currQueueSize;

        private Label maxQueueSize;

        private ListView<String> listView;

        private static final String PASSWORD = "CS1321R0X";

        private LinkedQueue<String> linkedQueue;

        private int total = 0;

        public static void main(String[] args){
            launch(args);
        }

        private void setCurrentSizeText(int size){
            currQueueSize.setText("Current Number of Students in the Queue: " + size);
        }

        private void setMaxSizeText(int size){
            maxQueueSize.setText("Max Number of Students in the Queue: " + size);
        }

        private void updateUI(){
            listView.getItems().clear();

            Iterator<String> iterator = linkedQueue.iterator();
            while (iterator.hasNext()){
                listView.getItems().add(iterator.next());
            }

            setCurrentSizeText(linkedQueue.size());
            setMaxSizeText(total);

        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            BorderPane root = new BorderPane();

            linkedQueue = new LinkedQueue<>();

            currQueueSize = new Label();
            maxQueueSize = new Label();

            setCurrentSizeText(0);
            setMaxSizeText(0);

            VBox header = new VBox(4, currQueueSize, maxQueueSize);
            root.setTop(header);

            listView = new ListView<>();
            listView.setOrientation(Orientation.VERTICAL);

            root.setCenter(listView);

            TextField nameField = new TextField();
            Button btnEnqueue = new Button("Add Student");
            Button btnDequeue = new Button("Dequeue Student");

            btnEnqueue.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(nameField.getText() != null && !nameField.getText().trim().isEmpty()) {
                        try {
                            linkedQueue.enqueue(nameField.getText());
                            total++;
                            updateUI();
                            nameField.setText("");
                        }catch (Exception ex){

                        }
                    }
                }
            });

            btnDequeue.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Privilege Check");
                    dialog.setHeaderText("Confirmation");
                    dialog.setContentText("Please Enter Password:");

                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        if(PASSWORD.equals(result.get())){
                            linkedQueue.dequeue();
                            updateUI();
                        }
                    }
                }
            });

            HBox bottomView = new HBox(4, nameField, btnEnqueue, btnDequeue);

            root.setBottom(bottomView);

            Scene scene = new Scene(root, 400, 400);
            primaryStage.setTitle("CS 1321 Office Hours Queue");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

