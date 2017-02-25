import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
public class Program2 {
    public static void main(String [] args) throws Exception{
        ArrayList<Cache> caches = new ArrayList();
        ArrayList<Video> videos = new ArrayList();
        ArrayList<Endpoint> endpoints = new ArrayList();
        Scanner kbd = new Scanner(System.in);
        System.out.println("Set name: ");
        //String name = kbd.nextLine();
        String name = "kittens";
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
                endpoints.get(idendpoint).addVideo(aux);
            }
            sc.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Caches:"+caches.size());        
        System.out.println("Videos:"+videos.size());
        System.out.println("Endpoints:"+endpoints.size());     
        
        ArrayList<Integer> orden = ordenof(endpoints);
        for(Integer iter : orden){
            Endpoint end = endpoints.get(iter);
            for(Video v : end.videos){
                for(Cache c : end.caches){
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
            writer.print(c.id+" ");
            for(Video v : c.videos){
                writer.print(v.id+" ");
            }
            writer.println("");
        }        
        writer.close();
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
