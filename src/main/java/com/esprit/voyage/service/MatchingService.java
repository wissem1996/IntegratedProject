package com.esprit.voyage.service;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.Matching;
import com.esprit.voyage.repository.MatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {
    @Autowired
    private MatchingRepository matchingRepository;
    @Autowired
    public JavaMailSender emailSender;

    /**
     * @param matching
     * @return
     */
    public Matching addMatch(Matching matching) {
        return matchingRepository.save(matching);
    }

    /**
     * @param id
     * @return
     */
    public Matching getMatchers(long id) {
        if (matchingRepository.findById((long) id).isPresent()) {
            return matchingRepository.findById(id).get();
        }
        return null;
    }

    /**
     * @param id
     * @return
     */
    public String deleteMatching(int id) {
        if (matchingRepository.findById((long) id).isPresent()) {
            matchingRepository.deleteById((long) id);
            return "Matching deleted successfully!";
        } else {
            return "You have no match!";
        }
    }

    /**
     * @param voyageID
     * @return
     */
    public List<Client> searchForMatch(long voyageID) {
        return matchingRepository.searchForMatch(voyageID);
    }

    /**
     * @param idClient
     * @return
     */
    public Client getNewMatcher(int idClient) {
        return matchingRepository.getNewMatcher(idClient);
    }

    /**
     * @param matchers
     * @param newMatcher
     */
    public void sendSimpleEmail(Matching matchers, Client newMatcher) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Matching");
        // send mail to all matchers
        String[] recipients = new String[matchers.getClientList().size()];
        for (int i = 0; i < matchers.getClientList().size(); i++) {
            recipients[i] = matchers.getClientList().get(i).getEmail();
            message.setTo(recipients);
            message.setText("Hello " + matchers.getClientList().get(i).getName()
                    + "\n\nYou have a new matcher!"
                    + " \n\n Details :"
                    + "\n\nName: " + newMatcher.getName()
                    + "\nNum Tel: " + newMatcher.getNumTel());
            this.emailSender.send(message);
        }
        message.setTo(newMatcher.getEmail());
        message.setText("Hello " + newMatcher.getName()
                + "\nYou are matched with this list of employes!");
        this.emailSender.send(message);
    }

}
