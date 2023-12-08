package com.example.guarden;//This class impliments the journal entry object
import  java.util.Date;
public class JournalEntry {
    private String entryName;
    private String entryContent;

    public JournalEntry(){
        Date date = new Date();
        entryName = "New Journal Entry: " + date.toString();
        entryContent = "Nothin here, boss";
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
