package com.company.server;

import com.company.Main;
import com.company.aes.AES;
import com.company.entity.Extracto;
import com.company.entity.Usuario;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Server extends Application implements Runnable {
    TextArea ta;
    Usuario reciboDatos = new Usuario();
    Image image = new Image(getClass().getResourceAsStream("..//image/logomenu.jpg"));
    ImageView wP = new ImageView(image);
    TextField tf, tf2;
    Label l1, l2, l3;
    VBox vb, vb2, vb3;
    HBox hb;
    Main main = new Main();

    @Override
    public void start(Stage primaryStage) {
        wP.setFitHeight(600);
        wP.setFitWidth(600);
        l1 = new Label("INFORMACIÓN DE CLIENTE");
        main.formatFontBold(l1);
        l2 = new Label("Usuario : ");
        main.formatFontBold(l2);
        l3 = new Label("Contraseña : ");
        main.formatFontBold(l3);

        tf = new TextField("");
        tf.setEditable(false);
        tf2 = new TextField("");
        tf2.setEditable(false);

        ta = new TextArea("");
        ta.setMaxHeight(300);
        ta.setMaxWidth(350);
        ta.setEditable(false);

        Thread hiloServidor = new Thread(this);
        hiloServidor.start();

        vb = new VBox(l2, l3);
        vb.setAlignment(Pos.CENTER_LEFT);
        vb.setSpacing(5);
        vb2 = new VBox(tf, tf2);
        vb2.setSpacing(5);
        vb2.setAlignment(Pos.CENTER_RIGHT);

        hb = new HBox(vb, vb2);
        hb.setSpacing(100);
        hb.setPadding(new Insets(15, 15, 15, 15));
        hb.setAlignment(Pos.CENTER);

        vb3 = new VBox(l1, hb, ta);
        vb3.setSpacing(50);
        vb3.setAlignment(Pos.CENTER);

        StackPane sp = new StackPane(wP, vb3);
        Scene scene = new Scene(sp);
        primaryStage.setTitle("Servidor");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("..//image/icon.jpg")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(8080);
            while (true) {

                Socket miConexion = server.accept();
                ObjectInputStream reciboDatosPak = new ObjectInputStream(miConexion.getInputStream());

                reciboDatos = (Usuario) reciboDatosPak.readObject();

                //parte de encriptacion y desencriptación
                final String claveEncriptacion = "ExamenPSP2Eval";
                AES encriptador = new AES();
                String encriptado = null, desencriptado = null;
                encriptado = encriptador.encriptar(String.valueOf(reciboDatos.getPass()), claveEncriptacion);
                desencriptado = encriptador.desencriptar(encriptado, claveEncriptacion);

                ta.setText("");
                tf.setText(reciboDatos.getUser());
                tf2.setText(encriptado);

                ta.setText("Información Personal: \nContraseña: " + desencriptado + "\nSaldo Actual: " + reciboDatos.getSaldo());
                ta.setText(ta.getText() + "\nEXTRACTOS: \n");
                for (Extracto e : reciboDatos.getExtractos()) {
                    if (e == null) {
                        ta.setText("No hay extractos\n");
                    } else {
                        ta.setText(ta.getText() + "\n" + e + "\n");
                    }
                }
                miConexion.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }
}




