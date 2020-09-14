package com.dnastack.interview.beaconsummarizer.model;

import lombok.Data;

@Data
public class BeaconSearchParm {
    public String chrom;
    public int pos;
    public String allele;
    public String ref;
    public String referenceAllele;
    public String[] beacon;
}
