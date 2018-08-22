package com.severgroup.controller;

import com.severgroup.service.AvgService;
import com.severgroup.to.AvgRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {
    private final static Logger LOGGER = Logger.getLogger(WebController.class);

    @Autowired
    private AvgService service;

    //http://localhost:8080/records
    @GetMapping("/records")
    public ModelAndView list() {
        List<AvgRecord> records = (List<AvgRecord>) service.findAll();
        if (records != null && records.size() > 0) {
            LOGGER.debug("send list of avg:");
            return new ModelAndView("records_List", "recordsList", records);
        } else return new ModelAndView("errorpage");
    }

    //http://localhost:8080/records/get?username=Ivan
    @GetMapping("/get{username}")
    public ModelAndView search(@RequestParam("username") String username, ModelMap map) {
        List<AvgRecord> records = service.findByName(username);
        if (records != null && records.size() > 0) {
            LOGGER.debug("send list of avg with user id = " + username);
            return new ModelAndView("records_List", "recordsList", records);
        } else return new ModelAndView("notfound", "errorname", username);
    }

    @GetMapping("/")
    public ModelAndView redirect() {
        return new ModelAndView("redirect:/records");
    }
}