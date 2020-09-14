package com.dnastack.interview.beaconsummarizer.service;

import com.dnastack.interview.beaconsummarizer.model.BeaconSearchParm;
import com.dnastack.interview.beaconsummarizer.model.JsonResults;

public interface BeaconSearchService {
    public JsonResults SearchService(BeaconSearchParm BeaconSearchParm);
}
