package com.company;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class has methods concerning http csv file handling
 */
public class CsvUtil {


    // CSV file must have a path - where should it be created. Asking the user
    private static String getPathForCsv(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name for file creation in the same directory as this jar. " +
                "\nMake sure to add \".csv\" to is end" +
                " \nOR define a complete path");
        return sc.nextLine();
    }

    public static void writeDataToScv(ArrayList<String[]> dataArray)
    {

        // first create file object for file placed at location
        // specified by filepath
        File file = new File(getPathForCsv());

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // Write to writer - adding the data found from endpoint
            for (String[] singleArray : dataArray){
                writer.writeNext(singleArray);
            }

            // closing writer connection
            writer.close();
            System.out.println("SUCCESS! \n" +
                    "Please check file in " + file);
        }
        catch (Exception e) {
            System.err.println("Entered path is invalid. GoEuroTest.jar can not create a CSV file");
            e.printStackTrace();
        }
    }
}
