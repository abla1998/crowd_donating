package org.mql.crowddonating.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mql.crowddonating.business.IAssociationBusiness;
import org.mql.crowddonating.business.IDonorBusiness;
import org.mql.crowddonating.business.implementations.PublicServicesBusiness;
import org.mql.crowddonating.dao.ConfirmationTokenRepository;
import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Domain;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.File;
import org.mql.crowddonating.models.Type;
import org.mql.crowddonating.models.User;
import org.mql.crowddonating.utilities.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("donorBusiness")
	private IDonorBusiness donorBusiness;

	@Autowired
	@Qualifier("associationBusiness")
	private IAssociationBusiness associationBusiness;

	@Autowired
	@Qualifier("confirmationTokenRepository")
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	@Qualifier("publicServicesBusiness")
	private PublicServicesBusiness publicServices;

	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

	@RequestMapping("/forbidden")
	public String accessDenied() {
		return "error/403";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}

	@GetMapping("/donator/register")
	public ModelAndView displayRegistration(ModelAndView modelAndView, Donor donor) {
		modelAndView.addObject("donor", donor);
		modelAndView.setViewName("auth/donator-registration");
		return modelAndView;
	}

	@PostMapping("/donator/register")
	public ModelAndView registerUser(ModelAndView modelAndView, Donor donor) {

		User existingUser = donorBusiness.findByEmailIgnoreCase(donor.getEmail());
		if (existingUser != null) {
			modelAndView.addObject("message", "This email already exists!");
			modelAndView.setViewName("auth/error");
		} else {
			donorBusiness.signup(donor);
			modelAndView.addObject("email", donor.getEmail());
			modelAndView.setViewName("auth/successfulRegisteration");
		}

		return modelAndView;
	}

	@GetMapping("/confirm-account/{token}")
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @PathVariable("token") String confirmationToken) {

		if (donorBusiness.confirmation(confirmationToken)) {
			modelAndView.setViewName("auth/accountVerified");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}

		return modelAndView;
	}

	@GetMapping("/association/register")
	public ModelAndView displayRegistration(ModelAndView modelAndView, Association association) {
		Map<String, String> errors = new HashMap<>();
		modelAndView.addObject("errors", errors);
		modelAndView.addObject("association", association);
		modelAndView.addObject("domains", publicServices.getAllDomains());
		modelAndView.setViewName("auth/association-registration");

		return modelAndView;
	}

	private void uploadDocuments(Association association, MultipartFile[] documents) {
		for (MultipartFile doc : documents) {
			if (!"".equals(doc.getName())) {
				File file = new File();
				file.setPath(Utility.upload("files/association/", doc));
				file.setType("document");
				file.setAssociation(association);
				associationBusiness.saveFile(file);
			}
		}
	}

	@PostMapping("/association/register")
	public ModelAndView registerUser(ModelAndView modelAndView, Association association, String[] associationDomains,
			@RequestParam MultipartFile[] documents) {

		User existingUser = associationBusiness.findByEmailIgnoreCase(association.getEmail());
		if (existingUser != null) {
			modelAndView.addObject("message", "This email already exists!");
			modelAndView.setViewName("auth/error");
		} else {
			if (associationDomains == null || associationDomains.length == 0) {
				modelAndView.setViewName("auth/association-registration");
			} else {
				for (String ct : associationDomains) {
					Domain domain = new Domain();
					domain.setLabel(ct);
					try {
						associationBusiness.addDomain(domain);
					} catch (Exception ex) {
						// domains = associationBusiness.findTypeByLabel(ct);
					}
					association.addDomain(domain);
					modelAndView.setViewName("auth/successRegistration");
				}
				associationBusiness.signup(association);
				uploadDocuments(association, documents);
			}
		}
		return modelAndView;
	}

}
