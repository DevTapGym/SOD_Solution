package web.internship.SODSolutions.mapper;

import org.mapstruct.*;
import web.internship.SODSolutions.dto.request.ReqPaymentDTO;
import web.internship.SODSolutions.dto.response.ResPaymentDTO;
import web.internship.SODSolutions.model.Payment;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectPhase", ignore = true)
    Payment toPayment(ReqPaymentDTO reqPaymentDTO);

    ResPaymentDTO toResPaymentDTO(Payment payment);

    List<ResPaymentDTO> toResPaymentDTO(List<Payment> payments);
}