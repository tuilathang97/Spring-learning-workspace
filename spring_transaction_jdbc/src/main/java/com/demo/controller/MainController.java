package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.dao.BankAccountDAO;
import com.demo.exception.BankTransactionException;
import com.demo.from.SendMoneyFrom;
import com.demo.model.BankAccountInfo;

@Controller
public class MainController {

	@Autowired
	private BankAccountDAO bankAccountDAO;
	
	@GetMapping("/")
	public String showBankAccount(Model model) {
		
		List<BankAccountInfo> list = bankAccountDAO.getBankAccounts();
		
		model.addAttribute("accountInfos", list);
		
		return "accountPage";
	}
	
	@GetMapping("/sendMoney")
	public String processSendMoney(Model model) {
		
		SendMoneyFrom smf = new SendMoneyFrom(1L, 2L, 700D);
		
		model.addAttribute(smf);
		
		return "sendMoneyPage";
	}
	
	@PostMapping("/sendMoney")
	public String processSendMoney(Model model, SendMoneyFrom smf) {
		
		System.out.println("Send money: " + smf.getAmount());
		try {
			bankAccountDAO.sendMoney(smf.getFromAccountId(), smf.getToAccountId(), smf.getAmount());
		} catch (BankTransactionException e) {
			// TODO Auto-generated catch block
			model.addAttribute("errMess", "Error: " + e.getMessage());
			return "/sendMoneyPage";
		}
		return "redirect:/";
	}
}
