package br.com.casadocodigo.loja.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

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
	
	/*
	 * Método onde configuramos como o servlet deve configurar os arquivos na requisição.
	 * Neste objeto, usaremos o método setMultipartConfig que requer um objeto do tipo MultipartConfigElement.
	 * O MultipartConfigElement espera receber uma String que configure o arquivo. Não usaremos nenhuma 
	 * configuração para o arquivo, queremos receber este do jeito que vier. Passamos então uma String vazia.
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
