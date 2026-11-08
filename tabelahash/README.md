# Tabela Hash de alunos

## Requisitos analisados no PPTX

O arquivo `Projeto - Tabela Hash 2a feira.pptx` determina:

- Armazenar matrícula inteira (chave), nome e idade de cada aluno.
- Usar uma tabela com **11 posições** e função `h(chave) = chave mod 11`.
- Tratar colisões por **encadeamento**, inserindo novos alunos no **final da lista**.
- Não cadastrar matrículas duplicadas.
- Calcular o endereço antes de inserir, buscar ou remover.
- Oferecer menu para inserir, buscar, remover, exibir a tabela e encerrar.
- Não utilizar bibliotecas prontas de implementação de tabela hash.

Os slides também exigem equipe de 5 a 7 integrantes, identificação dos membros na entrega e postagem no Blackboard antes da apresentação de 16/11/2026.

## Implementação

- `Aluno.java`: matrícula, nome e idade imutáveis; a chave não pode mudar depois da inserção.
- `TabelaHash.java`: vetor de 11 posições com nós de listas simplesmente encadeadas implementados manualmente, sem `HashMap`, `Hashtable` ou listas prontas.
- `Main.java`: menu de console; nomes podem conter espaços. Entradas numéricas inválidas são solicitadas novamente.
- `TabelaHashTeste.java`: testes com `main`, verificações explícitas e `AssertionError`, sem JUnit, Maven ou dependências externas. Não é necessário ativar `-ea`.

A função usa `Math.floorMod(matricula, 11)` para representar o módulo entre 0 e 10, inclusive para inteiros negativos. Para matrículas não negativas, o resultado é igual a `matricula % 11`. A tabela mantém as 11 posições mesmo quando há mais de 11 alunos.

### Operações

- `inserir(Aluno)`: retorna `true` ao cadastrar e `false` se a matrícula já existe; o cadastro anterior não é alterado.
- `buscar(int)`: retorna o aluno ou `null` se não existir.
- `remover(int)`: retorna `true` ao remover e `false` se a matrícula não existir.
- `toString()`: exibe todas as posições, incluindo as vazias, e as listas na ordem de inserção.

Todas as operações por matrícula percorrem apenas a lista do endereço calculado. O custo é O(k), sendo k o número de alunos nessa posição; no pior caso, O(n).

## Compilar e executar

Requer JDK 8 ou superior (`java` e `javac` disponíveis no PATH). Na raiz do projeto:

```sh
mkdir -p build
javac -encoding UTF-8 -d build tabelahash/*.java
java -cp build tabelahash.Main
```

## Executar os testes em Java puro

Após compilar:

```sh
java -cp build tabelahash.TabelaHashTeste
```

Os 11 cenários cobrem tabela vazia, preservação dos dados, colisões e ordem de inserção, duplicatas no início/meio/fim, remoções no início/meio/fim, reinserção, matrícula ausente em lista ocupada, 66 alunos distribuídos pelas posições com remoções alternadas e matrículas zero, negativas e nos limites de `int`.

Se uma verificação falhar, o programa informa o cenário e termina com `AssertionError` e código de saída diferente de zero.
