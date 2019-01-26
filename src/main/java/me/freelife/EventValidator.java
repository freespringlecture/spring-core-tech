package me.freelife;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        //이벤트 클래스 타입의 인스턴스를 검증하므로 파라메터 전달되는 클래스 타입이 이벤트인지 검증
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // errorCode - messageResolver를 사용해 메세지를 가져오는 메세지 키 값
        // defaultMessage - errorCode로 메세지를 찾지 못했을 때 사용할 메세지
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "notempty", "Empty title is now allowed");
    }
}
