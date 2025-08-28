package web.internship.SODSolutions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fields")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_name", unique = true, nullable = false)
    private String fieldName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "field")
    private List<Project> projects;

    @OneToMany(mappedBy = "field")
    private List<Form> forms;
}
