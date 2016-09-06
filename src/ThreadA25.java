package a25alunityconnect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Esta clase es el hilo encargado de buscar los resultados enviados por el 
 * analizador A25.
 * Si el hilo encuentra algun archivo de resultados, lo envia a la clase 
 * ProcesarArchivoA25 que es la encargada de segregar la informacion en datos para
 * Victrix y datos para el UnityConnect.
 * @author Carlos Rodriguez 13/01/2014
 */
public class ThreadA25 extends  Thread {
    
        static String rutaControlesUnity;
	static String rutaResultadosPacientes;
        static String rutaExportA25;
        private BufferedReader inputStream;
        
        /**
         * 
         * @param rutaControlesUnity: es la ruta donde se generara el archivo con los
         * resultados de control que seran enviados al UnityConnect.
         * @param rutaResultadosPacientes: es la ruta donde se generara el archivo con los
         * resultados que seran enviados a victrix.
         * @param rutaExportA25: es la ruta donde se buscan los archivos de resultados
         * generados por el analizador A25.
         */
        public ThreadA25(String rutaControlesUnity, String rutaResultadosPacientes, String rutaExportA25){
		super();
                this.rutaControlesUnity=rutaControlesUnity;
                this.rutaResultadosPacientes=rutaResultadosPacientes;
                this.rutaExportA25=rutaExportA25;
        }

	public void run(){
		try {
                     File carpeta = new File(rutaExportA25);
                     if( carpeta.exists() && carpeta.isDirectory() && carpeta.canRead() && carpeta.canWrite() ){
                           System.out.println("Carpeta rutaExportA25 OK!");
                           while (true) {
                               String[] archivos=carpeta.list();
                               //Entra al if si hay mas de un archivo en la carpeta.
                               if( (archivos.length) > 0){
                                   for(String file: archivos){
                                       //Creo un objeto de la clase File para obtener el tamano del archivo a ser enviado.
                                       if ( file.endsWith(".txt")){
                                           System.out.println("Nombre del archivo: "+ file);
                                           inputStream =new BufferedReader(new FileReader(rutaExportA25+file));
                                           ProcesarArchivoA25 processFile= new ProcesarArchivoA25(inputStream,file);
                                           System.out.println("Se reinicia el flujo BufferedReader");
                                           inputStream.close();
                                           inputStream=null;
                                           File archivo= new File(rutaExportA25+file);
                                           System.out.println("Borro archivo: "+file);
                                           archivo.delete();
                                       }else{
                                           System.out.println("Archivo con extension diferente a txt");
                                       }
                                   }
                               }else{
                                   System.out.println("No hay archivos para procesar.");
                                   System.out.println("Estado dormido por 10 segundos.");
                                   this.sleep(10000);
                               }
                            }
                     }else{
                         System.out.println("Hay problemas con la carpeta rutaExportA25, se cierra el programa!");
                   }
                } catch (Exception e) {
			System.out.println("Error por esperar: "+e.getMessage()+" "+e.getLocalizedMessage());
		}
	}
   
}
