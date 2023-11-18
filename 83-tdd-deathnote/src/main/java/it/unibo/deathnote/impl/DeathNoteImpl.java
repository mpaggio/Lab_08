package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{
    private final Map<String,String> deathNote = new HashMap<>();
    private String lastWrittenName;

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
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        throw new IllegalStateException("It's impossible to write a cause of death before writing a name");
    }

    @Override
    public boolean writeDetails(String details) {
        throw new IllegalStateException("It's impossible to write a detail of death before writing a name");
    }

    @Override
    public String getDeathCause(String name) {
        throw new IllegalArgumentException("The provider name is not written in this DeathNote");
    }

    @Override
    public String getDeathDetails(String name) {
        throw new IllegalArgumentException("The provider name is not written in this DeathNote");
    }

    @Override
    public boolean isNameWritten(String name) {
        return false;
    }

}