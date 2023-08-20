import java.util.*;
import java.text.DecimalFormat;

class Person {
  //initalize all relevant data types/structures
  private String personEthnicities; 
  private String personName;
  private String[][] personEthnicityData = null;
  private ArrayList<List<String>> newData = new ArrayList<List<String>>();
  private int genNum;

  public Person(String ethnicities, int genNum) {
    personEthnicities = ethnicities; //create Person object with the String ethnicity and generation number as parameters
    this.genNum = genNum;
    constructPerson(); //call constructPerson method to populate the double arrayList
  }
  public double genNum() {
    return (double)genNum;
  }
  public void constructPerson() {
    personName = personEthnicities.split(": ")[0];
    if(personEthnicities.split(": ").length>1){
      String[] personData = personEthnicities.split(": ")[1].split(","); //this will populate the data for the last generation who has their ethnicity information stored
      personEthnicityData = new String[personData.length][2];
      for(int i = 0; i<personData.length;i++) { //for loop to go through each ethnicity in personObject, seperating percent and ethnicity name
        personEthnicityData[i] = personData[i].split("-");
        newData.add(Arrays.asList(personEthnicityData[i]));
      } //if possible mannipulat the double array (easier), then transfer to the array list
    } 
  }
  public String getName() {
    return personName; //return name stored in Person object
  }

  public void addEthnicity(String[] e) {
    newData.add(Arrays.asList(e)); // add ethnicity to Person object
  }
  
  

  public void updateEthnicityData() {
    ArrayList<List<String>> tempArray = new ArrayList<List<String>>(); //create a tempArry just to keep the newData and tempArray clean (same 2D arrayList though)
    tempArray = newData;
    double total = 0;
    DecimalFormat df = new DecimalFormat("#.###"); //format so not too many decimal places show
    for(List<String> s : tempArray ) {
      total+=Double.parseDouble(s.get(1).substring(0,s.get(1).length()-1)); //get totalPercent
    }
    for(List<String> s : tempArray) {
      s.set(1,df.format(Double.parseDouble(s.get(1).substring(0,s.get(1).length()-1))/total*100)+"%"); //add all the correct percents of the parents to the child (all in correct proportions)
    }
    combineDuplicates(tempArray); //combine duplicates (those of the same ethnicity name)
  }

  public void combineDuplicates(ArrayList<List<String>> temp) {
    DecimalFormat df = new DecimalFormat("#.###"); //no more than 3 decimal places show
    for(int i =0; i<temp.size();i++) {
      ArrayList<List<String>> removeSame = new ArrayList<List<String>>(); //go through entire array and find those that are the same, and add it to remove same array list
      double combPerc = Double.parseDouble(temp.get(i).get(1).substring(0,temp.get(i).get(1).length()-1)); //the added percent
      for(int j =0;j<temp.size();j++) {
        if(j!=i) {
          if(temp.get(i).get(0).equals(temp.get(j).get(0))) {
            combPerc += Double.parseDouble(temp.get(j).get(1).substring(0,temp.get(j).get(1).length()-1));
            removeSame.add(temp.get(j)); //if same ethncitiy, combine their percents, and add the duplicate to the removeSame arrayList
          }
        }
      }
        temp.get(i).set(1,df.format(combPerc)+"%");
        for(List<String> m : removeSame) {
        temp.remove(m); //go through array again and remove the same value (remove by value not index)
      }

      }
      newData = temp; //update newData 2D arrayList

    
  }
  
  public String getEthnicity() {
    updateEthnicityData(); //make sure the ethnicity has been properly updated and formated
    String ethnicity = "";
    if(newData != null) {
      for(List<String> s: newData) {
       
        for(String m : s) {
          ethnicity+= m + " "; //get the ethnicity data of the Person object (combine in String format)
        }

      }
    }
    return ethnicity; //retun ethnicity data in String format
  }

}