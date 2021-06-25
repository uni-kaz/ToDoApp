package jp.kobespiral.unikaz.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.unikaz.todo.entity.ToDo;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {
    /**
     * 作成者と完了フラグを指定して ToDo を検索する
     * 
     * @param mid  作成者ID
     * @param done 完了フラグ
     * @return 検索結果
     */
    List<ToDo> findByMidAndDone(String mid, boolean done);

    /**
     * 完了フラグの値で ToDo を検索する
     * 
     * @param done 完了フラグ
     * @return 検索結果
     */
    List<ToDo> findByDone(boolean done);
}
