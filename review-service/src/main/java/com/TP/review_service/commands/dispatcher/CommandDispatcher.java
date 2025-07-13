package com.TP.review_service.commands.dispatcher;
import com.TP.review_service.commands.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandDispatcher {
    public void dispatch(Command command) {
        command.execute();
    }
}
