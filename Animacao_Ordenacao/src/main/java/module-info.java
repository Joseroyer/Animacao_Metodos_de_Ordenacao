module org.example.animacao_ordenacao {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.animacao_ordenacao to javafx.fxml;
    exports org.example.animacao_ordenacao;
}