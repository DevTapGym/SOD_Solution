package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}