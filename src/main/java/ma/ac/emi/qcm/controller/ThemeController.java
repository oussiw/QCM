package ma.ac.emi.qcm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ma.ac.emi.qcm.entities.Theme;
import ma.ac.emi.qcm.service.ThemeService;

@Controller
@RequestMapping("/themes")
public class ThemeController {
	@Autowired
	ThemeService themeService;
	
	@RequestMapping("/")
	public String theme(Model model,
			@RequestParam(defaultValue="0") int p,
			@RequestParam(defaultValue="3") int s,
			@RequestParam(defaultValue="") String mc){
		Page <Theme> pageTheme=themeService.getPageTheme(p, s, mc);
		model.addAttribute("pageTheme",pageTheme.getContent());
		model.addAttribute("nbPages",new int[pageTheme.getTotalPages()]);
		model.addAttribute("p",p);
		model.addAttribute("s",s);
		model.addAttribute("mc",mc);
		
		model.addAttribute("newTheme",new Theme());
		model.addAttribute("listMatiere",themeService.getListMatiere());
		return "themes/theme";
	}
	
	@RequestMapping("/delete")
	public String deleteTheme(@RequestParam Long id, int p, int s, String mc) {
		themeService.deleteTheme(id);
		return "redirect:/themes/?"+"p="+p+"&s="+s+"&mc="+mc;
	}
	
	@RequestMapping(value="/confirmation", method=RequestMethod.POST)
	public String addedDescriptionTheme(Model model,Theme theme, @RequestParam("m_id")Long matiere_id) {
		Theme newTheme=themeService.saveTheme(theme,matiere_id);
		model.addAttribute("newTheme",newTheme);
		return "themes/themeConfirmation";
	}
	
	@RequestMapping("/update")
	public String updateTheme(Model model,@RequestParam Long id) {
		model.addAttribute("theme",themeService.getThemeById(id));
		model.addAttribute("listMatiere",themeService.getListMatiere());
		return "themes/themeUpdate";
	}
	
	
	
}
