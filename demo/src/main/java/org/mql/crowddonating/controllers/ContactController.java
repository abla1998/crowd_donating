package org.mql.crowddonating.controllers;

import org.mql.crowddonating.business.IContactServices;
import org.mql.crowddonating.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private IContactServices contactServices;


    @GetMapping("/contact")
    public String contact() {
        return "contact/contact";
    }
    @PostMapping("/contact")
    public String addContact(Contact contact, RedirectAttributes redirectAttributes) {
        contactServices.add(contact);
        redirectAttributes.addFlashAttribute("message", "Your message have been sent successfully!");

        return "redirect:/contact";
    }

    @GetMapping("/admin/contacts/contacts")
    public String contactList(ModelMap map) {
        map.put("contacts", contactServices.getAll());
        return "admin/contacts/contacts";
    }

    @GetMapping("/admin/contacts/{id}")
    public String contactDetail(ModelMap map, @PathVariable long id) {
        map.put("contact", contactServices.get(id));
        return "admin/contacts/details";
    }

    @ResponseBody
    @DeleteMapping("/admin/contacts/{id}/delete")
    public boolean sponsorBan(ModelMap map, @PathVariable long id) {
        contactServices.delete(id);
        return true;
    }
}
