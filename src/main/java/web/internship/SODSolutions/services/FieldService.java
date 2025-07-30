package web.internship.SODSolutions.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.internship.SODSolutions.dto.response.ResFieldDTO;
import web.internship.SODSolutions.mapper.FieldMapper;
import web.internship.SODSolutions.model.Field;
import web.internship.SODSolutions.repository.FieldRepository;
import web.internship.SODSolutions.util.error.AppException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FieldService {
    FieldRepository fieldRepository;
    FieldMapper fieldMapper;

    public List<ResFieldDTO> getAllFields() {
        return fieldMapper.toResFieldDTO(fieldRepository.findAll());
    }

    @Transactional
    public ResFieldDTO createField(ResFieldDTO resFieldDTO) {
        Field newField = fieldMapper.toField(resFieldDTO);
        fieldRepository.save(newField);
        return fieldMapper.toResFieldDTO(newField);
    }

    @Transactional
    public ResFieldDTO updateField(ResFieldDTO resFieldDTO) {
        Field existingField = fieldRepository.findById(resFieldDTO.getId())
                .orElseThrow(() -> new AppException("Field not found with id: " + resFieldDTO.getId()));

        existingField.setFieldName(resFieldDTO.getFieldName());
        existingField.setDescription(resFieldDTO.getDescription());

        fieldRepository.save(existingField);
        return fieldMapper.toResFieldDTO(existingField);
    }

    @Transactional
    public void deleteField(long id) {
        if(!fieldRepository.existsById(id)) {
            throw new AppException("Field not found with id: " +id);
        }

        fieldRepository.deleteById(id);
    }
}
