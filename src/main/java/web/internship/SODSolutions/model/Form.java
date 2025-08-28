package web.internship.SODSolutions.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "forms")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String name;

    private String phone;

    private String email;

    private boolean isAdvised;

    @Column(name = "company_name")
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
}
