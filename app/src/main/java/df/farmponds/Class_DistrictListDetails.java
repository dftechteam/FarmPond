package df.farmponds;

class Class_DistrictListDetails {

    int id;
    String district_id;
    String district_name;
    String year_id;
    String state_id;

    public Class_DistrictListDetails(){}

    public Class_DistrictListDetails(String district_id, String district_name) {
        this.district_id = district_id;
        this.district_name = district_name;
    }

    public Class_DistrictListDetails(String district_id, String district_name, String state_id) {
        this.district_id = district_id;
        this.district_name = district_name;
        this.state_id = state_id;
    }

    public String getYear_id() {
        return year_id;
    }

    public void setYear_id(String year_id) {
        this.year_id = year_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    @Override
    public String toString() {
        return district_name;
    }

}
