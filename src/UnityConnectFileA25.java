    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package a25alunityconnect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Esta clase se encarga de crear el archivo que contiene los resultados de control
 * que son enviados al UnityConnect desde el analizador A25.
 * @author Carlos Rodriguez 13/01/2014
 */
public class UnityConnectFileA25 {
    
    private String rutaUnityC;
    private FileWriter flujoArchivoSalida=null;
    private BufferedWriter buffer=null;
    private PrintWriter printArchivo = null;

    /**
     * Constructor de la clase UnityConnectFileA25
     * @param controls: es un objeto del tipo ArrayList que almacena objetos del 
     * tipo String, donde cada String es un registro con la informacion de un resultado
     * de control enviado desde el A25.     
     * @throws Exception 
     */
    public UnityConnectFileA25(ArrayList<String> controls) throws Exception{
        System.out.println("Se crea clase UnityConnectFileA25");
        rutaUnityC=ThreadA25.rutaControlesUnity;
        generarArchivoControlesUnityConnect(controls);
    }
    
    /**
     * Este metodo es el encargado de crear el archivo con la informacion de los
     * resultados de control que son enviados al UnityConnect desde el analizador A25.
     * @param controls: es un objeto del tipo ArrayList que almacena objetos del 
     * tipo String, donde cada String en un registro con la informacion de un resultado
     * de control enviado desde el A25.
     * @throws Exception 
     */    
    public void generarArchivoControlesUnityConnect(ArrayList<String> controls) throws Exception {
        Calendar calendario = Calendar.getInstance();
	String year =calendario.get(Calendar.YEAR)+"";
	String mes =(1+calendario.get(Calendar.MONTH))+"";
	String dia =calendario.get(Calendar.DAY_OF_MONTH)+"";
	//Hora de la conexion.
	String hora = calendario.get(Calendar.HOUR_OF_DAY)+"";
	String minutos = calendario.get(Calendar.MINUTE)+"";
	String segundos = calendario.get(Calendar.SECOND)+"";
        String miliSegundos= calendario.get(Calendar.MILLISECOND)+"";
        try{ 
            File rU=new File(rutaUnityC);
            if(rU.exists() && rU.isDirectory() && rU.canRead() && rU.canWrite()){
                System.out.println("Carpeta rutaUnityConnect OK!");
                flujoArchivoSalida=new FileWriter(rutaUnityC+"A25UC"+year+mes+dia+hora+minutos+segundos+miliSegundos+".txt");
                System.out.println("Se crea el archivo de controles del A25 para el UnityConnect");
                buffer=new BufferedWriter(flujoArchivoSalida);
                printArchivo=new PrintWriter(buffer);
                System.out.println("Tamano arrayList: "+controls.size());
                System.out.println("Comienza el envio de informacion al archivo UnityConnect");
                for(String resultado :controls){
                    String [] test=resultado.split((char)9+"");
                    //cYL = control y lote
                    String [] cYL=test[0].split((char)32+"");
                    String UTI=reemplazoEspacio(test[1]);
                    System.out.println(UTI+" "+test[5]+" "+test[3]);
                    /**
                     * cYL[0].charAt(0)= nivel de dilucion
                     * cYL[1] = lote del reactivo
                     * UTI = examen de control
                     * test[5] = fecha y hora
                     * test[3] = resultado del examen de control
                     */
                    printArchivo.println(cYL[0].charAt(0)+" "+cYL[1]+" "+UTI+"_"+cYL[0].charAt(0)+" "+test[5]+" "+test[3]);
                    printArchivo.flush();
                }
            }else{
                System.out.println("Hay problemas con la carpeta rutaUnityConnect!");
            }
        }catch(FileNotFoundException ex){
            System.out.println("excepcion al generar archivo de controles para el UnityConnect: "+ex);
        }	
        //El finally simpre preguntara por los flujos abiertos, para que queden cerrados al finalizar el proceso.
        finally {
             try {
                if(flujoArchivoSalida != null){
                     flujoArchivoSalida.close();
                     System.out.println("Flujo FileWrite cerrado");
                }
             } catch( IOException e ) {
                 System.out.println("El flujo FileWriter no se cerro correctamente!");
             }
             if( buffer!=null ){
                 buffer.close();
                 System.out.println("Flujo BufferedWriter cerrado");
             }
             if(printArchivo != null) {
                 printArchivo.close();
                 System.out.println("Flujo PrintWriter cerrado");
                 printArchivo=null;
             }
        }
        
    }
    
    /**
     * Este metodo se encarga de cambiar los espacios que existan en el UTID por
     * guiones bajos.
     * @param uti: Es el UTID del registro de control.
     * @return: un objeto del tipo String que es el UTID del registro de control. 
     */
    public String reemplazoEspacio(String uti){
         String UTID;
         if(uti.contains(" ")){
                UTID=uti.replace(' ','_');
                System.out.println("Se reemplazo espacio en el examen, examen final: "+UTID);
                return UTID;
         }else{
                System.out.println("No se reemplaza espacio en el examen: "+uti);
                return uti;
         }
    }
    
}
