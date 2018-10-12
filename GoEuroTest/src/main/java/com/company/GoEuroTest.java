package com.company;


import java.util.ArrayList;
import java.util.Scanner;

public class GoEuroTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("WELCOME to GoEuroTest.jar! \n" +
                "Please enter a location to search");
        String location =  sc.nextLine();
        ArrayList<String[]> arr = HttpUtil.getDataFromEndPoint(location);
        CsvUtil.writeDataToScv(arr);
    }

}
