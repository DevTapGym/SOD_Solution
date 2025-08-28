package web.internship.SODSolutions.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.internship.SODSolutions.dto.request.ReqFormDTO;
import web.internship.SODSolutions.dto.response.ResFormDTO;
import web.internship.SODSolutions.mapper.FormMapper;
import web.internship.SODSolutions.model.Field;
import web.internship.SODSolutions.model.Form;
import web.internship.SODSolutions.repository.FieldRepository;
import web.internship.SODSolutions.repository.FormRepository;
import web.internship.SODSolutions.util.error.AppException;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FormService {

    FormRepository formRepository;
    FormMapper formMapper;
    FieldRepository fieldRepository;

    static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{8,15}$");

    public List<ResFormDTO> getAllForms() {
        return formMapper.toResFormDTO(formRepository.findAll());
    }

    public ResFormDTO getFormById(long formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new AppException("Form not found with id: " + formId));

        return formMapper.toResFormDTO(form);
    }

    @Transactional
    public ResFormDTO createForm(ReqFormDTO reqFormDTO) {
        validateForm(reqFormDTO);

        // Kiểm tra email đã tồn tại chưa
        if (formRepository.existsByEmail(reqFormDTO.getEmail())) {
            throw new AppException("Email already exists");
        }

        // Kiểm tra số điện thoại đã tồn tại chưa
        if (formRepository.existsByPhone(reqFormDTO.getPhone())) {
            throw new AppException("Phone number already exists");
        }

        Field field = fieldRepository.findById(reqFormDTO.getFieldId()).orElseThrow(
                () -> new AppException("Field not found with id: " + reqFormDTO.getFieldId()));

        Form form = formMapper.toForm(reqFormDTO);
        form.setField(field);

        return formMapper.toResFormDTO(formRepository.save(form));
    }

    @Transactional
    public void deleteForm(long formId) {
        if (!formRepository.existsById(formId)) {
            throw new AppException("Form not found with id: " + formId);
        }
        formRepository.deleteById(formId);
    }

    private void validateForm(ReqFormDTO form) {
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
