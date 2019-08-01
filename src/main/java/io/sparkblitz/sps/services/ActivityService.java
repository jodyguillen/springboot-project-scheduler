package io.sparkblitz.sps.services;

import io.sparkblitz.sps.models.ActivityDependency;
import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.repositories.ProjectRepository;
import io.sparkblitz.sps.repositories.TaskDependencyRepository;
import io.sparkblitz.sps.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService extends DataService<Activity, Integer, TaskRepository> {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskDependencyRepository taskDependencyRepository;

    public Activity save(Integer projectId, Activity activity) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if(null == project) {
            return null;
        }

        activity.setProject(project);
        return save(activity);
    }

    public List<Activity> findByProjectId(Integer projectId) {
        return getMyRepository().findByProjectId(projectId);
    }

    public Activity addNextActivity(Integer rootTaskId, Integer nextTaskId) {
        Activity root = getMyRepository().findById(rootTaskId).orElse(null);
        Activity next = null;
        if(null != root) {
            next = getMyRepository().findById(nextTaskId).orElse(null);
        }

        if(null == next) {
            return null;
        }

        ActivityDependency dependency = new ActivityDependency(root, next);
        taskDependencyRepository.save(dependency);
        return next;
    }

    public Activity addNextActivity(Activity activity, Activity next) {
        if(null == activity || null == next) {
            return null;
        }

        ActivityDependency dependency = new ActivityDependency(activity, next);
        taskDependencyRepository.save(dependency);
        return next;
    }

    public boolean hasPredecessors(Integer taskId) {
        Activity activity = getMyRepository().findById(taskId).orElse(null);
        return hasPredecessors(activity);
    }

    public boolean hasPredecessors(Activity activity) {
        if(null == activity) {
            return true; // non-existent anyway
        }
        ActivityDependency root = new ActivityDependency(null, activity);
        return taskDependencyRepository.exists(Example.of(root));
    }

    public List<Activity> findSuccessors(Integer taskId) {
        Activity root = getMyRepository().findById(taskId).orElse(null);
        return findSuccessors(root);
    }

    public List<Activity> findSuccessors(Activity activity) {
        if(null == activity) {
            return new ArrayList<>();
        }

        ActivityDependency dependency = new ActivityDependency(activity);
        List<ActivityDependency> next = (List<ActivityDependency>) taskDependencyRepository.findAll(Example.of(dependency));
        List<Activity> list = next.stream().map(d -> d.getNextActivity()).collect(Collectors.toList());
        return list;
    }
}
