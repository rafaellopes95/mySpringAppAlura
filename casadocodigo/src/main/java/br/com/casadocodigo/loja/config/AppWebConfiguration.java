package br.com.casadocodigo.loja.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;

@EnableWebMvc
@ComponentScan(basePackageClasses = {HomeController.class, ProdutoDAO.class, FileSaver.class})
public class AppWebConfiguration implements WebMvcConfigurer {

	/*
	 * Este bean resolve o nome das views que o controller vai devolver.
	 * Por exemplo, quando um controller fizer return de uma String "home", automaticamente o viewResolver vai
	 * adicionar o prefixo (caminho da view) e o sufixo (extenção do arquivo), por isso não há a necessidade
	 * de colocar "/home.jsp" ou então o caminho completo, já que o resolver se encarrega disso.
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	/* 
	 * Este bean serve para configurar as mensagens de erro que serão exibidas no front end para o caso de erros de validação (BindingResult e etc)
	 * através da taglib form:errors. Há um arquivo de propriedades no WEB-INF com as mensagens em questão.
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		
		return messageSource;
	}
	
	/*
	 * Este bean serve para configurar conversão do tipo de dados na aplicação, inclusive daqueles que serão feito
	 * binding, como é o caso de datas. No case de datas, as que tiverem a notação @DateTimeFormat utilizarão o pattern
	 * configurado aqui como o default.
	 */
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
		formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		formatterRegistrar.registerFormatters(conversionService);
		
		return conversionService;	
	}
	
	/*
	 * Este bean serve para configurar um resolver para receber requisições com multipart (arquivos e dados).
	 * se refere a um resolvedor de dados multimídia. Quando temos texto e arquivos por exemplo. Os arquivos 
	 * podem ser: imagem, PDF e outros. Este objeto é que identifica cada um dos recursos enviados e nos fornece 
	 * uma forma mais simples de manipulá-los.
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	/*
	 * Este método implementado da interface WebMvcConfigurer serve para que seja possível configurar o caminho
	 * onde os recursos de CSS, JavaScript e demais para a pasta resources.
	 */
	public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
		resourceHandlerRegistry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
}
