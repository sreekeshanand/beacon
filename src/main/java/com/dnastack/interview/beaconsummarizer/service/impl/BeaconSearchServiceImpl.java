package com.dnastack.interview.beaconsummarizer.service.impl;

import com.dnastack.interview.beaconsummarizer.client.beacon.BeaconClient;
import com.dnastack.interview.beaconsummarizer.model.Beacon;
import com.dnastack.interview.beaconsummarizer.model.BeaconSearchParm;
import com.dnastack.interview.beaconsummarizer.model.Responses;
import com.dnastack.interview.beaconsummarizer.service.BeaconSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component@Log4j2
public class BeaconSearchServiceImpl implements BeaconSearchService {
    @Autowired
    private BeaconClient beaconClient;
    @Override
    public void SearchService(BeaconSearchParm searchParm) {


       List<String> beaconLists=getBeaconLists();
        List<List<Responses>> jsonResponse= new ArrayList<>() ;

        beaconLists.forEach( item-> {

            searchParm.setBeacon(new String[]{item});

             List<Responses>  results=  beaconClient.getResponses(searchParm.getAllele(),searchParm.getChrom(),
                    searchParm.getPos(), searchParm.getRef(), searchParm.getBeacon());
       //      log.info(results.size());
            jsonResponse.add(results);

        });



       // searchParm.setBeacon(Arrays.asList(beaconLists.stream().toArray(String[]::new)));
       // searchParm.setReferenceAllele("");
       // log.info(searchParm.toString());

//        log.info(beaconClient.getResponses(searchParm.getAllele(),searchParm.getChrom(),
//                searchParm.getPos(), searchParm.getRef(), searchParm.getBeacon()));




   //    log.info(jsonResponse.size());;
        List<Responses> flat =
                jsonResponse.stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

        log.info(flat.toString());




//        @RequestParam String ref,
//        @RequestParam String chrom,
//        @RequestParam String pos,
//        @RequestParam String allele,
//        @RequestParam String referenceAllele
//        beaconLists.forEach( item-> {
//            searchParm.setBeacon(item.stream().toArray(String[]::new));
//            searchParm.setReferenceAllele("");
//            log.info(searchParm.toString());
//            List<Responses> results=  beaconClient.getResponses(searchParm);
//            log.info(results.toString());
//        });
    }
//TODO Can be Cached
    private List<String>  getBeaconLists(){
        return  beaconClient.getBeacons()
                .stream()
                .map(Beacon::getId)
                .collect(toList());

    }
}
