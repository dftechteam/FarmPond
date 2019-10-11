package df.farmponds;

class Class_YearListDetails {
    int id;
    String yearID;
    String year;

    public Class_YearListDetails(){}

    public Class_YearListDetails(String yearID, String year) {
        this.yearID = yearID;
        this.year = year;
    }

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    @Override
    public String toString() {
        return year;
    }

}
