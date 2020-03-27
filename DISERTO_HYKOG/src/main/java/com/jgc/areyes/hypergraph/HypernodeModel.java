package com.jgc.areyes.hypergraph;

public class HypernodeModel {

    private String hypernodeIRI;
    private String hypernodePath;
    private String spatialPredicate;
    private String spatialObject;
    private String temporalPredicate;
    private String temporalObject;
    private String spatialValue, temporalValue;
    private String hypernodeSubject;


    public HypernodeModel() {
    }

    public String getHypernodeIRI() {
        return hypernodeIRI;
    }

    public void setHypernodeIRI(String hypernodeIRI) {
        this.hypernodeIRI = hypernodeIRI;
    }

    public String getHypernodePath() {
        return hypernodePath;
    }

    public void setHypernodePath(String hypernodePath) {
        this.hypernodePath = hypernodePath;
    }

    public String getSpatialPredicate() {
        return spatialPredicate;
    }

    public void setSpatialPredicate(String spatialPredicate) {
        this.spatialPredicate = spatialPredicate;
    }

    public String getSpatialObject() {
        return spatialObject;
    }

    public void setSpatialObject(String spatialObject) {
        this.spatialObject = spatialObject;
    }

    public String getTemporalPredicate() {
        return temporalPredicate;
    }

    public void setTemporalPredicate(String temporalPredicate) {
        this.temporalPredicate = temporalPredicate;
    }

    public String getTemporalObject() {
        return temporalObject;
    }

    public void setTemporalObject(String temporalObject) {
        this.temporalObject = temporalObject;
    }

    public String getHypernodeSubject() {
        return hypernodeSubject;
    }

    public void setHypernodeSubject(String hypernodeSubject) {
        this.hypernodeSubject = hypernodeSubject;
    }

    public void setSpatialQueryValue(String spatialValue) {
        this.spatialValue= spatialValue;

    }
    public void setTemporalOldValue(String temporalValue) {
        this.temporalValue=temporalValue;

    }

    public String getSpatialValue() {
        return spatialValue;
    }

    public String getTemporalValue() {return temporalValue;
    }
}
