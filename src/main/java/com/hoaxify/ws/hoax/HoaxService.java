package com.hoaxify.ws.hoax;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.hoaxify.ws.file.FileAttachment;
import com.hoaxify.ws.file.FileAttachmentRepository;
import com.hoaxify.ws.hoax.vm.HoaxSubmitVM;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;

@Service
public class HoaxService {
	
	private final HoaxRepository hoaxRepository;
	
	private final UserService userService;
	
	private final FileAttachmentRepository fileAttachmentRepository;
	
	public HoaxService(HoaxRepository hoaxRepository, UserService userService, FileAttachmentRepository fileAttachmentRepository) {
		this.hoaxRepository = hoaxRepository;
		this.userService = userService;
		this.fileAttachmentRepository = fileAttachmentRepository;
	}

	public void save(HoaxSubmitVM hoaxSubmitVM, User user) {
		Hoax hoax = new Hoax();
		hoax.setContent(hoaxSubmitVM.getContent());
		hoax.setTimestamp(new Date());
		hoax.setUser(user);
		hoaxRepository.save(hoax);
		Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(hoaxSubmitVM.getAttachmentId());
		if(optionalFileAttachment.isPresent()) {
			FileAttachment fileAttachment = optionalFileAttachment.get();
			fileAttachment.setHoax(hoax);
			fileAttachmentRepository.save(fileAttachment);
		}
	}

	public Page<Hoax> getHoaxes(Pageable page) {
		return hoaxRepository.findAll(page);
	}

	public Page<Hoax> getHoaxesOfUser(String username, Pageable page) {
		User inDB = userService.getByUsername(username);
		
		return hoaxRepository.findByUser(inDB, page);
	}

	public Page<Hoax> getOldHoaxes(long id, String username, Pageable page) {
		
		Specification<Hoax> specification = idLessThan(id); // by default --> specIdLessThan
		
		if(username != null) {
			User inDB = userService.getByUsername(username);
			
			specification = specification.and(userIs(inDB));			
			
			//return hoaxRepository.findByIdLessThanAndUser(id, inDB, page);
		}
		
		
		return hoaxRepository.findAll(specification, page);
		//return hoaxRepository.findByIdLessThan(id, page);
	}

	public long getNewHoaxesCount(long id, String username) {
		
		Specification<Hoax> specification = idGreaterThan(id);
		
		if(username != null) {
			
			User inDB = userService.getByUsername(username);
			
			specification = specification.and(userIs(inDB));
			//return hoaxRepository.countByIdGreaterThanAndUser(id, inDB);
		}
		return hoaxRepository.count(specification);
	}

	public List<Hoax> getNewHoaxes(long id, String username, Sort sort) {
		
		Specification<Hoax> specification = idGreaterThan(id);
		
		if(username != null) {
			
			User inDB = userService.getByUsername(username);
			
			specification = specification.and(userIs(inDB));
			//return hoaxRepository.countByIdGreaterThanAndUser(id, inDB);
		}
		
		return hoaxRepository.findAll(specification, sort);

	}
	
	Specification<Hoax> idLessThan(long id){
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.lessThan(root.get("id"), id);
		};
	}
	
	Specification<Hoax> userIs(User user){
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(root.get("user"), user);
		};
	}
	
	Specification<Hoax> idGreaterThan(long id){
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.greaterThan(root.get("id"), id);
		};
	}
	


}
