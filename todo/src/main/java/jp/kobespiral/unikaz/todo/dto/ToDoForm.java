package jp.kobespiral.unikaz.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.kobespiral.unikaz.todo.entity.ToDo;
import lombok.Data;

@Data
public class ToDoForm {
    /**
     * 題目
     */
    @NotBlank
    @Size(max = 64)
    String title;

    public ToDo toEntity() {
        ToDo t = new ToDo(null, title, null, false, null, null);
        return t;
    }
}
