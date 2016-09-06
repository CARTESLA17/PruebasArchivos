/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package a25alunityconnect;

import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;

/**
 * Esta es la clase principal del programa.
 * En esta clase se carga de un archivo tipo properties la ruta donde se dejan los
 * resultados para el UnityConnect, la ruta donde se dejan los resultados para Victrix,
 * y la ruta donde se buscan los resultados que envia el analizador A25.
 * En esta clase tambien se crea el hilo que se encarga de buscar los resultados que
 * envia el analizador A25.
 * @author Carlos Rodriguez 13/01/2014
 */
public class A25AlUnityConnect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
	   //Activo el archivo de cliente.properties para saber la direccion de los archivos a enviar.
	   FileReader reader=new FileReader("A25Unity.properties");
	   Properties propiedades=new Properties();
           System.out.println("Cargo rutas desde properties");
           propiedades.load(reader);
           System.out.println("rutaUnity: "+propiedades.getProperty("rutaUnity"));
           System.out.println("rutaResultados: "+propiedades.getProperty("rutaResultados"));
           System.out.println("rutaExportA25: "+propiedades.getProperty("rutaExportA25"));
	   ThreadA25 hiloA25=new ThreadA25(propiedades.getProperty("rutaUnity"),propiedades.getProperty("rutaResultados")
                                 ,propiedades.getProperty("rutaExportA25"));
           System.out.println("Inicio hilo!");
	   hiloA25.start();
	} catch (IOException e) {
	   System.out.println("Excepcion al cargar archivo de propiedades: "+e.getStackTrace());
	} 
    }
}
