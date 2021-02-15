package com.example.myapplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

    class AppOperations {
        private boolean usernameCreated = false;
        Singleton singleton = Singleton.getInstance();
        Profile profile = singleton.profile;
        List<Record> sharedList = new ArrayList<>();


        public AppOperations() throws IOException {
        }

        Scanner scnr = new Scanner(System.in);

        public void setSharedList(List<Record> recordList){
            sharedList = recordList;
        }

        public void profileNotFound() throws IOException {
            System.out.println("Profile not found...\n");
            System.out.println("Would you like to make a new Profile?");
            System.out.println("enter '1' for YES or '2' for NO\n");
            int newProfileChoice = scnr.nextInt();

            if (newProfileChoice == 1) {
                usernameCreated = singleton.addToProfileCSV();
                profile.setUndecidedTitles(singleton.originalRecordList);
                Collections.shuffle(profile.undecidedTitles);
            }
            else {
                scnr.nextLine();
                usernameCreated = false;
            }
        }
        //Creates profile is non is found
        public void profileFound(String inputName) throws IOException {
            String undecidedListTag = inputName + "'s Undecided Titles.csv";
            String likedListTag = inputName + "'s Liked Titles.csv";
            String dislikedListTag = inputName + "'s Disliked Titles.csv";
            if (!inputName.equals("")) {
                profile.setUndecidedTitles(singleton.readCSV(undecidedListTag));
                Collections.shuffle(profile.undecidedTitles);
                profile.setLikedTitles(singleton.readCSV(likedListTag));
                profile.setDislikedTitles(singleton.readCSV(dislikedListTag));
                profile.setUserName(inputName);
                usernameCreated = true;
            }
            else{
                System.out.println("Please enter a valid name!");
            }
        }
        //Finds the profile's CSV lists
        public boolean getUsernameCreated() {
            System.out.println("Please enter your Username");
            String inputName = scnr.nextLine().toLowerCase();

            System.out.println("Looking for existing profile...\n");

            try {
                boolean existingProfile = singleton.checkExistingProfile(inputName);

                if (!existingProfile) {
                    profileNotFound();
                }
                if (existingProfile) {
                    profileFound(inputName);
                }
            } catch (IOException ignored){
                ignored.printStackTrace();
            }

            return usernameCreated;
        }
        //Populates Profiles CSV's and returns boolean
        public int menuOptions(){

            System.out.println("press '1' to 'Swipe' titles");
            System.out.println("press '2' to view titles you're interested in");
            System.out.println("Press '3' to pair likes Lists");
            System.out.println("Press '4' to exit out of the App\n");

            return scnr.nextInt();
        }
        //Provides options for menu
        public void showLikedLists(){
            boolean usingLikedLists = true;
                for (int i = 0; i < profile.likedTitles.size(); i++) {
                    System.out.println(profile.likedTitles.get(i).toCSVSingle(profile.likedTitles.get(i)));
                }
            while (usingLikedLists) {
                System.out.println("Press '1' to search a title by name");
                System.out.println("Press '2' to display your full Liked Lists again");
                System.out.println("press '3' to go to the previous menu\n");

                int likedMenuChoices = scnr.nextInt();
                if (likedMenuChoices == 1) {
                    System.out.println("What is the name of the title?");
                    scnr.nextLine();
                    String searchName = scnr.nextLine().toLowerCase();

                    for (int i = 0; i < profile.likedTitles.size(); i++) {
                        if (profile.likedTitles.get(i).titleName.contains(searchName)) {
                            System.out.println(profile.likedTitles.get(i).toCSVSingle(profile.likedTitles.get(i)));
                        }
                    }
                }
                if (likedMenuChoices == 2){
                    for (int i = 0; i < profile.likedTitles.size(); i++) {
                        System.out.println(profile.likedTitles.get(i).toCSVSingle(profile.likedTitles.get(i)));
                    }
                }

                if (likedMenuChoices == 3) {
                    usingLikedLists = false;
                }
            }
        }
        //Option 1
        public void getSharedLists() throws IOException {

                    boolean choiceGiven = false;

                    while (!choiceGiven) {
                        System.out.println("Press '1' to enter a User Name");
                        System.out.println("Press '2' to look at the list of available usernames");
                        System.out.println("press '3' to go back to the main menu");
                        int choice = scnr.nextInt();

                        if (choice == 1) {
                            scnr.nextLine();
                            System.out.println("Please enter a valid User Name");
                            String user = scnr.nextLine().toLowerCase();
                            String LikedListTag = user + "'s Liked Titles.csv";
                            boolean anotherTry = singleton.checkExistingProfile(user);

                            if (anotherTry){
                                List<Record> secondLikedList = singleton.readCSV(LikedListTag);

                               List<Record> sharedList = profile.getSharedList
                                       (profile.likedTitles,secondLikedList);
                               setSharedList(sharedList);
                                choiceGiven = true;
                            }
                            else {
                                System.out.println("User Name not found");
                            }
                        }
                        if (choice == 2){
                            for (int i = 0; i < singleton.profileList.size();i++){
                                System.out.println(singleton.profileList.get(i).toCSV(singleton.profileList, i));
                            }
                            scnr.nextLine();
                        }
                        if (choice == 3){
                            choiceGiven = true;
                        }
                }
        }
        //Option 2
        public void closingApp(){
            String undecidedListTag = profile.userName + "'s Undecided Titles.csv";
            String likedListTag = profile.userName + "'s Liked Titles.csv";
            String dislikedListTag = profile.userName + "'s Disliked Titles.csv";

            singleton.writeCSV(undecidedListTag, profile.undecidedTitles);
            singleton.writeCSV(likedListTag, profile.likedTitles);
            singleton.writeCSV(dislikedListTag, profile.dislikedTitles);
        }
        //Option 3
    }

