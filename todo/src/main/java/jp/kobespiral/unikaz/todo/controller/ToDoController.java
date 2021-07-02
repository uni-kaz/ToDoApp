package jp.kobespiral.unikaz.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobespiral.unikaz.todo.dto.LoginForm;
import jp.kobespiral.unikaz.todo.dto.ToDoForm;
import jp.kobespiral.unikaz.todo.entity.Member;
import jp.kobespiral.unikaz.todo.entity.ToDo;
import jp.kobespiral.unikaz.todo.service.MemberService;
import jp.kobespiral.unikaz.todo.service.ToDoService;

@Controller
public class ToDoController {
    @Autowired
    ToDoService tService;
    @Autowired
    MemberService mService;

    /**
     * ログイン画面を表示する
     * 
     * @param model
     * @return
     */
    @GetMapping("")
    String showLoginForm(@ModelAttribute("loginForm") LoginForm form, Model model) {
        model.addAttribute("loginForm", form);
        return "index";
    }

    @PostMapping("/login")
    String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, Model model) {
        // 入力チェックに引っかかった場合、ユーザー登録画面に戻る
        if (bindingResult.hasErrors()) {
            // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
            return showLoginForm(form, model);
        }
        // ユーザが存在するか確認
        mService.getMember(form.getMid());
        return "redirect:/" + form.getMid() + "/todo";
    }

    @GetMapping("/{mid}/todo")
    String showTopPage(@PathVariable String mid, @ModelAttribute("toDoForm") ToDoForm form, Model model) {
        // ユーザ情報をモデルに登録
        Member member = mService.getMember(mid);
        model.addAttribute("member", member);
        // ToDo と Done をモデルに登録
        List<ToDo> todos = tService.getToDoList(mid);
        model.addAttribute("todos", todos);
        List<ToDo> dones = tService.getDoneList(mid);
        model.addAttribute("dones", dones);
        // ToDo フォームをモデルに登録
        model.addAttribute("toDoForm", form);
        return "list";
    }

    @PostMapping("/{mid}/todo")
    String createToDo(@PathVariable String mid, @Validated @ModelAttribute("toDoForm") ToDoForm form,
            BindingResult result, Model model) {
        // 入力チェックに引っかかった場合、もとの画面に戻る
        if (result.hasErrors()) {
            // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
            return showTopPage(mid, form, model);
        }
        // ToDo を作成
        tService.createToDo(mid, form);
        return "redirect:/" + mid + "/todo";
    }

    @PostMapping("/{mid}/todo/{seq}/done")
    String finishToDo(@PathVariable String mid, @PathVariable Long seq, Model model) {
        // ToDo を Done にする
        tService.chengeDone(seq);
        return "redirect:/" + mid + "/todo";
    }

    @GetMapping("/{mid}/todo/all")
    String showAllToDo(@PathVariable String mid, Model model) {
        // ユーザ情報をモデルに登録
        Member member = mService.getMember(mid);
        model.addAttribute("member", member);
        // 全員の ToDo と Done のリストをモデルに登録
        List<ToDo> todos = tService.getToDoList();
        model.addAttribute("todos", todos);
        List<ToDo> dones = tService.getDoneList();
        model.addAttribute("dones", dones);
        return "alllist";
    }
}
