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
 * Esta clase se encarga de crear el archivo que contiene los resultados que 
 * son enviados a Victrix desde el analizadorA25
 * @author Carlos Rodriguez 13/01/2014
 */
public class FileA25ForVictrix {
    private String rutaResultados;
    private FileWriter flujoArchivoSalida=null;
    private BufferedWriter buffer=null;
    private PrintWriter printArchivo = null;
    private String nameA25File;
    
    /**
     * Constructor de la clase FileA25ForVictrix
     * @param rPacientes: es un objeto del tipo ArrayList que almacena objetos del 
     * tipo String, donde cada String en un registro con la informacion de un paciente
     * enviado desde el A25.
     * @param nameA25File: es el nombre del archivo de resultados generado por el
     * A25.
     * @throws Exception 
     */
    public FileA25ForVictrix(ArrayList<String> rPacientes,String nameA25File) throws Exception{
        System.out.println("Se crea clase FileA25ForVictrix");
        rutaResultados=ThreadA25.rutaResultadosPacientes;
        this.nameA25File=nameA25File;
        generarArchivoResultados(rPacientes);
    }
    
    /**
     * Este metodo es el encargado de crear el archivo con la informacion de los
     * resultados que son enviados a Victrix desde el analizador A25.
     * @param pacientes: es un objeto del tipo ArrayList que almacena objetos del 
     * tipo String, donde cada String es un registro con la informacion de un paciente
     * enviado desde el A25.
     * @throws Exception 
     */    
    public void generarArchivoResultados(ArrayList<String> pacientes) throws Exception {
        try{ 
            System.out.println("Se crea el archivo de resultados del A25 para Victrix");
            File rR=new File(rutaResultados);
            if(rR.exists() && rR.isDirectory() && rR.canRead() && rR.canWrite()){
                System.out.println("Carpeta rutaResultados OK!");
                flujoArchivoSalida=new FileWriter(rutaResultados+nameA25File);
                buffer=new BufferedWriter(flujoArchivoSalida);
                printArchivo=new PrintWriter(buffer);
                System.out.println("Tamano arrayList: "+pacientes.size());
                System.out.println("Comienza el envio de informacion al archivo resultados");
                for(String resultado :pacientes){
                    System.out.println("Resultado: "+resultado);
                    printArchivo.print(resultado+"\n");
                    printArchivo.flush();
                }
            }else{
               System.out.println("Hay problemas con la carpeta rutaResultados!");
            }
        }catch(FileNotFoundException ex){
            System.out.println("excepcion al generar archivo de resultados para Victrix: "+ex);
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
}
    
