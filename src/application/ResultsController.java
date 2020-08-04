package application;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class ResultsController {
		//the axis holding the names of the candidates
		@FXML
		CategoryAxis Candidates;
		
		//the axis holding the number of votes
		@FXML
		NumberAxis numVotes;
		
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
}
