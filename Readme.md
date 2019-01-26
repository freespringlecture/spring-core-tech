# 데이터 바인딩 추상화: PropertyEditor
`org.springframework.validation.DataBinder`
> 기술적인 관점: 프로퍼티 값을 타겟 객체에 설정하는 기능
> 사용자 관점: 사용자 입력값을 애플리케이션 도메인 모델에 동적으로 변환해 넣어주는 기능
> 해석하자면: 입력값은 대부분 “문자열”인데, 그 값을 객체가 가지고 있는 int, long, Boolean, Date 등 
> 심지어 Event, Book 같은 도메인 타입으로도 변환해서 넣어주는 기능

## PropertyEditor
- 스프링 3.0 이전까지 DataBinder가 변환 작업 사용하던 인터페이스
- 쓰레드-세이프 하지 않으므로 빈으로 등록해서 사용하면 안됨
  (상태 정보 저장 하고 있음, 따라서 싱글톤 빈으로 등록해서 쓰다가는...)
- Object와 String 간의 변환만 할 수 있어, 사용 범위가 제한적 임 
  (그래도 그런 경우가 대부분이기 때문에 잘 사용해 왔음 조심해서..)
```java
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
```

## @InitBinder
> 프로퍼티 에디터를 사용하기 위해 컨트롤러에서 사용할 바인더들을 등록하는 방법(전역적으로 등록하는 방법도 있음)
> DataBinder의 구현체 중에 하나인 WebDataBinder에 PropertyEditor를 등록
> 컨트롤러가 요청을 처리하기 전에 컨트롤러에서 정의한 DataBinder에 들어있는 PropertyEditor를 사용하게 됨
```java
@InitBinder
public void init(WebDataBinder webDataBinder) {
    webDataBinder.registerCustomEditor(Event.class, new EventEditor());
}
```