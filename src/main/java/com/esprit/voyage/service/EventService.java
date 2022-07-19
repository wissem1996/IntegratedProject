package com.esprit.voyage.service;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.Event;
import com.esprit.voyage.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {


    @Autowired(required = true)
    private EventRepository eventRepository;
    @Autowired
    private ClientService clientService;

    public ResponseEntity addEvent(Event event) throws Exception {

        if (event.getNbrDePlaces() == null) {
            return ResponseEntity.badRequest().body("nbre de places ");
        } else {
            Date today = Calendar.getInstance().getTime();

            if(event.getDateDebutEvent()==null){
                event.setDateDebutEvent(today);
            }
            if(event.getDateFinEvent()==null){
                event.setDateFinEvent(today);
            }
            eventRepository.save(event);
            return ResponseEntity.ok(event);

        }
    }

    public Event updateEvent(int id, Event event) {
        if (eventRepository.findById(id).isPresent()) {
            Event existingEvent = eventRepository.findById(id).get();
            existingEvent.setAdresse(event.getAdresse());

            return eventRepository.save(existingEvent);
        } else
            return null;
    }

    public String deleteEvent(int id) {
        if (eventRepository.findById(id).isPresent()) {
            eventRepository.deleteById(id);
            return "event supprimé";
        } else
            return "event non supprimé";
    }

    public List<Event> getAll() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public String paticiper(int idEvent, int idUser) throws Exception {
        Set<Client> users = new HashSet<>();
        Event event = eventRepository.findById(idEvent).get();
        Client user = clientService.findById(idUser);
        boolean findUser =false, findEvent=false;
        findUser= event.getUsers().stream().anyMatch(item -> user.getId() == idUser);
        findEvent = user.getEvents().stream().anyMatch(item -> event.getId() == idEvent);
         if(findEvent&&findUser){
             return "tu as deja participé à cette ";
         }
         else if (event.getNbrParticipants() < event.getNbrDePlaces()) {


                event.setNbrParticipants(event.getNbrParticipants() + 1);
                users.add(user);
                event.setUsers(users);
                eventRepository.save(event);
                return "vous etes participé evenement : " + event.getLibellé();


        }
        else return "le nbre de places maximum est atteint";

    }



    public Set <Event> showMyEvents(int idUser) {
        Client user = clientService.findById(idUser);
        Set events = user.getEvents();
        System.out.println(org.hibernate.Version.getVersionString());
        return events;
    }
    public Event findEventById (int id){
        Event event = eventRepository.findById(id).get();
        return  event ;
    }
    public void updateEventObject(Event event) {
    event.setNbrParticipants(event.getNbrParticipants()-1);
    eventRepository.save(event);

    }

    /**  public  Set <Event> eventUserByTravel(int idUser){



     //   return events;

    }**/

}
