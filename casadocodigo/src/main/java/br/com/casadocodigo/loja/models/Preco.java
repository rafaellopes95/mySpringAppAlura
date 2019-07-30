package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

/* @Embeddable permite o relacionamento com algum atributo que esteja anotado com @ElementCollection, que é o caso da classe Produto.
 * 
 */
@Embeddable
public class Preco {

	private BigDecimal valor;
	private TipoPreco tipo;
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public TipoPreco getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoPreco tipo) {
		this.tipo = tipo;
	}
}
