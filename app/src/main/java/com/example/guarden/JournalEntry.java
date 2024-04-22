package com.example.guarden;//This class impliments the journal entry object

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
