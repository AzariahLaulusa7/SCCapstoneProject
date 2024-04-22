package com.example.guarden;//This class implements the journal entry object

public class JournalEntry {
    private String entryName;
    private String entryContent;

    public JournalEntry(){

        entryName = "New Journal Entry: ";
        entryContent = "Nothin here, boss";
    }

    public JournalEntry(String entryName, String entryContent){

        this.entryName = entryName;
        this.entryContent = entryContent;
    }

    public void setFromString(String csvString) {
        String[] parts = csvString.split("\\|", 2);
        if (parts.length == 2) {
            entryName = parts[0].trim();
            entryContent = parts[1].trim();
        } else {
            entryName = "New Journal Entry";
            entryContent = "Content not available.";
        }
    }

    public String getString() {
        return entryName + "|" + entryContent;
    }

    public void setEntryName(String name){
        entryName = name;
    }

    public void setEntryContent(String content){
        entryContent = content;
    }

    public String getName(){
        return entryName;
    }

    public String getContent(){
        return entryContent;
    }
}
