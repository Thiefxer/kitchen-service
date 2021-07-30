package com.example.demo.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@Transactional
@SessionAttributes("kitchen")
public class MenuItemController {
	
	@Autowired
	MenuItemServiceImpl mService;
	
	@GetMapping("/edit_menu")
	public String editMenu(@ModelAttribute("kitchen") Kitchen kitchen, Model model) {
		//MenuItem menu = new MenuItem();
		//model.addAttribute("menu", menu);
		List<MenuItem> list = mService.findMenu(kitchen);
		model.addAttribute("items", list);
		//System.out.println("k_id in edit_menu: " + kitchen.getId());
		//System.out.println("Name: " + kitchen.getName());
		//System.out.println("Email: " + kitchen.getEmail());
		//System.out.println("Password: " + kitchen.getPassword());
		return "kitchen_register_form_menu";
	}
	
	@GetMapping("/add_menu_item")
	public String addItem(@ModelAttribute("kitchen") Kitchen kitchen, Model model) {
		MenuItem menu = new MenuItem();
		menu.setKitchen(kitchen);
		//System.out.println("before model ID: " + menu.getId());
		model.addAttribute("menu", menu);
		//System.out.println("after model ID: " + menu.getId());
		//System.out.println("Name: " + kitchen.getName());
		//System.out.println("Email: " + kitchen.getEmail());
		//System.out.println("Password: " + kitchen.getPassword());
		return "kitchen_register_form_menu_add_item";
	}
	
	@PostMapping("/add_menu_item")
	public String saveItem(@ModelAttribute("kitchen") Kitchen kitchen, @Valid @ModelAttribute("menu") MenuItem menu,
			BindingResult bindingResult) {
		//System.out.println("ID: " + kitchen.getId());
		//System.out.println("Name: " + kitchen.getName());
		//System.out.println("Email: " + kitchen.getEmail());
		//System.out.println("Password: " + kitchen.getPassword());
		//System.out.println("error in savemenu");
		if (bindingResult.hasErrors())
			return "kitchen_register_form_menu_add_item";
		else {
			//System.out.println("error in savemenu save");
			//mService.saveMenuItem(kitchen.getId(), menu);
			//List<MenuItem> temp = new ArrayList<MenuItem>();
			//temp = kitchen.getMenu();
			//temp.add(menu);
			//kitchen.setMenu(temp);
			//System.out.println("k_id before all: " + kitchen.getId());
			if (kitchen.getMenu() != null)
				kitchen.getMenu().clear();
			//System.out.println("k_id after clear: " + kitchen.getId());
			kitchen.addMenuItem(menu);
			//System.out.println("k_id after object method: " + kitchen.getId());
			mService.saveMenuItem(kitchen);
			//System.out.println("k_id after service: " + kitchen.getId());
			//System.out.println("ID: " + menu.getId());
			//System.out.println("Item Name: " + menu.getItemName());
			//System.out.println("Veg: " + menu.isVeg());
			//System.out.println("Price: " + menu.getPrice());
			//kitchen.getMenu().add(menu);
			//System.out.println("error in savemenu redirect");
			return "redirect:/edit_menu";
		}
		//model.addAttribute("menu", menu);
		//return "kitchen_register_form_menu_add_item";
	}
	
	@RequestMapping("/list_items")
	public String listMenuItems(@ModelAttribute("kitchen") Kitchen k, Model model) {
		List<MenuItem> list = mService.findMenu(k);
		model.addAttribute("items", list);
		return "kitchen_registration_form_2";
	}
	
	@RequestMapping("/edit_item/{id}")
	public ModelAndView edit(Model model, @PathVariable(name="id") Long id) {
		Kitchen k = (Kitchen) model.getAttribute("kitchen");
		ModelAndView mav = new ModelAndView("kitchen_register_form_menu_edit_item");
		MenuItem m = mService.findMenuItem(k, id);
		System.out.println("item being edit: " + m.toString());
		mav.addObject("menu", m);
		return mav;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("menu") MenuItem m, BindingResult br) {
		Kitchen k = (Kitchen) model.getAttribute("kitchen");
		if (br.hasErrors())
			return "kitchen_register_form_menu_edit_item";
		else {
			mService.saveMenuItem(k.getId(), m);
			return "redirect:/edit_menu";
		}
	}
	
	@RequestMapping("/delete_item/{id}")
	public String delete(Model model, @PathVariable(name="id") Long id) {
		Kitchen k = (Kitchen) model.getAttribute("kitchen");
		System.out.println("k_id is: " + k.getId());
		System.out.println("m_id is: " + id);
		System.out.println("menu items: " + k.getMenu().toString());
		mService.deleteMenuItem(k.getId(), id);
		return "redirect:/edit_menu";
	}
}
