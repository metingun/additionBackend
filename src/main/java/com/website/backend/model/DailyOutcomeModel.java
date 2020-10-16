package com.website.backend.model;

import java.util.HashMap;

public class DailyOutcomeModel {
    private double totalOutcome;
    private double totalPersonalOutcome;
    private HashMap<String, Double> outcomeTypes;

    public double getTotalOutcome() {
        return totalOutcome;
    }

    public void setTotalOutcome(double totalOutcome) {
        this.totalOutcome = totalOutcome;
    }

    public double getTotalPersonalOutcome() {
        return totalPersonalOutcome;
    }

    public void setTotalPersonalOutcome(double totalPersonalOutcome) {
        this.totalPersonalOutcome = totalPersonalOutcome;
    }

    public HashMap<String, Double> getOutcomeTypes() {
        return outcomeTypes;
    }

    public void setOutcomeTypes(HashMap<String, Double> outcomeTypes) {
        this.outcomeTypes = outcomeTypes;
    }
}
