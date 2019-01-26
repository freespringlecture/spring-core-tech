package me.freelife;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Single {

    @Autowired
//    private ObjectProvider<Proto> proto;
    private Proto proto;

    public Proto getProto() {
        return proto;
    }
}
