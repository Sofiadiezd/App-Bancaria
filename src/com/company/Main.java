package com.company;

import com.company.entity.Extracto;
import com.company.entity.Usuario;
import com.company.server.Server;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Application {
    final double MAX_FONT_SIZE_BIG = 40.0;
    static double dineroIni;
    Label l0, l1, l2, l3, l5, l6, l7;
    TextField tl, tl2;
    PasswordField tlaux;
    Button bt, bt2;
    static ArrayList<Usuario> usuarios = new ArrayList<>();
    Image image = new Image(getClass().getResourceAsStream("image/logomenu.jpg"));
    ImageView imageView, im2, im3, im4, im5, im6, im7;
    ImageView wP = new ImageView(image);
    Usuario user = new Usuario();
    VBox vb, vb2, vb5;
    HBox hb, hb2, hb3;
    Double dinero, dineroE, saldoMax, dineroHistorico;
    Boolean serverON = false;
    Date date;

    public static void main(String[] args) {
        //APENAS INICIA CREA LOS USUARIOS
        agregarUsuarios();
        launch(args);
    }

    private static void agregarUsuarios() {
        usuarios.add(new Usuario("Sofia", 6666, 666, new ArrayList<>()));
        usuarios.add(new Usuario("Jon", 7777, 100, new ArrayList<>()));
        usuarios.add(new Usuario("Javi", 8888, 300, new ArrayList<>()));
    }

    //PANTALLA LOGIN
    @Override
    public void start(Stage primaryStage) {
        //Si el servidor no está iniciado, lo inicia. Así al regresar para cambiar de usuario no vuelve a crearlo.
        if (serverON == false) {
            servidor();
        }

        //WALLPAPER
        imageView = new ImageView(new Image(getClass().getResourceAsStream("image/logo.jpg")));
        imageView.setFitHeight(800);
        imageView.setFitWidth(1200);


        l1 = new Label("User: ");
        l1.setTextFill(Color.WHITE);
        tl = new TextField();
        l2 = new Label("Password: ");
        l2.setTextFill(Color.WHITE);
        tlaux = new PasswordField();

        bt = new Button("Log In");
        bt2 = new Button("Salir");

        //HORIZONTAL
        hb = new HBox(bt, bt2);
        hb.setSpacing(60);

        //VERTICAL
        vb = new VBox(l1, tl, l2, tlaux, hb);
        vb.setPadding(new Insets(0, 10, 0, 10));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);

        hb2 = new HBox(vb);

        //EVENTOS
        tlaux.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!tlaux.getText().matches("\\d*")) {
                tlaux.setText(newValue.replaceAll("[^\\d]", ""));
                tlaux.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            } else {
                tlaux.setStyle("-fx-border-color: green ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            }
        });

        bt.setOnAction(event -> {
            for (Usuario u : usuarios) {
                if (u.getUser().equalsIgnoreCase(tl.getText()) && u.getPass() == Integer.parseInt(tlaux.getText())) {
                    user = u;
                    System.out.println("Todo bien: " + user.getUser());
                    saldoMax = user.getSaldo();
                    dineroIni = user.getSaldo();
                    menuM(primaryStage);
                } else {
                    System.out.println("ERROR - Credenciales Incorrectas ");
                    tlaux.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 10;");
                    tl.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 10;");
                }
            }
        });

        bt2.setOnAction(event -> System.exit(0));

        //SCENE
        StackPane sp = new StackPane(imageView, hb2);
        Scene scene = new Scene(sp);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("image/icon.jpg")));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void servidor() {
        serverON = true;
        Stage stage1 = new Stage();
        Server server = new Server();
        try {
            server.start(stage1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void menuM(Stage stage) {
        wP.setFitHeight(1000);
        wP.setFitWidth(1300);

        //INGRESO
        im2 = new ImageView(new Image(getClass().getResourceAsStream("image/ingreso.png")));
        im2.setFitHeight(150);
        im2.setFitWidth(150);
        im2.setOnMouseClicked(event -> ingresar(stage));

        //RETIRO
        im3 = new ImageView(new Image(getClass().getResourceAsStream("image/retiro.png")));
        im3.setFitHeight(150);
        im3.setFitWidth(150);
        im3.setOnMouseClicked(event -> retiro(stage));

        //TRANSFERENCIA
        im4 = new ImageView(new Image(getClass().getResourceAsStream("image/transferencia.png")));
        im4.setFitHeight(150);
        im4.setFitWidth(150);
        im4.setOnMouseClicked(event -> transferencia(stage));

        //SALDO
        im5 = new ImageView(new Image(getClass().getResourceAsStream("image/saldo.png")));
        im5.setFitHeight(150);
        im5.setFitWidth(150);
        im5.setOnMouseClicked(event -> saldo(stage));

        //ESTADISTICAS
        im6 = new ImageView(new Image(getClass().getResourceAsStream("image/estadisticas.png")));
        im6.setFitHeight(150);
        im6.setFitWidth(150);
        im6.setOnMouseClicked(event -> estadisticas(stage));

        //SERVER
        im7 = new ImageView(new Image(getClass().getResourceAsStream("image/server.png")));
        im7.setFitHeight(150);
        im7.setFitWidth(150);
        im7.setOnMouseClicked(event -> seguridad());

        //LOG OUT
        bt = new Button("Log Out");
        bt.setAlignment(Pos.BOTTOM_CENTER);
        bt.setOnAction(event -> {
            try {
                System.out.println("Hasta luego: " + user.getUser());
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //HORIZONTAL
        hb = new HBox(im2, im3, im4);
        hb.setSpacing(100);
        hb.setAlignment(Pos.CENTER);
        hb2 = new HBox(im5, im6, im7);
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

    private void estadisticas(Stage stage) {
        //Actualiza el saldo Máximo
        if (user.getSaldo() > saldoMax) {
            saldoMax = user.getSaldo();
        }

        wP.setFitHeight(900);
        wP.setFitWidth(1200);
        l1 = new Label("Saldo según operaciones");
        formatFontTitulo(l1);

        //Boton cancelar
        bt2 = new Button("Cancelar");
        bt2.setOnAction(event -> menuM(stage));

        //Setting pieChart data
        PieChart tarta = new PieChart();
        tarta.setData(getChartData());
        tarta.setLegendSide(Side.LEFT);
        tarta.setTitle("Porcentual");
        tarta.setClockwise(false);

        //LINE CHART
        NumberAxis xaxis = new NumberAxis(0, user.getExtractos().size(), 1);
        NumberAxis yaxis = new NumberAxis(0, saldoMax + 100, 50);
        xaxis.setLabel("Nº Operaciones");
        yaxis.setLabel("Saldo en €");

        LineChart linea = new LineChart(xaxis, yaxis);
        linea.setTitle("Historico");
        XYChart.Series serie = new XYChart.Series();
        serie.setName("Serie");

        int contador = 0;
        serie.getData().add(new XYChart.Data(contador, dineroIni));
        for (Extracto e : user.getExtractos()) {
            contador++;
            serie.getData().add(new XYChart.Data(contador, e.getDineroHistorico()));
        }
        linea.getData().add(serie);


        //HORIZONTAL
        hb = new HBox(tarta, linea);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(100);

        //VERTICAL
        vb = new VBox(l1, hb, bt2);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(50);
        vb.setPadding(new Insets(15, 15, 25, 15));

        //ESCENE
        StackPane sp = new StackPane(wP, vb);
        Scene scene = new Scene(sp);
        stage.setTitle("Estadísticas");
        stage.setScene(scene);
        stage.show();
    }

    //METODO PIE CHART
    private ObservableList<PieChart.Data> getChartData() {
        double ingresos = 0;
        double retiros = 0;
        double transferencias = 0;
        for (Extracto e : user.getExtractos()) {
            if (e.getClave().equalsIgnoreCase("ingreso")) {
                ingresos = ingresos + e.getCantidad();
            } else if (e.getClave().equalsIgnoreCase("retiro")) {
                retiros = retiros + e.getCantidad();
            } else {
                transferencias = transferencias + e.getCantidad();
            }
        }
        System.out.println("ingresos: " + ingresos + " retiros: " + retiros + " transferencias: " + transferencias);
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.addAll(new PieChart.Data("Saldo Actual", user.getSaldo()), new PieChart.Data("Ingresos", ingresos), new PieChart.Data("Retiros", retiros), new PieChart.Data("Transferencias", transferencias));
        return list;
    }

    private void saldo(Stage stage) {
        wP.setFitHeight(1000);
        wP.setFitWidth(900);

        l1 = new Label("EXTRACTOS");
        formatFontTitulo(l1);
        l2 = new Label("Usuario : ");
        formatFontBold(l2);
        l3 = new Label("Saldo actual : ");
        formatFontBold(l3);
        l5 = new Label(user.getUser());
        formatFontBold(l5);
        l6 = new Label(user.getSaldo() + "€");
        formatFontBold(l6);
        l0 = new Label();
        l7 = new Label();

        for (Extracto e : user.getExtractos()) {
            styleExtracto(e);
        }

        //CANCELAR
        bt2 = new Button("Cancelar");
        bt2.setOnAction(event -> menuM(stage));

        //IMPRIMIR TXT
        bt = new Button("Imprimir");
        bt.setOnAction(event -> {
            imprimirTxt();
            menuM(stage);
        });

        //ALIGNMENTS
        vb = new VBox(l2, l3);
        vb.setAlignment(Pos.CENTER_LEFT);
        vb2 = new VBox(l5, l6);
        vb2.setAlignment(Pos.CENTER_RIGHT);

        hb = new HBox(vb, vb2);
        hb.setSpacing(150);
        hb.setPadding(new Insets(15, 15, 15, 15));
        hb.setAlignment(Pos.CENTER);

        hb2 = new HBox(l0, l7);
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(150);
        hb2.setPadding(new Insets(15, 15, 15, 15));

        hb3 = new HBox(bt, bt2);
        hb3.setAlignment(Pos.CENTER);
        hb3.setSpacing(30);

        vb5 = new VBox(l1, hb, hb2, hb3);
        vb5.setSpacing(20);
        vb5.setPadding(new Insets(15, 15, 15, 15));
        vb5.setAlignment(Pos.CENTER);

        //ESCENE
        StackPane sp = new StackPane(wP, vb5);
        Scene scene = new Scene(sp);
        stage.setTitle("Extractos");
        stage.setScene(scene);
        stage.show();
    }

    //METODO DISEÑO PARA PINTAR LOS EXTRACTOS
    private void styleExtracto(Extracto e) {
        if (e.getClave().equalsIgnoreCase("TRANSFERENCIA ENVIADA")) {
            l0.setText(l0.getText() + "\n" + e.getClave() + "\nSaldo\n");
            l7.setText(l7.getText() + "\n-" + e.getCantidad() + "€" + "\n" + e.getDineroHistorico() + "€\n");

        } else if (e.getClave().equalsIgnoreCase("TRANSFERENCIA RECIBIDA")) {
            l0.setText(l0.getText() + "\n" + e.getClave() + "\nSaldo\n");
            l7.setText(l7.getText() + "\n+" + e.getCantidad() + "€" + "\n" + e.getDineroHistorico() + "€\n");
        } else if (e.getClave().equalsIgnoreCase("INGRESO")) {
            l0.setText(l0.getText() + "\n" + e.getClave() + "\nSaldo\n");
            l7.setText(l7.getText() + "\n+" + e.getCantidad() + "€" + "\n" + e.getDineroHistorico() + "€\n");
        } else {
            l0.setText(l0.getText() + "\n" + e.getClave() + "\nSaldo\n");
            l7.setText(l7.getText() + "\n-" + e.getCantidad() + "€" + "\n" + e.getDineroHistorico() + "€\n");
        }
    }

    //METODO IMPRESIÓN EN TXT
    private void imprimirTxt() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Extractos" + user.getUser() + ".txt"));
            oos.writeObject(user.getExtractos().toString());

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void transferencia(Stage stage) {
        wP.setFitHeight(600);
        wP.setFitWidth(700);

        l1 = new Label("Cantidad a Transferir: ");
        formatFontBold(l1);
        tl = new TextField();
        tl.setMaxWidth(200);
        tl.setMaxHeight(200);

        l2 = new Label("Usuario destino: ");
        formatFontBold(l2);
        tl2 = new TextField();
        tl2.setMaxWidth(200);
        tl2.setMaxHeight(200);

        tl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!tl.getText().matches("\\d*")) {
                tl.setText(newValue.replaceAll("[^\\d]", ""));
                tl.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            } else {
                tl.setStyle("-fx-border-color: green ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            }
        });
        tlaux.textProperty().addListener((observable, oldValue, newValue) -> {
            if (tl2.getText().matches("\\d*")) {
                tl2.setStyle("-fx-border-color: green ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            } else {
                tl2.setText(newValue.replaceAll("[^\\d]", ""));
                tl2.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            }
        });

        //ACEPTAR
        bt = new Button("Aceptar");
        bt.setOnAction(event -> {
            if (user.getUser().equalsIgnoreCase(tl2.getText())) {
                tl2.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
                System.out.println("No es posible trasnferirse dinero a una misma cuenta");
            } else {
                dinero = Double.parseDouble(tl.getText());
                dineroE = user.getSaldo();
                date = new Date();

                if (dinero > dineroE) {
                    tl.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
                    System.out.println("El usuario " + user.getUser() + " no tiene saldo suficiente");
                } else {

                    user.setSaldo(dineroE - dinero);
                    System.out.println("Saldo transferido: " + dinero);
                    System.out.println("Saldo actual: " + user.getSaldo());
                    dineroHistorico = user.getSaldo();
                    user.getExtractos().add(new Extracto("TRANSFERENCIA ENVIADA", dinero, dineroHistorico, date));

                    //Lista de usuarios para guardar la transferencia en el receptor
                    for (Usuario u : usuarios) {
                        if (u.getUser().equalsIgnoreCase(tl2.getText())) {
                            System.out.println(u.getUser());
                            dineroE = u.getSaldo();
                            u.setSaldo(dineroE + dinero);
                            System.out.println("Saldo recibido: " + dinero);
                            System.out.println("Saldo actual: " + u.getSaldo());
                            dineroHistorico = user.getSaldo();
                            u.getExtractos().add(new Extracto("TRANSFERENCIA RECIBIDA", dinero, dineroHistorico, date));
                            System.out.println(u.getExtractos().toString());
                            seguridad();
                        }
                    }
                    menuM(stage);
                }
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
        formatFontBold(l1);
        tl = new TextField();
        tl.setMaxWidth(200);
        tl.setMaxHeight(200);

        tl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!tl.getText().matches("\\d*")) {
                tl.setText(newValue.replaceAll("[^\\d]", ""));
                tl.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            } else {
                tl.setStyle("-fx-border-color: green ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            }
        });

        //ACEPTAR
        bt = new Button("Aceptar");
        bt.setOnAction(event -> {
            dinero = Double.parseDouble(tl.getText());
            dineroE = user.getSaldo();
            date = new Date();

            if (dinero > dineroE) {
                tl.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
                System.out.println("El usuario " + user.getUser() + " no tiene saldo suficiente");
            } else {
                user.setSaldo(dineroE - dinero);
                System.out.println("Saldo retirado: " + dinero);
                System.out.println("Saldo actual: " + user.getSaldo());
                dineroHistorico = user.getSaldo();
                user.getExtractos().add(new Extracto("RETIRO", dinero, dineroHistorico, date));
                seguridad();
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
        stage.setTitle("Retiro");
        stage.setScene(scene);
        stage.show();


    }

    private void ingresar(Stage stage) {
        wP.setFitHeight(500);
        wP.setFitWidth(700);
        l1 = new Label("Cantidad a Ingresar: ");
        formatFontBold(l1);
        tl = new TextField();
        tl.setMaxWidth(200);
        tl.setMaxHeight(200);

        tl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!tl.getText().matches("\\d*")) {
                tl.setText(newValue.replaceAll("[^\\d]", ""));
                tl.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            } else {
                tl.setStyle("-fx-border-color: green ; -fx-border-width: 2px ; -fx-border-radius: 15;");
            }
        });

        //ACEPTAR
        bt = new Button("Aceptar");
        bt.setOnAction(event -> {
            dinero = Double.parseDouble(tl.getText());
            dineroE = user.getSaldo();
            date = new Date();

            user.setSaldo(dineroE + dinero);
            System.out.println("Saldo ingresado: " + dinero);
            System.out.println("Saldo actual: " + user.getSaldo());
            dineroHistorico = user.getSaldo();
            user.getExtractos().add(new Extracto("INGRESO", dinero, dineroHistorico, date));
            seguridad();
            menuM(stage);
        });

        //CANCELAR
        bt2 = new Button("Cancelar");
        bt2.setOnAction(event -> menuM(stage));

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

    //CONEXIÓN CON EL SERVIDOR
    private void seguridad() {
        try {
            Socket miConexion = new Socket("localhost", 8080);
            //enviar objeto a traves de ObjectOutputStream
            ObjectOutputStream envioDatos = new ObjectOutputStream(miConexion.getOutputStream());

            envioDatos.writeObject(user);

            miConexion.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ESTILO PARA ALGUNOS LABEL
    public void formatFontBold(Label label) {
        label.setStyle("-fx-font-weight: bold");
    }

    //ESTILO PARA UN PAR DE TÌTULOS
    public void formatFontTitulo(Label label) {
        label.setFont(new Font(MAX_FONT_SIZE_BIG));
        label.setAlignment(Pos.CENTER);
        label.setMinSize(250, 50);
        label.setTextFill(Color.BLACK);
        label.setPadding(new Insets(15, 0, 0, 15));
        label.setStyle("-fx-font-weight:bold");
    }

}