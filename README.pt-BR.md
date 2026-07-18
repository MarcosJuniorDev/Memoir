# Memoir

[English](README.md)

Memoir é uma aplicação desktop em Java para organizar jogos e manter backups locais dos arquivos de save. A ideia central é simples: cadastrar o jogo, apontar onde ficam os saves, escolher onde os backups devem ser armazenados e deixar o Memoir cuidar da cópia, restauração e detecção de alterações.

O projeto usa Swing com FlatLaf para a interface, Maven para build, Gson para persistência em JSON e serviços próprios para backup, restauração, hash dos saves e monitoramento de processo.

## Recursos

- Cadastro de jogos com nome, executável, pasta de save e pasta de backup.
- Biblioteca visual com cards e capas dos jogos.
- Backup manual dos arquivos de save.
- Restauração do backup salvo para a pasta original de save.
- Detecção de mudanças por hash para evitar backups repetidos sem alteração.
- Registro da data do último backup.
- Comentários e avaliação por jogo.
- Auto backup opcional após o processo do jogo encerrar.
- Integração opcional com SteamGridDB para buscar capas automaticamente.
- Ícone na bandeja do sistema para manter o app acessível.

## Como funciona

Cada jogo cadastrado mantém as seguintes informações:

- nome do jogo;
- nome ou caminho do executável usado para detectar o processo;
- pasta onde o jogo salva os arquivos;
- pasta onde o Memoir deve guardar o backup;
- caminho da capa;
- hash do último backup;
- data do último backup;
- comentário, avaliação e estado do auto backup.

Quando um backup é executado, o Memoir calcula o hash da pasta de save. Se o hash atual for diferente do último hash salvo, ele copia a pasta de save para a pasta de backup configurada. Se nada mudou, o backup é ignorado.

Na restauração, o Memoir copia os arquivos da pasta de backup de volta para a pasta de save do jogo.

## Requisitos

- JDK 25 ou superior.
- Maven 3.9 ou superior.
- Ambiente desktop com suporte a Swing.

O `pom.xml` está configurado com o `release` do Java definido como 25. Se estiver usando outra versão do JDK, ajuste a propriedade `maven.compiler.release` antes de buildar.

## Build

Na raiz do projeto:

```bash
mvn clean package
```

O Maven gera um JAR executável com dependências em:

```text
target/memoir-1.0-jar-with-dependencies.jar
```

## Executando

Depois do build:

```bash
java -jar target/memoir-1.0-jar-with-dependencies.jar
```

Também é possível executar pela IDE usando a classe principal:

```text
main.App
```

## Configuração da SteamGridDB

A busca automática de capas usa a API da SteamGridDB. Para habilitar essa integração, crie o arquivo abaixo:

```text
src/main/resources/config.properties
```

Com o conteúdo:

```properties
steamgrid.api.key=SUA_CHAVE_AQUI
```

Esse arquivo está ignorado pelo Git e não deve ser versionado, porque contém uma chave privada de API.

Se esse arquivo não existir, o Memoir continua funcionando, mas a busca automática de capas pela SteamGridDB não terá uma chave válida para autenticação.

## Dados locais

Os dados dos jogos são salvos em um `gameData.json` dentro do diretório de dados do usuário, resolvido pela biblioteca `appdirs`.

No Linux, o caminho tende a seguir o padrão:

```text
~/.local/share/Memoir/gameData.json
```

As capas baixadas pela integração também são salvas no diretório de dados do usuário, dentro de uma pasta `covers`.

O arquivo `gameData.json` da raiz do projeto está ignorado pelo Git e não deve ser usado como fonte principal em produção.

## Estrutura do projeto

```text
src/main/App.java                         Ponto de entrada da aplicação
src/main/model/Game.java                  Modelo com os dados de cada jogo
src/main/service/BackupService.java       Backup, restauração e remoção de backups
src/main/service/GameRepository.java      Persistência local em JSON
src/main/service/HashFileSave.java        Cálculo de hash dos arquivos de save
src/main/service/AutoBackupService.java   Monitoramento do processo do jogo
src/main/service/SteamGridService.java    Integração com SteamGridDB
src/main/ui/                              Telas e componentes Swing
src/main/resources/                       Ícones, fontes e manifest
```

## Principais dependências

- Gson: serialização e leitura dos dados em JSON.
- Apache Commons IO: cópia e remoção de diretórios.
- MigLayout: organização dos layouts Swing.
- FlatLaf: look and feel moderno para a interface.
- Dorkbox SystemTray: integração com bandeja do sistema.
- AppDirs: resolução do diretório de dados do usuário por sistema operacional.

## Fluxo básico de uso

1. Abra o Memoir.
2. Clique em `ADD GAME`.
3. Informe o nome do jogo, executável, pasta de save e pasta de backup.
4. Se quiser, selecione ou busque uma capa.
5. Salve o cadastro.
6. Use `Backup` para criar uma cópia dos saves.
7. Use `Restore` para restaurar o backup quando necessário.
8. Ative `Auto backup` se quiser que o Memoir monitore o processo do jogo e faça backup ao encerrar.

## Notas de desenvolvimento

- A aplicação impede múltiplas instâncias usando um socket local na porta `63971`.
- O backup sanitiza nomes de pastas para evitar caracteres inválidos em Windows.
- O auto backup procura o executável informado no comando ou na linha de comando do processo, o que ajuda em cenários com Linux, Proton e Wine.
- O build usa `maven-assembly-plugin` para empacotar um JAR único com dependências.

## Licença

Memoir é licenciado sob a GNU General Public License v3.0. Veja [LICENSE](LICENSE) para mais detalhes.

As dependências de terceiros mantêm suas próprias licenças.

## Roadmap

- Backup em nuvem.
- Versionamento de saves.
