package com.dnastack.interview.beaconsummarizer.model;

import lombok.Data;

@Data
public class Organizations implements Comparable{
    public String organization;
    public int beacons;


    public int compareTo(Object o) {

        return (this.getBeacons() < ((Organizations) o).getBeacons() ? -1 : (this.getBeacons() == ((Organizations) o).getBeacons() ? 0 : 1));
    }
}
