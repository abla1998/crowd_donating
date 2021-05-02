package org.mql.crowddonating.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.mql.crowddonating.business.IAssociationBusiness;
import org.mql.crowddonating.business.IPublicServices;
import org.mql.crowddonating.business.IUserServices;
import org.mql.crowddonating.business.implementations.PublicServicesBusiness;
import org.mql.crowddonating.models.Association;
import org.mql.crowddonating.models.File;
import org.mql.crowddonating.models.Project;
import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProjectsController {

    @Autowired
    @Qualifier("publicServicesBusiness")
    private PublicServicesBusiness publicBusiness;

    @Autowired
    @Qualifier("userBusiness")
    private IUserServices userBusiness;

    @Autowired
    @Qualifier("associationBusiness")
    private IAssociationBusiness associationBusiness;

    @Autowired
    @Qualifier("publicServicesBusiness")
    private IPublicServices publicServices;

    @GetMapping("/projects")
    public String home(Model model) {
        List<Project> projects = publicBusiness.getAllProject().stream().filter(p -> p.isDisabled() == false).collect(Collectors.toList());
        model.addAttribute("projects", projects);
        return "projects/projects";
    }

    @GetMapping("/projects/add")
    public String formAddProject(Model model) {
        model.addAttribute("project", new Project());
        return "projects/add";
    }

    @GetMapping("/projects/{slug}")
    public String caseBySlug(ModelMap map, @PathVariable String slug, HttpServletResponse response) {
        Project project = publicServices.getProject(slug);
        if (project == null) {
            response.setStatus(404);
            return "error/404";
        }
        map.put("project", project);
        return "projects/details";
    }

    @PostMapping("/projects")
    public String add(ModelMap map, Project project, @RequestParam MultipartFile imageFile,
                      @RequestParam MultipartFile[] documents) {
        map.put("project", project);

        String projectSlug = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Association assoc = userBusiness.getAssociationByUserName(auth.getName());
            project.setAssociation(assoc);
            projectSlug = associationBusiness.addProject(project);
            if (imageFile.isEmpty())
                project.setImage("blog-1.jpg");
            else
                project.setImage(Utility.upload("images/projects/", imageFile));
            associationBusiness.updateProject(project);
            uploadDocuments(project, documents);


        } catch (DataIntegrityViolationException ex) {
            return "projects/add";
        }

        System.out.println(project);
        if (projectSlug != null)
            return "redirect:projects/" + projectSlug;
        return "redirect:/home";
    }

    private void uploadDocuments(Project project, MultipartFile[] documents) {
        for (MultipartFile doc : documents) {
            if (!"".equals(doc.getName())) {
                File file = new File();
                file.setPath(Utility.upload("files/projects/", doc));
                file.setType("document");
                file.setProject(project);
                associationBusiness.saveFile(file);
            }
        }
    }

    @GetMapping("/projects/association/{id}")
    public String projectByAssociation(ModelMap map, @PathVariable long id) {
        Association association = (Association) publicServices.getAssociationById(id);
        List<Project> projects = publicServices.getProjectsByAssociation(association).stream().filter(p -> p.isDisabled() == false).collect(Collectors.toList());

        map.put("projects", projects);
        return "projects/projects";
    }

    @GetMapping("/projects/association")
    public String myprojects(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Association association = userBusiness.getAssociationByUserName(auth.getName());
        List<Project> projects = publicServices.getProjectsByAssociation(association).stream().filter(p -> p.isDisabled() == false).collect(Collectors.toList());
        map.put("projects", projects);
        return "projects/projects";
    }


}
