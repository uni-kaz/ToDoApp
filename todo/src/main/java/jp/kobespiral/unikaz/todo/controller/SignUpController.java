package jp.kobespiral.unikaz.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobespiral.unikaz.todo.dto.MemberForm;
import jp.kobespiral.unikaz.todo.entity.Member;
import jp.kobespiral.unikaz.todo.service.MemberService;

@Controller
@RequestMapping("/sign_up")
public class SignUpController {
    @Autowired
    MemberService mService;

    /**
     * 一般ユーザの登録ページ HTTP-GET /sign_up
     * 
     * @param form
     * @param model
     * @return
     */
    @GetMapping("")
    String showSignUpForm(@ModelAttribute MemberForm form, Model model) {
        model.addAttribute("MemberForm", form);
        return "signup";
    }

    /**
     * ユーザ登録確認ページを表示 HTTP-POST /sign_up/check
     * 
     * @param form
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/check")
    String checMemberForm(@Validated @ModelAttribute("MemberForm") MemberForm form, BindingResult bindingResult,
            Model model) {
        // 入力チェックに引っかかった場合、ユーザー登録画面に戻る
        if (bindingResult.hasErrors()) {
            // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
            return showSignUpForm(form, model);
        }
        model.addAttribute("MemberForm", form);
        return "signup_check";
    }

    /**
     * ユーザ登録処理 -> 完了ページ HTTP-POST /sign_up/register
     * 
     * @param form
     * @param model
     * @return
     */
    @PostMapping("")
    String createMember(@ModelAttribute("MemberForm") MemberForm form, Model model) {
        Member m = mService.createMember(form);
        model.addAttribute("MemberForm", m);
        return "signup_complete";
    }
}
