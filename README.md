# Documento do Desafio PicPay

**********************************Nome do projeto: Desafio PicPay Simplificado**********************************

**Versão: 1.0**

# Introdução

O documento visa apresentar Informações detalhadas referente a API feita para solucionar o desafio apresentado, com o objetivo de mitigar o uso de uma maneira errada e esclarecer pontos importante (como consumir a API e entender seus erros), fazendo com que todos que utilizarem ou atuem nesse projeto conheçam todas as suas funcionalidades.

---

## Apresentação do projeto

A API se trata de uma simulação de transação entre um cliente e um lojista. Porém é passado algumas regras para esse tipo de operação. Após seguir as regras citadas abaixo, a API também deve ser uma API RESTful.

### Link do Repositório do desafio:

- [**PicPay-desafio-simplificado**](https://github.com/PicPay/picpay-desafio-backend#materiais-%C3%BAteis)

### Ferramentas utilizadas:

- **********************************Linguagem: Kotlin**********************************
- **************************************Banco de dados: MySQL**************************************
- **********FrameWorks: Spring boot**********

  

---

### Requisitos mínimos para execução do projeto

- Ter o Docker instalado → [Download Docker](https://docs.docker.com/get-docker/)
- Ter o git instalado → [Download Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

# Install

- Clone o repositório do projeto

```bash
git clone https://github.com/Oiachuva5/PicPaySimplificado.git
```

- Crie uma rede especifica para o projeto
    
    

```bash
docker network create --driver=bridge --subnet=172.18.0.0/16 --gateway=172.18.0.1 bco_default
```

- Acesse o diretório da aplicação e execute o seguinte comando

```json
docker compose up --build -d
```

# Rotas

- ******************Customer******************
    - A rota para tratar os usuarios é **/customer**
    
    ### /customer/teste
    
    **Tipo: GET**
    
    ****************************Descrição****************************: É utilizado para testar se a API estava funcionando
    
    ******************************Corpo da requisição:****************************** Não é necessário 
    
    ---
    
    ### /customer
    
    **Tipo: GET**
    
    ******************Descrição:****************** Devolve uma lista de Customers
    
    ******************************Corpo da requisição:****************************** Não é necessário 
    
    ---
    
    ### /customer/{id}
    
    **Tipo: GET**
    
    **********************Descrição:********************** Ao informar um id especifico, ele retornará apenas aquele customer (caso exista)
    
    ******Corpo da requisição:****** Não é necessário 
    
    ---
    
    ### /customer/cadastrar
    
    **Tipo: POST**
    
    ********************Descrição:********************
    
    ******Corpo da requisição:******
    
    ```json
    //Exemplo
    {
    	"nomeCompleto": "Roberto Carlos", //Tipo String 
    	"registroGoverno":"41730760961", //Tipo String, porém deve conter entre 11 a 14 caracteres
    	"email":"robertoCarlos@gmail.com", //Tipo String
    	"senha":"robertao@007", //Tipo String
    	"saldo":"300.00" //Tipo Float
    }
    ```
    
    ---
    
    ### /customer/{id}
    
    **Tipo: PUT**
    
    ********************Descrição:********************
    
    ********************Corpo da requisição:******************** Ao informar um id especifico e inserir o corpo da requisição, a alteração será feito
    
    ```json
    //Exemplo
    {
    	"nomeCompleto": "Marcelo Pasteis", //Tipo String 
    	"registroGoverno":"41730760961935", //Tipo String, porém deve conter entre 11 a 14 caracteres
    	"email":"pastelDoMarcelao@gmail.com", //Tipo String
    	"senha":"pastel@7reais", //Tipo String
    	"saldo":"300.00" //Tipo Float
    }
    ```
    
    ---
    
    ### /customer/{id}
    
    **Tipo: DELETE**
    
    ********************Descrição:******************** Ao informar um id especifico, ele retornará apenas aquele customer (caso exista)
    
    ---
    
- **************Transference**************
    
    ### /transference
    
    **Tipo: GET**
    
    ****************************Descrição****************************: Devolve uma lista de Customers
    
    ******************************Corpo da requisição:****************************** Não é necessário
    
    ---
    
    ### /transference/{id}
    
    **Tipo: GET**
    
    **********************Descrição:********************** Ao informar um id especifico, ele retornará apenas aquela transference (caso exista)
    
    ******Corpo da requisição:****** Não é necessário
    
    ---
    
    ### /transference
    
    **Tipo: POST**
    
    ********************Descrição:********************
    
    ******Corpo da requisição:******
    
    ```json
    {
      "envia":"pastelDoMarcelao@gmail.com", //Tipo String
    	"recebe":"pastel@7reais", //Tipo String
    	"valor":"300.00" //Tipo Float
    }
    ```
    

---

## Mensagens de erros:

### Erros na Operação:

- OP-001 - Requisição Invalida
- OP-002 - Transação negada

### Erros referente ao Customer:

- CO-001 - Customer <ID> não existe.
- CO-002 - Não foi possível atualizar o customer <ID>.
- CO-003 - Não foi possível deletar o customer <ID>.
- CO-004 - Insira um CPF ou um CNPJ.

### Erros referente ao Transaction:

- TO-001 - Customer <ID> não existe.
- TO-002 - Customer <ID> não é cliente.
- TO-003 - Customer <ID> não é lojista.
- TO-004 - Saldo insuficiente para efetuar esta operação.
- TO-005 - Impossivel prosseguir com a operação. O Customer selecionado está com o status inativo.

## Banco de dados

### Mapa Logico:
![image](https://github.com/Oiachuva5/PicPaySimplificado/assets/115567476/9e2c55f7-592b-4eae-8d76-b3124b334434)
