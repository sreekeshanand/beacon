package com.dnastack.interview.beaconsummarizer;

import com.dnastack.interview.beaconsummarizer.client.beacon.BeaconClient;
import com.dnastack.interview.beaconsummarizer.client.beacon.Organization;
import com.dnastack.interview.beaconsummarizer.model.Beacon;
import com.dnastack.interview.beaconsummarizer.model.BeaconSearchParm;
import com.dnastack.interview.beaconsummarizer.model.BeaconSummary;
import com.dnastack.interview.beaconsummarizer.service.BeaconSearchService;
import feign.QueryMap;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@RestController
@Log4j2
public class BeaconSummaryController {

    @Autowired
    private BeaconClient beaconClient;
    @Autowired
    public BeaconSearchService searchService;

    @GetMapping("/search")
    public BeaconSummary search(@RequestParam String ref,
                                @RequestParam String chrom,
                                @RequestParam String pos,
                                @RequestParam String allele,
                                @RequestParam String referenceAllele) {

        List<String> orgNames = beaconClient.getOrganizations()
                .stream()
                .map(Organization::getName)
                .collect(toList());

        return new BeaconSummary(orgNames);
    }

    @GetMapping("/search1")
    public List<String> modifySearch(@QueryMap BeaconSearchParm searchParm) {


        log.info(searchParm.toString());
        searchService.SearchService(searchParm);




//        BeaconIds beaconIds = new BeaconIds(searchResult);
//
//        List<Responses> searchResult1 = beaconClient.getResponses();
        return null;


        // return new BeaconSummary(orgNames);
    }
}
