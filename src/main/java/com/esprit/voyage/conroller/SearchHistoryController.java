package com.esprit.voyage.controller;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.SearchHistory;
import com.esprit.voyage.service.SearchHistoryService;
import com.esprit.voyage.services.exception.CBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/search")
public class SearchHistoryController {

    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * Called when no event are found based on search criteria.
     *
     * @param history
     * @param bindingResult
     * @return Object
     * @throws ValidationException
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Object createHistory(@Valid @RequestBody SearchHistory history, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new CBException("SearchHistory object is not valid");
        }
        Boolean isExist = searchHistoryService.isSearchAlreadySaved(history.getAdresse(), history.getIdClient());
        if (Boolean.TRUE.equals(isExist)) {
            throw new CBException("Search criteria already registered");
        }
        return new ResponseEntity<>(searchHistoryService.addHistory(history), HttpStatus.CREATED);
    }

    /**
     * Called when new event is added, to inform concerned clients about it
     *
     * @param adresse
     * @return
     */
    @GetMapping(value = "/{adresse}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object ofVerifSearchClient(@PathVariable(value = "adresse") String adresse) { //adresse lieuArrive
        List<SearchHistory> searchList = searchHistoryService.getHistory(adresse);
        if (searchList.isEmpty()) {
            throw new CBException("no client found for this criteria");
        }
        List<Client> clientList = searchHistoryService.getClientList(adresse);
        searchHistoryService.sendSms(clientList);
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

}
