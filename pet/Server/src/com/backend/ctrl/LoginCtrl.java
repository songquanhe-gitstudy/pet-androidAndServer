package com.backend.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.backend.defines.CommonDefines;
import com.backend.domain.AdminAccount;
import com.backend.mapper.AdminAccountMapper;

@Controller
@RequestMapping("/Login")
public class LoginCtrl {
	@Autowired
	private AdminAccountMapper AdminAccountService;
	
	@RequestMapping(value = "/initLogin")
	public ModelAndView initLogin(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		ModelAndView mv = new ModelAndView("backend/backLogin");
		return mv;
	}
	
	@RequestMapping(value = "/mainicon")
	public ModelAndView mainicon(){
		ModelAndView mv = new ModelAndView("backend/index");
		return mv;
	}
	@RequestMapping(value = "/doLogin")
	public ModelAndView doLogin(AdminAccount entity,  HttpServletRequest request ){
		
		AdminAccount account = AdminAccountService.selectByPrimaryKey(entity.getAccountName());
		ModelAndView mv = null;
			HttpSession session = request.getSession();
			session.setAttribute(CommonDefines.sessionServerUserName, account.getAccountName());
//			session.setAttribute(CommonDefines.sessionServerUserCategory, (short)3);
			mv = new ModelAndView("backend/backHome");
			mv.addObject("object", account);	
			return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/testlogin")
	public String homeDoLogin(AdminAccount entity, HttpServletRequest request){
		HttpSession session = request.getSession();
			AdminAccount account = AdminAccountService.selectByPrimaryKey(entity.getAccountName());
			if(account != null && account.getAccountPassword().equals(entity.getAccountPassword())){
				session.setAttribute(CommonDefines.sessionServerUserName, account.getAccountName());
//				session.setAttribute(CommonDefines.sessionServerUserCategory, account.getAccountCategory());
				return "success";  //密码验证成功
		}	
		return "failed";
	}
	@RequestMapping(value = "/backLogin")
	public ModelAndView backLogin(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute(CommonDefines.sessionServerUserName);
//		session.removeAttribute(CommonDefines.sessionServerUserCategory);
		
		ModelAndView mv = new ModelAndView("backend/backLogin");
		return mv;
	}
	
	@RequestMapping(value = "/testpage")
	public ModelAndView testPage(){		
		ModelAndView mv = new ModelAndView("backend/test");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/testLogin")
	public String doTest(){
		return "testLogin";
	}
}
