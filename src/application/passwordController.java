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
		System.out.println("The correct password is " + this.password);
	}
	public void submit(ActionEvent event) throws IOException{
		//If password is correct shows confirmation
		if( inputPassword.equals(password) ) {
			//If password is correct shows confirmation
			Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Vote Submitted");
			confirm.show();
			//goes back to the voting screen
			FXMLLoader loader = new FXMLLoader();
			FileInputStream stream = new FileInputStream(new File("src/resources/Sample.fxml"));
			mainPane = loader.load(stream);
			//pane you are going to show
			Scene scene = new Scene(mainPane);
			//pane you are on
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			VotingController vc = loader.getController();
			vc.initData(password, candidates, votes, instructions, after);
			window.show();
		}
		else{
			Alert deny = new Alert(Alert.AlertType.ERROR, "INCORRECT PASSWORD: please allow an administrator to continue.");
			deny.show();
		}
	}

	/* Method: results
	 * their vote is submitted and with the correct password they can view the results
	 */
	public void results(ActionEvent event) throws IOException{
		if( inputPassword.equals(password) ) {
			//If password is correct shows confirmation
			Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "ACCESS GRANTED");
			confirm.show();
			//goes back to the voting screen
			// FXMLLoader loader = new FXMLLoader();
			// FileInputStream stream = new FileInputStream(new File("src/resources/results.fxml"));
			// mainPane = loader.load(stream);
			//pane you are going to show
			// Scene scene = new Scene(mainPane);
			//pane you are on
			// Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			// window.setScene(scene);
			// ResultsController rc = loader.getController();
			// rc.initData(candidates, votes);
			// window.show();
		}
		else{
			Alert deny = new Alert(Alert.AlertType.ERROR, "INCORRECT PASSWORD: please allow an administrator to continue.");
			deny.show();
		}
	}


}
