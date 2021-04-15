package br.com.zupacademy.proposta.compartilhado;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@CNPJ
@CPF
@ConstraintComposition(CompositionType.OR)
public @interface CPForCNPJConstraint {

    String message() default "{field} está inválido!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
