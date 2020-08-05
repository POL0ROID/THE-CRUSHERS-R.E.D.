package application;
import java.util.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.*;
/**
 * The Main class sets up the initialization system that operates through the terminal,
 * which configures the program to the election as desired by the administrator.
 * After, it opens the window that end users will see on voting machines.
 */
public class Main extends Application {
	int numCandidates;
	String password;
	String instructions;
	String after;
	static final Model model = new Model();
	@Override
	public void start(Stage primaryStage) {
		String password;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to the R.E.D initalization system.");
		System.out.println("For security purposes, "
				+ "please make sure that "
				+ "only the voting booths are connected to the administration terminal, "
				+ "that the voting booths are not connected to anything else, "
				+ "that all devices have not been and will not be tampered with, "
				+ "and that all unused ports are disabled. ");
		while(true) {
			System.out.print("Enter the unique password you will use to finalize a vote "
					+ "and see results: ");
			if(sc.hasNextLine()) {
				password = sc.nextLine();
				break;
			}
			System.out.println("ERROR: No password input.");
		}
		passwordController.initPassword(password);
		while(true) {
			System.out.print("Enter the instructions you want end users to see; "
					+ "for example, \"Rank candidates in order of your preference.\": ");
			if(sc.hasNextLine()) {
				instructions = sc.nextLine();
				break;
			}
		}
		while(true) {
			System.out.print("Enter the message you want end users to see after they have voted; "
					+ "for example, "
					+ "\"Your vote has been accepted. "
					+ "Please allow an administrator to reconfigure the machine.\": ");
			if(sc.hasNextLine()) {
				after = sc.nextLine();
				break;
			}
		}
		sc.close();
		try {
			FXMLLoader loader = new FXMLLoader();
			FileInputStream stream = new FileInputStream(new File("src/resources/Sample.fxml"));
			AnchorPane root = (AnchorPane)loader.load(stream);
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			VotingController vc = loader.getController();
			vc.initData(password, instructions, after);
			primaryStage.setTitle("Ranked Choice Election Database - THE CRUSHERS");
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		} //this configures the javafx interface and passes an instance of the first controller
	}
	//dictated by java, but the superclass uses start
	public static void main(String[] args) {
		launch(args);
	}
}