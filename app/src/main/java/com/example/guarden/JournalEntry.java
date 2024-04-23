package com.example.guarden;//This class implements the journal entry object

public class JournalEntry {

    //Data
    private String entryName;
    private String entryContent;

    //Constructor
    public JournalEntry(){

        entryName = "New Journal Entry: ";
        entryContent = "Nothin here, boss";
    }

    //Overwritten constructor
    public JournalEntry(String entryName, String entryContent){

        this.entryName = entryName;
        this.entryContent = entryContent;
    }
    //Setters
    public void setEntryName(String name){
        entryName = name;
    }

    public void setEntryContent(String content){
        entryContent = content;
    }

    //Getters
    public String getName(){
        return entryName;
    }

    public String getContent(){
        return entryContent;
    }
}
