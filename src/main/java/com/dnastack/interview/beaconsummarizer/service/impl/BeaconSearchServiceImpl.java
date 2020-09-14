package com.dnastack.interview.beaconsummarizer.service.impl;

import com.dnastack.interview.beaconsummarizer.client.beacon.BeaconClient;
import com.dnastack.interview.beaconsummarizer.client.beacon.Organization;
import com.dnastack.interview.beaconsummarizer.model.*;
import com.dnastack.interview.beaconsummarizer.service.BeaconSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

        beaconLists.forEach(item -> {

            searchParm.setBeacon(new String[]{item});

            List<Responses> results = beaconClient.getResponses(searchParm.getAllele(), searchParm.getChrom(),
                    searchParm.getPos(), searchParm.getRef(), searchParm.getBeacon());
            //      log.info(results.size());
            jsonResponse.add(results);

        });


        List<Responses> flat =
                jsonResponse.stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());


        log.info(flat.toString());

        List<String> organizationList = getOrganizations();

        //  List<Responses> ref= new ArrayList();

        JsonResults results = new JsonResults();
        List<Organizations> orgList = new ArrayList<Organizations>();
        AtomicInteger found, notFound, notApplicable, notResponding = new AtomicInteger();
        organizationList.forEach(name -> {

            List<Responses> ref = flat.stream().filter(j -> j.beacon.organization.equals(name)).collect(toList());

            Organizations org = new Organizations();
            org.setOrganization(name);
            org.setBeacons(ref.size());
          //  log.info(org.toString());
            orgList.add(org);

        });

      //Organizations[] orge=  orgList.stream().toArray(Organizations[]::new);
        Collections.sort(orgList, Collections.reverseOrder());
results.setOrganizations( orgList.stream().toArray(Organizations[]::new));
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
