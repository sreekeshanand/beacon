package com.dnastack.interview.beaconsummarizer.client.beacon;

import com.dnastack.interview.beaconsummarizer.model.Beacon;
import com.dnastack.interview.beaconsummarizer.model.Responses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// This client uses Spring's wrapper of the Feign declarative HTTP client library.
// Instructions are at <https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html>

// Documentation for the Beacon REST API is at <https://beacon-network.org/#/developers/api/beacon-network>

@FeignClient("beacon")
public interface BeaconClient {

    @GetMapping("/api/organizations")
    List<Organization> getOrganizations();

    @GetMapping("api/responses")
    List<Responses>    getResponses (@RequestParam String ref,
                                  @RequestParam String chrom,
                                  @RequestParam int pos,
                                  @RequestParam String allele,
                                  @RequestParam String[] beacon);




    @GetMapping("api/beacons")
    List<Beacon> getBeacons();
}
