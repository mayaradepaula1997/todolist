package com.estudojava.todolist.repository;

import com.estudojava.todolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

//Responsavel por interagir com BD

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
