package application;
//Author: Emma Yanoviak -  hrb980

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class passwordController {

	//the field where the user inputs their password
	@FXML
	PasswordField inputPassword;

	@FXML
	AnchorPane mainPane;

	/* Method: submit
	 * checks to see if the password matches the one they set at the beginning
	 * and then goes back for the user to submit another vote
	 *
	 */
	String password;
	ArrayList<Voter> votes;
	ArrayList<Candidate> candidates;
	String after;
	String instructions;
	public void initData(String password, ArrayList<Candidate> candidates, String instructions, String after, ArrayList<Voter> votes){
		this.password = password;
		this.candidates = candidates;
		this.instructions = instructions;
		this.after = after;
		this.votes = votes;
	}
	public void submit(ActionEvent event) throws IOException{
		//FIXME: I need to get the password from the Initialize class and check to see if it matches what they input

		//If password is correct shows confirmation
		new Alert(Alert.AlertType.CONFIRMATION, "Vote Submitted");
		if( inputPassword.equals(password) ) {
			//If password is correct shows confirmation
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vote Submitted");
			alert.show();
			//goes back to the voting screen
			mainPane = FXMLLoader.load(getClass().getResource("/Sample.fxml"));
			//pane you are going to show
			Scene scene = new Scene(mainPane);
			//pane you are on
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		}
		//FIXME: if its not the correct password I need to display an error
		//new Alert(Alert.AlertType.ERROR, "Incorrect Password. Try again.");
	}

	/* Method: results
	 * their vote is submitted and with the correct password they can view the results
	 */
	public void results(ActionEvent event) throws IOException{
		//goes to the results scene

	}


}
