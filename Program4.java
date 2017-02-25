import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
public class Program4 {
    public static void main(String [] args) throws Exception{
        ArrayList<Cache> caches = new ArrayList();
        ArrayList<Video> videos = new ArrayList();
        ArrayList<Endpoint> endpoints = new ArrayList();
        ArrayList<Requests> requests = new ArrayList();
        Scanner kbd = new Scanner(System.in);
        //System.out.println("Set name: ");
        //String name = kbd.nextLine();
        String name = "videos_worth_spreading";
        File file = new File(name+".in");
        try {
            Scanner sc = new Scanner(file);            
            //read parameters
            int V = sc.nextInt(); // videos
            int E = sc.nextInt(); // endpoints           
            int R = sc.nextInt(); // request          
            int C = sc.nextInt(); // servers caches    
            int X = sc.nextInt();// megabytes
            for(int i = 0;i<C;i++){
                caches.add(new Cache(i,X));
            }
            sc.nextLine();
            for(int i = 0;i<V;i++){
                videos.add(new Video(i,sc.nextInt()));
            }
            sc.nextLine();
            for(int i = 0;i<E;i++){
                int latency = sc.nextInt();
                int ncaches = sc.nextInt();
                Endpoint endpoint = new Endpoint(i,latency);
                for(int j = 0;j<ncaches;j++){
                    int idCache = sc.nextInt();
                    int latencyaux = sc.nextInt();                    
                    endpoint.addCache(new Cache(idCache,X,latencyaux));                    
                }
                endpoints.add(endpoint);
                sc.nextLine();
            }
            for(int i = 0;i<R;i++){                
                int idvideo = sc.nextInt();
                int idendpoint = sc.nextInt();
                int nreq = sc.nextInt();                
                Video aux = new Video(idvideo,videos.get(idvideo).size,nreq);   
                Requests aux_r = new Requests(i,nreq,aux,endpoints.get(idendpoint));
                requests = ordenaR(requests,aux_r);
                endpoints.get(idendpoint).addVideo(aux);
            }
            sc.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("requests:"+requests.size());
        
        System.out.println("Caches:"+caches.size());        
        System.out.println("Videos:"+videos.size());
        System.out.println("Endpoints:"+endpoints.size());      
       
        for(Requests req : requests){
            Endpoint end = endpoints.get(req.end.id);            
            Video vid = req.video;        
            for(Cache chs : end.caches){
                Cache aux_chs = caches.get(chs.id);
                if(aux_chs.size >= vid.size && !aux_chs.imOnIt(vid.id)){                        
                        aux_chs.add(vid);                        
                        break;
                    }
            }
        } 
        for(int i = 0;i<100;i++){
            for(Requests req : requests){
                Video v = req.video;
                for(Cache c : caches){
                    int cacheId = c.id;
                    Cache re = caches.get(cacheId);
                    if(re.size >= v.size && !re.imOnIt(v.id)){                       
                       re.add(v);                       
                       break;
                    }
                }
            }
        }
        PrintWriter writer = new PrintWriter(""+name+".out");       
        for(int i = 0;i<100;i++){
            for(Video v : videos){
                for(Cache c : caches){
                    int cacheId = c.id;
                    Cache re = caches.get(cacheId);
                    if(re.size >= v.size && !re.imOnIt(v.id)){                       
                       re.add(v);                       
                       break;
                    }
                }
            }
        }
        for(Cache c : caches){
            System.out.println("Tamaño cache "+c.size);
            writer.print(c.id+" ");
            for(Video v : c.videos){
                writer.print(v.id+" ");
            }
            writer.println("");
        }        
        writer.close();
    }
    public static ArrayList<Requests> ordenaR(ArrayList<Requests> orde,Requests e) {              
        int counter = 0;
        if(orde.size() == 0){
            orde.add(e);                
        }else{                
            for(Requests or : orde) {
                if(or.requests < e.requests){
                    orde.add(counter++,e);                        
                    return orde;
                }
            } 
            orde.add(e);
        }        
        return orde;
    }
    public static ArrayList<Integer> ordenof(ArrayList<Endpoint> endpoints){
        ArrayList<Integer> ids = new ArrayList();
        ArrayList<Endpoint> orden = new ArrayList();
        for(Endpoint e : endpoints){
            if(orden.size() == 0){
                orden.add(e);
                ids.add(e.id);
            }else{
                int counter = 0;
                for(Endpoint or : orden) {
                    if(or.videos.get(0).requests > e.videos.get(0).requests){
                        orden.add(counter++,e);
                        ids.add(e.id);
                        break;
                    }
                } 
            }
        }
        return ids;
    }
}
