package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{
    private final Map<String,Description> deathNote = new HashMap<>();
    private String lastWrittenName = null;
    private static long VALID_TIME_FOR_CAUSES = 40;
    private static long VALID_TIME_FOR_DETAILS = 6000 + VALID_TIME_FOR_CAUSES;
    private static String DEFAULT_CAUSE = "Heart attack";
    private long timeOfDeath;

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber <= 0 || ruleNumber > RULES.size()){
            throw new IllegalArgumentException("Invalid rule number");
        }
        else{
            return RULES.get(ruleNumber);
        }
    }

    @Override
    public void writeName(String name) {
        if(name == null){
            throw new NullPointerException("Been given a null name");
        } 
        else{
            lastWrittenName = name;
            deathNote.put(name, new Description("", ""));
            timeOfDeath = System.currentTimeMillis();
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(lastWrittenName == null || cause == null){
            throw new IllegalStateException("It's impossible to write a cause of death before writing a name");
        } 
        else if(System.currentTimeMillis() - timeOfDeath <= VALID_TIME_FOR_CAUSES){
            deathNote.get(lastWrittenName).setCause(cause);
            return true; 
        }
        else{
            return false;
        }
    }

    @Override
    public boolean writeDetails(String details) {
        if(lastWrittenName == null || details == null){
            throw new IllegalStateException("It's impossible to write a detail of death before writing a name");
        } 
        else if(System.currentTimeMillis() - timeOfDeath <= VALID_TIME_FOR_DETAILS){
            deathNote.get(lastWrittenName).setDetail(details);
            return true; 
        }
        else{
            return false;
        }
    }

    @Override
    public String getDeathCause(String name) {
        if(isNameWritten(name) == false){
            throw new IllegalArgumentException("The provider name is not written in this DeathNote");
        }
        else if(deathNote.get(name).cause == ""){
            return DEFAULT_CAUSE;
        }
        else{
            return deathNote.get(name).getCause();
        }
    }

    @Override
    public String getDeathDetails(String name) {
        if(isNameWritten(name) == false){
            throw new IllegalArgumentException("The provider name is not written in this DeathNote");
        }
        else{
            return deathNote.get(name).getDetail();
        }
    }

    @Override
    public boolean isNameWritten(String name) {
        return deathNote.containsKey(name);
    }

    private class Description{
        private String cause;
        private String detail;

        private Description(String cause, String detail){
            setCause(cause);
            setDetail(detail);
        }

        private String getCause(){
            return cause;
        }

        private String getDetail(){
            return detail;
        }

        private void setCause(String cause){
            this.cause = cause;
        }

        private void setDetail(String detail){
            this.detail = detail;
        }
    }

}