package com.utkarshgoswami.springboot.todoapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {
	
//	private TodoService todoService;
	
	public TodoControllerJpa(TodoRepository todoRepository) {
		super();
//		this.todoService = todoService;
		this.todoRepository = todoRepository;
	}
	
	private TodoRepository todoRepository;



	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedinUsername(model);
		
		List<ToDo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos",todos);
		return "listTodos";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String username = getLoggedinUsername(model);
		ToDo todo = new ToDo(0, username, "", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodo(@ModelAttribute("todo") ToDo todoAttr, ModelMap model, @Valid ToDo todo, BindingResult result) {

		if(result.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.todo", result);
			return "todo";
		}
		String username = getLoggedinUsername(model);
		todo.setUsername(username);
		todoRepository.save(todo);
		
//		todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), false);
		return "redirect:list-todos";	
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoRepository.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		ToDo todo = todoRepository.findById(id).get();
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateNewTodo(@ModelAttribute("todo") ToDo todoAttr, ModelMap model, @Valid ToDo todo, BindingResult result) {

		if(result.hasErrors()) {
			return "todo";
		}
		String username = getLoggedinUsername(model);
		todo.setUsername(username);
		todoRepository.save(todo);
//		todoService.updateTodo(todo);
		return "redirect:list-todos";	
	}
	
	private String getLoggedinUsername(ModelMap model) {
		Authentication authentication=  SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}
