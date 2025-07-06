package com.estudojava.todolist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "todos")
public class Todo {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotBlank
   //@NotNull
   private String name;

   @NotBlank
   //@NotNull
   private String description;

   private Boolean completed;

   private Integer priority;

   public Todo(){

   }


   public Todo(String name, String description, Boolean completed, Integer priority) {
      this.name = name;
      this.description = description;
      this.completed = completed;
      this.priority = priority;
   }


   public Todo(Long id, @NotBlank String name, @NotBlank String description, boolean completed, int priority) {
      this.id = id;
      this.name= name;
      this.description = description;
      this.completed= completed;
      this.priority = priority;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Boolean getCompleted() {
      return completed;
   }

   public void setCompleted(Boolean completed) {
      this.completed = completed;
   }

   public Integer getPriority() {
      return priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }
}
