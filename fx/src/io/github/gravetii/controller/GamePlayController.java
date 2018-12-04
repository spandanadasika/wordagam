package io.github.gravetii.controller;

import io.github.gravetii.pojo.GamePlayResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static io.github.gravetii.controller.TableResultDisplayer.TableResult;

public class GamePlayController implements FxController {

  private final GameController ref;
  private TableResultDisplayer displayer;

  @FXML private TableView<TableResult> tblDisplay;
  @FXML private TableColumn<TableResult, Integer> idTblCol;
  @FXML private TableColumn<TableResult, String> wordTblCol;
  @FXML private TableColumn<TableResult, Integer> pointsTblCol;

  public GamePlayController(GameController ref) {
    this.ref = ref;
  }

  @FXML
  public void initialize() {
    this.idTblCol.prefWidthProperty().bind(tblDisplay.widthProperty().divide(4));
    this.wordTblCol.prefWidthProperty().bind(tblDisplay.widthProperty().divide(2));
    this.pointsTblCol.prefWidthProperty().bind(tblDisplay.widthProperty().divide(4));
    this.idTblCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    this.wordTblCol.setCellValueFactory(new PropertyValueFactory<>("word"));
    this.pointsTblCol.setCellValueFactory(new PropertyValueFactory<>("score"));
    this.tblDisplay.setRowFactory(
        callback -> {
          TableRow<TableResult> row = new TableRow<>();
          row.setOnMouseClicked(
              event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                  TableResult result = row.getItem();
                  this.ref.revisitWord(result.getWord());
                }
              });

          return row;
        });

    this.displayer = new TableResultDisplayer(this.tblDisplay);
  }

  @FXML
  public void onGoBtnClick(ActionEvent event) {
    GamePlayResult result = this.ref.validateWordOnBtnClick();
    if (result != null) {
      this.displayer.show(result.getWord(), result.getScore());
    }
  }
}