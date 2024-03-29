#!/bin/bash

PURPLE='0;35'
NC='\033[0m'
VERSAO=11

echo  "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) Olá Usuario, serei seu assistente para a isntalação do aplicativo na sua maquina;"
echo  "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)  Verificando aqui se você possui o Java instalado...;"
sleep 2

java -version
if [ $? -eq 0 ]
        then
                echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) : Você já tem o java instalado!!!"
        else
                echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)  Opa! Não identifiquei nenhuma versão do Java instalado, mas sem problemas, irei resolver isso agora!"
                echo "$(tput setaf 10)[Assistentente ErrorEagle]:$(tput setaf 7)  Confirme para mim se realmente deseja instalar o Java (S/N)?"
read inst
        if [ \"$inst\" == \"S\" ]
                then
                        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)  Ok! Você escolheu instalar o Java ;D"
                        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)  Adicionando o repositório!"
                        sleep 2
                        sudo add-apt-repository ppa:webupd8team/java -y
                        clear
                        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)  Atualizando! Quase lá."
                        sleep 2
                        sudo apt update && sudo apt upgrade -y
                        clear

                        if [ $VERSAO -eq 17 ]
                                then
                                        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) Preparando para instalar a versão 11 do Java. Confirme a instalação quando solicitado ;D"
                                        sudo apt install default-jre ; apt install openjdk-17-jre-headless; -y
 clear
                                        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) Java instalado com sucesso!"
                                fi
                else
                echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)  Você optou por não instalar o Java por enquanto, até a próxima então!"
        fi
fi

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Atualize os pacotes do sistema:  "
                        sleep 2
                        sudo apt update && sudo apt upgrade –y
clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Instalando o docker na maquina:  "
                        sleep 2
			sudo apt install docker.io
clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Verificando se o Docker foi instalado corretamente:  "
                        sleep 2
docker --version

clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Ativando o docker para que ele fique disponivel para uso:  "
                        sleep 2
sudo systemctl start docker
clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Habilitando o serviço do docker:  "
                        sleep 2
sudo systemctl enable docker
clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Baixando a image do docker:  "
                        sleep 2
sudo docker pull mysql:8.0.32
clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Confirmando o download da image:  "
                        sleep 2
sudo docker images
clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)criando o container e configurando o mysql dentro do container:  "
                        sleep 2
sudo docker run -d -p 3306:3306 --name ContainerSQL -e "MYSQL_DATABASE=erroreagle" -e "MYSQL_ROOT_PASSWORD=urubu100" mysql:8.0.32
clear

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Acessando Container:  "
                        sleep 2
sudo docker start ContainerSQL

sudo docker exec -i ContainerSQL mysql -u root -p'urubu100' <<EOF
use erroreagle;
CREATE TABLE IF NOT EXISTS Totem (id INT primary key NOT NULL AUTO_INCREMENT,
hostName VARCHAR(100) NOT NULL UNIQUE,
fkEmpresa INT NOT NULL);

CREATE TABLE IF NOT EXISTS Componente (
id INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS Configuracao (
fkTotem INT NOT NULL,
fkComponente INT NOT NULL,
capacidade DOUBLE NOT NULL,
unidadeMedida VARCHAR(10) NOT NULL,
PRIMARY KEY (fkTotem, fkComponente),
CONSTRAINT fk_Totem_has_TipoComponente_Totem1
FOREIGN KEY (fkTotem)
REFERENCES Totem (id),
CONSTRAINT fk_Totem_has_TipoComponente_TipoComponente1
FOREIGN KEY (fkComponente)
REFERENCES Componente (id));


CREATE TABLE IF NOT EXISTS Medida (
id INT NOT NULL AUTO_INCREMENT,
percentual DOUBLE NOT NULL,
dataHora DATETIME NOT NULL DEFAULT now(),
fkTotem INT NOT NULL,
fkComponente INT NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_Medidas_Configuracao1
FOREIGN KEY (fkTotem , fkComponente)
REFERENCES Configuracao (fkTotem , fkComponente));

CREATE TABLE IF NOT EXISTS TipoAlerta (
id INT NOT NULL AUTO_INCREMENT,
Criticidade VARCHAR(50) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS Limite (
fkComponente INT NOT NULL,
fkTipoAlerta INT NOT NULL,
maximo DOUBLE NOT NULL,
minimo DOUBLE NOT NULL,
PRIMARY KEY (fkComponente, fkTipoAlerta),
CONSTRAINT fk_TipoComponente_has_tiposAlertas_TipoComponente1
FOREIGN KEY (fkComponente)
REFERENCES Componente (id),
CONSTRAINT fk_TipoComponente_has_tiposAlertas_tiposAlertas1
FOREIGN KEY (fkTipoAlerta)
REFERENCES TipoAlerta (id));
EOF

echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) : Sua maquina já está preparada, agora vamos baixar o aplicativo da ErrorEagle"

        sleep 2
        wget https://raw.githubusercontent.com/ErrorEagle/ErrorEagle-jar/main/tela-swing/target/ErrorEagle.jar

        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) Concluindo Instalação..."
        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) Deseja executar o programa da ErrorEagle (s/n)"
    read get4

 if [ "$get4" == "s" ]; then
            echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)Executando aplicação"
        sleep 3
        chmod +x ErrorEagle.jar
        java -jar /home/ubuntu/Desktop/ErrorEagle.jar

        echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7)executando serviço"
        sleep 3
        exit
    else
            echo "$(tput setaf 10)[Assistente ErrorEagle]:$(tput setaf 7) Encerrando sistema!"
        sleep 3
        exit

    fi
