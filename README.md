### Pontos importantes

#### @Transactional
- Deve ser referenciado em um método público e acionado por outra classe. Caso seja acionado na mesma classe, o spring ignorará a anotação.
- Todo o processo dentro do método, partir da primeira iteração com a base de dados, será dentro de uma transação.
- A ausência dessa anotação, ocasionará a criação de uma transação a cada iteração com o banco de dados dentro do método. Exemplo: findName e updateName, criará 2 transações e mais idas a base.
- Garantia do processo de rollback, caso no mesmo método faça mais de um insert (no caso vi cascade) e ocorra uma exceção em seguida (por alguma regra de negócio por exemplo).
- O ideal que as transações sejam curtas, então cuidado com o uso e verifique o contexto, pois todo o processo do método estará dentro de uma transação.
- Faça uso do parâmetro readOnly = true, para consulta apenas.
- A situações onde seu uso faz sentido na interface de repositorio,  mas posso deixar o serviço chamador também anotado, o spring fará propagação e não criará outra transação.
Exemplo:
```
@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends BatchRepository<Author, Long> {
    @Query("Select a From Author a where a.name = ?1")
    Author fetchByName(String name);

    @Transactional
    @Modifying
    @Query("Delete from Author a where a.genre <> ?1")
    int deleteByNeGenre(String genre);
}
```
- Pense na modelagem do seu sistema, caso precise buscar uma informação no banco de dados, aplicar alguma regra de negócio e em seguida iteragir com a base novamente, pode manter a transação aberta por muito tempo, impactando no desempenho da aplicação. Talves faz sentido quebrar em mais métodos ou não utilizar o @Transactional. obs: sem a anotação, a cada iteração o spring criará uma transação.
