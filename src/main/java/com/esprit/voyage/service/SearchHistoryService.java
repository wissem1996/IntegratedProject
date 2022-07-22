package com.esprit.voyage.service;

import com.esprit.voyage.config.TwilioConfiguration;
import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.SearchHistory;
import com.esprit.voyage.repository.HistoryRepository;
import com.esprit.voyage.services.exception.CBException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SearchHistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private TwilioConfiguration twilioConfiguration;

    /**
     * @param history
     * @return
     */
    public SearchHistory addHistory(SearchHistory history) {
        return historyRepository.save(history);
    }

    /**
     * @param destination
     * @return
     */
    public List<SearchHistory> getHistory(String destination) {
        return historyRepository.history(destination);
    }

    /**
     * @param adresse
     * @return
     */
    public List<Client> getClientList(String adresse) {
        return historyRepository.listEmp(adresse);
    }

    /**
     * @param adresse
     * @param idClient
     * @return
     */
    public Boolean isSearchAlreadySaved(String adresse, int idClient) {
        List<SearchHistory> listHis = historyRepository.isSearchAlreadySaved(adresse, idClient);
        return !listHis.isEmpty();
    }

    /**
     * @param clientList
     */
    public void sendSms(List<Client> clientList) {
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        for (Client client : clientList) {
            String message = "Hello " + client.getName() // to replace email by name
                    + "\nWe inform you that a new voyage is available in our plateform based to your search."
                    + "\nYou can apply NOW!";
            if(client.getNumTel().toString()!=null){
                if (isPhoneNumberValid(client.getNumTel().toString())) {
                    MessageCreator creator = Message.creator(client.getNumTel(), from, message);
                    creator.create();
                } else {
                    throw new CBException("Phone number [" + client.getNumTel().toString() + "] is not a valid number");
                }
            }else{
                throw new CBException("Phone number is null");
            }

        }
    }

    /**
     * @param s
     * @return
     */
    public static boolean isPhoneNumberValid(String s) {
        Pattern p = Pattern.compile("\\+216[2459][0-9]{7}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

}
