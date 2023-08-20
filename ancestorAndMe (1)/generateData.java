import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.InputMismatchException;

 class generateData {
   private String nameOfFile; //initialize name of file String
   
   public generateData(String nameOfFile) {
     this.nameOfFile = nameOfFile; ////name of file inputed to generate random data
   }
   public void createFile() {
     try{
            //create FileWriter  object
            FileWriter outfile = new FileWriter(nameOfFile, true); //updates vs append
            int gen = (int)(Math.random()*5)+1; //random #of generations from [1,5]
            String lastName = randomName("lastNames.txt");
            outfile.write(randomName("firstNames.txt")+ " " + lastName + "\n"); //generate first random line (user name), calls method that generates random name

            for(int x=1; x<gen-1; x++) {
                int i = 0; //go through all generations except for last generation (which will also include ethnicity composition, not just name)
                
                while(i<Math.pow(2,x)) {
                  String middleName = "";
                  if(Math.random() >0.85) {
                    middleName = " " + randomName("firstNames.txt"); //low chance that there is a middle name (only if Math.radnom returns number greater that 0.85)
                  }
                  outfile.write(randomName("firstNames.txt")+ middleName + " " + lastName + "\n"); 
                  if(Math.random()<0.8) 
                    outfile.write(randomName("firstNames.txt")+ " " + lastName + "\n"); //high chance that the two parents share the same last name
                  else
                    outfile.write(randomName("firstNames.txt")+ " " + randomName("lastNames.txt") + "\n"); 
                  i+=2;
                  lastName=randomName("lastNames.txt");
                }
                
            }
            int r=0; //now work on populating final generation data
            while(r<Math.pow(2,gen-1)) {
              String middleName = "";
              lastName=randomName("lastNames.txt");
              if(Math.random() >0.85) { //same chances of having a middle name as mentioned above
                    middleName = " " + randomName("firstNames.txt");
                  }
              outfile.write(randomName("firstNames.txt")+ middleName + " " + lastName + ": " + randomEthnicity(100) + "\n"); //creates a random ethnciity composition (calls another method)
                  if(Math.random()<0.8) 
                    outfile.write(randomName("firstNames.txt")+ " " + lastName + ": " + randomEthnicity(100) + "\n"); //same chance for two parents to share a last name
                  else
                    outfile.write(randomName("firstNames.txt")+ " " + randomName("lastNames.txt") + ": " + randomEthnicity(100) + "\n"); //again random ethnicity string is created when randomEthncitiy(100) is called
                  r+=2;
                  
            }
            outfile.close(); //close writer
        }
        catch(IOException ex) { //if input output exception
            System.out.println("Error writing in a file.");
        }
        catch(InputMismatchException ex) { //if input mismatch exception
            System.out.println("Error writing in a file.");
        }

   }
   public String randomName(String fileName) throws FileNotFoundException{
     String name = "";
     Scanner sc = new Scanner(new FileReader(fileName)); //reads from the file "firstNames.txt" or "lastNames.txt"
     Random rand = new Random();
     int lineNumber = rand.nextInt(36); //number of lines in the firstNames.txt and lastNames.txt
     int n = 0;
     while(sc.hasNextLine()) {
       String line = sc.nextLine();
       if(lineNumber == n) {
         name = line; //random name selected in the file
       }
       ++n;
     }
     return name; //return that random name
   }

   public String randomEthnicity(double currPerc) throws FileNotFoundException{
     String ethnicity =""; //random ethncitiy
     if(currPerc<=0) {
       return ethnicity; //if ethnicity left to populate is less than 0, then full ethnicity String has been created
     }
     double[] ethnicPercent = {100,50,25,12.5,50,50,25,100}; //percent options for each ethnicity (possible percents)
     if(currPerc<25) { //if only <25% is left to populate, every population option should be less than 25%
       ethnicPercent[2]=12.5;
       ethnicPercent[1]=12.5;
       ethnicPercent[0]=12.5;
       ethnicPercent[4]=12.5;
       ethnicPercent[5]=12.5;
       ethnicPercent[6]=12.5;
       ethnicPercent[7]=12.5;

     }
     else if(currPerc<50) { //if only <50% is left to populate, every population option should be less than 50% -- second biggest option
       ethnicPercent[1]=25;
       ethnicPercent[0]=25;
       ethnicPercent[4]=25;
       ethnicPercent[5]=25;
       ethnicPercent[7]=25;
     }
     else if (currPerc<100) { //if only <100% is left to populate, every population option should be less than 100% -- second biggest option
       ethnicPercent[0]=50;
       ethnicPercent[7]=50;
     }
     int randomPercent = (int)(Math.random()*8); //random percent for int[] or possible percents
     Scanner sc = new Scanner(new FileReader("ethnicities.txt"));
     Random rand = new Random();
     int lineNumber = rand.nextInt(100); //size of ethnicity text file (num line)
     int n = 0;
     while(sc.hasNextLine()) {
       String line = sc.nextLine();
       if(lineNumber == n) {
         ethnicity = line; //generate random ethnicity
       }
       ++n;
     }
     double randomPerc = ethnicPercent[randomPercent];
     return ethnicity + "-" + randomPerc + "%," + randomEthnicity(currPerc-randomPerc); //add the new ethnicity with its percent (in the accepted data processing string format that ancestorAndMe was written in) + recur with currPerc-randomPerc (percent left to populate)

   }


}