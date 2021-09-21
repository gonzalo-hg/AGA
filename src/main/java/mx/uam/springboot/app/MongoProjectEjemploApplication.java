package mx.uam.springboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.plugins.PluginFolder;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

@SpringBootApplication

public class MongoProjectEjemploApplication {

	public static void main(String[] args) throws KettleException  {
		SpringApplication.run(MongoProjectEjemploApplication.class, args);
		//StepPluginType.getInstance().getPluginFolders()
		//		.add(new PluginFolder("/plugins", false, true));
		StepPluginType.getInstance().getPluginFolders()
				.add(new PluginFolder("classes", false, true));
		StepPluginType.getInstance().getPluginFolders()
				.add(new PluginFolder("libswt", false, true));
		StepPluginType.getInstance().getPluginFolders()
				.add(new PluginFolder("pentaho", false, true));
		KettleEnvironment.init();
		TransMeta transMeta = new TransMeta("transform2.ktr");
		Trans trans = new Trans(transMeta);
		trans.setLogLevel(LogLevel.ERROR);
		trans.execute(null);
		trans.waitUntilFinished();

		if (trans.getErrors() > 0) {
			System.out.println("Ocurrio un error");
		}
		
	}

}
