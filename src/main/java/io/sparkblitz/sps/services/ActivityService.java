package io.sparkblitz.sps.services;

import io.sparkblitz.sps.domain.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.sparkblitz.sps.repositories.ActivityRepository;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> listNext(String code) {
        return activityRepository.findNextByCode(code);
    }
}
