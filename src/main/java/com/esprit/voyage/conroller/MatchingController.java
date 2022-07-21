package com.esprit.voyage.controller;


import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.Matching;
import com.esprit.voyage.entity.Voyage;
import com.esprit.voyage.service.MatchingService;
import com.esprit.voyage.services.exception.CBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/match")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    /**
     * Called when new Client apply for event, to match it with others client
     *
     * @param voyage
     * @return message confirmation
     */
    @PostMapping(value = "/apply", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String applyEvent(@RequestBody Voyage voyage) {
        List<Client> listClients = matchingService.searchForMatch(voyage.getId());

        if (listClients.isEmpty()) {
            throw new CBException("No clients available for matching");
        }
        createMatch(new Matching(voyage.getIdClient(), true, listClients));
        Client newMatcher = matchingService.getNewMatcher(voyage.getIdClient());
        Matching matchers = getMatchers(voyage.getIdClient());
        matchingService.sendSimpleEmail(matchers, newMatcher);
        return "Congratulation you are matched with " + listClients.size() + " clients"
                + "\nyou will receive an email for more details about your matchers";
    }

    public void createMatch(Matching matching) {
        matchingService.addMatch(matching);
    }

    /**
     * @param id
     * @return list of matchers
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Matching getMatchers(@PathVariable(value = "id") long id) {
        Matching matching = matchingService.getMatchers(id);
        if (matching == null) {
            throw new CBException("No matchers found!");
        }
        return matchingService.getMatchers(id);
    }

    /**
     * Called when client cancel the event
     *
     * @param id
     * @return list of matchers
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteMatching(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(matchingService.deleteMatching(id), HttpStatus.OK);
    }

}