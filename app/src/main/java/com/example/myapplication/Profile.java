package com.example.myapplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Profile {
    String userName;
    List<Record> likedTitles = new ArrayList<>();
    List<Record> dislikedTitles = new ArrayList<>();
    List<Record> undecidedTitles = new ArrayList<>();
    String userID;
    int selectID;

    public Profile(String userName, String userID) {
        this.userName = userName;
        this.userID = userID;
    }

    public Profile() {
    }


    public Profile makeNewProfile(List<Profile> profileList) {// Method used to create new Profile for Singleton writeProfileCSV

        Scanner scnr = new Scanner(System.in);
        String userName = scnr.nextLine();
        selectID = profileList.size();
        String userID = Integer.toString(selectID);

        Profile newProfile = new Profile(userName, userID);

        return newProfile;
    }

    public String toCSV(List<Profile> profileList, int i) {
        String userName = profileList.get(i).userName;
        String userID = profileList.get(i).userID;

        return userName + "," + userID + "\n";
    }

    public String toCSVSingle(Profile profile){
        String userName = profile.userName;
        String userID = profile.userID;

        return userName + "," + userID + "\n";
    }

    public void setUndecidedTitles(List<Record> recordList) {
        undecidedTitles = recordList;
    }

    public void setUserName(String inputName) {
        userName = inputName;
    }

    public void setLikedTitles(List<Record> recordList) {
        likedTitles = recordList;
    }

    public void setDislikedTitles(List<Record> recordList) {
        dislikedTitles = recordList;
    }

    public List<Record> likeOrDislike(List<Record> undecidedList) {

        Record tempRecord = new Record();
        Scanner scnr = new Scanner(System.in);
        List<Record> newUndecidedList = new ArrayList<>(undecidedList);
        System.out.println("Are you interested in this title?");
        System.out.println("Please enter: '1' for YES, '2' for NO, '3' to stop\n");

        for (int i = undecidedList.size() - 1; i >= 0; i--) {

            System.out.println(tempRecord.toCSV(undecidedList, i));
            int choice = scnr.nextInt();

            if (choice == 1) {
                System.out.println("That's a Like!!\n");
                likedTitles.add(undecidedList.get(i));
                newUndecidedList.remove(i);
            }
            if (choice == 2) {
                System.out.println("Nah that ones Boring\n");
                dislikedTitles.add(undecidedList.get(i));
                newUndecidedList.remove(i);
            }
            if (choice == 3) {
                System.out.println("Alright we'll stop here\n");
                break;
            } else {
                System.out.println("Please enter numbers 1,2,or 3");
            }
        }
        return newUndecidedList;
    }

    public List<Record> getSharedList(List<Record> firstLikedList, List<Record> secondLikedList) {
        List<Record> sharedList = new ArrayList<>();
        for (Record value : firstLikedList) {
            for (Record record : secondLikedList) {
                if (value.titleName.equals(record.titleName)) {
                    sharedList.add(record);
                }
            }
        }
        for (Record record : sharedList) System.out.println(record.toCSVSingle(record));
        return sharedList;
    }
}

