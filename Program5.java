import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
public class Program5 {
    public static void main(String [] args) throws Exception{
        ArrayList<Cache> caches = new ArrayList();
        ArrayList<Video> videos = new ArrayList();
        ArrayList<Endpoint> endpoints = new ArrayList();
        ArrayList<Requests> requests = new ArrayList();
        Scanner kbd = new Scanner(System.in);
        System.out.println("HashCode2017");
        //String name = kbd.nextLine();
        String name[] = {"trending_today","videos_worth_spreading","me_at_the_zoo","kittens"};
        for(int m = 0 ; m< 4;m++){
            caches = null;
            videos = null;
            endpoints = null;
            requests = null;
            caches = new ArrayList();
            videos = new ArrayList();
            endpoints = new ArrayList();
            requests = new ArrayList();
            System.out.println(name[m]);
            File file = new File(name[m]+".in");
            int X = 0;
            int C = 0;
            int V = 0;
            int E = 0;
            int R = 0;
            try {
                Scanner sc = new Scanner(file);            
                //read parameters
                V = sc.nextInt(); // videos
                E = sc.nextInt(); // endpoints           
                R = sc.nextInt(); // request          
                C = sc.nextInt(); // servers caches    
                X = sc.nextInt();// megabytes
                
                System.out.println("Crendo caches...");
                for(int i = 0;i<C;i++){
                    caches.add(new Cache(i,X));
                }
                sc.nextLine();
                System.out.println("Crendo videos...");
                for(int i = 0;i<V;i++){
                    videos.add(new Video(i,sc.nextInt()));
                }
                sc.nextLine();
                System.out.println("Crendo endpoints...");
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
                System.out.println("Crendo peticiones...");
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
                System.out.print("Ha ocurrido el error inesperado: ");
                e.printStackTrace();
            }
            System.out.println("Creado satisfactoriamente:");
            System.out.println("Requests: "+requests.size());            
            System.out.println("Caches: "+caches.size());        
            System.out.println("Videos: "+videos.size());
            System.out.println("Endpoints: "+endpoints.size());      
            System.out.println("Ejecutando: Algoritmo 1");
            for(Requests req : requests){
                Endpoint end = endpoints.get(req.end.id);            
                Video vid = req.video;        
                for(Cache chs : end.caches){
                    Cache aux_chs = caches.get(chs.id);
                    boolean comp = false;
                    //comprobamos que no este en niguna cache que ya esta conectada 
                    for(Cache chss : end.caches){ 
                        Cache aux_chss = caches.get(chss.id);
                        comp = comp | aux_chss.imOnIt(vid.id);
                    }
                    //aux_chs.imOnIt(vid.id);
                    if(aux_chs.size >= vid.size && !comp){                        
                            aux_chs.add(vid);                        
                            break;
                        }
                }
            } 
            System.out.println("Terminado: Algoritmo 1");
            System.out.println("Ejecutando: Algoritmo 2");
            for(int i = 0;i<10;i++){
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
            System.out.println("Terminado: Algoritmo 2");
            PrintWriter writer = new PrintWriter(""+name[m]+".out");              
            writer.println(C+" ");
            for(Cache c : caches){                
                writer.print(c.id+" ");
                for(Video v : c.videos){
                    writer.print(v.id+" ");
                }
                writer.println("");
            }        
            writer.close();
            System.out.println("Escrito en archivo con nombre: "+name[m]);
        }
    }
    public static ArrayList<Requests> ordenaR(ArrayList<Requests> orde,Requests e) {      
        Endpoint aux_end = e.end;
        int counter = 0;
        if(orde.size() == 0){
            orde.add(e);                
        }else{  
            for(Requests or : orde) {
                Endpoint aux_in = or.end;
                int guad1 = 0;
                int guad2 = 0;
                if(aux_end.caches.size() > 0){
                    guad1 =  aux_end.latency - aux_end.caches.get(0).laten;
                }
                if(aux_in.caches.size() > 0 && aux_end.caches.size() > 0){
                    guad2 =  aux_end.latency - aux_end.caches.get(0).laten;
                }          
                // el valor mayor si se pone > castiga el resultado contra todo pronóstico
                if(e.requests*(guad1) < or.requests*(guad2)){
                    orde.add(counter,e);                        
                    return orde;
                } 
                counter++;
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
