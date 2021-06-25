package jp.kobespiral.unikaz.todo.dto;

import jp.kobespiral.unikaz.todo.entity.ToDo;
import lombok.Data;

@Data
public class ToDoForm {
    /**
     * 題目
     */
    String title;

    public ToDo toEntity() {
        ToDo t = new ToDo(null, title, null, false, null, null);
        return t;
    }
}
