
package dk.aau;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    // tidshåndterings variabler 
    public static long days = (long) 7; // antallet af dage der kigged bagud
    public static String currentDate;
    public static String previousDate;
    public static String dateForAge;
    
    // brugerinterface variabler
    private static Stage primaryStage;
    public static BorderPane rootLayout;

    
    @Override                           // programmet skal gøre med kommandoen gradle run, og kræver at gradle er downloadet
    public void start(Stage primaryStage) { // primary stafe stage er en varibel i java fx
        // sætter scenen, det der hentes i scenebuilder, scenen er i vores primary stage
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MIOP");

        // sætter tiden , fordi programmet tager tid når programemt åbnes. 
        LocalDateTime myDateObj = LocalDateTime.now(); //år m d t s ms 
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // formater uden ms 
        DateTimeFormatter formatForAge = DateTimeFormatter.ofPattern("yyMMdd"); // formater uden s for at kunne berege aldeer
        currentDate = myDateObj.format(myFormatObj); // string 
        dateForAge = myDateObj.format(formatForAge);// sætter formatet for date age yy mm dd 
        LocalDateTime a = myDateObj.minus(days, ChronoUnit.DAYS);// loksl tids objekt fra trækker days fra currentdate
        previousDate = a.format(myFormatObj);

        // sætter brugerinterfacet op på scenen
        initRootLayout();
        //showInfoPage();
        showCprOverview();
    }

    // opsætter root layour
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(); //klasse der kan loade fxml fil 
            loader.setLocation(App.class.getResource("/view/RootLayout.fxml")); //hvor skal der loades fra
            rootLayout = (BorderPane) loader.load(); //loader det, type  cast fra boaderpane ramme achor inden i. 

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout); //pop up , grundstuktur skal være rootlayot 
            primaryStage.setScene(scene); //Dt sættes til primary stage 
            primaryStage.show(); //til sidst vises det, det sættes i en try catch 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // lukker vinduet
    public static void closeWindow() {
        primaryStage.close();
    }

    public static void changeStageToCpr(){
        showCprOverview();
    }

    public static void changeStageToInfo(){
        showInfoPage();
    }

    // viser cpr brugergrænseflade siden
    public static void showCprOverview() {
        try {
            // Load person overview.

            FXMLLoader loader = new FXMLLoader(); //loader class
            loader.setLocation(App.class.getResource("/view/cprOverview.fxml"));
            AnchorPane cprOverview = (AnchorPane) loader.load(); //Nu er det anchor pane som er fylder root layout ud 

            // Set person overview into the center of root layout.
            rootLayout.setCenter(cprOverview);
            // closeWindow();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showInfoPage() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/infoPage.fxml"));
            AnchorPane infoPage = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(infoPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {        // KODEN KØRES MED GRALDE RUN ! KRÆVER AT GRADLE ER DOWNLOADET
        launch(args);
    }
}