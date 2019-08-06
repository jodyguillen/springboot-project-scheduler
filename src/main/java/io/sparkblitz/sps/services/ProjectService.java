package io.sparkblitz.sps.services;

import io.sparkblitz.sps.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.sparkblitz.sps.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void removeAll(){
        projectRepository.deleteAll();
    }

    public Project getById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Iterable<Project> list() {
        return projectRepository.findAll();
    }
}
