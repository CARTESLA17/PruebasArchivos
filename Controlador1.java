package com.chispitascar.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chispitascar.persistencia.AdministradorPersistencia;
import com.chispitascar.persistencia.Ciudad;
import com.chispitascar.persistencia.Marca;
import com.chispitascar.persistencia.Modelo;
import com.chispitascar.persistencia.Vehiculo;


@RestController
@RequestMapping("/controlador")
public class Controlador1 {
	
	private AdministradorPersistencia admonPersistencia= new AdministradorPersistencia();
	
		
	
	@RequestMapping(value = "/todos",method = RequestMethod.GET)
	public @ResponseBody List<Vehiculo> getVehiculos() {
		System.out.println("GET original");	
		return admonPersistencia.buscarTodosLosVehiculos();		
	}	
	
	@RequestMapping(value = "/ciudades",method = RequestMethod.GET)
	public @ResponseBody List<Ciudad> buscarCiudades() {
		System.out.println("GET ciudades");	
		return admonPersistencia.buscarTodasLasCiudades();		
	}
	
	@RequestMapping(value = "/marcas",method = RequestMethod.GET)
	public @ResponseBody List<Marca> buscarMarcas() {
		System.out.println("GET marcas");	
		return admonPersistencia.buscarTodasLasMarcas();		
	}
	
	@RequestMapping(value = "/modelos",method = RequestMethod.GET)
	public @ResponseBody List<Modelo> buscarModelos() {
		System.out.println("GET modelos");	
		return admonPersistencia.buscarTodasLasModelos();		
	}
	
	@RequestMapping(value = "/addVehiculo",method = RequestMethod.POST)
	public @ResponseBody int addVehiculo(@RequestBody Vehiculo vehiculo) {
		System.out.println("marca: "+vehiculo.getMarca());
		System.out.println("post");
		System.out.println("vehiculo nuevo: "+vehiculo.toString());
		int valor=revisarDatosVehiculo(vehiculo);
		if(valor==1){
			return admonPersistencia.insertarVehiculo(vehiculo);			
		}
		return valor;
	}
	
	@RequestMapping(value = "/modelosPorMarca", method = RequestMethod.POST)
	public @ResponseBody List<Modelo> update(@RequestBody Vehiculo vehiculo) {
		if( (vehiculo.getMarcaId()+"").matches("[0-9]+")){
			return admonPersistencia.buscarModelosPorMarca(vehiculo.getMarcaId());
		}else{
			return null;
		}
	}
	
	public int revisarDatosVehiculo(Vehiculo vehiculo){
		if(vehiculo.getPlaca().length()<=6){
			if(vehiculo.getPuertas().matches("[0-9]+")){
				if( (vehiculo.getCiudadId()+"").matches("[0-9]+")){
					if( (vehiculo.getMarcaId()+"").matches("[0-9]+")){
						if( (vehiculo.getModeloId()+"").matches("[0-9]+")){
							return 1;
						}else{
							return 3;
						}
					}else{
						return 3;
					}
				}else{
					return 3;
				}
			}else{
				return 3;
			}
		}else{
			return 3;
		}
	}
	
	
	
	
	
	
}

