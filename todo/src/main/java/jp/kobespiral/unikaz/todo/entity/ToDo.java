package jp.kobespiral.unikaz.todo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ToDo {
    /**
     * 通し番号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seq;
    /**
     * 題目
     */
    String title;
    /**
     * 作成者
     */
    String mid;
    /**
     * 完了フラグ
     */
    boolean done;
    /**
     * 作成日時
     */
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    /**
     * 完了日時
     */
    @Temporal(TemporalType.TIMESTAMP)
    Date doneAt;
}
