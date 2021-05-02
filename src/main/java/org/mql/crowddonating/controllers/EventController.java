package org.mql.crowddonating.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.mql.crowddonating.business.IAssociationBusiness;
import org.mql.crowddonating.business.IPublicServices;
import org.mql.crowddonating.business.IUserServices;
import org.mql.crowddonating.business.implementations.UserBusiness;
import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Event;
import org.mql.crowddonating.models.File;
import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EventController {

	@Autowired
	@Qualifier("associationBusiness")
	private IAssociationBusiness associationBusiness;

	@Autowired
	@Qualifier("publicServicesBusiness")
	private IPublicServices publicServices;

	@Autowired
	@Qualifier("userBusiness")
	private IUserServices userBusiness;

	@GetMapping("/events")
	public String sponsors(Model model) {
		List<Event> events = publicServices.getAllEvents();
		model.addAttribute("events", events);
		return "Event/events";
	}

	@GetMapping("/events/add")
	public String addForm(ModelMap map) {
		Map<String, String> errors = new HashMap<>();
		map.put("errors", errors);
		map.put("event", new Event());
		return "Event/add";
	}

	@PostMapping("/events")
	public String add(ModelMap map, String title, String description, Date plannedDate,
			@RequestParam("image") MultipartFile image) {
		Event event = new Event();
		event.setTitle(title);
		event.setDescription(description);
		event.setPlannedDate(plannedDate);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Association association = userBusiness.getAssociationByUserName(auth.getName());
		event.setAssociation(association);
		event.setImage(Utility.upload("images/event/", image));
		associationBusiness.addEvent(event);
		Map<String, String> errors = new HashMap<>();
		map.put("errors", errors);
		map.put("event", event);
		return "redirect:/events/" + event.getId();
	}

	@GetMapping("/events/update/{id}")
	public String updateForm(ModelMap map, @PathVariable long id, HttpServletResponse response) {
		Map<String, String> errors = new HashMap<>();
		map.put("errors", errors);
		Event event = publicServices.getEventById(id);
		if (event == null) {
			response.setStatus(404);
			return "error/404";
		}
		map.put("event", event);
		return "Event/update";
	}

	@PutMapping("/events")
	public String update(ModelMap map, @RequestParam MultipartFile imageFile, Event event) {
		System.out.println(event.getPlannedDate());
		Map<String, String> errors = new HashMap<>();
		map.put("event", event);
		event.setImage(Utility.upload("images/Event/", imageFile));
		associationBusiness.updateEvent(event);

		return "redirect:/events/" + event.getId();
	}

	@GetMapping("/events/{id}")
	public String eventById(ModelMap map, @PathVariable long id, HttpServletResponse response) {
		Event event = publicServices.getEventById(id);
		map.put("event", event);
		if (event == null) {
			response.setStatus(404);
			return "error/404";
		}
		return "Event/details";
	}

	@DeleteMapping("/events/{id}")
	public String delete(@PathVariable long id, Model model) {
		Event event = publicServices.getEventById(id);
		System.out.println("Event : " + event.getTitle());
		Path filePath = Paths.get(Utility.uploadDir, "images/Event/", event.getImage());
		try {
			Files.delete(filePath);
		} catch (IOException ignored) {
		}
		associationBusiness.deleteEvent(id);
		return "redirect:/events";
	}

	@GetMapping("/events/association/{id}")
	public String eventsByAssociation(ModelMap map, @PathVariable long id) {

		Association association = (Association) publicServices.getAssociationById(id);
		map.put("events", publicServices.getEventsByAssociation(association));
		return "Event/events";
	}

	@GetMapping("/events/association")
	public String myEvents(ModelMap map) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Association association = userBusiness.getAssociationByUserName(auth.getName());
		map.put("events", publicServices.getEventsByAssociation(association));
		return "Event/events";

	}
}
