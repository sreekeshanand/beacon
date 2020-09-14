package com.dnastack.interview.beaconsummarizer.model;

import lombok.Data;

@Data
public class JsonResults {
    public int found,notFound,notApplicable,notResponding;
    public Organizations[] organizations;
}
