1) Pré-requisitos - Para executar este projeto, você precisará ter instalado:

- Android Studio (versão mais recente recomendada).

- Docker Desktop.

2) Instruções de Execução Siga estes passos para configurar e rodar a aplicação:

- Passo 1: Iniciar o Banco de Dados MongoDB

O projeto utiliza uma instância local do MongoDB rodando em um container Docker. Para iniciá-la, abra seu terminal (PowerShell, CMD, ou Terminal do Mac/Linux) e execute o seguinte comando:

Bash

docker run --name mongo-saude-mental -p 27017:27017 -d mongo Este comando irá baixar a imagem do MongoDB (se ainda não tiver) e iniciar um container chamado mongo-saude-mental. A porta 27017 ficará disponível para a aplicação se conectar.

ATENÇÃO, CASO HAJA UM ERRO DE EXECUÇÃO, VERIFIQUE O ARQUIVO "MongoDbManager" E MUDE O ENDEREÇO DE ACESSO DE "10.0.2.2:27017" para "SEU_ENDEREÇO_DE_IP:27017" QUE IRÁ FUNCIONAR A ATRAVÉS DO CONTEINER DOCKER

- Passo 2: Abrir e Executar o Projeto no Android Studio

Abra o Android Studio.

Clique em "Open" e selecione a pasta deste projeto.

Aguarde o Gradle sincronizar todas as dependências.

Selecione um emulador ou conecte um dispositivo Android.

Clique no botão "Run 'app'" (ícone de play verde ▶️).

Pronto! Na primeira vez que o aplicativo for iniciado, ele se conectará ao container Docker, criará o banco de dados e populará a coleção de perguntas automaticamente.

Como a Inicialização dos Dados Funciona Ao iniciar pela primeira vez, a classe MyApplication chama um SetupService. Este serviço verifica se a coleção de perguntas no MongoDB está vazia. Se estiver, ele a preenche com as perguntas padrão necessárias para o funcionamento do questionário. Em execuções futuras, o serviço detectará que os dados já existem e não fará nada.
