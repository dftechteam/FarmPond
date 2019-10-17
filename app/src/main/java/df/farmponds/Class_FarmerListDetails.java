package df.farmponds;

public class Class_FarmerListDetails {

    int id;
    String yearid;
    String stateid;
    String districtid;
    String talukid;
    String villageid;
    String grampanchayatid;
    String farmerid;
    String farmercode;
    String farmername;
    String farmerimage;
    byte[] localfarmerimage;

    public Class_FarmerListDetails(){}


    public Class_FarmerListDetails(String farmername, String farmercode) {
        this.farmername = farmername;
        this.farmercode = farmercode;
    }

    public String getFarmerid() {
        return farmerid;
    }

    public Class_FarmerListDetails(String yearid, String stateid, String districtid, String talukid, String villageid, String grampanchayatid, String farmerid, String farmercode, String farmername, String farmerimage, byte[] localimage)  {
        this.yearid = yearid;
        this.stateid = stateid;
        this.districtid = districtid;
        this.talukid = talukid;
        this.villageid = villageid;
        this.grampanchayatid = grampanchayatid;
        this.farmerid = farmerid;
        this.farmercode = farmercode;
        this.farmername = farmername;
        this.farmerimage = farmerimage;
        this.localfarmerimage = localimage;

    }

    public byte[] getLocalfarmerimage() {
        return localfarmerimage;
    }

    public void setLocalfarmerimage(byte[] localfarmerimage) {
        this.localfarmerimage = localfarmerimage;
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getYearid() {
        return yearid;
    }

    public void setYearid(String yearid) {
        this.yearid = yearid;
    }

    public String getVillageid() {
        return villageid;
    }

    public void setVillageid(String villageid) {
        this.villageid = villageid;
    }

    public String getGrampanchayatid() {
        return grampanchayatid;
    }

    public void setGrampanchayatid(String grampanchayatid) {
        this.grampanchayatid = grampanchayatid;
    }



    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public String getDistrictid() {
        return districtid;
    }

    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getTalukid() {
        return talukid;
    }

    public void setTalukid(String talukid) {
        this.talukid = talukid;
    }

    public String getFarmercode() {
        return farmercode;
    }

    public void setFarmercode(String farmercode) {
        this.farmercode = farmercode;
    }

    public String getFarmername() {
        return farmername;
    }

    public void setFarmername(String farmername) {
        this.farmername = farmername;
    }

    public String getFarmerimage() {
        return farmerimage;
    }

    public void setFarmerimage(String farmerimage) {
        this.farmerimage = farmerimage;
    }

    @Override
    public String toString() {
        return farmername;
    }



}


