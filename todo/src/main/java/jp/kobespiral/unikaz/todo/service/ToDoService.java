package jp.kobespiral.unikaz.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.unikaz.todo.dto.ToDoForm;
import jp.kobespiral.unikaz.todo.entity.ToDo;
import jp.kobespiral.unikaz.todo.exception.ToDoAppException;
import jp.kobespiral.unikaz.todo.repository.MemberRepository;
import jp.kobespiral.unikaz.todo.repository.ToDoRepository;

@Service
public class ToDoService {
    @Autowired
    MemberRepository mRepo;
    @Autowired
    ToDoRepository tRepo;

    /**
     * ToDo を作成する
     * 
     * @param mid  作成者ID
     * @param form フォームの内容
     * @return 作成した ToDo
     */
    public ToDo createToDo(String mid, ToDoForm form) {
        // ユーザが存在するかをチェック
        checkMid(mid);
        ToDo todo = form.toEntity();
        // ToDo に情報をセット
        todo.setMid(mid);
        todo.setDone(false);
        todo.setCreatedAt(new Date());
        return tRepo.save(todo);
    }

    /**
     * 通し番号を指定して ToDo を取得する
     * 
     * @param seq ToDo の通し番号
     * @return 指定した通し番号を持つ ToDo
     */
    public ToDo getToDo(Long seq) {
        ToDo t = tRepo.findById(seq).orElseThrow(
                () -> new ToDoAppException(ToDoAppException.NO_SUCH_TODO_EXISTS, seq + ": No such todo exists"));
        return t;
    }

    /**
     * 指定したユーザの ToDo リストを取得する
     * 
     * @param mid 検索ユーザID
     * @return 指定したユーザの ToDo リスト
     */
    public List<ToDo> getToDoList(String mid) {
        // ユーザが存在するかをチェック
        checkMid(mid);
        return tRepo.findByMidAndDone(mid, false);
    }

    /**
     * 指定したユーザの Done リストを取得する
     * 
     * @param mid 検索ユーザID
     * @return 指定したユーザの Done リスト
     */
    public List<ToDo> getDoneList(String mid) {
        // ユーザが存在するかをチェック
        checkMid(mid);
        return tRepo.findByMidAndDone(mid, true);
    }

    /**
     * 全員の ToDo リストを取得する
     * 
     * @return 全員の ToDo リスト
     */
    public List<ToDo> getToDoList() {
        return tRepo.findByDone(false);
    }

    /**
     * 全員の Done リストを取得する
     * 
     * @return 全員の Done リスト
     */
    public List<ToDo> getDoneList() {
        return tRepo.findByDone(true);
    }

    /**
     * ToDo を Done に変更する
     * 
     * @param mid 自分のメンバーID
     * @param seq ToDo の通し番号
     * @return Done になった ToDo
     */
    public ToDo done(String mid, Long seq) {
        ToDo todo = getToDo(seq);
        if (!mid.equals(todo.getMid())) {
            // ToDoの作成者が自分でないとき
            throw new ToDoAppException(ToDoAppException.INVALID_TODO_OPERATION, "You cannot operate others' todos.");
        }
        todo.setDone(true);
        todo.setDoneAt(new Date());
        return tRepo.save(todo);
    }

    /**
     * ToDo を更新する
     * 
     * @param mid  自分のメンバーID
     * @param seq  ToDo の通し番号
     * @param form フォームの内容
     * @return 更新された ToDo
     */
    public ToDo updateToDo(String mid, Long seq, ToDoForm form) {
        ToDo todo = getToDo(seq);
        if (!mid.equals(todo.getMid())) {
            // ToDoの作成者が自分でないとき
            throw new ToDoAppException(ToDoAppException.INVALID_TODO_OPERATION, "You cannot operate others' todos.");
        }
        // ToDo の内容を更新
        todo.setTitle(form.getTitle());
        return tRepo.save(todo);
    }

    /**
     * ToDo を削除する
     * 
     * @param mid 自分のメンバーID
     * @param seq ToDo の通し番号
     */
    public void deleteToDo(String mid, Long seq) {
        ToDo todo = getToDo(seq);
        if (!mid.equals(todo.getMid())) {
            // ToDoの作成者が自分でないとき
            throw new ToDoAppException(ToDoAppException.INVALID_TODO_OPERATION, "You cannot operate others' todos.");
        }
        tRepo.delete(todo);
    }

    /**
     * ユーザが存在するかをチェックする
     * 
     * @param mid 検索ユーザID
     */
    private void checkMid(String mid) {
        // ユーザが存在するかをチェック
        mRepo.findById(mid).orElseThrow(
                () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, mid + ": No such member exists"));
    }
}
