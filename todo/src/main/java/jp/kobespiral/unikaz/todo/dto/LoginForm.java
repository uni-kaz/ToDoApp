package jp.kobespiral.unikaz.todo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {
    @NotBlank
    String mid; // メンバーID
    @NotBlank
    String password; // パスワード
}
