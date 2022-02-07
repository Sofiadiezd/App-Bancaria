package com.company;


import com.company.entity.Extracto;
import com.company.entity.Usuario;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {
    Label l1, l2, l3, l4, l5;
    TextField tl, tl2;
    Button bt, bt2;
    ArrayList<Usuario> usuarios = new ArrayList<>();
    ArrayList<Extracto> extractosL = new ArrayList<>();
    Image image;
    Image image2 = new Image(getClass().getResourceAsStream("image/logomenu.jpg"));
    ImageView imageView, im2, im3, im4, im5, im6;
    ImageView wP = new ImageView(image2);
    Usuario user = new Usuario();
    VBox vb;
    HBox hb, hb2;
    Double dinero, dineroE;
    String operacion;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        usuarios.add(new Usuario("Sofia", 666, 666666));
        usuarios.add(new Usuario("Jon", 777, 100000));
        usuarios.add(new Usuario("Javi", 888, 0));

        image = new Image(getClass().getResourceAsStream("image/logo.jpg"));
        imageView = new ImageView(image);
        imageView.setFitHeight(800);
        imageView.setFitWidth(1200);

        l1 = new Label("User: ");
        l1.setTextFill(Color.WHITE);
        tl = new TextField();
        l2 = new Label("Password: ");
        l2.setTextFill(Color.WHITE);
        tl2 = new TextField();
        bt = new Button("Log In");


        VBox v1 = new VBox(l1, tl, l2, tl2, bt);
        v1.setPadding(new Insets(0, 10, 0, 10));
        v1.setSpacing(10);
        HBox h = new HBox(v1);
        v1.setAlignment(Pos.CENTER);

        StackPane sp = new StackPane(imageView, h);
        Scene scene = new Scene(sp);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("image/icon.jpg")));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(scene);
        primaryStage.show();

        bt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Usuario u : usuarios) {
                    if (u.getUser().equalsIgnoreCase(tl.getText()) && u.getPass() == Integer.parseInt(tl2.getText())) {
                        user = u;
                        System.out.println("Todo bien: " + user.getUser());
                        menuM(primaryStage);
                    }
                }
            }
        });

    }

    private void menuM(Stage stage) {
        wP.setFitHeight(1000);
        wP.setFitWidth(1300);

        //INGRESO
        im2 = new ImageView(new Image(getClass().getResourceAsStream("image/ingreso.png")));
        im2.setFitHeight(150);
        im2.setFitWidth(150);
        im2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ingresar(stage);
            }
        });

        //RETIRO
        im3 = new ImageView(new Image(getClass().getResourceAsStream("image/retiro.png")));
        im3.setFitHeight(150);
        im3.setFitWidth(150);
        im3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                retiro(stage);
            }
        });

        //TRANSFERENCIA
        im4 = new ImageView(new Image(getClass().getResourceAsStream("image/transferencia.png")));
        im4.setFitHeight(150);
        im4.setFitWidth(150);
        im4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transferencia(stage);
            }
        });

        //SALDO
        im5 = new ImageView(new Image(getClass().getResourceAsStream("image/saldo.png")));
        im5.setFitHeight(150);
        im5.setFitWidth(150);
        im5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saldo(stage);
            }
        });

        //ESTADISTICAS
        im6 = new ImageView(new Image(getClass().getResourceAsStream("image/estadisticas.png")));
        im6.setFitHeight(150);
        im6.setFitWidth(150);

        //Button
        bt = new Button("Log Out");
        bt.setAlignment(Pos.BOTTOM_CENTER);
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Hasta luego: " + user.getUser());
                    start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //HORIZONTAL
        HBox hb = new HBox(im2, im3, im4);
        hb.setSpacing(100);
        hb.setAlignment(Pos.CENTER);
        HBox hb2 = new HBox(im5, im6);
        hb2.setSpacing(100);
        hb2.setAlignment(Pos.CENTER);

        //VERTICAL
        vb = new VBox(hb, hb2, bt);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(100);

        //ESCENE
        StackPane sp = new StackPane(wP, vb);
        Scene scene = new Scene(sp);
        stage.setTitle("Menú Principal");
        stage.setScene(scene);
        stage.show();

    }

    private void saldo(Stage stage) {
        wP.setFitHeight(600);
        wP.setFitWidth(700);


        //ESCENE
        StackPane sp = new StackPane();
        Scene scene = new Scene(sp);
        stage.setTitle("Transferencia");
        stage.setScene(scene);
        stage.show();
    }

    private void transferencia(Stage stage) {
        wP.setFitHeight(600);
        wP.setFitWidth(700);

        l1 = new Label("Cantidad a Transferir: ");
        formatFont(l1);
        tl = new TextField();
        tl.setMaxWidth(200);
        tl.setMaxHeight(200);

        l2 = new Label("Usuario a Transferir: ");
        formatFont(l2);
        tl2 = new TextField();
        tl2.setMaxWidth(200);
        tl2.setMaxHeight(200);

        //ACEPTAR
        bt = new Button("Aceptar");
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dinero = Double.parseDouble(tl.getText());
                dineroE = user.getSaldo();
                user.setSaldo(dineroE - dinero);
                System.out.println("Saldo transferido: " + dinero);
                System.out.println("Saldo actual: " + user.getSaldo());
                operacion = "TRANSFERENCIA ENVIADA";
                guardarExtracto(operacion, dinero);

                for (Usuario u : usuarios) {
                    if (u.getUser().equalsIgnoreCase(tl2.getText())) {
                        dineroE = u.getSaldo();
                        u.setSaldo(dineroE + dinero);
                        System.out.println("Saldo recibido: " + dinero);
                        System.out.println("Saldo actual: " + u.getSaldo());
                        for (int i = 0; i < u.getExtractos().size(); i++) {
                            u.getExtractos().get(i).setClave("TRANSFERENCIA RECIBIDA");
                            u.getExtractos().get(i).setCantidad(dinero);
                        }
                        System.out.println(u.getExtractos().toString());
                    }
                }

                menuM(stage);
            }
        });

        //CANCELAR
        bt2 = new Button("Cancelar");
        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuM(stage);
            }
        });

        hb = new HBox(bt, bt2);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(50);
        vb = new VBox(l1, tl, l2, tl2, hb);
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);

        //ESCENE
        StackPane sp = new StackPane(wP, vb);
        Scene scene = new Scene(sp);
        stage.setTitle("Transferencia");
        stage.setScene(scene);
        stage.show();
    }

    private void retiro(Stage stage) {
        wP.setFitHeight(500);
        wP.setFitWidth(700);
        l1 = new Label("Cantidad a Retirar: ");
        formatFont(l1);
        tl = new TextField();
        tl.setMaxWidth(200);
        tl.setMaxHeight(200);

        //ACEPTAR
        bt = new Button("Aceptar");
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dinero = Double.parseDouble(tl.getText());
                dineroE = user.getSaldo();
                operacion = "RETIRO";
                user.setSaldo(dineroE - dinero);
                System.out.println("Saldo retirado: " + dinero);
                System.out.println("Saldo actual: " + user.getSaldo());

                guardarExtracto(operacion, dinero);
                menuM(stage);
            }
        });

        //CANCELAR
        bt2 = new Button("Cancelar");
        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuM(stage);
            }
        });

        hb = new HBox(bt, bt2);
        hb.setSpacing(50);
        hb.setAlignment(Pos.CENTER);
        vb = new VBox(l1, tl, hb);
        vb.setSpacing(50);
        vb.setAlignment(Pos.CENTER);

        //ESCENE
        StackPane sp = new StackPane(wP, vb);
        Scene scene = new Scene(sp);
        stage.setTitle("Ingreso");
        stage.setScene(scene);
        stage.show();


    }

    private void guardarExtracto(String operacion, Double dinero) {
        extractosL.add(new Extracto(operacion, dinero));
        user.setExtractos(extractosL);
        for (Extracto e : user.getExtractos()) {
            System.out.println(e.toString());
        }
    }

    private void ingresar(Stage stage) {
        wP.setFitHeight(500);
        wP.setFitWidth(700);
        l1 = new Label("Cantidad a Ingresar: ");
        formatFont(l1);
        tl = new TextField();
        tl.setMaxWidth(200);
        tl.setMaxHeight(200);

        //ACEPTAR
        bt = new Button("Aceptar");
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dinero = Double.parseDouble(tl.getText());
                dineroE = user.getSaldo();
                user.setSaldo(dineroE + dinero);
                System.out.println("Saldo ingresado: " + dinero);
                System.out.println("Saldo actual: " + user.getSaldo());
               operacion = "INGRESO";
               guardarExtracto(operacion,dinero);
                menuM(stage);
            }
        });

        //CANCELAR
        bt2 = new Button("Cancelar");
        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuM(stage);
            }
        });

        hb = new HBox(bt, bt2);
        hb.setSpacing(50);
        hb.setAlignment(Pos.CENTER);
        vb = new VBox(l1, tl, hb);
        vb.setSpacing(50);
        vb.setAlignment(Pos.CENTER);

        //ESCENE
        StackPane sp = new StackPane(wP, vb);
        Scene scene = new Scene(sp);
        stage.setTitle("Ingreso");
        stage.setScene(scene);
        stage.show();

    }

    private void formatFont(Label label) {
        label.setStyle("-fx-font-weight: bold");
    }
}
