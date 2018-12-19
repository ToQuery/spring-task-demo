package io.github.toquery.task.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppJobStatus {
    private String id;
    private boolean running;
    private String groupName;

    public AppJobStatus(String id, boolean running) {
        this.id = id;
        this.running = running;
    }
}
