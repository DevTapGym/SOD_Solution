package web.internship.SODSolutions.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.internship.SODSolutions.model.Form;
import web.internship.SODSolutions.repository.FormRepository;
import web.internship.SODSolutions.util.error.AppException;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FormService {

    FormRepository formRepository;

    static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{8,15}$");

    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    public Form getFormById(long formId) {
        return formRepository.findById(formId)
                .orElseThrow(() -> new AppException("Form not found with id: " + formId));
    }

    @Transactional
    public Form createForm(Form form) {
        validateForm(form);

        // Kiểm tra email đã tồn tại chưa
        if (formRepository.existsByEmail(form.getEmail())) {
            throw new AppException("Email already exists");
        }

        // Kiểm tra số điện thoại đã tồn tại chưa
        if (formRepository.existsByPhone(form.getPhone())) {
            throw new AppException("Phone number already exists");
        }

        return formRepository.save(form);
    }

    @Transactional
    public void deleteForm(long formId) {
        if (!formRepository.existsById(formId)) {
            throw new AppException("Form not found with id: " + formId);
        }
        formRepository.deleteById(formId);
    }

    private void validateForm(Form form) {
        if (form.getName() == null || form.getName().trim().isEmpty()) {
            throw new AppException("Full name is required");
        }
        if (form.getPhone() == null || !PHONE_PATTERN.matcher(form.getPhone()).matches()) {
            throw new AppException("Invalid phone number format");
        }
        if (form.getEmail() == null || !EMAIL_PATTERN.matcher(form.getEmail()).matches()) {
            throw new AppException("Invalid email format");
        }
        if (form.getCompanyName() == null || form.getCompanyName().trim().isEmpty()) {
            throw new AppException("Company name is required");
        }
    }
}
