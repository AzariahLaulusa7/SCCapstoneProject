package com.example.guarden;

import org.junit.Test;
import static org.junit.Assert.*;

public class JournalEntryTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        JournalEntry entry = new JournalEntry();

        // Act & Assert
        assertEquals("New Journal Entry: ", entry.getName());
        assertEquals("Nothin here, boss", entry.getContent());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        String name = "Test Entry";
        String content = "This is a test entry.";
        JournalEntry entry = new JournalEntry(name, content);

        // Act & Assert
        assertEquals(name, entry.getName());
        assertEquals(content, entry.getContent());
    }

    @Test
    public void testSetName() {
        // Arrange
        JournalEntry entry = new JournalEntry();
        String name = "Updated Entry Name";

        // Act
        entry.setEntryName(name);

        // Assert
        assertEquals(name, entry.getName());
    }

    @Test
    public void testSetContent() {
        // Arrange
        JournalEntry entry = new JournalEntry();
        String content = "Updated entry content.";

        // Act
        entry.setEntryContent(content);

        // Assert
        assertEquals(content, entry.getContent());
    }
}
