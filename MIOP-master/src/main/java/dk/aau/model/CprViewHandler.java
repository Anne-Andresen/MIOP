package dk.aau.model;
//controller til view classerne 
import dk.aau.person.HealthCarePersonale;
import dk.aau.person.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CprViewHandler {
    
    private static long cpr;
    private Stage dialogStage;
    
    @FXML
    private TextField writeCPR; /* Text field tillander at der vises en kasse hvor der ikke
    er tekst i for brugeren i modsætning til label hvor der automatisk indsætte snoget
    den tillader at brugeren kan indtaste en enkelt linje tekst i den her forbindelse CPR */
    
    // Oversætter tekststrængen i brugergrænsefladen til en long
    public static long setCprNumber(String cprNumber) { 
        cpr = Long.valueOf(cprNumber).longValue(); //tYOE CASTES TIL en long den tager væærdien af objekt og gør den til en nummeriske vrædi
        //I try catch fordi så går den ikke ned hvis der sker en fejl. 
        return cpr;
    }
    
    // Giver funktionalitet til knappen, klasse som handler 
    public void getInformation() {
        try {   // succes kriterie
            Patient.setCprNumber(CprViewHandler.setCprNumber(writeCPR.getText()));
            HealthCarePersonale hcp = new HealthCarePersonale(); /*Hvis cpr er et nummer så.. oprettes en ny healthcarepersonale
           Den oprettes til ar begynde med, hvis et login skal forekomme skal der være et personelle inden patient ofr tat 
           verificere adgangen */
            Patient pa = new Patient();/*Opretter en udgave af patient (patient.jsvs)*/
        } catch (Exception e) {
            // forkert format grundet tegn og/eller bogstaver
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ugyldigt CPR-nummer format");
            alert.setHeaderText("CPR-nummeret skal være 10 tal.");
            alert.setContentText("SPR-nummeret skal have formatet 10 tal.\nIngen bogstaver, tegn eller mellemrum. ");
            alert.showAndWait();
        }   
    }
}