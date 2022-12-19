package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "class", schema = "dblabbgrupp4", catalog = "")
public class ProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "classID", nullable = false)
    private int classId;
    @Basic
    @Column(name = "className", nullable = true, length = 20)
    private String className;
    @Basic
    @Column(name = "duration", nullable = true)
    private Integer duration;
    @Basic
    @Column(name = "classSchoolIDFK", nullable = true)
    private Integer classSchoolIdfk;
    @ManyToOne
    @JoinColumn(name = "classSchoolIDFK", referencedColumnName = "schoolID", insertable = false, updatable = false)
    private SchoolEntity schoolByClassSchoolIdfk;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getClassSchoolIdfk() {
        return classSchoolIdfk;
    }

    public void setClassSchoolIdfk(Integer classSchoolIdfk) {
        this.classSchoolIdfk = classSchoolIdfk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramEntity that = (ProgramEntity) o;

        if (classId != that.classId) return false;
        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (classSchoolIdfk != null ? !classSchoolIdfk.equals(that.classSchoolIdfk) : that.classSchoolIdfk != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classId;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (classSchoolIdfk != null ? classSchoolIdfk.hashCode() : 0);
        return result;
    }

    public SchoolEntity getSchoolByClassSchoolIdfk() {
        return schoolByClassSchoolIdfk;
    }

    public void setSchoolByClassSchoolIdfk(SchoolEntity schoolByClassSchoolIdfk) {
        this.schoolByClassSchoolIdfk = schoolByClassSchoolIdfk;
    }

    @Override
    public String toString() {
        return "ClassEntity{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", duration=" + duration +
                ", classSchoolIdfk=" + classSchoolIdfk +
                ", schoolByClassSchoolIdfk=" + schoolByClassSchoolIdfk +
                '}';
    }
}
