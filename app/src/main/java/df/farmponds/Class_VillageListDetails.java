package df.farmponds;

class Class_VillageListDetails {

    int id;
    String village_id;
    String village_name;
    String taluk_id;

    public Class_VillageListDetails(){}

    public Class_VillageListDetails(String village_id, String village_name, String taluk_id) {
        this.village_id = village_id;
        this.village_name = village_name;
        this.taluk_id = taluk_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getTaluk_id() {
        return taluk_id;
    }

    public void setTaluk_id(String taluk_id) {
        this.taluk_id = taluk_id;
    }

    @Override
    public String toString() {
        return village_name;
    }

}
