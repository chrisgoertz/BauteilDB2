package application;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import application.Bauteil;

public class SampleController {
	
	private final ObservableList<Bauteil> data = FXCollections.observableArrayList();
	private final int MAJOR_VERSION__ = 0;
	private final int MINOR_VERSION__ = 0;
	private final int PATCH_VERSION__ = 39;
	private final String disclaimer = ""
			+"Bauteil-Datenbank V"+MAJOR_VERSION__+"."+MINOR_VERSION__+"."+PATCH_VERSION__+"\n"
			+"##############################################\n"
			+"This Software is licenced under GPL\n"
			+"http://www.gnu.org/licenses/gpl-3.0.html\n"
			+"##############################################\n"
			+"2017 by Christian Goertz\n"
			+"https://github.com/chrisgoertz/bauteildb\n"
			+"this software is still under developement\n"
			+"feel free to use or develope und GPL\n"
			+"##############################################\n"
			+"the author is not responsible for data lose or\n"
			+"any damages of your system\n"
			+"this software is early alpha stadium\n"
			+"##############################################\n";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Bauteil> tableView;

    @FXML
    private TextField tfName;

    @FXML
    private TableColumn<Bauteil, String> tableColumnNotes;

    @FXML
    private TableColumn<Bauteil, String> tableColumnOrt;

    @FXML
    private TextArea LogField;

    @FXML
    private TextField tfOrt;

    @FXML
    private TextField tfMenge;

    @FXML
    private TextField tfNotes;

    @FXML
    private TableColumn<Bauteil, Integer> tableColumnMenge;

    @FXML
    private Button btnDelete;

    @FXML
    private TableColumn<Bauteil, String> tableColumnName;

    @FXML
    private TableColumn<Bauteil, Integer> tableColumnId;

    @FXML
    void onActionInsert(ActionEvent event) {
    	insertBauteil();
    }

    @FXML
    void onActionUpdate(ActionEvent event) {
    	//this.log("Update clicked");
    	System.out.println("unimplemented");
    }

    @FXML
    void onActionDelete(ActionEvent event) {
    	deleteItemFromDB();
    }

    @FXML
    void initialize() {
//    	this.LogField.appendText("Bauteil-Datenbank V0.0.39\n");
//    	this.LogField.appendText("##############################################\n");
//    	this.LogField.appendText("This Software is licenced under GPL\n");
//    	this.LogField.appendText("http://www.gnu.org/licenses/gpl-3.0.html\n");
//    	this.LogField.appendText("##############################################\n");
//    	this.LogField.appendText("2017 by Christian Goertz\n");
//    	this.LogField.appendText("https://github.com/chrisgoertz/bauteildb\n");
//    	this.LogField.appendText("##############################################\n");
    	this.LogField.appendText(disclaimer);
    	System.out.print(disclaimer);
    	assert btnInsert != null : "fx:id=\"btnInsert\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tableColumnNotes != null : "fx:id=\"tableColumnNotes\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tableColumnOrt != null : "fx:id=\"tableColumnOrt\" was not injected: check your FXML file 'Sample.fxml'.";
        assert LogField != null : "fx:id=\"LogField\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tfOrt != null : "fx:id=\"tfOrt\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tfMenge != null : "fx:id=\"tfMenge\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tfNotes != null : "fx:id=\"tfNotes\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tableColumnMenge != null : "fx:id=\"tableColumnMenge\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tableColumnName != null : "fx:id=\"tableColumnName\" was not injected: check your FXML file 'Sample.fxml'.";
        assert tableColumnId != null : "fx:id=\"tableColumnId\" was not injected: check your FXML file 'Sample.fxml'.";
        
        tfMenge.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	//if (!newValue.matches("\\d{0,7}\\d{0,4})?")) {
            		if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                            tfMenge.setText(oldValue);
                }
            }
        });
        
        //TESTCODE
        DBConnection.createTable();
        data.addAll(DBConnection.selectFromDB());

        initTableView();
        

    }
    /**
     * initialize of data table
     */
    void initTableView() {
    	tableView.setEditable(true);
    	//########################################################
    	//id column
    	tableColumnId.setMinWidth(30);
    	tableColumnId.setCellValueFactory(
    			new PropertyValueFactory<>("Id"));
    	
    	
    	//########################################################
    	//id column
    	tableColumnName.setMinWidth(100);
    	tableColumnName.setCellValueFactory(
    			new PropertyValueFactory<>("Name"));
    	tableColumnName.setCellFactory(TextFieldTableCell.<Bauteil>forTableColumn());
    	tableColumnName.setOnEditCommit(
    			new EventHandler<CellEditEvent<Bauteil,String>>(){

					@Override
					public void handle(CellEditEvent<Bauteil, String> event) {
						Bauteil b = event.getTableView().getItems()
								.get(event.getTablePosition().getRow());
						b.setName(event.getNewValue());
						DBConnection.updateIntoTable(b);
						log("Change Name of "+b.toString()+" from "+event.getOldValue()+" to "+event.getNewValue());
					}
    			});
    	//########################################################
    	//menge column
    	
    	tableColumnMenge.setMinWidth(50);
    	tableColumnMenge.setCellValueFactory(
    			new PropertyValueFactory<Bauteil, Integer>("Menge"));
    	tableColumnMenge.setCellFactory(TextFieldTableCell.<Bauteil, Integer>forTableColumn(new IntegerStringConverter()));
    	tableColumnMenge.setOnEditCommit(
    			new EventHandler<CellEditEvent<Bauteil,Integer>>(){

					@Override
					public void handle(CellEditEvent<Bauteil, Integer> event) {
						Bauteil b = event.getTableView().getItems()
								.get(event.getTablePosition().getRow());
						b.setMenge(event.getNewValue());
						DBConnection.updateIntoTable(b);
						log("Change Menge of "+b.toString()+" from "+event.getOldValue()+" to "+event.getNewValue());
					}
    			});

    	//########################################################
    	//ort column
    	tableColumnOrt.setMinWidth(100);
    	tableColumnOrt.setCellValueFactory(
    			new PropertyValueFactory<>("Ort"));
    	tableColumnOrt.setCellFactory(TextFieldTableCell.<Bauteil>forTableColumn());
    	tableColumnOrt.setOnEditCommit(
    			new EventHandler<CellEditEvent<Bauteil,String>>(){

					@Override
					public void handle(CellEditEvent<Bauteil, String> event) {
						Bauteil b = event.getTableView().getItems()
								.get(event.getTablePosition().getRow());
						b.setOrt(event.getNewValue());
						DBConnection.updateIntoTable(b);
						log("Change Ort of "+b.toString()+" from "+event.getOldValue()+" to "+event.getNewValue());
					}
    			});
    	//########################################################
    	//notes column
    	
    	tableColumnNotes.setMinWidth(100);
    	tableColumnNotes.setCellValueFactory(
    			new PropertyValueFactory<>("Notes"));
    	tableColumnNotes.setCellFactory(TextFieldTableCell.<Bauteil>forTableColumn());
    	tableColumnNotes.setOnEditCommit(
    			new EventHandler<CellEditEvent<Bauteil,String>>(){

					@Override
					public void handle(CellEditEvent<Bauteil, String> event) {
						Bauteil b = event.getTableView().getItems()
								.get(event.getTablePosition().getRow());
						b.setNotes(event.getNewValue());
						DBConnection.updateIntoTable(b);
						log("Change Notes of "+b.toString()+" from "+event.getOldValue()+" to "+event.getNewValue());
					}
    			});
    	tableView.setItems(data);
    }
    
    /**
     * InsertButton clicked
     * Check if values are valid
     * Insert Bauteil into DB
     */
    void insertBauteil() {
    	if (isAnyTextFieldEmpty()){
    		this.log("alle Felder müssen ausgefüllt werden (Ausnahme: Notes)");
    		return;
    	}
    	String BauteilName = tfName.getText();
    	int BauteilMenge = Integer.valueOf(tfMenge.getText());
    	String BauteilOrt = tfOrt.getText();
    	String BauteilNotes = tfNotes.getText() != null || tfNotes.getText().equals("") ? tfNotes.getText(): "";
    	
    	Bauteil b = new Bauteil(BauteilName, BauteilMenge, BauteilOrt, BauteilNotes);
    	String BauteilDaten = "Name: "+b.getName()+" Menge: "+b.getMenge()+" Ort: "+b.getOrt()+" Notes: "+b.getNotes();
    	this.log("inserting: "+BauteilDaten);
    	//this.log("DB connect");
    	DBConnection.insertIntoTable(b);
    	b.setId(DBConnection.getLastRow());
    	data.add(b);
    	clearTextFields();
    }
    
    /**
     * checks if the textfields are empty
     * @return true if any Textfield is empty except the notes textfield
     */
    boolean isAnyTextFieldEmpty() {
    	if(			tfName.getText().equals("") || tfName.getText() == null
    			|| 	tfMenge.getText().equals("") || tfMenge.getText() == null
    			|| 	tfOrt.getText().equals("") || tfMenge.getText() == null) {
    		return true;
    	}
    	
    	return false;
    }
    /**
     * empty all textfields
     */
    void clearTextFields() {
    	this.tfName.setText("");
    	this.tfMenge.setText("");
    	this.tfOrt.setText("");
    	this.tfNotes.setText("");
    }
    
    void log(String l) {
    	int tsHour = Integer.valueOf(ZonedDateTime.now().getHour());
    	int tsMinute = Integer.valueOf(ZonedDateTime.now().getMinute());
    	int tsSecond = Integer.valueOf(ZonedDateTime.now().getSecond());
    	        
    	String timestamp = tsHour+":"+tsMinute+":"+tsSecond+" ";
    	this.LogField.appendText(timestamp+l+"\n");
    }
    void deleteItemFromDB() {
    	Bauteil b = tableView.getSelectionModel().getSelectedItem();
    	String content = "id: "+b.getId()+" Name: "+b.getName()+"\n"
    			+"Menge: "+b.getMenge()+" Ort: "+b.getOrt()+"\n"
    			+"Notes: "+b.getNotes();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Bestätigen");
    	alert.setHeaderText("Datensatz löschen?");
    	alert.setContentText(content);
    	ButtonType buttonTypeYes = new ButtonType("Ja");
    	ButtonType buttonTypeNo = new ButtonType("Nein");
    	alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == buttonTypeYes) {
    		log("datensatz gelöscht");
    		DBConnection.delteBauteil(b);
        	data.remove(b);	
    	}
    	else {
    		log("löschen abgebrochen");
    	}
    	
    }
}
