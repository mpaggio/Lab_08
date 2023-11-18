package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{

    @Override
    public String getRule(int ruleNumber) {
        throw new IllegalArgumentException("Invalid rule number");
    }

    @Override
    public void writeName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeName'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

}