import java.util.*;
public class Endpoint {
    public int id;
    public int latency;
    public ArrayList<Cache> caches;
    public ArrayList<Video> videos;
    public Endpoint(){
        caches = new ArrayList();
        videos = new ArrayList();
    }
    public Endpoint(int id, int latency){
        this.id = id;
        this.latency = latency;
        caches = new ArrayList();
        videos = new ArrayList();
    }
    public void addCache(Cache cache){
        if(caches.size() == 0){
              caches.add(cache);          
        }
        else{
            int x = 0;
            for(Cache v : caches){
                x++;
                if(v.laten > cache.laten){
                    caches.add(x-1,cache);
                    break;
                }
            }            
        }
    }  
    public void addVideo(Video video){
        if(videos.size() == 0){
              videos.add(video);          
        }
        else{
            int x = 0;
            for(Video v : videos){
                x++;
                if(v.requests < video.requests){
                    videos.add(x-1,video);
                    break;
                }
            }            
        }
    }
}

