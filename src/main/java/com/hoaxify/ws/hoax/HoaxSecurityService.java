package com.hoaxify.ws.hoax;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hoaxify.ws.user.User;

@Service
public class HoaxSecurityService {
	
	private final HoaxRepository hoaxRepository;
	
	public HoaxSecurityService(HoaxRepository hoaxRepository) {
		this.hoaxRepository = hoaxRepository;
	}
	
	public boolean isAllowedToDelete(long id, User loggedInUser) {
		
		Optional<Hoax> optionalHoax = hoaxRepository.findById(id);
		if(!optionalHoax.isPresent()) {
			return false;
		}
		
		Hoax hoax = optionalHoax.get();
		if(hoax.getUser().getId() != loggedInUser.getId()) {
			return false;
		}
		return true;
	}

}
