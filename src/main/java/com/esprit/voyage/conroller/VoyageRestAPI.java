package com.esprit.voyage.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.esprit.voyage.entity.Event;
import com.esprit.voyage.entity.EventExcelExporter;
import com.esprit.voyage.entity.VoyageExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.voyage.entity.Voyage;
import com.esprit.voyage.service.VoyageService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/voyages")
public class VoyageRestAPI {

	@Autowired
	private VoyageService voyageService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Voyage> createVoyage(@RequestBody Voyage voyage) {
		return new ResponseEntity<>(voyageService.addVoyage(voyage), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Voyage> updateVoyage(@PathVariable(value = "id") int id, @RequestBody Voyage newVoyage) {
		return new ResponseEntity<>(voyageService.updateVoyage(id, newVoyage), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteVoyage(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(voyageService.deleteVoyage(id), HttpStatus.OK);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Voyage>> getVoyages() {
		return new ResponseEntity<>(voyageService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Voyage>> filter(@RequestParam(value = "key")  String key ,
										  @RequestParam(value = "value") String value) {
		return new ResponseEntity<>(voyageService.filter(key,value), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stat", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getStat(@RequestParam(value = "key")  String key ,
			@RequestParam(value = "value")String value) {
		return new ResponseEntity<>(voyageService.getStatVoyage(key,value), HttpStatus.OK);
	}
	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=events_" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);

		List<Voyage> listVoyage = voyageService.getAll();

		VoyageExcelExporter excelExporter = new VoyageExcelExporter(listVoyage);

		excelExporter.export(response);
	}
}
