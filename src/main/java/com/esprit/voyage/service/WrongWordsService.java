package com.esprit.voyage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.esprit.voyage.entity.ERole;
import com.esprit.voyage.entity.WrongWords;
import com.esprit.voyage.repository.WrongWordsRepository;
import com.esprit.voyage.security.services.UserDetailsImpl;

@Service
public class WrongWordsService {


	@Autowired
	private WrongWordsRepository wrongWordsRepository;

	public String addWrongWords(WrongWords wrongWords) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		List<String> authorities = privilge.getAuthorities().stream().map(role -> new String(role.getAuthority()))
				.collect(Collectors.toList());
		for (String role : authorities) {
			if (role.equals(ERole.ROLE_ADMIN.toString())) {
				wrongWordsRepository.save(wrongWords);
				return "Wrong word added";
			}
		}
		return "Permission denied";

	}

	public String deleteWrongWords(int id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		List<String> authorities = privilge.getAuthorities().stream().map(role -> new String(role.getAuthority()))
				.collect(Collectors.toList());
		for (String role : authorities) {
			if (role.equals(ERole.ROLE_ADMIN.toString())) {

				if (wrongWordsRepository.findById(id).isPresent()) {

					wrongWordsRepository.deleteById(id);
					return "Wrong Words deleted";

				}

			}
		}
		return "Permission denied";

	}

	public List<WrongWords> getAllWrongWords() {
		List<WrongWords> wrongWords = wrongWordsRepository.findAll();
		return wrongWords;
	}

}
