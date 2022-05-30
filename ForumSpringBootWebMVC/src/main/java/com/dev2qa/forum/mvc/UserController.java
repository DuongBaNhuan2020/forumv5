package com.dev2qa.forum.mvc;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class UserController {
	@Autowired
	private UserService userService;
//	@RequestMapping(value={"/"})
//	public ModelAndView home() {
//		ModelAndView mav = new ModelAndView("forum_login");
//		System.out.println("co vao day");
//		return mav;
//	}
	@RequestMapping(value={"/new", "/"})
	public String newUserForm(Map<String, Object> model) {
		User user=new User();
		model.put("user", user);
		System.out.println("co vao day /new");
		return "forum_login";
	}
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Validated User user,BindingResult result) {
		if (result.hasErrors()) {
			return "forum_login";
		}
		user.setJoinDate(Calendar.getInstance());
		userService.save(user);
		User user1=new User();
		for(User u:userService.listAll()) {
			if(u.getUserName().equals(user.getUserName())) {
				user1.setId(u.getId());;
			}
		}
		long user_id=user1.getId();
		userService.user_Id_Current(user_id);
		return "redirect:/topicList";
	}
}
