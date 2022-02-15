package com.company.server;
import com.company.aes.AES;
import com.company.entity.Extracto;
import com.company.entity.Usuario;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Server extends Application implements Runnable{
    TextArea ta;
    JScrollPane scroll;
    Button bt, bt2;
    Usuario reciboDatos = new Usuario();
    Image image;
    ImageView wP = new ImageView(image);
    Label l, l0, l1, l2, l3, l4, l5, l6, l7;

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(8080);
            //modificar datos para recepcion de paquete
            Usuario reciboDatos;

            while (true) {
                Socket miConexion = server.accept();
                ObjectInputStream reciboDatosPak = new ObjectInputStream(miConexion.getInputStream());
                reciboDatos = (Usuario) reciboDatosPak.readObject();

                //parte de encriptacion
                final String claveEncriptacion = "ExamenPSP2Eval";
                AES encriptador = new AES();
                String encriptado = null, desencriptado = null;
                try {
                    encriptado = encriptador.encriptar(String.valueOf(reciboDatos.getPass()), claveEncriptacion);
                    desencriptado = encriptador.desencriptar(encriptado, claveEncriptacion);
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
                ArrayList<Extracto> extList = new ArrayList<Extracto>();
                ta.append("\nUsuario: " + reciboDatos.getUser()+ "\nContraseña encriptada: " + encriptado + "\nContraseña desencriptada: " + desencriptado);
                ta.append("\nEXTRACTOS: \n");
                for(Extracto e : reciboDatos.getExtractos()){
                    if(e == null){
                        ta.append("No hay extractos\n");
                        break;
                    }else {
                        extList.add(e);
                        ta.append(e.toString() + "\n");
                    }
                }
                miConexion.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        l = new Label("Mensaje");
        ta = new TextArea();
        ta.setBounds(50, 50, 200, 200);
        image = new Image(getClass().getResourceAsStream("image/logomenu.jpg"));
        ta.setEditable(false);
        scroll = new JScrollPane(ta);
        scroll.setBounds(50, 50, 200, 200);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        Thread hiloServidor = new Thread(this);
        hiloServidor.start();

        //EXTRACTOS SERVIDOR
        wP.setFitHeight(1000);
        wP.setFitWidth(900);
        l1 = new Label("EXTRACTOS");
        l2 = new Label("Usuario : ");
        l3 = new Label("Saldo actual : ");
        l5 = new Label(reciboDatos.getUser());
        l6 = new Label(Double.toString(reciboDatos.getSaldo()) + "€");
        l0 = new Label();
        l7 = new Label();

        for (Extracto e : reciboDatos.getExtractos()) {
            l0.setText(l0.getText() + "\n" + e.getClave() + "\n");
            l7.setText(l7.getText() + "\n" + e.getCantidad() + "€" + "\n" + e.getDineroHistorico() + "€");
        }

        //CANCELAR
        bt2 = new Button("Cancelar");
        bt2.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                System.exit(0);
            }
        });
        VBox vb = new VBox(l1, l2, l3, l0, l5, l6, l7);
        StackPane sp = new StackPane(wP, vb);
        Scene scene = new Scene(sp);
        primaryStage.setTitle("Servidor");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("image/icon.jpg")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



