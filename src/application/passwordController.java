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
import javafx.scene.text.*;

/* passwordController checks the password against what was put into the console,
 * and links to the two other FXMLs and controllers.
 */
public class passwordController {

	//the field where the user inputs their password
	@FXML
	PasswordField inputPassword;

	@FXML
	AnchorPane mainPane;

	@FXML
	Text t = new Text();

	/* Method: submit
	 * checks to see if the password matches the one they set at the beginning
	 * and then goes back for the user to submit another vote
	 *
	 */
	public static String mainPassword;
	ArrayList<Integer> ranking;
	String after;
	String instructions;

	public static void initPassword(String password){
		mainPassword = password;
	}
	public void initData(String password, String instructions, String after, ArrayList<Integer> ranking){
		initPassword(password);
		this.instructions = instructions;
		this.after = after;
		this.ranking = ranking;
		System.out.println("The correct password is " + mainPassword);
		t.setText(after);
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
			VotingController vc = loader.getController();
			vc.initData(mainPassword, instructions, after);
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
			loader.getController();
			window.setScene(scene);
			window.show();
		}
		else{
			Alert deny = new Alert(Alert.AlertType.ERROR, "INCORRECT PASSWORD: please allow an administrator to continue.");
			deny.show();
		}
	}


}
