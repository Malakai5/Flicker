package com.example.myapplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Singleton { // Used to read CSV file on initiation and never need to read it again.
    private static final String COMMA_REGEX_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private static Singleton single_instance = null;
    List<Profile> profileList;
    List<Record> originalRecordList;
    Profile profile;
    List<Record> undecidedTitles;
    List<Record> likedTitles;
    List<Record> dislikedTitles;

    private Singleton() throws IOException {
        this.profileList = readProfileCSV();
        this.originalRecordList = readCSV("netflix_database");
        this.profile = new Profile();
        this.undecidedTitles = profile.undecidedTitles;
        this.likedTitles = profile.likedTitles;
        this.dislikedTitles = profile.dislikedTitles;

    }

    public void headerWriter(String filename) throws IOException {
        String headers;
        headers = "TitleName,YearMade,Genre,TvRating,TitleId,Score \n";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(headers);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void makeEmptyCSVs() throws IOException {
        String undecidedList = profile.userName + "'s Undecided Titles.csv";
        String likedList = profile.userName + "'s Liked Titles.csv";
        String dislikedList = profile.userName + "'s Disliked Titles.csv";

        headerWriter(undecidedList);
        headerWriter(likedList);
        headerWriter(dislikedList);
        List<Record> jumbledList = originalRecordList;
        Collections.shuffle(jumbledList);

        writeCSV(undecidedList, jumbledList);

    }

    public List<Record> readCSV(String fileName) throws IOException {
        String[] data;
        List<Record> recordList = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            //Reads the initial file

            String line;
            while ((line = br.readLine()) != null) {
                data = line.split(COMMA_REGEX_DELIMITER);//Will split the lines into pieces

                String titleName = data[0];
                String tvRating = data[1];
                String genre = data[2];
                String titleId = data[3]; //Throwing an index out of bounds clause
                String yearMade = data[4];
                String score = data[5];

                Record currentRecords = new Record(titleName, tvRating, genre, titleId, yearMade, score);
                recordList.add(currentRecords);

            }

        } catch (IOException e) {
            System.out.println("Titles lost...\n" +
                    "Creating new titles lists\n");
            makeEmptyCSVs();
            profile.setUndecidedTitles(originalRecordList);

        }
        return recordList;
    }

    public void writeCSV(String fileName, List<Record> recordList){
        Record tempRecord = new Record();

        try (PrintWriter br = new PrintWriter( new FileWriter(fileName))) {

            String headers;

            headers = "TitleName,YearMade,Genre,TvRating,TitleId,Score \n";
            br.write(headers);

            for (int i = 0;i < recordList.size();i++){

                if (i !=0) {
                    br.write(String.valueOf(tempRecord.toCSV(recordList,i)));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Profile> readProfileCSV() {// This Method seems to work Completely as intended
        String file = "ProfileCSV.csv";
        List<Profile> profileList = new ArrayList<>();
        String[] data;

        BufferedReader br = null;
        try {
            br =  new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null){
                data = line.split(COMMA_REGEX_DELIMITER);

                String userName = data[0].toLowerCase();
                String userID = data[1];

                Profile currentProfile = new Profile(userName,userID);
                profileList.add(currentProfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null){
                try {

                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return profileList;
    }

    public boolean addToProfileCSV() throws IOException {//Adds new Profile to profileCSV.csv
        System.out.println("Please enter your UserName\n");
        String file = "profile_list";
        Profile newProfile = new Profile();
        Profile tempProfile = new Profile();

        newProfile = newProfile.makeNewProfile(profileList);
        System.out.println("Profile has been Created!\n");
        boolean notBlank = !newProfile.userName.isEmpty();
            if (notBlank){
            profileList.add(newProfile);
            profile.setUserName(newProfile.userName);
            Collections.shuffle(originalRecordList);
            createUndecidedCsv(newProfile.userName.toLowerCase(), originalRecordList);//Creates the undecided list for new Profiles.
            createLikedCsv(newProfile.userName.toLowerCase(), likedTitles);
            createDislikedCsv(newProfile.userName.toLowerCase(), dislikedTitles);

            try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {

                String headers;
                headers = "UserName,UserID\n";

                br.write(headers);

                for (int i = 0; i < profileList.size(); i++) {
                    if (i != 0) {
                        br.write(String.valueOf(tempProfile.toCSV(profileList, i)));
                    }
                }
            }
        }
            else{
                System.out.println("Please enter a valid Username!!\n");
            }
        return notBlank;
    }

    private void createUndecidedCsv(String userName, List<Record> recordList){
        String csvTag = ".csv";
        String file = userName.toLowerCase() + "'s Undecided Titles" + csvTag;
        writeCSV(file, recordList);
    }
    private void createLikedCsv(String userName, List<Record> recordList){
        String csvTag = ".csv";
        String file = userName.toLowerCase() + "'s Liked Titles" + csvTag;
        writeCSV(file, recordList);
    }
    private void createDislikedCsv(String userName, List<Record> recordList){
        String csvTag = ".csv";
        String file = userName.toLowerCase() + "'s Disliked titles" + csvTag;
        writeCSV(file, recordList);
    }

    public boolean checkExistingProfile(String inputName){
        boolean results = false;
        if (!inputName.equals("")) {
            for (Profile value : profileList) {
                if (value.userName.contains(inputName)) {
                    results = true;
                    System.out.println("Profile found!!\n");
                    break;
                }
            }
        }
        return results;
    }

    public static Singleton getInstance() throws IOException {
        if (single_instance == null)
            single_instance = new Singleton();
        return single_instance;
    }
    }