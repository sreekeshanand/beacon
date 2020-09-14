package com.dnastack.interview.beaconsummarizer.model;

import lombok.Data;

@Data
public class JsonResults {
    public long found = 0;
    public long notFound = 0;
    public long notApplicable = 0;
    public long notResponding = 0;
    public Organizations[] organizations;
}
