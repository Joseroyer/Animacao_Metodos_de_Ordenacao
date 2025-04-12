package org.example.animacao_ordenacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {
    public Thread animationThread;

    AnchorPane pane;
    Button botao_inicio, botaoReset;

    private Label statusLabel, indexLabel;

    private TextArea codeViewer;
    private Button[] vetLines;
    private int[] vetValores;
    private final int ms = 400;

    private final String[] quickSortCodeLines = {
            "public void quickSort(int ini, int fim) {",          // 0
            "    int i = ini, j = fim, aux;",                     // 1
            "    boolean flag = true;",                           // 2
            "",                                                   // 3
            "    while (i < j) {",                                // 4
            "        if (flag) {",                                // 5
            "            while (i < j && vetValores[i] <= vetValores[j]) {", // 6
            "                i++;",                               // 7
            "            }",                                      // 8
            "        } else {",                                   // 9
            "            while (i < j && vetValores[j] >= vetValores[i]) {", // 10
            "                j--;",                               // 11
            "            }",                                      // 12
            "        }",                                          // 13
            "",                                                   // 14
            "        aux = vetValores[i];",                       // 15
            "        vetValores[i] = vetValores[j];",             // 16
            "        vetValores[j] = aux;",                       // 17
            "        flag = !flag;",                              // 18
            "    }",                                              // 19
            "",                                                   // 20
            "    if (ini < i - 1)",                               // 21
            "        quickSort(ini, i - 1);",                     // 22
            "    if (j + 1 < fim)",                               // 23
            "        quickSort(j + 1, fim);",                     // 24
            "}"                                                   // 25
    };


    @Override
    public void start(Stage stage) throws IOException {

        pane = new AnchorPane();
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(500);
        botao_inicio.setText("Quick Sort");
        botao_inicio.setOnAction(e -> {
            iniciaQuickSort(0, vetValores.length - 1);
        });
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
//        pane.getChildren().add(botaoReset);
        createAndFillVetor();
        criarBotoes(pane);


        //TextArea com fonte
        codeViewer = new TextArea();
        codeViewer.setLayoutX(650);
        codeViewer.setLayoutY(20);
        codeViewer.setPrefWidth(500);
        codeViewer.setPrefHeight(500);
        codeViewer.setEditable(false);
        pane.getChildren().add(codeViewer);

        //StatusLabel
        statusLabel = new Label("Aguardando ordenação...");
        statusLabel.setLayoutX(10);
        statusLabel.setLayoutY(550);
        pane.getChildren().add(statusLabel);


        Scene scene = new Scene(pane, 1200, 600);
        stage.setTitle("Pesquisa e Ordenação! Métodos Quick Sort e Heap");
        stage.setScene(scene);
        stage.show();
    }

    public void criarBotoes(AnchorPane pane) {
        double barWidth = 50;   // largura fixa do botão
        double barHeight = 50; // altura fixa do botão
        double spacing = 10;
        double startX = 20;
        double startY = 200;

        vetLines = new Button[10];
        for (int i = 0; i < vetValores.length; i++) {

            //Insere botões com tamanho fixo
            vetLines[i] = new Button(vetValores[i] + "");
            vetLines[i].setPrefWidth(barWidth);
            vetLines[i].setPrefHeight(barHeight);
            double posX = startX + i * (barWidth + spacing);
            vetLines[i].setLayoutX(startX + i * (barWidth + spacing));
            vetLines[i].setLayoutY(200);
            vetLines[i].setStyle("-fx-text-fill: white; -fx-background-color: #3366ff;");
            pane.getChildren().add(vetLines[i]);

            //Insere label com posição
            indexLabel = new Label(i + "");
            indexLabel.setLayoutX(posX + barWidth / 2.0 - 5); // centraliza aproximado
            indexLabel.setLayoutY(startY + barHeight + 5);    // abaixo do botão
            pane.getChildren().add(indexLabel);
        }
    }

    public void createSquares(AnchorPane pane) {
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


    public void createAndFillVetor() {
        vetValores = new int[10];
        Random random = new Random();
        for (int i = 0; i < vetValores.length; i++) {
            vetValores[i] = random.nextInt(100) + 1;
        }
    }

    public void iniciaQuickSort(int ini, int fim) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                quickSort(ini, fim);
                Platform.runLater(() -> statusLabel.setText("Ordenação finalizada com sucesso!"));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void quickSort(int ini, int fim) {
        int i = ini, j = fim, aux;
        boolean flag = true;

        destacarIJ(i, j);
        atualizaCodigo(codeViewer, 1);
        sleep(ms);

        atualizaCodigo(codeViewer, 2);
        sleep(ms);

        while (i < j) {
            atualizaCodigo(codeViewer, 4);
            sleep(ms);

            if (flag) {
                atualizaCodigo(codeViewer, 5);
                sleep(ms);

                while (i < j && vetValores[i] <= vetValores[j]) {
                    atualizaCodigo(codeViewer, 6);
                    sleep(ms);

                    i++;
                    destacarIJ(i, j);

                    atualizaCodigo(codeViewer, 7);
                    sleep(ms);
                }

                atualizaCodigo(codeViewer, 8);
                sleep(ms);
            } else {
                atualizaCodigo(codeViewer, 9);
                sleep(ms);

                while (i < j && vetValores[j] >= vetValores[i]) {
                    atualizaCodigo(codeViewer, 10);
                    sleep(ms);

                    j--;
                    destacarIJ(i, j);

                    atualizaCodigo(codeViewer, 11);
                    sleep(ms);
                }

                atualizaCodigo(codeViewer, 12);
                sleep(ms);
            }

            atualizaCodigo(codeViewer, 15);
            sleep(ms);
            aux = vetValores[i];

            atualizaCodigo(codeViewer, 16);
            sleep(ms);
            vetValores[i] = vetValores[j];

            atualizaCodigo(codeViewer, 17);
            sleep(ms);
            vetValores[j] = aux;

            Button tempButton = vetLines[i];
            vetLines[i] = vetLines[j];
            vetLines[j] = tempButton;

            move_botoes(vetLines[i], vetLines[j]);

            flag = !flag;
            atualizaCodigo(codeViewer, 18);
            destacarIJ(i, j);
            sleep(ms);
        }

        atualizaCodigo(codeViewer, 19);
        sleep(ms);

        if (ini < i - 1) {
            atualizaCodigo(codeViewer, 21);
            sleep(ms);
            quickSort(ini, i - 1);
        }

        if (j + 1 < fim) {
            atualizaCodigo(codeViewer, 23);
            sleep(ms);
            quickSort(j + 1, fim);
        }

        atualizaCodigo(codeViewer, 25);
        sleep(ms);
    }


    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void atualizaCodigo(TextArea codeViewer, int linha) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < quickSortCodeLines.length; i++) {
            if (i == linha) {
                builder.append(">> ").append(quickSortCodeLines[i]).append("\n");
            } else {
                builder.append("   ").append(quickSortCodeLines[i]).append("\n");
            }
        }
        Platform.runLater(() -> codeViewer.setText(builder.toString()));
    }

    private void destacarIJ(int i, int j) {
        Platform.runLater(() -> {
            for (int k = 0; k < vetLines.length; k++) {
                if (k == i) {
                    vetLines[k].setStyle("-fx-background-color: #3399ff; -fx-text-fill: white;"); // i = azul
                } else if (k == j) {
                    vetLines[k].setStyle("-fx-background-color: #9933ff; -fx-text-fill: white;"); // j = roxo
                } else {
                    vetLines[k].setStyle("-fx-background-color: transparent; -fx-text-fill: #ff0000;");
                }
            }
        });
    }

    public void move_botoes(Button line1, Button line2) {
        Button bigger, smaller;
        double auxSmaller, auxBigger;
        if (line1.getLayoutX() > line2.getLayoutX()) {
            bigger = line1;
            auxBigger = line1.getLayoutX();
            smaller = line2;
            auxSmaller = line2.getLayoutX();
        } else {
            bigger = line2;
            auxBigger = line2.getLayoutX();
            smaller = line1;
            auxSmaller = line1.getLayoutX();
        }
        smaller.setStyle("-fx-background-color: #ff0000;");
        bigger.setStyle("-fx-background-color: #ff0000;");
        while (smaller.getLayoutX() < auxBigger || bigger.getLayoutX() > auxSmaller) {
            if (smaller.getLayoutX() < auxBigger) {
                smaller.setLayoutX(smaller.getLayoutX() + 5);
            }
            if (bigger.getLayoutX() > auxSmaller) {
                bigger.setLayoutX(bigger.getLayoutX() - 5);
            }
            sleep(25);
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