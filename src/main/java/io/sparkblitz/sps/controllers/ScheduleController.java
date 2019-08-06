package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.scheduling.Calendar;
import io.sparkblitz.sps.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private SchedulingService schedulingService;

    @RequestMapping(value="/projects/{projectId}", method = RequestMethod.POST)
    public String schedule(@PathVariable("projectId") Long projectId, @RequestParam(name = "start", required = false) @DateTimeFormat(pattern="yyyyMMdd") Date startDate,
                           @RequestParam(name = "unit", required = false) String unit, Model model) {
        Calendar calendar = schedulingService.plotCalendar(projectId, startDate, unit);
        model.addAttribute("calendar", calendar);
        return "calendar";
    }
}
