package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Project;


import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> getProjectByUser_Email(String email);

}