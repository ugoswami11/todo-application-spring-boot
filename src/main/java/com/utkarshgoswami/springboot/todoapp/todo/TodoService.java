package com.utkarshgoswami.springboot.todoapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {
	
	private static int todoCount =0;
	
	private static List<ToDo> todos = new ArrayList<>();
	static {
		todos.add(new ToDo(++todoCount, "user1", "Learn AWS", LocalDate.now().plusYears(1), false));
		todos.add(new ToDo(++todoCount, "user2", "Learn DevOps", LocalDate.now().plusYears(2), false));
		todos.add(new ToDo(++todoCount, "user3", "Learn Full Stack", LocalDate.now().plusYears(1), false));
	}
	
	public List<ToDo> findByUsername(String username){
		Predicate<? super ToDo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}
	
	public void addTodo(String username, String description, LocalDate targetDate, boolean done) {
		ToDo todo = new ToDo(++todoCount, username, description, targetDate, done);
		todos.add(todo);
	}
	
	public void deleteById(int id) {
		
		Predicate<? super ToDo> predicate = todo -> todo.getId() ==id;
		
		todos.removeIf(predicate);
	}

	public ToDo findById(int id) {
		Predicate<? super ToDo> predicate = todo -> todo.getId() ==id;
		ToDo todo =todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodo(@Valid ToDo todo) {
		// TODO Auto-generated method stub
		deleteById(todo.getId());
		todos.add(todo);
		
	}

}
