package df.farmponds;

class Class_StateListDetails {

    int id;
    String state_id;
    String state_name;
    String year_id;


    public Class_StateListDetails(){}

    public Class_StateListDetails(String state_id, String state_name) {
        this.state_id = state_id;
        this.state_name = state_name;
    }

    public String getYear_id() {
        return year_id;
    }

    public void setYear_id(String year_id) {
        this.year_id = year_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    @Override
    public String toString() {
        return state_name;
    }

}
