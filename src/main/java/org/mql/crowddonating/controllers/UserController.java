package org.mql.crowddonating.controllers;


import org.mql.crowddonating.business.IPublicServices;
import org.mql.crowddonating.business.IUserServices;
import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    @Qualifier("userBusiness")
    private IUserServices userBusiness;
    
    @Autowired
    @Qualifier("publicServicesBusiness")
    private IPublicServices publicServices;


    @GetMapping("/association/profile")
    public String associationProfile(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Association assoc = userBusiness.getAssociationByUserName(auth.getName());
        getProfileStats(map, assoc);
        return "association/profile";
    }

	private void getProfileStats(ModelMap map, Association assoc) {
		map.put("association", assoc);

        List<Case> cases = publicServices.getCasesByAssociation(assoc.getId());
        map.put("cases", cases);
		map.put("stats", publicServices.globalStatsForAssociation(cases));
		
		map.put("projects", publicServices.getProjectsByAssociation(assoc));
		map.put("pstats", publicServices.globalProjectsStatsForAssociation(assoc));

	}
    
    @GetMapping("/association/profile/{id}")
    public String associationById(ModelMap map, @PathVariable long id, HttpServletResponse response) {
        Association assoc = userBusiness.getAssociationById(id);
        getProfileStats(map, assoc);
        return "association/profile";
    }

    @GetMapping("/association/{idA}/file/delete/{id}")
    public String deleteFile(@PathVariable long idA, @PathVariable long id, Model model) {
        userBusiness.deleteFile(id, "files/associations/");
        return "redirect:/association/profile/" + idA;
    }


    @GetMapping("/donor/profile/{id}")
    public String donorById(ModelMap map, @PathVariable long id, HttpServletResponse response) {
        Donor donor = userBusiness.getDonorById(id);
        System.out.println(donor);
        if (donor == null) {
            response.setStatus(404);
            return "error/404";
        }
        map.put("donor", donor);
        return "donor/profile";
    }
    
    @GetMapping("association/update")
    public String getUpdateForm(ModelMap map) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	map.put("association", userBusiness.getAssociationByUserName(auth.getName()));
    	map.put("domains", publicServices.getAllDomains());
        return "association/update";
    }
    
}
