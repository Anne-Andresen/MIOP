package dk.aau.person; 

import java.sql.ResultSet;
import java.sql.SQLException;

import dk.aau.person.Patient;
import dk.aau.database.Queryable;

public class PersonHandler implements Queryable {

    // overskriver kommandoen i interfacet. Denne metode bliver kun kaldt hvis returnSqlQuery kommandoen går igennem
    @Override    /*Polymorfi i forhold til queryable */
    public void processResultSet(ResultSet rs) throws SQLException {
           while(rs.next()){
            Patient patientIFokus = new Patient(rs.getString("FirstName"), rs.getString("LastName")); // opretter en instans 
        
        }
    } /*V opretter en patient der hedder patientiFokus for at få fornavn og efternavn*/

    // kommandoen til SQL databasen
    @Override   /*Polymorfi i forhold til queryable overskrives , metoderne er tomme også giver dem noget fyld  */
    public String returnSqlQuery() {
        String sqlStatement = "SELECT FirstName, LastName FROM Patients WHERE Patients.CPRnumber=" + Patient.getCprNummer();
        return sqlStatement;
    }
 }