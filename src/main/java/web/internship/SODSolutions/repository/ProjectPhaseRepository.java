package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.ProjectPhase;

public interface ProjectPhaseRepository extends JpaRepository<ProjectPhase, Long> {
}