package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course", schema = "dblabbgrupp4", catalog = "")
public class CourseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "courseID", nullable = false)
    private int courseId;
    @Basic
    @Column(name = "courseName", nullable = true, length = 20)
    private String courseName;
    @Basic
    @Column(name = "room", nullable = true, length = 20)
    private String room;
    @Basic
    @Column(name = "courseClassIDFK", nullable = true)
    private Integer courseClassIdfk;
    @Basic
    @Column(name = "courseTeacherIDFK", nullable = true)
    private Integer courseTeacherIdfk;
    @ManyToOne
    @JoinColumn(name = "courseClassIDFK", referencedColumnName = "classID")
    private ClassEntity clazzByCourseClassIdfk;
    @ManyToOne
    @JoinColumn(name = "courseTeacherIDFK", referencedColumnName = "teacherID")
    private TeacherEntity teacherByCourseTeacherIdfk;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getCourseClassIdfk() {
        return courseClassIdfk;
    }

    public void setCourseClassIdfk(Integer courseClassIdfk) {
        this.courseClassIdfk = courseClassIdfk;
    }

    public Integer getCourseTeacherIdfk() {
        return courseTeacherIdfk;
    }

    public void setCourseTeacherIdfk(Integer courseTeacherIdfk) {
        this.courseTeacherIdfk = courseTeacherIdfk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (courseId != that.courseId) return false;
        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null) return false;
        if (room != null ? !room.equals(that.room) : that.room != null) return false;
        if (courseClassIdfk != null ? !courseClassIdfk.equals(that.courseClassIdfk) : that.courseClassIdfk != null)
            return false;
        if (courseTeacherIdfk != null ? !courseTeacherIdfk.equals(that.courseTeacherIdfk) : that.courseTeacherIdfk != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = courseId;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (courseClassIdfk != null ? courseClassIdfk.hashCode() : 0);
        result = 31 * result + (courseTeacherIdfk != null ? courseTeacherIdfk.hashCode() : 0);
        return result;
    }

    public ClassEntity getClazzByCourseClassIdfk() {
        return clazzByCourseClassIdfk;
    }

    public void setClazzByCourseClassIdfk(ClassEntity clazzByCourseClassIdfk) {
        this.clazzByCourseClassIdfk = clazzByCourseClassIdfk;
    }

    public TeacherEntity getTeacherByCourseTeacherIdfk() {
        return teacherByCourseTeacherIdfk;
    }

    public void setTeacherByCourseTeacherIdfk(TeacherEntity teacherByCourseTeacherIdfk) {
        this.teacherByCourseTeacherIdfk = teacherByCourseTeacherIdfk;
    }
}
