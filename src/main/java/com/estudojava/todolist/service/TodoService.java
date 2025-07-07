package com.estudojava.todolist.service;

import com.estudojava.todolist.entity.Todo;

import com.estudojava.todolist.exception.BadRequestException;
import com.estudojava.todolist.repository.TodoRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;


    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public List<Todo> create(Todo todo){
        todoRepository.save(todo);

        return list();
    }


    /* Consulta ordenada, verificamos primeiro na lista as tarefas com maior prioridade
    e depois uma ordenação por nome. Sort: recurso de ordenação */

    public List<Todo> list(){

       Sort sort = Sort.by("priority").descending()
                .and(Sort.by("name").ascending());

       return todoRepository.findAll(sort);


    }


    public List<Todo> update(Long id, Todo todo) {
        todoRepository.findById(id).ifPresentOrElse(existingTodo -> {
            todo.setId(id);
            todoRepository.save(todo);
        }, () -> {
            throw new BadRequestException("Todo %d não existe!".formatted(id));
        });

        return list();
    }



    public List<Todo> delete(Long id) {
        todoRepository.findById(id).ifPresentOrElse((existingTodo)
                -> todoRepository.delete(existingTodo), () -> {

                throw new BadRequestException("Todo %d não existe! ".formatted(id));

        });
        return list();
    }
}

