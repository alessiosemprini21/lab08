package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.HashMap;
import java.util.Map;

/**
 * an implmentation of {@link DeathNote} that records for each
 * human written in the deathnote the moment the name was written,
 * the cause of death, the moment the cause was accepted and 
 * the details of the death
 * 
 */
public class DeathNoteImpl implements DeathNote {

    private final Map<String, DeathNoteValue> deathNoteNames = new HashMap<>();
    private String currentName;

    /**
     * the data recorded for a single human written in the deathnote
     * when the name was written, the cause of death, when the cause
     * was accepted and the details of the death
     * 
     */
    private static class DeathNoteValue {
        private String deathCause;
        private String deathDetails;
        private long timeNameWritten;
        private Long timeCauseAccepted;

        public DeathNoteValue() {
            this.timeNameWritten = System.currentTimeMillis();
            this.timeCauseAccepted = null;
        }
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("The number does not match a rule");
        }
        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override 
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.deathNoteNames.put(name, new DeathNoteValue());
        this.currentName = name;
    }

    @Override 
    public boolean writeDeathCause(final String cause) {
        if (currentName == null || cause == null) {
            throw new IllegalStateException();
        }
        DeathNoteValue mapEntry = deathNoteNames.get(currentName);
        if ((System.currentTimeMillis() - mapEntry.timeNameWritten) <= 40) {
            mapEntry.deathCause = cause;
            mapEntry.timeCauseAccepted = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    @Override 
    public boolean writeDetails(final String details) {
        if (currentName == null || details == null) {
            throw new IllegalStateException();
        }
        final DeathNoteValue mapEntry = deathNoteNames.get(currentName);
        if (System.currentTimeMillis() - mapEntry.timeCauseAccepted <= 6040) {
            mapEntry.deathDetails = details;
            return true;
        }
        return false;
    }

    @Override 
    public String getDeathCause(final String name) {
        if (!deathNoteNames.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        if (deathNoteNames.get(name).deathCause == null) {
            return "heart attack";
        }
        return deathNoteNames.get(name).deathCause;
    }

    @Override 
    public String getDeathDetails(final String name) {
        if (!deathNoteNames.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        if (deathNoteNames.get(name).deathDetails == null) {
            return "";
        }
        return deathNoteNames.get(name).deathDetails;
    }

    @Override 
    public boolean isNameWritten(final String name) {
        return deathNoteNames.containsKey(name);
    }

}
