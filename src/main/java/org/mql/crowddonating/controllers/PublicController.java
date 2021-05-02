package org.mql.crowddonating.controllers;

import org.mql.crowddonating.business.IPublicServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

	@Autowired
	@Qualifier("publicServicesBusiness")
	public IPublicServices publicServices;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("events", publicServices.getLastNEvents());
		model.addAttribute("cases", publicServices.findLastNCases());
		model.addAttribute("projects", publicServices.findLastNProjects());
		model.addAttribute("sponsors", publicServices.getAllSponsors());
		model.addAttribute("stats", publicServices.globalStats());
		model.addAttribute("pstats", publicServices.projectGlobalStats());
		return "public/index";
	}
}
