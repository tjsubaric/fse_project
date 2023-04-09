// package com.uiowa.fse_project.controller;

// public class PatientController {

//     @Autowired
// 	private UserRepository userRepo;

// 	@ModelAttribute
// 	private void userDetails(Model m, Principal p) {
// 		String email = p.getName();
// 		UserDtls user = userRepo.findByEmail(email);

// 		m.addAttribute("user", user);

// 	}

// 	@GetMapping("/")
// 	public String home() {
// 		return "user/home";
// 	}
// }
