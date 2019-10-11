package df.farmponds;

class Class_GrampanchayatListDetails {

    int id;
    String Gramanchayat_id;
    String Gramanchayat_name;
    String districtid;

    public Class_GrampanchayatListDetails(){}

    public Class_GrampanchayatListDetails(String gramanchayat_id, String gramanchayat_name, String districtid) {
        Gramanchayat_id = gramanchayat_id;
        Gramanchayat_name = gramanchayat_name;
        this.districtid = districtid;
    }

    public String getGramanchayat_id() {
        return Gramanchayat_id;
    }

    public void setGramanchayat_id(String gramanchayat_id) {
        Gramanchayat_id = gramanchayat_id;
    }

    public String getGramanchayat_name() {
        return Gramanchayat_name;
    }

    public void setGramanchayat_name(String gramanchayat_name) {
        Gramanchayat_name = gramanchayat_name;
    }

    public String getDistrictid() {
        return districtid;
    }

    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    @Override
    public String toString() {
        return Gramanchayat_name;
    }

}
