/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader
    private boolean creatoGrafo = false;

    @FXML
    void doComponente(ActionEvent event) {
    	
    	txtResult.clear();
    	if (!this.creatoGrafo) {
    		txtResult.appendText("Creare grafo!\n");
    	}
    	
    	Album a = this.cmbA1.getValue();
    	
    	if (a==null) {
    		txtResult.appendText("Inserire un album.\n");
    	}
    	Set<Album> connessa = this.model.getConnessa(a);
    	double dTot = this.model.durataTotConnesssa(connessa);
    	
    	txtResult.appendText("Componente connessa di " + a.getTitle() + ": \nDimensione componente = " + connessa.size()+"\nDurata componente = " + dTot);
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	String input = txtDurata.getText();
    	if (input.compareTo("")==0) {
    		txtResult.appendText("Non e' stato inserito un valore\n");
    	}
    	double d = 0.0;
    	try {
    		d = Double.parseDouble(input);
    	}catch (NumberFormatException e ) {
    		txtResult.appendText("Non e' stato inserito un valore accettabile.\n");
    		return;
    	}
    	this.model.creaGrafo(d);
    	this.creatoGrafo = true;
    	
    	txtResult.appendText("Grafo creato!\n#Vertici: " + this.model.getNumVertici()+"\n#Arci: " + this.model.getNumArchi()+"\n");
    	
    	this.cmbA1.getItems().addAll(this.model.getVertici());
    }

    @FXML
    void doEstraiSet(ActionEvent event) {
    	
    	txtResult.clear();
    	if (!this.creatoGrafo) {
    		txtResult.appendText("Creare grafo!\n");
    	}
    	
    	Album a = this.cmbA1.getValue();
    	String input = txtX.getText();
    	if (a==null || input.compareTo("")==0) {
    		txtResult.appendText("Inserire un valore.\n");
    	}
    	
    	double dTot = 0.0;
    	
    	try {
    		dTot = Double.parseDouble(input);
    	}catch (NumberFormatException e ) {
    		txtResult.appendText("Inserito valore non accettabile.\n");
    		return;
    	}
    	
    	Set<Album> set = this.model.getSet(a, dTot);
    	txtResult.appendText("Ricorsione : \nDuratatotale del set trovato " + this.model.durataTotConnesssa(set)+"\n");
    	for (Album al : set) {
    		txtResult.appendText(al.toString()+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
