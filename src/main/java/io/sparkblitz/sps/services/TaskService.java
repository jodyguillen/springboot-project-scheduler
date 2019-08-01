package io.sparkblitz.sps.services;

import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.models.Task;
import io.sparkblitz.sps.repositories.ProjectRepository;
import io.sparkblitz.sps.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService extends DataService<Task, Integer, TaskRepository> {

    @Autowired
    private ProjectRepository projectRepository;

    public Task save(Integer projectId, Task task) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if(null == project) {
            return null;
        }

        task.setProject(project);
        return save(task);
    }

    public List<Task> findByProjectId(Integer projectId) {
        return getMyRepository().findByProjectId(projectId);
    }
}
