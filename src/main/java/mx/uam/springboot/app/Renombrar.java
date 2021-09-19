package mx.uam.springboot.app;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Renombrar {
	
	public static void main(String[] args) {
		
	
		
		File carpetaFotos = new File("C:\\Users\\gonza\\OneDrive\\Imágenes\\Saved Pictures\\FOTOGRAFIAS 19-0");//Carpeta donde se almacenan las fotos
		File[] listaFotos = carpetaFotos.listFiles();//Lista de fotos 
		//File[] listaFotosAux //Lista de fotos auxiliar
		String[] nombreFotos = new String[listaFotos.length];//Arreglo que guarda los nombres de las fotos
		String extensionFoto = null,extensionAux=null;
		int contadorFotos=0;
		//Verificamos que los archivos contenidos sean jpg
		for (int i = 0; i < listaFotos.length; i++) {
			nombreFotos[i] = listaFotos[i].getName();//Se guarda el nombre de la foto
			System.out.println("Nombre: "+ FilenameUtils.getBaseName(nombreFotos[i]));
			;
			extensionFoto = FilenameUtils.getExtension(nombreFotos[i]);//Guardamos la extension de la foto
			if(extensionFoto.contains("jpg")) {//Si es una foto cambiamos el nombre
				contadorFotos++;//Contamos las fotos en la carpeta
				/*if(nombreFotos[i].contains(".")) {
				extensionAux = 	nombreFotos[i].substring(nombreFotos[i].lastIndexOf(".")+1);
				}*/
				
				File fotoRenombrada = new File("C:\\Users\\gonza\\OneDrive\\Escritorio\\renombrados"+"\\"+i+"."+extensionFoto);
				listaFotos[i].renameTo(fotoRenombrada);
			}
		}
		
	}

}
