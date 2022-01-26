package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.Counteragent;
import com.example.MyFamilyMoney.repo.CounteragentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class СounteragentController {

    @Autowired
    private CounteragentRepository counteragentRepository;

    @GetMapping("/counteragent")
    public String counteragentMain(Model model) {
        Iterable<Counteragent> counteragents = counteragentRepository.findAll();
        model.addAttribute("counteragents", counteragents);
        return "counteragent-main";
    }

    @GetMapping("/counteragent/add")
    public String counteragentAdd(Model model) {
        return "counteragent-add";
    }

    @PostMapping("/counteragent/add")
    public String counteragentAdd(@RequestParam String name, @RequestParam String description, Model model) {
        Counteragent counteragent = new Counteragent(name, description);
        counteragentRepository.save(counteragent);
        return "redirect:/counteragent";
    }
    @GetMapping("/counteragent/{id}")
    public String counteragentDetails(@PathVariable(value = "id") long id, Model model) {
        if (!counteragentRepository.existsById(id)) {
            return "redirect:/conteragent";
        }
        Optional<Counteragent> counteragent = counteragentRepository.findById(id);
        ArrayList<Counteragent> res = new ArrayList<>();
        counteragent.ifPresent(res::add);
        model.addAttribute("counteragent", res);
        return "counteragent-detail";
    }
    @GetMapping("/counteragent/{id}/edit")
    public String counteragentEdit(@PathVariable(value = "id") long id, Model model) {
        if (!counteragentRepository.existsById(id)) {
            return "redirect:/counteragent";
        }
        Optional<Counteragent> counteragent = counteragentRepository.findById(id);
        ArrayList<Counteragent> res = new ArrayList<>();
        counteragent.ifPresent(res::add);
        model.addAttribute("counteragent", res);
        return "counteragent-edit";
    }
    @PostMapping("/counteragent/{id}/edit")
    public String counteragentPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String description,  Model model) {
        Counteragent counteragent = counteragentRepository.findById(id).orElseThrow();
        counteragent.setName(name);
        counteragent.setDescription(description);
        counteragentRepository.save(counteragent);
        return "redirect:/counteragent";
    }
    @PostMapping("/counteragent/{id}/remove")
    public String counteragentPostDelete(@PathVariable(value = "id") long id, Model model) {
        Counteragent counteragent = counteragentRepository.findById(id).orElseThrow();
        counteragentRepository.delete(counteragent);
        return "redirect:/counteragent";
    }
}

