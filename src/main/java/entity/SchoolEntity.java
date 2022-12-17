package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "school", schema = "dblabbgrupp4", catalog = "")
public class SchoolEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "schoolID", nullable = false)
    private int schoolId;
    @Basic
    @Column(name = "schoolName", nullable = true, length = 20)
    private String schoolName;
    @Basic
    @Column(name = "address", nullable = true, length = 50)
    private String address;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

        SchoolEntity that = (SchoolEntity) o;

        if (schoolId != that.schoolId) return false;
        if (schoolName != null ? !schoolName.equals(that.schoolName) : that.schoolName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = schoolId;
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  "ID:" + schoolId +
                ", School:'" + schoolName + '\'' +
                ", Address:'" + address + '\'';
    }
}
