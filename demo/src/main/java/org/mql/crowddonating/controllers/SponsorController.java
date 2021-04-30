package org.mql.crowddonating.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletResponse;

import org.mql.crowddonating.business.IAdminBusiness;
import org.mql.crowddonating.business.IPublicServices;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Sponsor;

import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class SponsorController {
	
	@Autowired
	@Qualifier("adminBusiness")
	private IAdminBusiness adminBusiness;
	
	@Autowired
	@Qualifier("publicServicesBusiness")
	private IPublicServices publicServices;
	
	@GetMapping("/sponsors")
	public String sponsors(Model model) {
		
		List<Sponsor> sponsors = publicServices.getAllSponsors();
		
		model.addAttribute("sponsors", sponsors);
		return "sponsors/sponsors";
	}
	@GetMapping("/sponsors/search")
	public String sponspors(Model model, @RequestParam(name = "name") String name,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		Page<Sponsor> sponsors = publicServices.getSponsorByName("%" + name + "%", page - 1, size);
		int[] pages = new int[sponsors.getTotalPages()];
		
		model.addAttribute("currentPage", page);
		model.addAttribute("sponsors", sponsors);
		return "sponsors/sponsors";
	}
	

	/*@GetMapping("/sponsors/{id}")
	public String sponsorById(Model model, @PathVariable long id) {
		Sponsor sponsor = publicServices.getSponsorById(id);
		
		model.addAttribute("sponsor", sponsor);
		return "sponsors/sponsor";
	}*/

	@GetMapping("/sponsors/{id}")
	public String sponsorById(ModelMap map, @PathVariable long id, HttpServletResponse response) {
		Sponsor sponsor = publicServices.getSponsorById(id);
		if (sponsor == null) {
			response.setStatus(404);
			return "error/404";
		}
		map.put("sponsor", sponsor);
		return "sponsors/details";
	}
	
	@DeleteMapping("/sponsors/{id}")
	public String delete(@PathVariable long id, Model model) {
		Sponsor s = publicServices.getSponsorById(id);
		
		adminBusiness.deleteSponsor(s);
		return "sponsors/sponsors";
	}
	@GetMapping("/sponsors/add")
	public String addForm(ModelMap map) {
		Map<String, String> errors = new HashMap<>();
		map.put("errors", errors);
		map.put("sponsor", new Sponsor());
		
		return "sponsors/add";
	}
	@PostMapping("/sponsors")
	public String add(ModelMap map, String name, String url, @RequestParam("logo") MultipartFile logo, String description) {
		Sponsor sponsor = new Sponsor();
		sponsor.setName(name);
		sponsor.setUrl(url);
		sponsor.setDescription(description);
		sponsor.setLogo(Utility.upload("images/partners/", logo));
		Map<String, String> errors = new HashMap<>();
		map.put("errors", errors);
		map.put("sponsor", sponsor);
		adminBusiness.addSponsor(sponsor);
		
		return "sponsors/sponsors";
		}
	
	@GetMapping("/sponsors/update/{id}")
	public String updateForm(ModelMap map, @PathVariable Long id,HttpServletResponse response) {
	Map<String, String> errors = new HashMap<>();
		map.put("errors", errors);
		Sponsor sponsor = publicServices.getSponsorById(id);
		if (sponsor == null) {
			response.setStatus(404);
			return "error/404";
		}else {
		map.put("sponsor", sponsor);
		return "sponsors/update";
	}}
	
	@PutMapping("/sponsors")
	public String update(ModelMap map, Sponsor sponsor, @RequestParam MultipartFile imageFile) {
		Map<String, String> errors = new HashMap<>();
		map.put("errors", errors);
		map.put("sponsor", sponsor);
		
		try {
			if (!imageFile.isEmpty()) {
				sponsor.setLogo(Utility.upload("images/sponsors/", imageFile));
			} else
				sponsor.setLogo(publicServices.getSponsorById(sponsor.getId()).getLogo());
			adminBusiness.updateSponsor(sponsor);
		
		} catch (DataIntegrityViolationException ex) {
			errors.put("name", "A case with the same name already exists!");
			return "sponsors/update";
		}

	return "redirect:sponsors/" ;
	}

}
