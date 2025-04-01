package ca.usherbrooke.trainingapi;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Classe permettant de configurer le déploiement de l'application dans un conteneur servlet.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configure l'application.
	 *
	 * @param application le builder de l'application
	 * @return le builder configuré
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TrainingApiApplication.class);
	}

}
