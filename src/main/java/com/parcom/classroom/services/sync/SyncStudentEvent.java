package com.parcom.classroom.services.sync;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SyncStudentEvent extends ApplicationEvent {

    private final Long idStudent;

    public SyncStudentEvent(Object source, Long idStudent) {
        super(source);
        this.idStudent = idStudent;
    }
}
