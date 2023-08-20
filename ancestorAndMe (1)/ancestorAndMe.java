import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

class ancestorAndMe {
  //initializing variables/data structures 
  private HashMap<Integer, Person> familyTree = new LinkedHashMap<Integer, Person>(); //collects all family members data (stored in Person object)
  private String readFile;
  private int numLines;
  
  public ancestorAndMe(String readFile) throws FileNotFoundException  {
    this.readFile = readFile; //constructor takes in parameter of file name, accesible by rest of class
  
    
  }
  public int getNumLines() {
    return numLines; //get number of lines in file

  }
  public String getFamilyEthnicity(int n) {
    return  familyTree.get(n).getEthnicity(); //get family ethnicity data composition
  }

  public String getFamilyName(int n) {
  return familyTree.get(n).getName(); //get name of family member

  }

  public void createPersons() throws NullPointerException {
    Scanner sc = null;
    try{
      sc = new Scanner(new FileReader(readFile)); //reads file
      
      int genNum = 1;
      int peoplePerGen = 1;
      int line =2; //sets starting values for generation number, people in generation, and line number
      if(sc.hasNextLine()) 
        familyTree.put(1,new Person(sc.nextLine(), 0)); //add to HashMap
      else {
        generateData newFileName = new generateData(readFile); //if data file is empty, populate empty data file
        newFileName.createFile();
        createPersons(); //create the person files again
        return;
      }
      while(sc.hasNextLine()) {
        familyTree.put(line,new Person(sc.nextLine(), genNum)); //add to the HashMap a new person object
        if(peoplePerGen>=(Math.pow(2,genNum)))  { //if all people in one generation filled
          genNum++; //move onto a new generation
          peoplePerGen=0;
        }
        peoplePerGen++;
        line++; //incrememnt line
      }
      numLines = line-1; //since line is incremented one extra time
      inheritTraitsRecur(genNum-1,familyTree.size(),1); //recursive method called
      
    }
    catch(FileNotFoundException e) {
      System.out.println("generating fake data ..."); //if no data file exists, generate fake one
      generateData newFileName = new generateData(readFile);
      newFileName.createFile(); 
      createPersons(); //run this method again with a data file existing now
      return;
    }
  }

  public void inheritTraitsRecur(int gen, int lineNum, int pos) {
    if (gen<0) //if all generations have been dealt with
      return;
    else if(lineNum<=(Math.pow(2,gen))) {
      inheritTraitsRecur(gen-1, lineNum, 0); //if all people in one gen have been dealt with
    } 

    else {
      String addPercent = familyTree.get(lineNum).getEthnicity();
      String[] addPerc = addPercent.split("\\s+"); //get the parent ethnicity and percent associated with that
      for(int i = 0;i<addPerc.length-1;i+=2) {
        String[] insertOneEthnic = {addPerc[i],addPerc[i+1]};
        familyTree.get((int)Math.pow(2,gen)-pos).addEthnicity(insertOneEthnic); //add parent ethnicity to the childrens Person object (call Person object "addEthnictiy()" method)
      }
        
      if(lineNum%2!=0)
        inheritTraitsRecur(gen, lineNum-1, pos); //if one parent has been added to one child, move onto the next parent for the same child
      else
        inheritTraitsRecur(gen, lineNum-1, pos+1); //if both parents have been added to one child, move onto the next parent for the next child
        }
    }
      
  

  public void printPersons() throws FileNotFoundException{ //for testing  to createPersons
    createPersons(); //populate HashMaps
    for (Person i : familyTree.values()) { //go through all the people objects in the hashMap
      System.out.println(i.getName()); //print their name
      System.out.println(i.getEthnicity()); //print their ethnic composition
      System.out.println();
      }
  }
  
  }
  
