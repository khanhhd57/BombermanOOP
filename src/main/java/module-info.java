module uet.oop.bomberman {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires transitive javafx.graphics;
  requires log4j;

  opens uet.oop.bomberman to javafx.fxml;
  exports uet.oop.bomberman;
}