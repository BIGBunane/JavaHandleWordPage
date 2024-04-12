package com.Liam;

import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FxDemoApp extends Application {
    private String sourcePath = "";
    TextArea textArea=null;
    @Override
    public void start(Stage stage) throws Exception {
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        //icon
//        InputStream fis = FxDemoApp.class.getClassLoader().getResourceAsStream("icon-3.gif");
        FileInputStream fis = new FileInputStream("icon-3.gif");
        Image image = new Image(fis);
        ImageView imageView = new ImageView(image);
        HBox hBoxIcon = new HBox(imageView);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        //top
        //open-btn
        Button btn_open = new Button("open");
        //open event
        DirectoryChooser chooser = new DirectoryChooser();
        btn_open.setOnAction(e->{
            chooser.setTitle("choose a directory");
            File file = chooser.showDialog(stage);
            if (file!=null){
                sourcePath = file.getAbsolutePath();
            }
        });

        //run=btn
        Button btn_run = new Button("run");
        btn_run.setOnAction(e->{
            if ("".equals(sourcePath)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("path is null");
                alert.setContentText("Please click 'open' button to choose work path");
                alert.setHeaderText("Path is null");
                alert.showAndWait();
            }else{
                //start replacing !!!
                AsposeDemo asposeDemo = new AsposeDemo();
                try {
                    textArea.appendText("---------------Start Replacing---------------\r\n");
                    asposeDemo.replaceFacePage(sourcePath);
                    for(String s:AsposeDemo.infoMsg){
                        textArea.appendText("[INFO]:"+s+"\r\n");
                    }
                    textArea.appendText("---------------Replacing End---------------\r\n");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        hBox.getChildren().addAll(hBoxIcon,btn_open,btn_run);
        hBox.setAlignment(Pos.CENTER);

        //textarea
        HBox hBoxTextArea = new HBox();
        hBoxTextArea.setAlignment(Pos.CENTER);
        textArea = new TextArea("Face-Page Replacing Tool:\r\n");
        hBoxTextArea.getChildren().add(textArea);

        //boderpanel setting
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(hBoxTextArea);

        //scene setting
        Scene scene = new Scene(borderPane,400,400);
        stage.setScene(scene);
        stage.show();
    }

}
