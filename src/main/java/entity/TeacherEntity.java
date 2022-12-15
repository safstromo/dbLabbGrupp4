package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher", schema = "dblabbgrupp4", catalog = "")
public class TeacherEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "teacherID")
    private int teacherId;
    @Basic
    @Column(name = "teacherName", nullable = true, length = 20)
    private String teacherName;
    @Basic
    @Column(name = "address", nullable = true, length = 50)
    private String address;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherEntity that = (TeacherEntity) o;

        if (teacherId != that.teacherId) return false;
        if (teacherName != null ? !teacherName.equals(that.teacherName) : that.teacherName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return
                "ID: " + teacherId + " |"+
                " Teachers Name: " + teacherName +" |"+
                " Adress: " + address;
    }

    @Override
    public int hashCode() {
        int result = teacherId;
        result = 31 * result + (teacherName != null ? teacherName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;

    }
}
