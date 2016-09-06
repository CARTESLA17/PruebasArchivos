/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package a25alunityconnect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Esta clase se encarga de procesar el archivo de resultados enviado por el A25
 * @author Carlos Rodriguez 13/01/2014
 */
    public class ProcesarArchivoA25 {
    
    private ArrayList<String> resultadosPacientes;
    private ArrayList<String> resultadosControles;
    private BufferedReader inputStream;
    private String nameFile;

    /**
     * Constructor de la clase ProcesarArchivoA25
     * @param inputStream: es el buffer que permite leer los datos del archivo de
     * resultados enviado por el A25.
     * @param nameFile: Es el nombre del archivo de resultados generado por el A25.
     * @throws IOException
     * @throws Exception 
     */
    public ProcesarArchivoA25(BufferedReader inputStream, String nameFile) throws IOException, Exception {
        this.inputStream = inputStream;
        this.nameFile=nameFile;
        resultadosPacientes= new ArrayList<>();
        resultadosControles= new ArrayList<>();
        revisarArchivo();
    }
    
    /**
     * Este metodo es el encargado de segmentar la información que viene del A25
     * en los resultados que se enviaran a Victrix y en los resultados de control
     * que se enviaran al UnityConnect, y tambien se envia dicha informacion a
     * las clases que se encargan de generar los archivos para sus correspondientes
     * receptores.
     * @throws IOException
     * @throws Exception 
     */
    public void revisarArchivo() throws IOException, Exception{
        String linea;
        try{
            int i=0;
            while( ( linea=inputStream.readLine() )!= null ){
                String [] resulst=linea.split((char)9+"");
                String [] controlYLote=resulst[0].split((char)32+"");
                if( controlYLote.length == 2){
                    if ( resulst.length > 0 && controlYLote[0].matches("[0-9]{1}(CT|CTPCR|CTORI|CTPROT|CTCKMB|CTADA)") ) {
                        System.out.println("Se anexa a la lista resultado control: "+linea);
                        resultadosControles.add(linea);
                        System.out.println("Resultado control anexado");
                    }else{
                        System.out.println("Se anexa a la lista resultado paciente: "+linea);
                        resultadosPacientes.add(linea);
                        System.out.println("Resultado paciente anexado");
                    }
                } else{
                    System.out.println("Se anexa a la lista resultado paciente: "+linea);
                    resultadosPacientes.add(linea);
                    System.out.println("Resultado paciente anexado");
                }
                
            }
            if( resultadosControles != null && !resultadosControles.isEmpty() ){
                UnityConnectFileA25 UCFA25 = new UnityConnectFileA25(resultadosControles);
            }else{
                System.out.println("No hay resultados de controles");
            }
            if( resultadosPacientes != null && !resultadosPacientes.isEmpty()){
                FileA25ForVictrix FA25FV= new FileA25ForVictrix(resultadosPacientes,nameFile);
            }else{
                System.out.println("No hay resultados de controles");
            }
        }catch(FileNotFoundException ex){
		System.out.println("Excepcionen ProcesarArchivoA25"+ex);
	}
    }
    
}
