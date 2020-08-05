package application;
import java.util.ArrayList;

import javafx.fxml.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.event.*;

/* ResultsController populates and displays the results graph,
 * iterates through rounds,
 * and populates and displays again.
 */
public class ResultsController {
		//the axis holding the names of the candidates
		@FXML
		CategoryAxis Candidates = new CategoryAxis();

		//the axis holding the number of votes
		@FXML
		NumberAxis numVotes = new NumberAxis();

		@FXML
		BarChart<String,Number> bc = new BarChart<String,Number>(Candidates, numVotes);

		@FXML
        XYChart.Series<String, Number> xychart = new XYChart.Series<String, Number>();

		ArrayList<Voter> votes;
		ArrayList<Candidate> candidates;
		ArrayList<Integer> totVotes;

		static int roundNum = 1;

		public void initData(ArrayList<Candidate> candidates, ArrayList<Voter> votes){
			this.candidates = candidates;
			this.votes = votes;
			round();
		}
		public void round() {
	        bc.setTitle("Round 1");
	        resultsSetup(candidates, votes);
		    for(int i=0; i < candidates.size(); i++){
		        	xychart.getData().add(new XYChart.Data<String, Number>(candidates.get(i).name,
		        			candidates.get(i).totVotes));
		    }
		    bc.getData().add(xychart);
		}
  	public void resultsSetup(ArrayList<Candidate> candidates, ArrayList<Voter> votes) {
  		for (Voter v: votes)
  			for(int j=0; j < v.ranking.size(); j++){
  				if(v.ranking.get(j)==1)
  					candidates.get(j).totVotes++;
  				System.out.println(candidates.get(j).name);
  				System.out.println(candidates.get(j).totVotes);
   			}
  		for(Voter w: votes)
  			System.out.println(w.ranking);
			//the above line helps us keep track of our input data!
  	}
  	//The below function should be called when an administrator hits the next round button.
  	public void resultsTabulate(ActionEvent event) {
  		++roundNum;
  		for(Voter w: votes) {
  			int lowestCand = Main.model.lowestCand(candidates, roundNum);
  			candidates.get(lowestCand).eliminated = true;
  			for(int k=0; k < candidates.size(); k++) {
  				if(candidates.get(k).eliminated == true) {
  						for(int r=roundNum; r < candidates.size(); r++) {
  							boolean brake = false;
  							for(int s=0; s < candidates.size(); s++) {
  								if(w.ranking.get(s) == r
  										&& candidates.get(s).eliminated == false) {
  									brake = true;
  									candidates.get(s).totVotes++;
  									break;
  								}
  							}
  							if(brake)
  								break;
  						}
  					candidates.get(k).totVotes=-1;
  				}
  			}
  		}
  		/**
  		 * very important! this print is the only way rounds 2+ are displayed currently.
  		 */
  		for(Candidate c: candidates)
  			System.out.println(c.totVotes);
  		// bc.getData().clear();
  		// bc.layout();
  		// bc.getData().addAll( xychart );
  	}
}
