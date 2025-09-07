package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.internship.SODSolutions.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.id = 2")
    Role getRoleUser();

    @Query("select r from Role r where r.id = 1")
    Role getRoleAdmin();
}