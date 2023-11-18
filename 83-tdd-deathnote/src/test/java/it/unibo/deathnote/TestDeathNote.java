package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;
import static it.unibo.deathnote.api.DeathNote.RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import static java.lang.Thread.sleep;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDeathNote {
    private DeathNote deathNote;
    private static String MARCO_PAGGETTI = "Marco Paggetti";
    private static String ANOTHER_HUMAN = "Paolo Rossi";
    private static final int INVALID_TIME_FOR_CAUSES = 100;
    private static final int INVALID_TIME_FOR_DETAILS = 6000 + INVALID_TIME_FOR_CAUSES;
    
    @BeforeEach
    void initialization(){
        deathNote = new DeathNoteImpl();
    }

    @Test
    void testInvalidRuleNumber(){
        for(int illegalRuleNumber : List.of(-1, 0, RULES.size() + 1)){
            try{
                deathNote.getRule(illegalRuleNumber);
            } catch (IllegalArgumentException e){
                assertNotNull(e);
                assertEquals("Invalid rule number", e.getMessage());
            }
        }
    }

    @Test
    void testNoNullRule(){
        for(int i = 1; i<RULES.size(); i++){
            assertNotNull(deathNote.getRule(i));
            assertFalse(deathNote.getRule(i).isBlank());
        }
    }

    @Test
    void testHumanDeath(){
        assertFalse(deathNote.isNameWritten(MARCO_PAGGETTI));
        deathNote.writeName(MARCO_PAGGETTI);
        assertTrue(deathNote.isNameWritten(MARCO_PAGGETTI));
        assertFalse(deathNote.isNameWritten(ANOTHER_HUMAN));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testDeathCause() throws InterruptedException{
        try{
            deathNote.writeDeathCause("Hit by a car");
        } catch(IllegalStateException e){
            assertEquals("It's impossible to write a cause of death before writing a name", e.getMessage());
        }
        deathNote.writeName(MARCO_PAGGETTI);
        assertNotEquals("Heart attack", deathNote.getDeathCause(MARCO_PAGGETTI));
        assertNull(deathNote.getDeathCause(MARCO_PAGGETTI));
        deathNote.writeDeathCause("Heart attack");
        assertEquals("Heart attack", deathNote.getDeathCause(MARCO_PAGGETTI));
        deathNote.writeName(ANOTHER_HUMAN);
        assertTrue(deathNote.isNameWritten(ANOTHER_HUMAN));
        deathNote.writeDeathCause("Karting accident");
        assertEquals("Karting accident", deathNote.getDeathCause(ANOTHER_HUMAN));
        sleep(INVALID_TIME_FOR_CAUSES);
        assertFalse(deathNote.writeDeathCause("Hit by a car"));
        assertEquals("Karting accident", deathNote.getDeathCause(ANOTHER_HUMAN));
    }

    @Test
    void TestDeathSuccess() throws InterruptedException{
        try{
            deathNote.writeDetails("Studied too much");
        } catch(IllegalStateException e){
            assertEquals("It's impossible to write a detail of death before writing a name", e.getMessage());
        }
        deathNote.writeName(MARCO_PAGGETTI);
        assertNotEquals("Studied too much", deathNote.getDeathDetails(MARCO_PAGGETTI));
        assertNull(deathNote.getDeathDetails(MARCO_PAGGETTI));
        assertTrue(deathNote.writeDetails("Ran for too long"));
        assertEquals("Ran for too long", deathNote.getDeathDetails(MARCO_PAGGETTI));
        deathNote.writeName(ANOTHER_HUMAN);
        assertTrue(deathNote.isNameWritten(ANOTHER_HUMAN));
        sleep(INVALID_TIME_FOR_DETAILS);
        assertFalse(deathNote.writeDetails("Studied too much"));
        assertEquals("Ran for too long", deathNote.getDeathDetails(MARCO_PAGGETTI));
    }

}