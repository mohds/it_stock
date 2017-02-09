package project1;

public class Spec {
    String spec_id, spec_name;
    
    public void set_spec_id(String s){
        spec_id = s;
    }
    public String get_spec_id(){
        return spec_id;
    }
    
    public void set_spec_name(String s){
        spec_name = s;
    }
    public String get_spec_name(){
        return spec_name;
    }
    
    public Spec(String spec_name, String spec_id) {        
        super();
        set_spec_id(spec_id);
        set_spec_name(spec_name);
    }
}
