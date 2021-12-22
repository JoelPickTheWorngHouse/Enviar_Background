package Enviar_Email;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable  {
	
	//Model
	
	private Task<Void> tarea;
	//View
	
	@FXML
    private Button Button_Cerrar;

    @FXML
    private Button Button_Enviar;

    @FXML
    private Button Button_Vaciar;

    @FXML
    private CheckBox CheckBox_SSL;

    @FXML
    private Label LabelSTMP;

    @FXML
    private Label Label_Asunto;

    @FXML
    private Label Label_Destinatario;

    @FXML
    private Label Label_Remitente;

    @FXML
    private Label Label_SSL;

    @FXML
    private PasswordField PassField_Contrasena;

    @FXML
    private TextArea TExtArea_Mensaje;

    @FXML
    private TextField TextField_Asunto;

    @FXML
    private TextField TextField_Destinatario;

    @FXML
    private TextField TextField_IP_Servidor;

    @FXML
    private TextField TextField_Puerto;

    @FXML
    private TextField TextField_Remitente;

    @FXML
    private VBox VBox_View;

    
    public Controller() throws IOException {

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VistaE.fxml"));
		loader.setController(this);
		loader.load();
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {

		
	}

    @FXML
    void Action_Cerrar(ActionEvent event) {
    	Node source=(Node) event.getSource();
		Stage stage=(Stage) source.getScene().getWindow();
		stage.close();

		    	tarea.cancel(true);

    }

    @FXML
    void Action_Check_SSL(ActionEvent event) {

    }

    @FXML
    private void Action_Enviar(ActionEvent event) {
    	tarea = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				try {
			    	Email email=new SimpleEmail();
			    	email.setHostName(TextField_IP_Servidor.getText());
			    	email.setSmtpPort(Integer.parseInt(TextField_Puerto.getText()));
			    	email.setAuthenticator(new DefaultAuthenticator(TextField_Remitente.getText(),PassField_Contrasena.getText()));
			    	email.setSSLOnConnect(CheckBox_SSL.isSelected());
			    	email.setFrom(TextField_Remitente.getText());
			    	email.setSubject(TextField_Asunto.getText());
			    	email.setMsg(TExtArea_Mensaje.getText());
			    	email.addTo(TextField_Destinatario.getText());
			    	email.send();
			    	
			    	Alert exito=new Alert (AlertType.INFORMATION);
			    	exito.setTitle("mensaje enviado");
			    	exito.setHeaderText("Mensaje enviado con éxito al destinatario");
			    	exito.show();
			    	
			    	TExtArea_Mensaje.clear();
				 	TextField_Asunto.clear();
				    TextField_Destinatario.clear();
				    TextField_IP_Servidor.clear();
				    TextField_Puerto.clear();
				    TextField_Remitente.clear();
				    PassField_Contrasena.clear();
			    	}catch(Exception e) {
			    		Alert error=new Alert(AlertType.ERROR);
			    		error.setTitle("Error *~*");
			    		error.setHeaderText("Error al enviar el mensaje");
			    		error.show();
			    		e.printStackTrace();
			    		
			    	}
				return null;
			
				
			}	
    	};
    	tarea.setOnSucceeded(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Éxito");
			alert.setHeaderText("Todo fue bien");
			alert.setContentText(e.getSource().getMessage());
			alert.showAndWait();
		});

		tarea.setOnFailed(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fallo");
			alert.setHeaderText("Algo no fue bien");
			alert.setContentText(e.getSource().getException().getMessage());
			alert.showAndWait();
		});
    			

		new Thread(tarea).start();	
    }

    @FXML
    void Action_Vaciar(ActionEvent event) {
    	
    	 	TExtArea_Mensaje.clear();
    	 	TextField_Asunto.clear();
    	    TextField_Destinatario.clear();
    	    TextField_IP_Servidor.clear();
    	    TextField_Puerto.clear();
    	    TextField_Remitente.clear();
    	    PassField_Contrasena.clear();
    	
    }

	    public VBox getView() {
			return VBox_View;
		}
	
}
