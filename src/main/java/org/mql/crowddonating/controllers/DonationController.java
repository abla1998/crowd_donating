package org.mql.crowddonating.controllers;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletResponse;

import org.mql.crowddonating.business.IDonorBusiness;
import org.mql.crowddonating.business.IPublicServices;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.Donor;
import org.mql.crowddonating.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DonationController {

    @Autowired
    @Qualifier("publicServicesBusiness")
    private IPublicServices publicServices;

    @Autowired
    @Qualifier("donorBusiness")
    private IDonorBusiness donorBusiness;

    @GetMapping("/cases/{slug}/donate")
    public String donateForm(Model model, @PathVariable String slug) {

        Case aCase = publicServices.getCaseBySlug(slug);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Donor donor = donorBusiness.getDonorByUsername(auth.getName());
    	
        model.addAttribute("donor", donor);

        Donation donation = new Donation();
        donation.setCase(aCase);

        model.addAttribute("donation", donation);

        return "donor/donate";
    }
    
    @GetMapping("/projects/{slug}/donate")
    public String donateFormProject(Model model, @PathVariable String slug) {

        Project project = publicServices.getProject(slug);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Donor donor = donorBusiness.getDonorByUsername(auth.getName());
    	
        model.addAttribute("donor", donor);

        Donation donation = new Donation();
        donation.setProject(project);

        model.addAttribute("donation", donation);

        return "donor/project";
    }

    @PostMapping("/cases/{slug}/donate")
    public String processDonate(Donation donation, Double donationValue, @PathVariable String slug) {
        donation.setAmount(donationValue);
        donorBusiness.addDonation(donation);
        return "redirect:/cases/" + donation.getCase().getSlug();
    }

    @GetMapping("/donations/{id}")
    public String caseBySlug(ModelMap map, @PathVariable Long id, HttpServletResponse response) {
        Donation donation = publicServices.getDonationById(id);
        if (donation == null) {
            response.setStatus(404);
            return "error/404";
        } else {
            map.put("donation", donation);
            double total = donation.getCase().getAmount();
            double percent = (donation.getAmount() / total) * 100;
            DecimalFormat df2 = new DecimalFormat(".##");
            map.put("percentDonation", df2.format(percent));
            map.put("percent", Math.round(percent) + "");
            return "donations/details";
        }
    }
}
