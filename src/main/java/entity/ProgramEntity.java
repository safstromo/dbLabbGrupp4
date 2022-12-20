package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "program", schema = "dblabbgrupp4", catalog = "")
public class ProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "programID", nullable = false)
    private int programId;
    @Basic
    @Column(name = "programName", nullable = true, length = 20)
    private String programName;
    @Basic
    @Column(name = "duration", nullable = true)
    private Integer duration;
    @Basic
    @Column(name = "programSchoolIDFK", nullable = true)
    private Integer programSchoolIdfk;
    @ManyToOne
    @JoinColumn(name = "programSchoolIDFK", referencedColumnName = "schoolID", insertable = false, updatable = false)
    private SchoolEntity schoolByProgramSchoolIdfk;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getProgramSchoolIdfk() {
        return programSchoolIdfk;
    }

    public void setProgramSchoolIdfk(Integer programSchoolIdfk) {
        this.programSchoolIdfk = programSchoolIdfk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramEntity that = (ProgramEntity) o;

        if (programId != that.programId) return false;
        if (programName != null ? !programName.equals(that.programName) : that.programName != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (programSchoolIdfk != null ? !programSchoolIdfk.equals(that.programSchoolIdfk) : that.programSchoolIdfk != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = programId;
        result = 31 * result + (programName != null ? programName.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (programSchoolIdfk != null ? programSchoolIdfk.hashCode() : 0);
        return result;
    }

    public SchoolEntity getSchoolByProgramSchoolIdfk() {
        return schoolByProgramSchoolIdfk;
    }

    public void setSchoolByProgramSchoolIdfk(SchoolEntity schoolByProgramSchoolIdfk) {
        this.schoolByProgramSchoolIdfk = schoolByProgramSchoolIdfk;
    }

    @Override
    public String toString() {
        return
                "ID: " + programId + " |"+
                        " Program Name: " + programName +" |"+
                        " Duration: " + duration + " years";
    }
}
