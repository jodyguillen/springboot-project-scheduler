package io.sparkblitz.sps.services;

import io.sparkblitz.sps.domain.Activity;
import io.sparkblitz.sps.domain.Project;
import io.sparkblitz.sps.scheduling.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SchedulingService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ActivityService activityService;

    public Calendar plotCalendar(Long projectId, Date startDate, String unit) {
        Project project = projectService.getById(projectId);
        Calendar calendar = new Calendar(project, startDate, unit);
        calendar.schedule((activityCode) -> activityService.listNext(activityCode));
        return calendar;
    }
}
