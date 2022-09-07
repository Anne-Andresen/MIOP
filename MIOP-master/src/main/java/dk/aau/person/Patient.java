package dk.aau.person;

import dk.aau.App;
import dk.aau.biomark.ResultBiomarker;
import dk.aau.database.DatabaseManipulator;
import dk.aau.scorer.Score;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Patient extends Person {   // gør patient til subklasse til Person-klassen Nedar
    
    // Attributter
    private static long cpr;
    private static String gender;
    private static int age;
    public static Stage dialogStage;
    public int currentYear;
    public int currentMonth;
    public int currentDay;

    
    // constructor for tempPatient. Bliver CPR-nummeret ikke verificeret, bliver den "rigtige" patient instans aldrig oprettet
    // hvis CPR nummeret bliver verificeret, bliver den "rigtige" patinet construktor kaldt, og en instans af den bliver oprettet
    // inkapsulering
    public Patient(){
        // evt. nulstil her
        //    Scanner myInput= new Scanner(System.in);
        //    System.out.println("Indtast CPR-nummer:");
        //    long CPRnummerHent = myInput.nextLong();
        
        verifyCpr(cpr);
        
        //    myInput.close(); // scannere skal lukkes 
    }
    
    public static void setCprNumber(long cprNumber){ /*Indkapsulering*/
        cpr = cprNumber;
    }
    
    /*
    Constructor for den rigtige "Patient". Denne construtor bliver kaldt
    når data tilhørende patienten hentes.
    */
    public Patient(String FirstName, String LastName) { 
        firstName = FirstName;
        lastName = LastName;
        calcAge();
        calcGender();
        
        
    }
    
    
    private void verifyCpr(long CPRnumber){
        int length = String.valueOf(CPRnumber).length();            // Laver en int der er ekvivalent til længden af det  laver det om til string, tæller antallet af karaktere og tager det som længden  
        if (length != 10){                                          // indtastede CPR nummer og tjekker at dets længde 
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ugyldigt CPR-nummer format");
            alert.setHeaderText("CPR-nummeret skal være 10 tal.");
            alert.setContentText("Indsæt alle 10 cifre i CPR-nummeret.");
            
            alert.showAndWait(); /*erroe message waits for u to do something */
        } else {                                                    // Hvis længden er 10, gemmes CPRnummeret til senere metoder
            cpr=CPRnumber;                                    
            getHealthCaredata(cpr);                                 // Henter Sundhedsdata for patienten hent sundhedsdata
            if (firstName == null){                                 // Hvis patineten IKKE HAR et fornavn, indikerer det at ..
                Alert alert = new Alert(AlertType.ERROR);           // .. der ikke er en person med det CPR-nummer, og der laves en fejlmeddelelse
                alert.initOwner(dialogStage);
                alert.setTitle("Ugyldigt CPR-nummer");
                alert.setHeaderText("Ingen patienter med det indtastede CPR-nummer.");
                alert.setContentText("CPR-nummeret er ikke registreret, eller du har ikke adgang til det.\nPrøv igen eller kontakt administrationen.");
                
                alert.showAndWait();             
            } else {                                            // succes                                
                
                // INSERT HERE
                
                /*
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initOwner(dialogStage);
                alert.setTitle("Succes");
                alert.setHeaderText("CPR-nummeret blev succesfuldt verificerert");
                alert.setContentText("--- Resultatsiden er under konstruktion --- \nResultaterne kan ses i terminalen.");
                alert.show();
                */
                //App.closeWindow();
                
                ResultBiomarker b = new ResultBiomarker();        // Hvis petienten HAR et fornavn oprettes en instans af ResultBiomarker og Score /*Der skal oprettes en patient inden det andet , komposition */
                Score Score = new Score(); 

                
                App.changeStageToInfo(); // ændre scenen fra CPR to information handleren. 
                
            }
        } 
    }
    
    public static long getCprNummer(){                              // Getter der anvedes til at hente data i alle handlers
        return cpr;                                                 // alle kan hente den, men de kan ikke ændre attributten cpr
    }
//gul metode, lyseblå klasse 
    
    private void getHealthCaredata(long CPRnummerHent){              // Opretter PersonHandler og henter sundhedsdata
        PersonHandler ph = new PersonHandler();             
        DatabaseManipulator.executeQueryWithResultSet(ph);
    }
    
    public static void showHealthCareData(){                        // skal anvendes til user interface
        System.out.println("");
        System.out.println("---------------------- MIOP ----------------------");
        System.out.println("");                             
        System.out.println("CPR-number:  " + cpr);
        System.out.println("First name:  " + firstName);
        System.out.println("Last name:   " + lastName);
        System.out.println("Gender:      " + gender);
        System.out.println("Age:         " + age);
    }
    
    // Under construction! But this concept should work
    private void calcAge(){
        //    App.dateForAge
        System.out.println(App.dateForAge);
        String ageVar = String.valueOf(cpr);      // long til string  type caster 
        char yearOneVar = App.dateForAge.charAt(0); /* definere årets første værdi for året , årtiet, der er på plads 0 fordi vi tideligere  */
        char yearTwoVar = App.dateForAge.charAt(1);/*anden værdi for året , årstallet 7 i 1997 */
        char monthOneVar = App.dateForAge.charAt(2);/*først værdi for måned  , 0 i 08 */
        char monthTwoVar = App.dateForAge.charAt(3);/*anden værdi for måned  , 8 i 08 */
        char dayOneVar = App.dateForAge.charAt(4); /*første værdi for dag  , 1 i 17 */
        char dayTwoVar = App.dateForAge.charAt(5);/*anden værdi for dag  , 7 i 17 */ // punktum bag ved noget at du anvender en metode der tilhører klasen
        String dayVar = Character.toString(dayOneVar) + Character.toString(dayTwoVar); /*Nuværende dag*/
        String monthVar = Character.toString(monthOneVar) + Character.toString(monthTwoVar);
        String yearVar = Character.toString(yearOneVar) + Character.toString(yearTwoVar);
        currentDay = Integer.parseInt(dayVar);
        currentMonth = Integer.parseInt(monthVar);
        currentYear = Integer.parseInt(yearVar);
        
        //int dateForAge = Integer.parseInt(currentYear);
        //int monthForAge = Integer.parseInt(currentMonth);
        //int dayForAge = Integer.parseInt(currentDay);
        
        
        char yearOne = ageVar.charAt(4); /*karakter 4 og 5 har året */
        char yearTwo = ageVar.charAt(5);
        String yearString = Character.toString(yearOne) + Character.toString(yearTwo);
        int year = Integer.parseInt(yearString);
        
        char monthOne = ageVar.charAt(2);
        char monthTwo = ageVar.charAt(3); 
        String monthString = Character.toString(monthOne) + Character.toString(monthTwo);
        int month = Integer.parseInt(monthString);
        
        char dayOne = ageVar.charAt(0);
        char dayTwo = ageVar.charAt(1); 
        String dayString = Character.toString(dayOne) + Character.toString(dayTwo);
        int day = Integer.parseInt(dayString);
        
        int offset;
        if (currentYear >= year){ // offset er til hvis således at det ikke bliver negativ alder 
            offset = 0;
        } else {
            offset = 100;
        }
        age = currentYear +offset - year;
        if(month > currentMonth){
            age -=1;
        } else if(month == currentMonth){
            if(day < currentDay){
                age -=1;

            }
        }
        
        System.out.println("Age: " + age);
        System.out.println("Days compared: " + currentDay + " - " + day);
        System.out.println("Months:        " + currentMonth + " - " + month);
        System.out.println("Years:         " + currentYear + " - " + year);
        
    }
    
    private void calcGender(){
        if (cpr % 2 == 0){
            gender = "Kvinde";
        } else {
            gender = "Mand";
        }
    }

    public static String getName(){
        String name = firstName + " " + lastName;
        return name;
    }
    public static String getGender(){
        return gender;
    }
    public static int getAge(){
        return age;
    }
    public static void setName(String n) {
        firstName = n;
    }
    
}