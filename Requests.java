import java.util.*;
public class Requests {
    int id;
    int requests;
    Video video;    
    Endpoint end;    
    public Requests(int id, int requests, Video vid ,Endpoint end){
        this.id = id;
        this.requests = requests;
        this.video = vid;
        this.end = end;        
    }
}
