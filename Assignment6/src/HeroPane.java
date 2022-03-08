// Assignment #: 6
//         Name: Karl S. Miranda
//    StudentID: 1223636674
//      Lecture: TTh 0900 AM
//  Description: Create your army GUI 


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class HeroPane extends HBox{
	    //Contains a list of heroes
		ArrayList<Hero> heroList;

		//Save current Hero Type
		String selectedHeroType;


		//Main layout components
		TextArea rightTextArea;
		VBox leftVBox;
		ComboBox<String> heroTypeComboBox;
		ImageView imageView;

		//Declared GridPane and 4 Labels 
		GridPane inputPane;
		Label name;
		Label strength;
		Label charisma;
		Label damage; 
		
		//Declared 4 TextFields and 1 random Button 
		TextField nameField;
		TextField strengthField;
		TextField charismaField;
		TextField damageField;
		Button randButton; 
		
		//Declared addHero Button and 1 red Label
		Button addHero;
		Label redLabel; 
		
		
		//Define window size
		public static final int WINSIZE_X = 950, WINSIZE_Y = 600;


		// Constructor - what to do when HeroPane is first created
		public HeroPane(ArrayList<Hero> heroList) {

			//Assign the heroList passed into this constructor
			this.heroList = heroList;

			//Initialize main layout components
			this.leftVBox = new VBox();
			this.rightTextArea = new TextArea();
			
			//Setting up ComboBox
			String[] heroType = { "Mage", "Fighter", "Unicorn", "Zombie" };
			heroTypeComboBox = new ComboBox<String>();
			heroTypeComboBox.setValue("Hero Type");
			heroTypeComboBox.getItems().addAll(heroType);
			//heroTypeComboBox.getSelectionModel().selectFirst(); 
			heroTypeComboBox.setOnAction(new HeroTypeComboBoxHandler());
			leftVBox.getChildren().add(heroTypeComboBox);

			//Initialized Labels with their corresponding Strings
			name = new Label("Name");
			strength = new Label("Strength");
			charisma = new Label("Charisma");
			damage = new Label("Damage"); 
			
			//Initialized new TextFields
			nameField = new TextField();
			strengthField = new TextField();
			charismaField = new TextField();
			damageField = new TextField();
			
			//Set damageField TextField as not editable 
			damageField.setEditable(false);
			
			//Initialized "Random" button 
			randButton = new Button("Random");
			

			
			//Initialize the instance variables and set Label color to RED
			redLabel = new Label();
			redLabel.setTextFill(Color.RED);
			
			//Initialized "Add New Hero!!!" button 
			addHero = new Button("Add New Hero!!!"); 
			
			// Organized Labels, TextFields, and Button onto the GridPane
			inputPane = new GridPane();
			inputPane.add(name, 0, 0);
			inputPane.add(nameField, 1, 0);
			inputPane.add(strength, 0, 1);
			inputPane.add(strengthField, 1, 1);
			inputPane.add(charisma, 0, 2);
			inputPane.add(charismaField,1, 2);
			inputPane.add(damage, 0, 3);
			inputPane.add(damageField,1, 3);
			inputPane.add(randButton, 3, 3);

			//Binded both addHero and Random button to their respective handlers. 
			addHero.setOnAction(new AddNewHeroButtonHandler());
			randButton.setOnAction(new RandomButtonHandler());
			

			//Add GridPane, “Add Hero” Button, and red Label to leftVBox
			leftVBox.getChildren().add(inputPane);
			leftVBox.getChildren().add(addHero);
			leftVBox.getChildren().add(redLabel);
			
			//VBox layout alignment
			inputPane.setHgap(20);
			leftVBox.setPadding(new Insets(40, 50, 0, 50));
			leftVBox.setSpacing(40);
			leftVBox.setAlignment(Pos.TOP_CENTER);
			leftVBox.setPrefWidth(WINSIZE_X / 2);

			//Setting up ImageView
			imageView = new ImageView();
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(100);
			leftVBox.getChildren().add(imageView);
			FileInputStream input;
			try {
				input = new FileInputStream("unicorn.png");
				Image image = new Image(input);
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				imageView.setImage(null);
			}
			
			//Add main components to "Add Hero" tab
			this.getChildren().addAll(leftVBox, rightTextArea);
		}
		
		// Generate random damage value (50 <= damage <= 100)
		private class RandomButtonHandler implements EventHandler<ActionEvent> {
			public void handle(ActionEvent event) {
				//Max and min integer range
				int min = 50;
				int max = 100;
				
				//If damageField is empty conditional statement
				if(damageField.getText().trim().isEmpty()) {
					int value = (int)(Math.random() * (max - min + 1) + min);    //random number in 50-100 range
					String newValue = String.valueOf(value);					 //parse integer value to string
				
					damageField.setText(newValue);								 //setDamage field text to random int number
					redLabel.setText("Damage is already generated");			 //Set red label text 
				}
			}
		}


		//"Add New Hero" button handler 
		private class AddNewHeroButtonHandler implements EventHandler<ActionEvent> {

			// This method will be called once we click the button
			public void handle(ActionEvent event) {

				// Create 4 String variables and assign them to the values retrieved from their TextFields
				String nameInput = nameField.getText();
				String strengthInput = strengthField.getText();
				String charismaInput = charismaField.getText();
				String damageInput = damageField.getText(); 

				try {
					//If hero type is not selected throw exceptions
					if (selectedHeroType == null) {
						throw new Exception("Hero type is not yet selected");
					}
					
					//If one of the textField is empty throw exception
					if (nameInput.trim().isEmpty() || strengthInput.trim().isEmpty() || charismaInput.trim().isEmpty() || damageInput.trim().isEmpty()) {
						throw new Exception("At least one of the text fields is empty");
					}

					//Loop through heroList to verify it's not a duplicate hero, throw exception
					for (int i = 0;i < heroList.size(); ++i) {
						String value = String.valueOf(heroList.get(i));
						if (value.contains(nameInput)){
							throw new Exception("Hero existed!");
						}
					}
					
					//Parse strength, charisma, and damage input to integer values
					int newStrength = Integer.parseInt(strengthInput);
					int newCharisma = Integer.parseInt(charismaInput);
					int newDamage = Integer.parseInt(damageInput);
					
					//Check if Strength or Charisma are negative numbers
					if (newStrength < 0 || newCharisma < 0) {
						throw new Exception("Both Strength and Charisma must be positive numbers");
					}

					//Check if the sum of Strength and Charisma exceeds 100. 
					int sumOfTwo = newStrength + newCharisma;
					if (sumOfTwo > 100) {
						throw new Exception("The sum of strength and charisma must be less or equal to 100");
					}

					
					//Input is valid, now create new Hero object 
					String newSelectedHeroType = String.valueOf(selectedHeroType);		//String value for hero type
					String newNameInput = String.valueOf(nameInput);					//String value for name input
					
					//New Hero object 
					Hero newHero = new Hero(newNameInput, newSelectedHeroType, newStrength, newCharisma, newDamage);
					heroList.add(newHero);   											//Adding hero object to heroList

					//Set the Red Label to "Hero added successfully" and empty all TextFields
					redLabel.setText("Hero added successfully"); 
					nameField.clear();
					strengthField.clear();
					charismaField.clear();
					damageField.clear();

					//Calls updateTextArea() to update heroes list
					updateTextArea(); 

				} catch (NumberFormatException exception) {
					// set RED LABEL to "At least one of the text fields is in the incorrect format"
					redLabel.setText("At least one of the text fields is in the incorrect format");
					

				} catch (Exception exception) {
					//The message that we passed in "throw new Exception(MESSAGE);" above
					// Set the value of the RED LABEL to exception.getMessage() to display error message 
					redLabel.setText(exception.getMessage()); 
				}

				}
		}
		

		//Create a String containing all hero information
		private void updateTextArea() {
			//clears rightTextArea before displaying list of heroes
			rightTextArea.clear(); 
			
			//Loop through each hero in heroList
			for (int i = 0; i < heroList.size(); ++i) {
				Hero realHero = heroList.get(i); 
				
				String newString = realHero.toString();    					//Calls hero class toStrings method 
				String newline = "\n"; 										//new line for spacing purposes 
				rightTextArea.appendText(newString);						//Add hero info to rightTextArea
				rightTextArea.appendText(newline); 							//Add newline for spacing 
			}
		}
		
		
		//Button handler for comboBox 
		private class HeroTypeComboBoxHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				selectedHeroType = heroTypeComboBox.getSelectionModel().getSelectedItem();
				FileInputStream input;
				try {
					input = new FileInputStream(selectedHeroType.toLowerCase() + ".png");
					Image image = new Image(input);
					imageView.setImage(image);
				} catch (FileNotFoundException e) {
					imageView.setImage(null);
				}

			}
		}

		
		
}
	
