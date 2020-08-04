package application;

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

	/** Method: submit
	 * checks to see if the password matches the one they set at the beginning
	 * and then goes back for the user to submit another vote
	 *
	 */
	public static String mainPassword;
	ArrayList<Voter> votes;
	ArrayList<Candidate> candidates;
	ArrayList<Integer> ranking;
	String after;
	String instructions;
	public void inPass(String pass) {
		mainPassword = pass;
	}
	public void initData(String password, ArrayList<Candidate> candidates, String instructions, String after, ArrayList<Voter> votes, ArrayList<Integer> ranking){
		this.candidates = candidates;
		this.instructions = instructions;
		this.after = after;
		this.votes = votes;
		this.ranking = ranking;
		System.out.println("The correct password is " + mainPassword);
	}
	public void submit(ActionEvent event) throws IOException{
		//If password is correct shows confirmation
		String checkedPassword = inputPassword.getText();
		if( checkedPassword.equals(mainPassword) ) {
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
			for(int k=0; k < candidates.size(); ++k){
				ranking.set(k, 0);
			}
			VotingController vc = loader.getController();
			vc.backData(mainPassword, instructions, after, candidates, votes, ranking);
			window.show();
		}
		else{
			Alert deny = new Alert(Alert.AlertType.ERROR, "INCORRECT PASSWORD: please allow an administrator to continue.");
			deny.show();
		}
	}

	/** Method: results
	 * their vote is submitted and with the correct password they can view the results
	 */
	public void results(ActionEvent event) throws IOException{
		String checkedPassword = inputPassword.getText();
		if( checkedPassword.equals(mainPassword) ) {
			//If password is correct shows confirmation
			Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "ACCESS GRANTED");
			confirm.show();
			//goes back to the voting screen
			FXMLLoader loader = new FXMLLoader();
			FileInputStream stream = new FileInputStream(new File("src/resources/results.fxml"));
			mainPane = loader.load(stream);
			//pane you are going to show
			Scene scene = new Scene(mainPane);
			//pane you are on
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			ResultsController rc = loader.getController();
			rc.initData(candidates, votes);
			window.setScene(scene);
			window.show();
		}
		else{
			Alert deny = new Alert(Alert.AlertType.ERROR, "INCORRECT PASSWORD: please allow an administrator to continue.");
			deny.show();
		}
	}


}
