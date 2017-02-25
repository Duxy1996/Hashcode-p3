import java.util.*;
public class Cache {
    int id;
    int size;
    int laten;
    ArrayList<Video> videos;
    public Cache(){
        videos = new ArrayList();
    }
    public Cache(int id,int size){
        this.id = id;
        this.size = size;
        videos = new ArrayList();
    }
    public Cache(int id,int size,int laten){
        this.id = id;
        this.size = size;
        this.laten = laten;
        videos = new ArrayList();
    }
    public void setSize(int size){
        this.size = size;
    }
    public void add(Video video) {
        this.size = this.size - video.size;
        if(videos.size() == 0){
            videos.add(video);
        }else{
            for(int i = 0; i< videos.size();i++){
                if(videos.get(i).requests < video.requests){
                    videos.add(i,video);
                    break;
                }                
            }
        }        
    }
    public boolean imOnIt(int id){
        for(Video v : videos){
            if(v.id == id ){
                return true;
            }
        }
        return false;
    }
}
