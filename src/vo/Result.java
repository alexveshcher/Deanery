package vo;

/**
 * Created by alex on 1/27/17.
 */
public class Result {
    private int group_id;
    private int student_id;
    private String mark;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Result{" +
                "group_id=" + group_id +
                ", student_id=" + student_id +
                ", mark=" + mark +
                '}';
    }
}
