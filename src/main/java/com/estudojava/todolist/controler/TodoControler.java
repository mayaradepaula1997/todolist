package com.estudojava.todolist.controler;

import com.estudojava.todolist.entity.Todo;
import com.estudojava.todolist.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoControler {


    private TodoService todoService;

    public TodoControler(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public List<Todo> create(@RequestBody @Valid Todo todo){

        return todoService.create(todo);
    }

    @GetMapping
    public List<Todo> list(){

        return todoService.list();
    }


    @PutMapping("{id}")
    public List<Todo> update(@PathVariable Long id, @RequestBody Todo todo){

        return todoService.update(id,todo);

    }

    @DeleteMapping("{id}")
    public List<Todo> delete(@PathVariable ("id") Long id){

        return todoService.delete(id);
    }

}
