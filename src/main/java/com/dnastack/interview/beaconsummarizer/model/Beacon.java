package com.dnastack.interview.beaconsummarizer.model;

import lombok.Data;

@Data
public class Beacon {
    public String id;
    public String name;
    public String organization;
    public String description;
    public String aggregator;
    public String email;
    public String homePage;
    public String[] supportedReferences;
    public String visible;
    public String enabled;
}
