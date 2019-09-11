package br.com.casadocodigo.loja.models;

import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Produto {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	private String descricao;
	private int paginas;
	private String sumarioPath;
	
	/*
	 * Esta annotation irá criar uma tabela para os precos e fazer o relacionamento com ela automaticamente sem precisar do OneToMany,
	 * já que não faz sentido manter uma tabela de preços com DAO e tudo mais já que só fará parte do produto e será manipulados ao mesmo
	 * que outras informações do produto em si. A anotation @Embeddable é necessária no escopo de classe da classe Preço.
	 * 
	 * O fetchType EAGER foi necessário aqui pois como os preços fazem parte do Produto e a query de listagem (listar da ProdutoDAO) busca todos os atributos, se
	 * o typo de fetch for LAZY ocorrerá uma exception: LazyInitializationException: failed to lazily initialize a collection of role: 
	 * br.com.casadocodigo.loja.models.Produto.precos, could not initialize proxy - no Session
	*/
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Preco> precos;
	
	/*
	 * Annotation utilizada para converter data para um formato específico.
	 */
	@DateTimeFormat
	private Calendar dataLancamento;
	
	public Produto() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getPaginas() {
		return paginas;
	}
	
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public List<Preco> getPrecos() {
		return precos;
	}

	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getSumarioPath() {
		return sumarioPath;
	}

	public void setSumarioPath(String sumarioPath) {
		this.sumarioPath = sumarioPath;
	}

	/*
	 * HashCode e Equals são necessários para que objetos da classe CarrinhoItem possam ser devidamente comparados e encontrados 
	 * no LinkedHashMap da classe CarrinhoCompras.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Produto [id=" + id + ", " + (titulo != null ? "titulo=" + titulo + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "") + "paginas=" + paginas + ", "
				+ (sumarioPath != null ? "sumarioPath=" + sumarioPath + ", " : "")
				+ (precos != null ? "precos=" + precos + ", " : "")
				+ (dataLancamento != null ? "dataLancamento=" + dataLancamento : "") + "]";
	}
}
