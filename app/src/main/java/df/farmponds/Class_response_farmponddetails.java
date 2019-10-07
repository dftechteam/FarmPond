package df.farmponds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/*
 "Farmers": [
        {
        },
         ],
    "statusMessage": "Success"
}

*/
public class Class_response_farmponddetails
{




/*@SerializedName("Farmers")
@Expose*/
private List<Class_farmponddetails> class_farmponddetails_obj = new ArrayList<Class_farmponddetails>();
//@Expose
private String statusMessage;






  public List<Class_farmponddetails> get_farmponddetails() {
    return class_farmponddetails_obj;
  }


  public void set_farmponddetails(List<Class_farmponddetails> farmponddetails) {
    this.class_farmponddetails_obj = farmponddetails;
  }




public String get_responsestatus() {
  return statusMessage;
}


public void set_responsestatus(String responsestatus) {
  this.statusMessage = responsestatus;
}

}
