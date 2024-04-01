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

    public void setFromString(String csvString){
        String[] stringArray = csvString.split(",");
        entryName = stringArray[0].toString();

        for(int i=1;i<stringArray.length; i++ ){
            entryContent = entryContent + stringArray[i].toString();
        }
        /*if(entryName == null){
            entryName = "New Entry";
        }
        if(entryContent == null){
            entryContent = "No Content";
        }*/
    }

    public String getString(){
        String csvString = entryName + " , " + entryContent;
        return csvString;
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
