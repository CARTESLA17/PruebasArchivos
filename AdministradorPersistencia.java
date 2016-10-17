package com.chispitascar.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

 




public class AdministradorPersistencia {
	
	private static Connection conexion=null;
		
	public AdministradorPersistencia() {
	}
	
	public synchronized Connection getConnection(){             
        try{
        	if(conexion==null){
        		Class.forName("com.mysql.jdbc.Driver");
	            String servidor = "jdbc:mysql://localhost/chispitascar";
	            String usuarioDB="root";
	            String passwordDB="admin";
	            conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
        	}
        }catch(ClassNotFoundException ex){
           System.out.println("ClassNotFoundException" + ex.getMessage());
            conexion=null;
        }catch(SQLException ex){   
        	System.out.println("SQLException" + ex.getMessage());
            conexion=null;
        }catch(Exception ex){
        	System.out.println("Exception" + ex.getMessage());
            conexion=null;
        }finally{
            return conexion;
        }
    }
	
	
	
	
	
	public synchronized List<Vehiculo> buscarTodosLosVehiculos(){
		List<Vehiculo> lista =new ArrayList<Vehiculo>();
		Vehiculo vehiculo=null;
		Statement sentencia = null;
		ResultSet resultado = null;			
	    try {
	    	sentencia = this.getConnection().createStatement();
	    	resultado = sentencia.executeQuery("SELECT * FROM vehiculo ORDER BY fechaRegistro ASC;");
	    	while(resultado.next()){
	    		vehiculo= new Vehiculo();
	    		vehiculo.setFechaRegistro(resultado.getString(("fechaRegistro")));
	    		vehiculo.setPuertas(resultado.getInt(("puertas"))+"");
	    		vehiculo.setPlaca(resultado.getString(("placas"))+"");
	    		vehiculo.setMarcaId(resultado.getInt(("marca")));
	    		vehiculo.setModeloId(resultado.getInt(("modelo")));
	    		vehiculo.setCiudadId(resultado.getInt(("ciudad")));	    		
				lista.add(vehiculo);		    		
	    	} 
	    	//cerrarResultset(resultado);
	    	
	    	for(Vehiculo car: lista){
	    		
	    		resultado = sentencia.executeQuery("SELECT ciudad FROM ciudad where id='"+car.getCiudadId()+"';");
	    		resultado.next();
	    		car.setCiudad(resultado.getString(("ciudad")));
	    		//cerrarResultset(resultado);
	    		resultado = sentencia.executeQuery("SELECT marca FROM marca where id='"+car.getMarcaId()+"';");
	    		resultado.next();
	    		car.setMarca(resultado.getString(("marca")));
	    		//cerrarResultset(resultado);
	    		resultado = sentencia.executeQuery("SELECT modelo FROM modelo where id='"+car.getMarcaId()+"';");	    		
	    		resultado.next();
	    		car.setModelo(resultado.getString(("modelo")));	
	    		
	    	}	    	
	    	cerrarResultset(resultado);
    		cerrarStatemet(sentencia);    		
	    	return lista;
		}catch (SQLException e) {			
		    	 System.out.println("SQLException buscarTodasLasPersonas: "+e.getMessage());
		    	 cerrarResultset(resultado);			     
		    	 cerrarStatemet(sentencia);	
		    	 return lista;
		}	   	
	}
	
	public synchronized List<Ciudad> buscarTodasLasCiudades(){
		List<Ciudad> lista =new ArrayList<Ciudad>();
		Ciudad ciudad=null;
		Statement sentencia = null;
		ResultSet resultado = null;			
	    try {
	    	sentencia = this.getConnection().createStatement();
	    	resultado = sentencia.executeQuery("SELECT * FROM ciudad;");
	    	while(resultado.next()){
	    		ciudad= new Ciudad();
	    		ciudad.setId(resultado.getInt(("id")));
	    		ciudad.setNombre(resultado.getString(("ciudad")));	    		  		
				lista.add(ciudad);		    		
	    	} 	    		    	
	    	cerrarResultset(resultado);
    		cerrarStatemet(sentencia);    		
	    	return lista;
		}catch (SQLException e) {			
		    	 System.out.println("SQLException buscarTodasLasPersonas: "+e.getMessage());
		    	 cerrarResultset(resultado);			     
		    	 cerrarStatemet(sentencia);	
		    	 return lista;
		}	   	
	}
	
	public synchronized List<Marca> buscarTodasLasMarcas(){
		List<Marca> lista =new ArrayList<Marca>();
		Marca marca=null;
		Statement sentencia = null;
		ResultSet resultado = null;			
	    try {
	    	sentencia = this.getConnection().createStatement();
	    	resultado = sentencia.executeQuery("SELECT * FROM marca;");
	    	while(resultado.next()){
	    		marca= new Marca();
	    		marca.setId(resultado.getInt(("id")));
	    		marca.setNombre(resultado.getString(("marca")));	    		  		
				lista.add(marca);		    		
	    	} 	    		    	
	    	cerrarResultset(resultado);
    		cerrarStatemet(sentencia);    		
	    	return lista;
		}catch (SQLException e) {			
		    	 System.out.println("SQLException buscarTodasLasPersonas: "+e.getMessage());
		    	 cerrarResultset(resultado);			     
		    	 cerrarStatemet(sentencia);	
		    	 return lista;
		}	   	
	}
	
	public synchronized List<Modelo> buscarTodasLasModelos(){
		List<Modelo> lista =new ArrayList<Modelo>();
		Modelo modelo=null;
		Statement sentencia = null;
		ResultSet resultado = null;			
	    try {
	    	sentencia = this.getConnection().createStatement();
	    	resultado = sentencia.executeQuery("SELECT * FROM modelo;");
	    	while(resultado.next()){
	    		modelo= new Modelo();
	    		modelo.setId(resultado.getInt(("id")));
	    		modelo.setNombre(resultado.getString(("modelo")));	    		  		
				lista.add(modelo);		    		
	    	} 	    		    	
	    	cerrarResultset(resultado);
    		cerrarStatemet(sentencia);    		
	    	return lista;
		}catch (SQLException e) {			
		    	 System.out.println("SQLException buscarTodasLasPersonas: "+e.getMessage());
		    	 cerrarResultset(resultado);			     
		    	 cerrarStatemet(sentencia);	
		    	 return lista;
		}	   	
	}
	
	public synchronized List<Modelo> buscarModelosPorMarca(int id){
		List<Modelo> lista =new ArrayList<Modelo>();
		Modelo modelo=null;
		Statement sentencia = null;
		ResultSet resultado = null;			
	    try {
	    	sentencia = this.getConnection().createStatement();
	    	resultado = sentencia.executeQuery("SELECT * FROM modelo WHERE marca='"+id+"';");
	    	while(resultado.next()){
	    		modelo= new Modelo();
	    		modelo.setId(resultado.getInt(("id")));
	    		modelo.setNombre(resultado.getString(("modelo")));	    		  		
				lista.add(modelo);		    		
	    	} 	    		    	
	    	cerrarResultset(resultado);
    		cerrarStatemet(sentencia);    		
	    	return lista;
		}catch (SQLException e) {			
		    	 System.out.println("SQLException buscarTodasLasPersonas: "+e.getMessage());
		    	 cerrarResultset(resultado);			     
		    	 cerrarStatemet(sentencia);	
		    	 return lista;
		}	   	
	}
	
	public synchronized int insertarVehiculo(Vehiculo vehiculo){
		Statement sentencia = null;
		ResultSet resultado = null;	
	    try {
	    	sentencia = this.getConnection().createStatement();
	    	resultado = sentencia.executeQuery("SELECT * FROM vehiculo WHERE placas='"+vehiculo.getPlaca()+"';");
	    	if(resultado.next()){
	    		cerrarResultset(resultado);
	    		cerrarStatemet(sentencia);
	    		return 4;
	    	}
	    	cerrarResultset(resultado);	
	    	this.getConnection().setAutoCommit(false);
	    	sentencia.execute("INSERT INTO vehiculo(fechaRegistro,modelo,marca,ciudad,puertas, placas)"
	    			+"values('"+generarDateTime()+"','"+vehiculo.getModeloId()+"','"+vehiculo.getMarcaId()
	    			+"','"+vehiculo.getCiudadId()+"','"+vehiculo.getPuertas()+"','"+vehiculo.getPlaca()+"');"); 
	    	this.getConnection().commit();
			this.getConnection().setAutoCommit(true);
			cerrarStatemet(sentencia);
			return 1;				    
		}catch (SQLException e) {
			try {
					this.getConnection().rollback();
				 }catch(SQLException e1) {
					 System.out.println("SQLException rollback insertarVehiculo: "+e.getMessage());
					 cerrarResultset(resultado);			     
			    	 cerrarStatemet(sentencia);	
			    	 return 2;
				 }
		    	 System.out.println("SQLException insertarVehiculo: "+e.getMessage());
		    	 cerrarResultset(resultado);			     
		    	 cerrarStatemet(sentencia);	
		    	 return 2;
		}	   	
	}
	
	public String generarDateTime(){		
		Calendar calendario = Calendar.getInstance();
		String year =calendario.get(Calendar.YEAR)+"";
		String mes =organizarFechaOHora((1+calendario.get(Calendar.MONTH))+"");
		String dia =organizarFechaOHora(calendario.get(Calendar.DAY_OF_MONTH)+"");
		String hora = organizarFechaOHora(calendario.get(Calendar.HOUR_OF_DAY)+"");
		String minutos =organizarFechaOHora( calendario.get(Calendar.MINUTE)+"");
		String segundos =organizarFechaOHora( calendario.get(Calendar.SECOND)+"");	
		return year+"-"+mes+"-"+dia+" "+hora+":"+minutos+":"+segundos;
	}
	
	public String organizarFechaOHora(String dato){
		if(dato.length()==1){
			return dato="0"+dato;
		}else{
			return dato;
		}
	}
	
	
	
	public void cerrarResultset(ResultSet rs){   
        try{
        	if(rs!=null){
        		rs.close();
        	}
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
     
  
    public void cerrarStatemet(Statement st){
        try{
        	if(st!=null){
        		st.close();
        	}
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
	
	
}
