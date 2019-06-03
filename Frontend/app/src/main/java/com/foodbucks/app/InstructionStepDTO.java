package com.foodbucks.app;

import java.util.UUID;

public class InstructionStepDTO {
    private String description;
    private int instructionNr;
    private UUID instructionStepId;

    public InstructionStepDTO(String description, int instructionNr, UUID instructionStepId) {
        this.description = description;
        this.instructionNr = instructionNr;
        this.instructionStepId = instructionStepId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInstructionNr() {
        return instructionNr;
    }

    public void setInstructionNr(int instructionNr) {
        this.instructionNr = instructionNr;
    }

    public UUID getInstructionStepId() {
        return instructionStepId;
    }

    public void setInstructionStepId(UUID instructionStepId) {
        this.instructionStepId = instructionStepId;
    }
}
