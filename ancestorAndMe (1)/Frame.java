import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Frame extends JFrame implements ActionListener{
  //initalize all display
  String font = "Times New Roman"; 
  private JLabel title, chooseRandomNum;
  private JEditorPane displayUserData;
  private JEditorPane displayAncestorData = new JEditorPane("text/html","");
  private JTextField fileName;
  private JButton playAround, wantToExperiment, endSimulation, chooseRandomNumber;
  private ancestorAndMe Trial;
  private JComboBox<String> enterNumber;
  private Container c;
  int length = 500;
  int width = 500;
  
  public Frame() {
    startFrame(); //call startFram method when constructor is initialized
    
  }
  public void startFrame() {

    //starting display 
    setTitle("Ancestor And Me");
    setSize(width,length);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(true);

    c=getContentPane();
    c.setLayout(null);

    //text that asks users to input file

    title = new JLabel("Input file name: ");
    title.setFont(new Font(font, Font.PLAIN, 20));
    title.setSize(200,30);
    title.setLocation(width/2-250,length/2-207);
    title.setHorizontalAlignment(JLabel.CENTER);

    c.add(title);

    //text field where users can type in the file name

    fileName = new JTextField();
    fileName.setFont(new Font(font,Font.PLAIN,15));
    fileName.setSize(100,20);
    fileName.setLocation(width/2-50,length/2-200);
    fileName.addActionListener(this);

    c.add(fileName);

    //button where they can conitnue on to the next scene

    playAround = new JButton("CALCULATE ETHNICITY");
    playAround.setFont(new Font(font, Font.PLAIN,10));
    playAround.setSize(175,30);
    playAround.setLocation(125-50,length/2-150);
    playAround.addActionListener(this);

    c.add(playAround);

    setVisible(true);

  }
 
  public void selectionFrame() {

  }


  public void actionPerformed(ActionEvent e) {
    //if button pressed to calculate ethnicity create ancestor and me object and calculate ethnicity with inputted file name
    if(e.getSource()==playAround && !fileName.getText().equals("")) {
      System.out.println("file name: " + fileName.getText());
      try {
        Trial = new ancestorAndMe(fileName.getText());
        Trial.printPersons();
        playAround.setVisible(false);
        title.setVisible(false);
        fileName.setVisible(false);
        //display new screen and also count number of lines in file for future JComboBox
        String[] numberOptions = new String[Trial.getNumLines()];
        for(int i =0;i<numberOptions.length;i++) {
          numberOptions[i]=String.valueOf(i+1);
        }
        enterNumber = new JComboBox<String> (numberOptions);

        //JEditorPane for text box to look nicer (displays ethnic breakdown)

        displayUserData = new JEditorPane("text/html","");
        
        displayUserData.setFont(new Font(font, Font.PLAIN, 10));
        displayUserData.setEditable(false);
        displayUserData.setText("<b>Your ethnicity composition: </b>" + Trial.getFamilyEthnicity(1) + "...." + "would you like to check your ancestor data?");

        displayUserData.setSize(300,300);
        displayUserData.setLocation(10,10);
        c.add(displayUserData);

        //button to further experiment

        wantToExperiment = new JButton("Yes");
        wantToExperiment.setFont(new Font(font, Font.PLAIN,10));
        wantToExperiment.setSize(70,30);
        wantToExperiment.setLocation(330,10);
        wantToExperiment.addActionListener(this);
        c.add(wantToExperiment);

        //button to exit program

        endSimulation = new JButton("No");
        endSimulation.setFont(new Font(font, Font.PLAIN,10));
        endSimulation.setSize(70,30);
        endSimulation.setLocation(400,10);
        endSimulation.addActionListener(this);
        c.add(endSimulation);

      }
      catch(FileNotFoundException ex) {
        System.out.println("these are confusing times"); //won't reach it logically, but needed nonetheless
        
      }     
    }
    if(e.getSource()==wantToExperiment) {

        //set up for new scene if user wants to continue experimenting
        displayUserData.setVisible(false);
        endSimulation.setVisible(false);
        wantToExperiment.setVisible(false);
        displayAncestorData.setVisible(false);

        //JComboBox to select random number associated with a Person object

        enterNumber.setFont(new Font(font, Font.PLAIN, 20));
        enterNumber.setSize(100,40);
        enterNumber.setLocation(width/2-50,length/2-200);
        c.add(enterNumber);

        //asking user to choose a random number

        chooseRandomNum = new JLabel("choose number: ");
        chooseRandomNum.setFont(new Font(font,Font.PLAIN,15));
        chooseRandomNum.setSize(200,30);
        chooseRandomNum.setLocation(width/2-215,length/2-200);

        c.add(chooseRandomNum);

        //button for user to move onto next scene get the ethnicity composition of Person object associated with random number selected

        chooseRandomNumber = new JButton("CONTINUE");
        chooseRandomNumber.setFont(new Font(font, Font.PLAIN,10));
        chooseRandomNumber.setSize(175,30);
        chooseRandomNumber.setLocation(125-50,length/2-150);
        chooseRandomNumber.addActionListener(this);
        c.add(chooseRandomNumber);

        //display 
        enterNumber.setVisible(true);
        chooseRandomNum.setVisible(true);
    }
    if(e.getSource() == endSimulation) { //if "no" or "end", end the simulation/exit the progra,
      System.exit(0);
    }
    if(e.getSource()==chooseRandomNumber) {
      //once they have chosen the random number and clicked "continue", set up for new scene
      chooseRandomNumber.setVisible(false);
      enterNumber.setVisible(false);
      chooseRandomNum.setVisible(false);

      //display ancestor (you randomly selected) ethncitiy compoisiton

      displayAncestorData = new JEditorPane("text/html","");
        
      displayAncestorData.setFont(new Font(font, Font.PLAIN, 10));
      displayAncestorData.setEditable(false);
      displayAncestorData.setText("<b>" + Trial.getFamilyName(Integer.parseInt(String.valueOf(enterNumber.getSelectedItem()))) + "</b>" + ": " +Trial.getFamilyEthnicity(Integer.parseInt(String.valueOf(enterNumber.getSelectedItem()))));
      displayAncestorData.setSize(300,300);
      displayAncestorData.setLocation(10,10);
      c.add(displayAncestorData);

      //ask user if they want to end simulation

      endSimulation = new JButton("End");
      endSimulation.setFont(new Font(font, Font.PLAIN,10));
      endSimulation.setSize(70,30);
      endSimulation.setLocation(400,10);
      endSimulation.addActionListener(this);
      c.add(endSimulation); 

      //button to choose another random number
     
      wantToExperiment = new JButton("Again");
        wantToExperiment.setFont(new Font(font, Font.PLAIN,10));
        wantToExperiment.setSize(70,30);
        wantToExperiment.setLocation(330,10);
        wantToExperiment.addActionListener(this);
        c.add(wantToExperiment);


    }
    
  }

}

