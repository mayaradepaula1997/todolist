package com.estudojava.todolist;

import com.estudojava.todolist.entity.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


//import static com.estudojava.todolist.TestConstants.TODOS;
import static com.estudojava.todolist.TestConstants.TODO;
import static com.estudojava.todolist.TestConstants.TODOS;


//Banco H2: Banco de teste
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)    //Para rodar em uma porta aleatoria


@Sql("/remove.sql")

class  TodolistApplicationTests {

	@Autowired
	private WebTestClient webTestClient;                                        //estudar o que é feito exatamente

	@Test
	void testCreateTodoSuccess() {

		Todo todo = new Todo("todo 1", "descrição todos 1", false, 1);

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo)
				.exchange()                                        //faz a requisição
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].name").isEqualTo(todo.getName())
				.jsonPath("$[0].description").isEqualTo(todo.getDescription())
				.jsonPath("$[0].completed").isEqualTo(todo.getCompleted())
				.jsonPath("$[0].priority").isEqualTo(todo.getPriority());


	}


	@Test
	void testCreateTodoFailure() {

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(
						new Todo("", "", false, 2)
				)
				.exchange()
				.expectStatus().isBadRequest();

	}

	@Sql("/import.sql")
	@Test
	public void testUpdateTodoSuccess() {

		Todo todo = new Todo(TODO.getId(), TODO.getName() + "up", TODO.getDescription(), TODO.getCompleted(), TODO.getPriority() + 1);

		webTestClient
				.put()
				.uri("/todos/{id}", TODO.getId())
				.bodyValue(todo)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(5)
				.jsonPath("$[0].name").isEqualTo(todo.getName())
				.jsonPath("$[0].description").isEqualTo(todo.getDescription())
				.jsonPath("$[0].completed").isEqualTo(todo.getCompleted())
				.jsonPath("$[0].priority").isEqualTo(todo.getPriority());


	}

	@Test
	public void testUpdateTodoFailure() {

		var unexinstingId = 1L;

		webTestClient
				.put()
				.uri("/todos/" + unexinstingId)
				.bodyValue(new Todo(unexinstingId, "", "", false, 0))
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Sql("/import.sql")
	@Test
	public void testDeleteTodoSuccess() {
		webTestClient
				.delete()
				.uri("/todos/" + TODOS.get(0).getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(4)
				.jsonPath("$[0].name").isEqualTo(TODOS.get(1).getName())
				.jsonPath("$[0].description").isEqualTo(TODOS.get(1).getDescription())
				.jsonPath("$[0].completed").isEqualTo(TODOS.get(1).getCompleted())
				.jsonPath("$[0].priority").isEqualTo(TODOS.get(1).getPriority());
	}


	@Test
	public void testDeleteTodoFailure() {
		var unexinstingId = 1L;

		webTestClient
				.delete()
				.uri("/todos/" + unexinstingId)
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Sql("/import.sql")
	@Test
	public void testListTodos() throws Exception {
		webTestClient
				.get()
				.uri("/todos")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(5)
				.jsonPath("$[0].id").isEqualTo(TODOS.get(0).getId())
				.jsonPath("$[0].name").isEqualTo(TODOS.get(0).getName())
				.jsonPath("$[0].description").isEqualTo(TODOS.get(0).getDescription())
				.jsonPath("$[0].completed").isEqualTo(TODOS.get(0).getCompleted())
				.jsonPath("$[0].priority").isEqualTo(TODOS.get(0).getPriority());
	}

}

