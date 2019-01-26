package me.freelife;

import java.beans.PropertyEditorSupport;

public class EventEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Event event = (Event) getValue();
        return super.getAsText();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        //문자열을 숫자로 변환함
        setValue(new Event(Integer.parseInt(text)));
    }
}
