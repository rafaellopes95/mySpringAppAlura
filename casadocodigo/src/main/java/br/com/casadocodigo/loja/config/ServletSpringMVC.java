package br.com.casadocodigo.loja.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Esta configuração determina quais classes serão a responsáveis por configurar a aplicação.
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppWebConfiguration.class, JPAConfiguration.class};
	}

	/*
	 * Esta configuração determina a partir de qual path da URL esta aplicação Spring vai considerar. Como neste caso é
	 * tudo abaixo do /, todos os request mappings da aplicação serão abaixo dele.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	/*
	 * Esta configuração é usada para configurar os possíveis filtros do Spring caso haja necessidade. Neste caso, apenas o de 
	 * Character encoding está sendo configurado.
	 */	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}

}
