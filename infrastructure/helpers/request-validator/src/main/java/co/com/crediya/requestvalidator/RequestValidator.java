package co.com.crediya.requestvalidator;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;

@Component
public class RequestValidator {
    private final Validator validator;

    public RequestValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> List<String> validate(T request){
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(request, request.getClass().getName());
        validator.validate(request, errors);

        if (errors.hasErrors()){
            return errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
        }

        return Collections.emptyList();
    }
}
