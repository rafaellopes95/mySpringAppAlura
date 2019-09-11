package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}
	
	public List<Produto> listar() {
		return manager.createQuery("select p from Produto p", Produto.class).getResultList();
	}

	/*
	 * Ao utilizar o método find do entity manager, até funcionaria para trazer um produto do banco de dados, mas o problema
	 * é que como os preços dos livros são uma tabela à parte (mesmo embutida na classe Produto), por padrão o Hibernate não
	 * vai trazer informação dos preços por default, já que o find traz apenas o que for da classe Produto (causando um 
	 * LazyInitializationException). Sendo assim, a forma de contornar este problema é justamente criando uma query onde há
	 * um join do produto com seus respectivos preços (join fetch).
	 * 
	 * Explicação do Alura:
	 * Este erro de LazyInitializationException indica que ao carregar o produto, os preços não foram carregados juntos. 
	 * Ou seja, tentamos exibir os preços sem carregá-los do banco de dados. Isto acontece porque nosso ProdutoDAO no 
	 * método find só busca o produto, sem se relacionar com seus preços.
	 * 
	 */
	public Produto find(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos precos where p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}
}
