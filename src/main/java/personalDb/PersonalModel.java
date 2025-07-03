/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalDb;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class PersonalModel extends DefaultTableModel{
    private static DbPersonal dbCon = new DbPersonal();
    private static Object[][] data;
    private static String[] columns = {
        "ID", "Name", "Age", "NID", "Sex", "Contact No", 
        "Email", "Address", "Blood Type", "Symptoms", "Date of Birth"
    };

    public PersonalModel(Object[][] data, String[] columns) {
        super(data, columns);
    }

    public static void initData() {
        ArrayList<Personal> allPersonalRecords = dbCon.getAllPersonalRecords(); 
        data = new Object[allPersonalRecords.size()][11]; 
        for (int i = 0; i < allPersonalRecords.size(); i++) {
            Personal person = allPersonalRecords.get(i);
            
            data[i][0] = person.getId();
            data[i][1] = person.getName();
            data[i][2] = person.getAge();
            data[i][3] = person.getNid(); 
            data[i][4] = person.getSex();
            data[i][5] = person.getContactNo();
            data[i][6] = person.getEmail();
            data[i][7] = person.getAddress();
            data[i][8] = person.getBloodType();
            data[i][9] = person.getSymptoms(); 
            data[i][10] = person.getDateOfBirth();
        }
    }

    public static Object[][] getData() {
        return data;
    }
    
    public void reloadPersonalRecords() {
        initData();
    }

    public static String[] getColumns() {
        return columns;
    }
    
    public ArrayList<Personal> search(String search) {
    ArrayList<Personal> searchResults = new ArrayList<>();
    // Database search logic here
    return searchResults; 
}

    
    
    
}
