package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.impl.DeathNoteImpl;
import it.unibo.deathnote.api.DeathNote;

class TestDeathNote {

    private DeathNote deathNote;

    @BeforeEach 
    void initDeathNote() {
        deathNote = new DeathNoteImpl();
    }

    @Test 
    void testRulesNumber() {
        //checking that rule number 0 and negative rules do not exist in the DeathNote rules
       IllegalArgumentException rule0 = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override 
            public void execute() {
                deathNote.getRule(0);
            }
        });
        IllegalArgumentException negativeRule = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override 
            public void execute() {
                deathNote.getRule(-1);
            }
        });
        IllegalArgumentException notExistingRule = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override 
            public void execute() {
                deathNote.getRule(DeathNote.RULES.size() + 1);
            }
        });
        rule0.getMessage();
        negativeRule.getMessage();
        notExistingRule.getMessage();
    }

    @Test 
    void testEmptyRules() {
        //checking that no rule is empty or null in the DeathNote rules
        for (int i = 1; i <= DeathNote.RULES.size(); i++) {
            String rule = deathNote.getRule(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test 
    void testHumanDie() {
        //checking that the human whose name is written in the DeathNote will eventually die
        assertFalse(deathNote.isNameWritten("Alessio Semprini"));
        deathNote.writeName("Alessio Semprini");
        assertTrue(deathNote.isNameWritten("Alessio Semprini"));
        assertFalse(deathNote.isNameWritten("Mattia Leonardo Lupascu"));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test 
    void testWrittenCause() throws InterruptedException {
        //checking that if the cause of death is written within the next 40 milliseconds of writing the person's name, it will happen
        assertThrows(IllegalStateException.class, new Executable() {
            @Override 
            public void execute() {
                deathNote.writeDeathCause("ate too much");
            }
        });
        deathNote.writeName("Alessio Semprini");
        assertEquals("heart attack", deathNote.getDeathCause("Alessio Semprini"));
        deathNote.writeName("Mattia Leonardo Lupascu");
        assertTrue(deathNote.writeDeathCause("karting accident"));
        Thread.sleep(100);
        assertFalse(deathNote.writeDeathCause("caught cheating by his girlfriend"));
        assertEquals("karting accident", deathNote.getDeathCause("Mattia Leonardo Lupascu"));
    }

    @Test 
    void testWrittenDetails() throws InterruptedException {
        //checking that after writing the cause of death, details of the death should be written in the next 6 seconds and 40 milliseconds of writing the death's cause
        assertThrows(IllegalStateException.class, new Executable() {
            @Override 
            public void execute() {
                deathNote.writeDetails("smashed face");
            }
        });
        deathNote.writeName("Alessio Semprini");
        assertEquals("", deathNote.getDeathDetails("Alessio Semprini"));
        assertTrue(deathNote.writeDetails("ran for too long"));
        assertEquals("ran for too long", deathNote.getDeathDetails("Alessio Semprini"));
        deathNote.writeName("Mattia Leonardo Lupascu");
        Thread.sleep(6100);
        assertFalse(deathNote.writeDetails("girlfriends found guilty"));
        assertEquals("", deathNote.getDeathDetails("Mattia Leonardo Lupascu"));
    }
}