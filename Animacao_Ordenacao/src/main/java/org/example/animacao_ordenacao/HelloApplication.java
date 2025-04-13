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
    AnchorPane pane;
    Button botaoInicio, botaoReset;

    private Label statusLabel, indexLabel;
    private Label iLabel, jLabel, auxLabel, vetILabel, vetJLabel;

    private TextArea codigoFonteTA;
    private Button[] vetLines;
    private int[] vetValores;
    private final int ms = 400;

    private final String[] codigoQuickSortVetor = {
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
        botaoInicio = new Button();
        botaoInicio.setLayoutX(10);
        botaoInicio.setLayoutY(500);
        botaoInicio.setText("Quick Sort");
        botaoInicio.setOnAction(e -> {
            iniciaQuickSort(0, vetValores.length - 1);
        });
        pane.getChildren().add(botaoInicio);

        botaoReset = new Button();
        botaoReset.setLayoutX(100);
        botaoReset.setLayoutY(500);
        botaoReset.setText("Reset");
        botaoReset = new Button();
        botaoReset.setLayoutX(100);
        botaoReset.setLayoutY(500);

        inserirValoresVetor();
        criarBotoes(pane);

        //TextArea com fonte
        codigoFonteTA = new TextArea();
        codigoFonteTA.setLayoutX(650);
        codigoFonteTA.setLayoutY(20);
        codigoFonteTA.setPrefWidth(500);
        codigoFonteTA.setPrefHeight(500);
        codigoFonteTA.setEditable(false);
        pane.getChildren().add(codigoFonteTA);

        //StatusLabel
        statusLabel = new Label("Aguardando ordenação...");
        statusLabel.setLayoutX(10);
        statusLabel.setLayoutY(550);
        pane.getChildren().add(statusLabel);

        iLabel = new Label("i: 0");
        iLabel.setLayoutX(30);
        iLabel.setLayoutY(360);
        pane.getChildren().add(iLabel);

        jLabel = new Label("j: 0");
        jLabel.setLayoutX(80);
        jLabel.setLayoutY(360);
        pane.getChildren().add(jLabel);

        auxLabel = new Label("aux: 0");
        auxLabel.setLayoutX(120);
        auxLabel.setLayoutY(360);
        pane.getChildren().add(auxLabel);

        vetILabel = new Label("vet[i]: 0");
        vetILabel.setLayoutX(30);
        vetILabel.setLayoutY(400);
        pane.getChildren().add(vetILabel);

        vetJLabel = new Label("vet[j]: 0");
        vetJLabel.setLayoutX(100);
        vetJLabel.setLayoutY(400);
        pane.getChildren().add(vetJLabel);


        Scene scene = new Scene(pane, 1200, 600);
        stage.setTitle("Pesquisa e Ordenação -> Método Quick Sort ");
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

            //Insere label com posição do i
            indexLabel = new Label(i + "");
            indexLabel.setLayoutX(posX + barWidth / 2.0 - 5);
            indexLabel.setLayoutY(startY + barHeight + 5);
            pane.getChildren().add(indexLabel);
        }
    }


    private void resetVetor() {
        inserirValoresVetor();
        for (int i = 0; i < vetValores.length; i++) {
            vetLines[i] = new Button(vetValores[i] + "");
            vetLines[i].setMinHeight(vetValores[i]);
        }
    }

    public void inserirValoresVetor() {
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
                atualizarInfo(0, 0, 0, true);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void quickSort(int ini, int fim) {
        int i = ini, j = fim, aux = 0;
        boolean flag = true;

        atualizarInfo(i, j, aux,false);

        destacarIJ(i, j);
        atualizaCodigo(codigoFonteTA, 1);
        sleep(ms);

        atualizaCodigo(codigoFonteTA, 2);
        sleep(ms);

        while (i < j) {
            atualizaCodigo(codigoFonteTA, 4);
            sleep(ms);

            if (flag) {
                atualizaCodigo(codigoFonteTA, 5);
                sleep(ms);

                while (i < j && vetValores[i] <= vetValores[j]) {
                    atualizaCodigo(codigoFonteTA, 6);
                    sleep(ms);

                    i++;
                    destacarIJ(i, j);
                    atualizarInfo(i, j, aux,false);

                    atualizaCodigo(codigoFonteTA, 7);
                    sleep(ms);
                }

                atualizaCodigo(codigoFonteTA, 8);
                sleep(ms);
            } else {
                atualizaCodigo(codigoFonteTA, 9);
                sleep(ms);

                while (i < j && vetValores[j] >= vetValores[i]) {
                    atualizaCodigo(codigoFonteTA, 10);
                    sleep(ms);

                    j--;
                    destacarIJ(i, j);
                    atualizarInfo(i, j, aux,false);

                    atualizaCodigo(codigoFonteTA, 11);
                    sleep(ms);
                }

                atualizaCodigo(codigoFonteTA, 12);
                sleep(ms);
            }

            atualizaCodigo(codigoFonteTA, 15);
            sleep(ms);
            aux = vetValores[i];
            atualizarInfo(i, j, aux,false);

            atualizaCodigo(codigoFonteTA, 16);
            sleep(ms);
            vetValores[i] = vetValores[j];
            atualizarInfo(i, j, aux,false);

            atualizaCodigo(codigoFonteTA, 17);
            sleep(ms);
            vetValores[j] = aux;
            atualizarInfo(i, j, aux,false);

            Button tempButton = vetLines[i];
            vetLines[i] = vetLines[j];
            vetLines[j] = tempButton;

            move_botoes(vetLines[i], vetLines[j]);

            flag = !flag;
            atualizaCodigo(codigoFonteTA, 18);
            destacarIJ(i, j);
            sleep(ms);
        }
        destacarIJ(i, j);
        atualizaCodigo(codigoFonteTA, 19);
        sleep(ms);

        if (ini < i - 1) {
            atualizaCodigo(codigoFonteTA, 21);
            atualizarInfo(ini, i-1, 0,false);
            sleep(ms);
            quickSort(ini, i - 1);
        }

        if (j + 1 < fim) {
            atualizaCodigo(codigoFonteTA, 23);
            atualizarInfo(j+1, fim, 0,false);
            sleep(ms);
            quickSort(j + 1, fim);
        }

        atualizaCodigo(codigoFonteTA, 25);
        sleep(ms);
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void atualizaCodigo(TextArea codigoFonteTA, int linha) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < codigoQuickSortVetor.length; i++) {
            if (i == linha) {
                builder.append(">> ").append(codigoQuickSortVetor[i]).append("\n");
            } else {
                builder.append("   ").append(codigoQuickSortVetor[i]).append("\n");
            }
        }
        Platform.runLater(() -> codigoFonteTA.setText(builder.toString()));
    }

    private void destacarIJ(int i, int j) {
        Platform.runLater(() -> {
            for (int k = 0; k < vetLines.length; k++) {
                if (k == i) { // azul
                    vetLines[k].setStyle("-fx-background-color: #00a135; -fx-text-fill: white;");
                } else if (k == j) { //roxo
                    vetLines[k].setStyle("-fx-background-color: #9933ff; -fx-text-fill: white;");
                } else {
                    vetLines[k].setStyle("-fx-background-color: #3366ff;; -fx-text-fill: white;");
                }
            }
        });
    }

    private void atualizarInfo(int i, int j, int aux, boolean isFim) {
        Platform.runLater(() -> {
            iLabel.setText("i: " + i);
            jLabel.setText("j: " + j);
            auxLabel.setText("aux: " + aux);

            vetILabel.setText("vet[i]: " + (!isFim ? vetValores[i] : 0));
            vetJLabel.setText("vet[j]: " + (!isFim ? vetValores[j] : 0));
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