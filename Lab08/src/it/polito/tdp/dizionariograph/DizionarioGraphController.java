package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	
	private Model model;
	private int numeroLettere;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumLettere;

    @FXML
    private TextField txtParola;
    
    @FXML
    private Button btnGrafo;

    @FXML
    private Button btnVicini;

    @FXML
    private Button btnGradoMax;

    @FXML
    private TextArea txtResult;
    
    @FXML
    private Button btnReset;

    @FXML
    void doGeneraGrafo(ActionEvent event) {
    	txtResult.clear();
    	try {
    		numeroLettere = Integer.parseInt(txtNumLettere.getText());
    		model.createGraph(numeroLettere);
    		
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Inserire il numero di lettere delle parole da cercare.");
  		
    	}
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtResult.clear();
    	txtNumLettere.clear();
    	txtParola.clear();
    	model.clear();
    	
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	txtResult.clear();
    	try {
    		numeroLettere = Integer.parseInt(txtNumLettere.getText());
    		String parola = txtParola.getText().toLowerCase();
    		if(parola.length()==numeroLettere) {
    			if(model.parolaPresente(parola) == false) {
        			txtResult.setText("Inserire una parola presente nel dizionario.");
        			return;
        		}

    			txtResult.appendText(""+model.displayNeighbours(parola).toString());
    		}else {
    			txtResult.setText("Aggiungere una parola di lunghezza "+ numeroLettere + ".\n");
    		}
    		
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Inserire il numero di lettere delle parole da cercare.");
  		
    	}
    	
    }

    @FXML
    void doTrovaGradoMax(ActionEvent event) {
    	txtResult.clear();
    	if(model.getGraph().vertexSet().size()==0) {
    		txtResult.setText("Cliccare su 'Genera Grafo' per creare il grafo prima.");
    	}
    	txtResult.appendText(model.findMaxDegree());
    	
    	
    }

    
    @FXML
    void initialize() {
        assert txtNumLettere != null : "fx:id=\"txtNumLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGrafo != null : "fx:id=\"btnGrafo\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGradoMax != null : "fx:id=\"btnGradoMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }
    
    public void setModel(Model m) {
    	this.model = m;
    }
}
