package com.dnastack.interview.beaconsummarizer.service.impl;

import com.dnastack.interview.beaconsummarizer.client.beacon.BeaconClient;
import com.dnastack.interview.beaconsummarizer.client.beacon.Organization;
import com.dnastack.interview.beaconsummarizer.model.*;
import com.dnastack.interview.beaconsummarizer.service.BeaconSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@Log4j2
public class BeaconSearchServiceImpl implements BeaconSearchService {
    @Autowired
    private BeaconClient beaconClient;

    @Override
    public JsonResults SearchService(BeaconSearchParm searchParm) {


        List<String> beaconLists = getBeaconLists();
        List<List<Responses>> jsonResponse = new ArrayList<>();
        // Get Beacon List
        beaconLists.forEach(item -> {
            searchParm.setBeacon(new String[]{item});
            List<Responses> results = beaconClient.getResponses(searchParm.getAllele(),
                    searchParm.getChrom(),
                    searchParm.getPos(),
                    searchParm.getRef(),
                    searchParm.getBeacon());
            jsonResponse.add(results);

        });
        // Flatten the List of list
        List<Responses> flattenResponse =
                jsonResponse.stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

        // log.info(flattenResponse.toString());

        // Creating the Json Results
        JsonResults results = new JsonResults();
        List<Organizations> orgList = new ArrayList<Organizations>();

        // Getting the Beacons for each organization
        getOrganizations().forEach(name -> {
            List<Responses> ref = flattenResponse.stream().
                    filter(j -> j.beacon.organization.equals(name)).
                    collect(toList());
            Organizations org = new Organizations();
            org.setOrganization(name);
            org.setBeacons(ref.size());
            orgList.add(org);
        });

        // Ordering based on the number of Beacon
        Collections.sort(orgList, Collections.reverseOrder());


        results.setOrganizations(orgList.stream().toArray(Organizations[]::new));
        try {
            results.setNotFound(flattenResponse.stream()
                    .filter(j -> j.response == null).count());
        } catch (NullPointerException e) {
            results.setNotFound(0);
        }
        try {
            results.setFound(flattenResponse.stream()
                    .filter(j -> j.response.equalsIgnoreCase("true")).count());
        } catch (NullPointerException e) {
            results.setFound(0);
        }
        try {
            results.setNotResponding(flattenResponse.stream()
                    .filter(j -> j.response.equalsIgnoreCase("false")).count());
        } catch (NullPointerException e) {
            results.setNotResponding(0);
        }
        try {
            results.setNotApplicable(flattenResponse.stream()
                    .filter(j -> j.response.equalsIgnoreCase("Error")).count());
        } catch (NullPointerException e) {
            results.setNotApplicable(0);
        }

//  Throwing Null pointer error as response is only having null been saved
        //        results.setFound( flattenResponse.stream().filter(j -> j.response.equalsIgnoreCase("true")).count());


        return results;

    }

    //TODO Can be Cached
    private List<String> getBeaconLists() {
        return beaconClient.getBeacons()
                .stream()
                .map(Beacon::getId)
                .collect(toList());

    }

    //TODO Can be Cached
    private List<String> getOrganizations() {
        return beaconClient.getOrganizations()
                .stream()
                .map(Organization::getName)
                .collect(toList());
    }
}
