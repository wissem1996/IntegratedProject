package com.esprit.voyage.controller;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.esprit.voyage.entity.Event;
import com.esprit.voyage.entity.EventExcelExporter;
import com.esprit.voyage.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author sejaziri
 */
@RestController
@RequestMapping(value = "/api/events")
public class EventRestApi {

    @Autowired(required = true)
    private EventService eventService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseEntity> createEvent(@RequestBody Event event) throws Exception {
        return new ResponseEntity<>(eventService.addEvent(event), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Event> updateEvent(@PathVariable(value = "id") int id, @RequestBody Event event) {
        return new ResponseEntity<>(eventService.updateEvent(id, event), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEvent(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(eventService.deleteEvent(id), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Event>> getEvents() {
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }
    @PostMapping(value = "/participer/{idEvent}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> participer(@PathVariable(value = "idEvent") int idEvent, @PathVariable(value = "idUser") int idUser) throws Exception {
        return new ResponseEntity<>(eventService.paticiper(idEvent,idUser), HttpStatus.OK);
    }

    @GetMapping(value = "/eventByid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<Event>> showMyEvents (@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(eventService.showMyEvents(id), HttpStatus.OK);
    }
    
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=events_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
         
        List<Event> listEvents = eventService.getAll();
         
        EventExcelExporter excelExporter = new EventExcelExporter(listEvents);
         
        excelExporter.export(response);
    }

}
