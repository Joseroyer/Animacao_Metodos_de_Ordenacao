package org.example.animacao_ordenacao;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {
    public Thread animationThread;

    AnchorPane pane;
    Button botao_inicio, botaoReset;
    private Button vet[];
    private Button[] vetLines;
    private int[] vetValores;
    private int[] vetInverte;

    @Override
    public void start(Stage stage) throws IOException {

        pane = new AnchorPane();
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(500);
        botao_inicio.setText("Quick Sort");
        botao_inicio.setOnAction(e->{ iniciaQuickSort(0, vetValores.length - 1);});
        pane.getChildren().add(botao_inicio);
        botaoReset = new Button();
        botaoReset.setLayoutX(100);
        botaoReset.setLayoutY(500);
        botaoReset.setText("Reset");
        botaoReset = new Button();
        botaoReset.setLayoutX(100);
        botaoReset.setLayoutY(500);
//        botaoReset.setText("Heap Sort");
//        botaoReset.setOnAction(e->{ iniciaHeapSort(pane); });
        pane.getChildren().add(botaoReset);
        createAndFillVetor();
        createRectangles(pane);




//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        Scene scene = new Scene(pane, 1200, 600);
        stage.setTitle("Pesquisa e Ordenacao!");
        stage.setScene(scene);
        stage.show();
    }

    public void createRectangles(AnchorPane pane){
        double barWidth = 30;
        double spacing = 5;
        double startX = 40;

        vetLines = new Button[10];
        for (int i = 0; i < vetValores.length; i++) {
            vetLines[i] = new Button(vetValores[i] + "");
            vetLines[i].setMinHeight(vetValores[i]);
            vetLines[i].setMinWidth(barWidth);
            vetLines[i].setLayoutX((startX * i) + spacing);
            vetLines[i].setLayoutY(0);
            vetLines[i].setStyle("-fx-text-fill: #ff0000;");
            pane.getChildren().add(vetLines[i]);
        }
    }

    public void createSquares(AnchorPane pane){
        double barWidth = 30;
        double spacing = 5;
        double startX = 40;

        vetLines = new Button[10];
        for (int i = 0; i < vetValores.length; i++) {
            vetLines[i] = new Button(vetValores[i] + "");
            vetLines[i].setMinWidth(barWidth);
            vetLines[i].setLayoutX((startX * i) + spacing);
            vetLines[i].setLayoutY((startX * i) + spacing);
            vetLines[i].setStyle("-fx-text-fill: #ff0000;");
            pane.getChildren().add(vetLines[i]);
        }
    }
    private void resetVetor() {
        createAndFillVetor();
        for (int i = 0; i < vetValores.length; i++) {
            vetLines[i] = new Button(vetValores[i] + "");
            vetLines[i].setMinHeight(vetValores[i]);
        }
    }


    public void createAndFillVetor(){
        vetValores = new int[10];
        Random random = new Random();
        for (int i = 0; i < vetValores.length; i++) {
            vetValores[i] = random.nextInt(151) + 1;
        }
    }

    public void iniciaQuickSort(int ini, int fim){
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                quickSort(ini,fim);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
    public void quickSort(int ini, int fim)
    {
        int i = ini, j = fim, aux;
        Button auxLine;
        boolean flag = true;
        while (i < j){
            if(flag){
                while (i < j && vetValores[i] <= vetValores[j]){
                    vetLines[i].setStyle("-fx-text-fill: #ff0000;");
                    i++;
                    vetLines[i].setStyle("-fx-background-color: #0077ff;");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                while (i < j && vetValores[j] >= vetValores[i]){
                    vetLines[j].setStyle("-fx-text-fill: #ff0000;");
                    j--;
                    vetLines[j].setStyle("-fx-background-color: #002aff;");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            move_botoes(vetLines[i], vetLines[j]);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            aux = vetValores[i];
            vetValores[i] = vetValores[j];
            vetValores[j] = aux;
            auxLine = vetLines[i];
            vetLines[i] = vetLines[j];
            vetLines[j] = auxLine;
            flag = !flag;
        }

        if(ini < i-1)
            quickSort(ini, i - 1);
        if(j + 1 < fim)
            quickSort(j + 1, fim);
    }
    public void move_botoes(Button line1, Button line2)
    {
        Button bigger, smaller;
        double auxSmaller, auxBigger;
        if(line1.getLayoutX() > line2.getLayoutX()){
            bigger = line1;
            auxBigger = line1.getLayoutX();
            smaller = line2;
            auxSmaller = line2.getLayoutX();
        }else{
            bigger = line2;
            auxBigger = line2.getLayoutX();
            smaller = line1;
            auxSmaller = line1.getLayoutX();
        }
        smaller.setStyle("-fx-background-color: #ff0000;");
        bigger.setStyle("-fx-background-color: #ff0000;");
        while (smaller.getLayoutX() < auxBigger || bigger.getLayoutX() > auxSmaller) {
            if(smaller.getLayoutX() < auxBigger) {
                smaller.setLayoutX(smaller.getLayoutX() + 5);
            }
            if(bigger.getLayoutX() > auxSmaller) {
                bigger.setLayoutX(bigger.getLayoutX() - 5);
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        smaller.setLayoutX(auxBigger);
        bigger.setLayoutX(auxSmaller);
        smaller.setStyle("-fx-text-fill: #ff0000;");
        bigger.setStyle("-fx-text-fill: #ff0000;");

    }

    public static void main(String[] args) {
        launch();
    }
}