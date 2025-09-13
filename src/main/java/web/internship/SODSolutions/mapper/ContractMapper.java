package web.internship.SODSolutions.mapper;

import org.mapstruct.*;
import web.internship.SODSolutions.dto.request.ReqContractDTO;
import web.internship.SODSolutions.dto.response.ResContractDTO;
import web.internship.SODSolutions.model.Contract;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    Contract toContract(ReqContractDTO reqContractDTO);

    @Mapping(target = "projectId",source = "project.id")
    ResContractDTO toResContractDTO(Contract contract);

    List<ResContractDTO> toResContractDTOs(List<Contract> contracts);
}