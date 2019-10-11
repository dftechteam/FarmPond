package df.farmponds;

class Class_TalukListDetails {

    int id;
    String taluk_id;
    String taluk_name;
    String district_id;

    public Class_TalukListDetails(){}

    public Class_TalukListDetails(String taluk_id, String taluk_name, String district_id) {
        this.taluk_id = taluk_id;
        this.taluk_name = taluk_name;
        this.district_id = district_id;
    }

    public String getTaluk_id() {
        return taluk_id;
    }

    public void setTaluk_id(String taluk_id) {
        this.taluk_id = taluk_id;
    }

    public String getTaluk_name() {
        return taluk_name;
    }

    public void setTaluk_name(String taluk_name) {
        this.taluk_name = taluk_name;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    @Override
    public String toString() {
        return taluk_name;
    }

}
