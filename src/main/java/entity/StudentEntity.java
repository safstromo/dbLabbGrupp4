package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student", schema = "dblabbgrupp4", catalog = "")
public class StudentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "studentID", nullable = false)
    private int studentId;
    @Basic
    @Column(name = "studentName", nullable = true, length = 50)
    private String studentName;
    @Basic
    @Column(name = "age", nullable = true)
    private Integer age;
    @Basic
    @Column(name = "address", nullable = true, length = 50)
    private String address;
    @Basic
    @Column(name = "studentClassIDFK", nullable = true)
    private Integer studentClassIdfk;
    @ManyToOne
    @JoinColumn(name = "studentClassIDFK", referencedColumnName = "classID" ,insertable =false, updatable = false)
    private ProgramEntity clazzByStudentClassIdfk;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStudentClassIdfk() {
        return studentClassIdfk;
    }

    public void setStudentClassIdfk(Integer studentClassIdfk) {
        this.studentClassIdfk = studentClassIdfk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (studentId != that.studentId) return false;
        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (studentClassIdfk != null ? !studentClassIdfk.equals(that.studentClassIdfk) : that.studentClassIdfk != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (studentClassIdfk != null ? studentClassIdfk.hashCode() : 0);
        return result;
    }

    public ProgramEntity getClazzByStudentClassIdfk() {
        return clazzByStudentClassIdfk;
    }

    public void setClazzByStudentClassIdfk(ProgramEntity clazzByStudentClassIdfk) {
        this.clazzByStudentClassIdfk = clazzByStudentClassIdfk;
    }
}
