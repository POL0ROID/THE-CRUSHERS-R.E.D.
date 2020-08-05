package application;
import java.util.ArrayList;

import javafx.fxml.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.event.*;
import javafx.collections.*;

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

		ArrayList<Integer> totVotes;

		static int roundNum = 1;

		public void round() {
	        bc.setTitle("Round 1");
	        bc.setAnimated(false);
	        for(Candidate c : Main.model.getCandidates())
	        Candidates.getCategories().add(c.name);
	        Main.model.resultsSetup();
		    for(int i=0; i < Main.model.getCandidates().size(); i++){
		        	xychart.getData().add(new XYChart.Data<String, Number>
		        	(Main.model.getCandidates().get(i).name,
		        			Main.model.getCandidates().get(i).totVotes));
		    }

		    bc.getData().add(xychart);
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
