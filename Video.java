
public class Video {
    public int id;
    public int size;
    public int requests;
    public Video(int id){
        this.id = id;
    }
    public Video(int id,int size){
        this.id = id;
        this.size = size;
    }
    public Video(int id,int size,int requests){
        this.id = id;
        this.size = size;
        this.requests = requests;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setRequests(int requests){
        this.requests = requests;
    }
}
