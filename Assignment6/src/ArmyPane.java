// Assignment #: 6
//         Name: Karl S. Miranda
//    StudentID: 1223636674
//      Lecture: TTh 0900 AM
//  Description: Create your army GUI 

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

//armyPane extends BorderPane 
public class ArmyPane extends BorderPane{
		//contains a list of heroes
		ArrayList<Hero> heroList;
		
		//Variables containing army Damage, Strength, and Charisma
		int totalDamage;
		int totalStrength;
		int totalCharisma;

		//Declare variables Label, VBox, and one Button
		Label armyInfo;
		VBox armyVBox; 
		Button loadHeroButton; 

		//ArmyPane 
		public ArmyPane(ArrayList<Hero> heroList) {
			this.heroList = heroList;

			//Initialize the instance variables for armyInfo, ArmyVBox, and loadHeroButton 
			armyInfo = new Label();
			armyVBox = new VBox();
			loadHeroButton = new Button("Load Heroes/Clear Selection");
			
			//Bind loadHeroButton to its button handler
			loadHeroButton.setOnAction(new LoadHeroesButtonHandler());
			
			// Organize components to their positions on BorderPane
			this.setTop(armyInfo);
			this.setBottom(loadHeroButton);
			this.setLeft(armyVBox);

		}
		
		//LoadsHeroesButtonHandler
		private class LoadHeroesButtonHandler implements EventHandler<ActionEvent> {

			@Override
			public void handle(ActionEvent event) {
				
				//Clear the VBox before adding list of heroes
				armyVBox.getChildren().clear();
				
				//loop through hero list and creating CheckBoxes with each hero info 
				for (int i = 0; i < heroList.size(); ++i ) {
					CheckBox box = new CheckBox(heroList.get(i).toString()); 			//CheckBox with hero info
					box.setOnAction(new CheckBoxHandler(heroList.get(i)));				//Bind each CheckBox with CheckBoxHandler
					armyVBox.getChildren().add(box);									//add each box to armyVBox
					
				}
			}
		}
		
		//CheckBoxHandler
		private class CheckBoxHandler implements EventHandler<ActionEvent> {

			Hero hero;
			
			//CheckBoxHandler, pass in a Hero object so it can be accessed later
			public CheckBoxHandler(Hero _hero) {
				this.hero = _hero;
			}

			@Override
			public void handle(ActionEvent event) {
				
				//get the CheckBox that triggered the event, cast it to CheckBox
				CheckBox box = (CheckBox) event.getSource(); 
				
				//If the CheckBox was selected add damage, charisma, and strength to total amount
				if (box.isSelected()) {
					totalDamage += hero.getDamage();
					totalCharisma += hero.getCharisma();
					totalStrength += hero.getStrength(); 
				}
				//When CheckBox is unselected then subtract damage, charisma, and strength from total amount
				else {
					totalDamage -= hero.getDamage(); 
					totalCharisma -= hero.getCharisma(); 
					totalStrength -= hero.getStrength(); 
				}

				//Set the armyInfo label to display totalDamage, totalStrength, and totalCharisma 
				String newString = ("Total Damage: " + totalDamage + "\t\tTotal Strength: " + totalStrength + "\tTotal Charisma: " + totalCharisma); 
				armyInfo.setText(newString);


			}
		}
}
