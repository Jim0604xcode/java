package io.tecky.forms;
import io.tecky.models.Memo;

public class MemoForm {

    private String content;
    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "MemoForm{" +
                "content='" + content + '\'' +
                '}';
    }

    public Memo createMemo(){
        var newMemo = new Memo();
        newMemo.setContent(this.content);
        return newMemo;
    }
}