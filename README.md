Sistema de backup de jogos para linux

Inicialmente backup local e futuramente talvez via api para algum drive em cloud

TODO:

Implementações para versão atual:

- Botão delete do cadastro do game
- verificar se o usuário quer deletar o backup do save junto com o cadastro do game
- botão do restore backup
- edição de caminho na tela de informações do game
- sistema de estrelas para nota do jogo
- opção de comentário sobre o jogo(talvez tenho que refatorar o modal Game)
- Fazer um icone para o Aplicativo
----------
Implementações pro futuro: 

- Conectar a uma api de algum drive para backup em nuvem
- versionamento de save, caso o usuário queira vai ser salvo vários saves
- fazer o memoir buscar pelo exe nos processos e fazer backup automático após o processo encerrar.
- comprimir o arquivo para zip?
- verificar o layout dos pop up das janela, muito feio e não combina com o tema da interface
----
BUGS PARA CORREÇÃO:

- Nome muito longo invadindo espaço dos outros card no grid da MainScreen 
- Se eu deletar a pasta de save do backup ele continua com a data do ultimo backup atualizada e se eu der backup now ele
e vai dizer que o backup esta atualizado pois ele vai comparar o hash do jSON com o da save do game.