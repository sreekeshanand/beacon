package com.dnastack.interview.beaconsummarizer.model;

import lombok.Data;
import lombok.Value;

@Value
public class Responses {
    public Beacon beacon;
    public String response;
//    public String frequency;
//    public String externalUrl;
//    public Info info;
//    public AuthHint  authHint;
//    public Query query;
//    public FullBeaconResponse fullBeaconResponse;
}
