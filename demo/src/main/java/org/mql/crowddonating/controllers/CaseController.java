package org.mql.crowddonating.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mql.crowddonating.business.IAssociationBusiness;
import org.mql.crowddonating.business.IDonorBusiness;
import org.mql.crowddonating.business.IPublicServices;
import org.mql.crowddonating.business.IUserServices;
import org.mql.crowddonating.dao.RoleRepository;
import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.Case;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.File;
import org.mql.crowddonating.models.Role;
import org.mql.crowddonating.models.Type;
import org.mql.crowddonating.models.User;
import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CaseController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("associationBusiness")
    private IAssociationBusiness associationBusiness;

    @Autowired
    @Qualifier("publicServicesBusiness")
    private IPublicServices publicServices;

    @Autowired
    @Qualifier("donorBusiness")
    private IDonorBusiness donorBusiness;

    @Autowired
    @Qualifier("userBusiness")
    private IUserServices userBusiness;

    @Autowired
    private RoleRepository roleDao;

    @GetMapping("/cases")
    public String cases(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "8") int size) {
        if (page <= 0)
            page = 1;
        Page<Case> cases = publicServices.getAllCases(page - 1, size);
        return getPageCases(model, page, cases);
    }

    private String getPageCases(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
                                Page<Case> cases) {
        int[] pages = new int[cases.getTotalPages()];
        model.addAttribute("types", publicServices.getAllTypes());
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("cases", cases);
        return "cases/cases";
    }

    @GetMapping("/cases/search")
    public String cases(Model model, @RequestParam(name = "s") String s,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "8") int size) {
        Page<Case> cases = publicServices.getCasesByName("%" + s + "%", page - 1, size);
        return getPageCases(model, page, cases);
    }

    @GetMapping("/cases/type/{id}")
    public String casesByType(Model model, @PathVariable long id) {
        Type type = publicServices.getTypeById(id);
        model.addAttribute("types", publicServices.getAllTypes());
        model.addAttribute("cases", type.getCases());
        model.addAttribute("currentPage", 1);
        return "cases/cases";
    }   

    @GetMapping("/cases/{slug}")
    public String caseBySlug(ModelMap map, @PathVariable String slug, HttpServletResponse response) {
        Case aCase = publicServices.getCaseBySlug(slug);
        if (aCase == null) {
            response.setStatus(404);
            return "error/404";
        }
        map.put("case", aCase);
		map.put("cases", publicServices.findLastNCases());
		map.put("events", publicServices.getLastNEvents());
        return "cases/details";
    }

    @GetMapping("/cases/association/{id}")
    public String caseByAssociation(ModelMap map, @PathVariable long id) {
        
    	Association association = (Association) publicServices.getAssociationById(id);
    	map.put("cases", publicServices.getCasesByAssociation(association));
        return "cases/cases";
    }
    
    @GetMapping("/cases/association")
    public String myCases(ModelMap map) {
        
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Association association = userBusiness.getAssociationByUserName(auth.getName());
         map.put("cases", publicServices.getCasesByAssociation(association));
        return "cases/cases";
        
    }
    
    @DeleteMapping("/cases/{id}")
    public String delete(@PathVariable long id, Model model) {
        Case aCase = publicServices.getCaseById(id);
        System.out.println("Case : " + aCase.getName());
        for (File file : aCase.getFiles()) {
            associationBusiness.deleteFile(file.getId(), "files/cases/");
        }
        Path filePath = Paths.get(Utility.uploadDir, "images/cases/", aCase.getImage());
        try {
            Files.delete(filePath);
        } catch (IOException ignored) {
        }
        System.out.println();
        associationBusiness.deleteCase(id);
        return "redirect:/cases";
    }

    @GetMapping("/cases/add")
    public String addForm(ModelMap map) {
        Map<String, String> errors = new HashMap<>();
        map.put("errors", errors);
        map.put("aCase", new Case());
        map.put("types", publicServices.getAllTypes());
        return "cases/add";
    }

    @GetMapping("/cases/update/{slug}")
    public String updateForm(ModelMap map, @PathVariable String slug, HttpServletResponse response) {
        Map<String, String> errors = new HashMap<>();
        map.put("errors", errors);
        Case aCase = publicServices.getCaseBySlug(slug);
        List<Type> types = publicServices.getAllTypes().stream().filter(type -> !aCase.getTypes().contains(type))
                .collect(Collectors.toList());
        map.put("types", types);
        if (aCase == null) {
            response.setStatus(404);
            return "error/404";
        }
        map.put("aCase", aCase);
        return "cases/update";
    }

    @PutMapping("/cases")
    public String update(ModelMap map, Case aCase, @RequestParam MultipartFile imageFile,
                         @RequestParam MultipartFile[] documents) {
        Map<String, String> errors = new HashMap<>();
        map.put("errors", errors);
        map.put("aCase", aCase);
        try {
            if (!imageFile.isEmpty()) {
                aCase.setImage(Utility.upload("images/cases/", imageFile));
            } else
                aCase.setImage(publicServices.getCaseBySlug(aCase.getSlug()).getImage());
            associationBusiness.updateCase(aCase);
            uploadDocuments(aCase, documents);
            associationBusiness.updateCase(aCase);
        } catch (DataIntegrityViolationException ex) {
            errors.put("name", "A case with the same name already exists!");
            return "private/cases/update";
        }
        return "redirect:cases/" + aCase.getSlug();
    }

    @PostMapping("/cases")
    public String add(ModelMap map, Case aCase, String[] caseTypes, @RequestParam MultipartFile imageFile,
                      @RequestParam MultipartFile[] documents) {
        Map<String, String> errors = new HashMap<>();
        map.put("errors", errors);
        map.put("aCase", aCase);
        map.put("types", publicServices.getAllTypes());
        if (caseTypes == null) {
            errors.put("types", "At least one type is required!");
            return "cases/add";
        } else if (caseTypes.length == 0) {
            errors.put("types", "At least one type is required!");
            return "cases/add";
        } else {
            for (String ct : caseTypes) {
                Type type = new Type();
                type.setLabel(ct);
                try {
                    associationBusiness.addType(type);
                } catch (Exception ex) {
                    type = associationBusiness.findTypeByLabel(ct);
                }
                aCase.addType(type);
            }
        }
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Association assoc = userBusiness.getAssociationByUserName(auth.getName());
            aCase.setAssociation(assoc);
            associationBusiness.addCase(aCase);
            if (imageFile.isEmpty())
                aCase.setImage("blog-1.jpg");
            else
                aCase.setImage(Utility.upload("images/cases/", imageFile));
            associationBusiness.updateCase(aCase);
            uploadDocuments(aCase, documents);
        } catch (DataIntegrityViolationException ex) {
            errors.put("name", "A case with the same name already exists!");
            return "cases/add";
        }
        return "redirect:cases/" + aCase.getSlug();
    }

    @DeleteMapping("/cases/files/{id}")
    public String deleteFile(@PathVariable long id, HttpServletRequest request) {
        associationBusiness.deleteFile(id, "/files/cases/");
        return "redirect:" + request.getHeader("Referer");
    }

    private void uploadDocuments(Case aCase, MultipartFile[] documents) {
        for (MultipartFile doc : documents) {
            if (!"".equals(doc.getName())) {
                File file = new File();
                String fileName = Utility.upload("files/cases/", doc);
                file.setPath(fileName);
                file.setType(fileName.substring(fileName.lastIndexOf(".") + 1));
                file.setCase(aCase);
                associationBusiness.saveFile(file);
            }
        }
    }

    @PostConstruct
    public void createAssocAndDonor() {
        Role role = new Role(1, "DONATOR");
        roleDao.save(role);
        role = new Role(2, "ASSOCIATION");
        roleDao.save(role);
        role = new Role(3, "ADMIN");
        roleDao.save(role);


//        Association assoc = new Association();
//        assoc.setId(1);
//        assoc.setEmail("assoc-123@gmail.com");
//        assoc.setUsername("assoc-1");
//        assoc.setName("assoc-1");
//        assoc.setCover("cover.jpg");
//        role.setRole("ASSOCIATION");
//        assoc.addRole(roleDao.findByRole("ASSOCIATION"));
//        assoc.setEnabled(true);
//        assoc.setBanned(false);
//        assoc.setPassword(bCryptPasswordEncoder.encode("123"));
//        assoc.setAvatar("bd7dec8a-a7ab-4cfb-b1bc-2114a8662c55_akali.png");
//        assoc.setAddress("Adress Morocco n123");
//        assoc.setPhone("053650303030");
//        assoc.setDescription("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Commodi deleniti eaque hic laboriosam minima modi perspiciatis quia quo quos ratione saepe, similique voluptatibus voluptatum. Ab blanditiis neque odit omnis sapiente.");
//
//        publicServices.addAssociation(assoc);
//
//        System.out.println("Assoc added.");
    }
}