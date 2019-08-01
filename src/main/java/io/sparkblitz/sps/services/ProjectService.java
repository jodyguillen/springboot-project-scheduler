package io.sparkblitz.sps.services;

import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends DataService<Project, Integer, ProjectRepository> {
}
