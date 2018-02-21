package cz.vut.fit.pis.bakery.bakery.controller;

import cz.vut.fit.pis.bakery.bakery.model.Task;

import cz.vut.fit.pis.bakery.bakery.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskRepository taskRepository;

    private final SimpleDateFormat simpleDateFormat;

    @Autowired
    public TaskController(TaskRepository taskRepository, SimpleDateFormat simpleDateFormat) {
        this.taskRepository = taskRepository;
        this.simpleDateFormat = simpleDateFormat;
    }

    @GetMapping("/{day}")
    public ResponseEntity<List<Task>> getPlanForTheDay(@PathVariable(value = "day") String day){
        List<Task> tasks;

        try {

            //TODO: Clear + " 02:00:00.000000"
            tasks = taskRepository.findByDate(simpleDateFormat.parse(day + " 02:00:00.000000"));
        } catch (ParseException e) {
            return ResponseEntity.notFound().build();
        }

        if (tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{from}/{to}")
    public ResponseEntity<List<Task>> getPlanInPeriod(@PathVariable(value = "from") String from, @PathVariable(value = "to") String to){
        List<Task> tasks;
        try {
            //TODO: Clear + " 02:00:00.000000"
            tasks = taskRepository.getTasksInPeriod(simpleDateFormat.parse(from+ " 02:00:00.000000"), simpleDateFormat.parse(to+ " 02:00:00.000000"));
        } catch (ParseException e) {
            return ResponseEntity.notFound().build();
        }

        if (tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tasks);
    }


    @PostMapping("/")
    public Task createPlan(@RequestBody Task task){
        try {
            task.setDate(simpleDateFormat.parse(simpleDateFormat.format(task.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return taskRepository.save(task);
    }

    //TODO: PutMapping

}
