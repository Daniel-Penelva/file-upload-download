# Classes `Deflater` e `ByteArrayOutputStream`

As classes `Deflater` e `ByteArrayOutputStream` são classes importantes na plataforma Java, especialmente quando se trata de compressão de dados.

## 1. **Deflater**:
A classe `Deflater` faz parte do pacote `java.util.zip` e é usada para realizar **compressão de dados** em formato zlib. Ela implementa um algoritmo de compressão sem perdas, que reduz o tamanho dos dados através da eliminação de redundâncias. Algumas características principais:

- **Compressão de Dados**: A principal função da classe `Deflater` é comprimir dados. Ela aceita uma matriz de bytes de entrada e produz uma saída compactada.
- **Configurações de Compressão**: Você pode configurar diferentes níveis de compressão usando as constantes fornecidas pela classe `Deflater`, como `BEST_SPEED`, `BEST_COMPRESSION` e `DEFAULT_COMPRESSION`.
- **Fluxo de Compressão**: Ela opera em um modelo de fluxo, o que significa que você alimenta os dados para compressão e obtém os dados compactados conforme necessário, sem a necessidade de carregar todos os dados na memória de uma vez.

## 2. **Inflater**:

A classe `Inflater` faz parte do pacote `java.util.zip` em Java e é usada para **descomprimir dados** que foram previamente comprimidos usando o algoritmo de compressão zlib. Algumas características principais:

1. **Descompressão de Dados**:
   - `Inflater` é utilizada para descomprimir dados que foram previamente comprimidos usando o algoritmo de compressão zlib.
   - Ela implementa o algoritmo de descompressão necessário para desfazer a compressão realizada pelo `Deflater`, que é sua contraparte na compressão de dados.

2. **Funcionamento**:
   - Você fornece os dados comprimidos para a instância de `Inflater` através do método `setInput(byte[] input)`.
   - Em seguida, você chama o método `inflate(byte[] output)` repetidamente para extrair blocos de dados descomprimidos.
   - Cada chamada para `inflate` processa uma parte dos dados comprimidos de entrada e produz uma parte dos dados descomprimidos de saída.
   - Você continua chamando `inflate` até que todos os dados comprimidos tenham sido processados e os dados descomprimidos estejam totalmente disponíveis.

3. **Tratamento de Dados**:
   - `Inflater` lida com dados comprimidos em formato zlib ou formato gzip. O formato é automaticamente detectado com base nos primeiros bytes dos dados de entrada.
   - Ela pode lidar com uma variedade de opções de formato, como dados com ou sem cabeçalhos ou com ou sem verificação de soma de verificação (checksum).

4. **Controle de Fluxo**:
   - `Inflater` opera em um modelo de fluxo, o que significa que você pode descomprimir grandes quantidades de dados sem precisar armazenar todos eles na memória ao mesmo tempo.
   - Isso é útil para descomprimir arquivos grandes ou transmitir dados pela rede em tempo real.


## 3. **ByteArrayOutputStream**:
A classe `ByteArrayOutputStream` faz parte do pacote `java.io` e é usada para escrever dados em um array de bytes. Principais características incluem:

- **Armazenamento de Dados em Memória**: A classe `ByteArrayOutputStream` fornece um buffer de bytes em memória, permitindo que os dados sejam gravados nele.
- **Flexibilidade**: Ela fornece métodos simples para escrever diferentes tipos de dados, como bytes, inteiros, etc., no array de bytes.
- **Facilidade de Uso**: Por ser uma subclasse de `OutputStream`, ela pode ser usada em conjunto com outras classes de fluxo de Java, como `FileOutputStream`, `DataOutputStream`, etc.

Essas classes são úteis em diversas situações, como compactação de dados para transmissão pela rede, armazenamento de dados em memória, entre outras.

# Classe ImageUtils

```java
package com.api.fileuploaddownload.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }
    
}
```

O código têm duas funções estáticas em uma classe chamada `ImageUtils`: `compressImage` e `decompressImage`. Essas funções são projetadas para comprimir e descomprimir dados de imagem usando a classe `Deflater` para compressão e `Inflater` para descompressão.

Aqui está uma explicação detalhada de cada função:

1. **`compressImage`**:
   - Recebe um array de bytes `data` que representa os dados de uma imagem a ser comprimida.
   - Cria um objeto `Deflater` para realizar a compressão.
   - Define o nível de compressão como o melhor possível (`BEST_COMPRESSION`).
   - Configura os dados de entrada para o `Deflater`.
   - Configura o `Deflater` para terminar a compressão.
   - Cria um `ByteArrayOutputStream` para armazenar os dados comprimidos.
   - Enquanto o `Deflater` não termina a compressão, continua a comprimir os dados de entrada e escreve os dados comprimidos no `ByteArrayOutputStream`.
   - Fecha o `ByteArrayOutputStream`.
   - Retorna os dados comprimidos como um array de bytes.

```java
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }
```

2. **`decompressImage`**:
   - Recebe um array de bytes `data` que representa os dados de uma imagem comprimida.
   - Cria um objeto `Inflater` para realizar a descompressão.
   - Configura os dados de entrada para o `Inflater`.
   - Cria um `ByteArrayOutputStream` para armazenar os dados descomprimidos.
   - Enquanto o `Inflater` não termina de descomprimir os dados, continua a descomprimir os dados de entrada e escreve os dados descomprimidos no `ByteArrayOutputStream`.
   - Fecha o `ByteArrayOutputStream`.
   - Retorna os dados descomprimidos como um array de bytes.

```java
    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }
```

Ambas as funções utilizam um buffer temporário de tamanho `4*1024` (4KB) para armazenar os dados durante o processo de compressão e descompressão.

É importante notar que, no caso de descompressão, se houver algum erro durante o processo (por exemplo, uma exceção é lançada), o código simplesmente ignora o erro e retorna um array de bytes vazio. Vale ressaltar que ssso pode ser melhorado para lidar com exceções de forma mais adequada, dependendo dos requisitos do sistema.

---

# Interface `MultipartFile`

A interface `MultipartFile` faz parte do framework Spring, especificamente do módulo Spring Web, e é usada para representar um arquivo enviado em uma solicitação HTTP multipart. É frequentemente usada em aplicativos da web para receber arquivos enviados por usuários por meio de formulários da web.

Algumas características importantes da classe `MultipartFile`:

1. **Representação de Arquivos**:
   - `MultipartFile` é uma interface que representa um arquivo enviado como parte de uma solicitação multipart.
   - Ela encapsula informações sobre o arquivo, como o nome do arquivo, o tipo de conteúdo (MIME type), tamanho do arquivo e os próprios dados do arquivo.

2. **Recebimento de Arquivos em Aplicações Web**:
   - É comumente usado em métodos de controladores Spring para receber arquivos enviados em solicitações HTTP multipart.
   - No contexto de um aplicativo Spring MVC ou Spring Boot, você pode usá-lo como um parâmetro de método em um controlador para receber o arquivo enviado pelo cliente.

3. **Métodos Principais**:
   - `getOriginalFilename()`: Retorna o nome original do arquivo como fornecido pelo cliente.
   - `getContentType()`: Retorna o tipo de conteúdo (MIME type) do arquivo.
   - `getSize()`: Retorna o tamanho do arquivo em bytes.
   - `getBytes()`: Retorna um array de bytes que contém os dados do arquivo.
   - `transferTo(File dest)`: Salva o arquivo em disco, escrevendo-o no arquivo especificado por `dest`.

4. **Tratamento de Dados de Arquivo**:
   - A classe `MultipartFile` permite que você obtenha os dados do arquivo como um array de bytes (`getBytes()`), o que facilita o processamento e armazenamento do arquivo em diferentes formatos e locais.

5. **Validação e Processamento**:
   - Você pode validar os arquivos recebidos usando métodos fornecidos pela classe `MultipartFile`, como verificar o tamanho do arquivo ou o tipo de conteúdo.
   - Após a validação, você pode processar o arquivo conforme necessário, como salvar no sistema de arquivos, armazenar em um banco de dados ou processá-lo de alguma outra forma.

Em resumo, `MultipartFile` é uma classe fundamental no desenvolvimento de aplicativos da web Spring para receber arquivos enviados pelos clientes e trabalhar com eles de forma eficaz e segura.

# Classe StorageService

Este é uma classe de serviço chamada `StorageService` que esta sendo usada para manipular operações de armazenamento de imagens. 

Esta classe de serviço lida com operações de upload e download de imagens, tanto para armazenamento em um banco de dados quanto no sistema de arquivos do servidor.

```java
package com.api.fileuploaddownload.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.fileuploaddownload.entity.ImageData;
import com.api.fileuploaddownload.repository.StorageRepository;
import com.api.fileuploaddownload.util.ImageUtils;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    // Caminho onde vai fazer o download do arquivo
    private final String FOLDER_PATH = "C:/Projetos Spring Boot no VSCode/API-upload-download/MyFiles";

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }


    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }


    public String uploadImageToFileSystem(MultipartFile file) throws IOException {

        String filePath=FOLDER_PATH+file.getOriginalFilename();

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName)throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}
```

Explicação do que este script faz:


1. **Annotation `@Service`**:
   - Indica que esta classe é um componente de serviço gerenciado pelo Spring. Isso significa que pode ser injetada em outras classes que dependem dela.

2. **Injeção de Dependência `@Autowired`**:
   - O repositório `StorageRepository` é injetado nesta classe. A `StorageRepository` é uma interface que estende `JpaRepository` ou outra interface de repositório do Spring, e é usada para interagir com o banco de dados para operações de armazenamento relacionadas às imagens.

3. **Váriavel de valor fixo `FOLDER_PATH`**
   - Este define uma variável privada chamada `FOLDER_PATH` com um valor de string fixo que representa o caminho do diretório no sistema de arquivos onde os arquivos serão salvos durante o processo de upload. Neste caso, o caminho do diretório é `C:/Projetos Spring Boot no VSCode/API-upload-download/MyFiles`. Isso é usado no método `uploadImageToFileSystem` para definir o caminho do arquivo no sistema de arquivos.

3. **Método `uploadImage`**:
   - Recebe um objeto `MultipartFile` chamado `file`, que representa o arquivo de imagem a ser enviado.
   - Cria um objeto `ImageData` usando um padrão de construtor de tipo de construção (`builder`). Este objeto `ImageData` representa os metadados da imagem, incluindo seu nome, tipo de conteúdo e os próprios dados da imagem (que são comprimidos usando `ImageUtils.compressImage(file.getBytes())`).
   - Salva o objeto `ImageData` no repositório de armazenamento usando o método `save` do `storageRepository`.
   - Retorna uma mensagem indicando que o arquivo foi enviado com sucesso, incluindo o nome original do arquivo.

    ```java
    public String uploadImage(MultipartFile file) throws IOException {

            ImageData imageData = storageRepository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes())).build());
            
            if (imageData != null) {
                return "file uploaded successfully : " + file.getOriginalFilename();
            }
            return null;
        }
    ```

   - **Propósito:** Este método aceita um arquivo de imagem, o comprime, salva-o em um repositório de armazenamento e retorna uma mensagem indicando o status do upload.
   - **Funcionamento:** 
       - Salva os dados da imagem no repositório `storageRepository`.
       - Comprime a imagem utilizando `ImageUtils.compressImage()`.
       - Retorna uma mensagem de sucesso se a operação for bem-sucedida.

4. **Método `downloadImage`**:
   - Recebe uma string `fileName`, que representa o nome do arquivo da imagem que se deseja baixar.
   - Utiliza o repositório `storageRepository` para buscar os dados da imagem no banco de dados, usando o método `findByName`. O retorno é encapsulado em um `Optional` para lidar com casos em que o arquivo não existe no banco de dados.
   - Descomprime os dados da imagem recuperados do banco de dados usando `ImageUtils.decompressImage`.
   - Retorna os dados da imagem descomprimidos como um array de bytes.

    ```java
    public byte[] downloadImage(String fileName){
            Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
            byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
            return images;
        }
    ```

   - **Propósito:** Responsável por fazer o download de uma imagem armazenada no banco de dados e retornar os dados da imagem como um array de bytes.
   - **Funcionamento:** 
       - Busca os dados da imagem no repositório `storageRepository` com base no nome do arquivo.
       - Descomprime os dados da imagem utilizando `ImageUtils.decompressImage()`.
       - Retorna os dados da imagem como um array de bytes.

5. **Método `uploadImageToFileSystem`**
   - Este método é usada para salvar um arquivo no sistema de arquivos do servidor. A função recebe um parâmetro `MultipartFile` chamado `file`, que representa o arquivo a ser salvo.

   - A função começa criando uma variável `filePath` que é a concatenação do caminho do diretório onde o arquivo será salvo (`FOLDER_PATH`) com o nome original do arquivo (`file.getOriginalFilename()`).

   - Em seguida, a função cria um objeto `FileData` chamado `fileData` usando o método `fileDataRepository.save()`. O objeto `FileData` é usado para armazenar informações sobre o arquivo, como o nome, o tipo e o caminho do arquivo. O objeto `FileData` é criado usando o método `builder()` do `FileData` e é populado com as informações do arquivo, como o nome original do arquivo (`file.getOriginalFilename()`), o tipo de conteúdo do arquivo (`file.getContentType()`) e o caminho do arquivo (`filePath`).

   - Em seguida, a função transfere o arquivo para o sistema de arquivos usando o método `file.transferTo(new File(filePath))`. Esse método transfere o conteúdo do arquivo para o arquivo no sistema de arquivos especificado pelo caminho `filePath`.

   - Por fim, a função verifica se o objeto `fileData` não é nulo e retorna uma mensagem de sucesso se o arquivo foi salvo com sucesso. Se o objeto `fileData` for nulo, a função retorna `null`.

   - Em resumo, o script `uploadImageToFileSystem` é uma função Java que é usada para salvar um arquivo no sistema de arquivos do servidor. A função recebe um parâmetro `MultipartFile` chamado `file`, que representa o arquivo a ser salvo. A função cria um objeto `FileData` usando o método `fileDataRepository.save()` para armazenar informações sobre o arquivo e transfere o arquivo para o sistema de arquivos usando o método `file.transferTo(new File(filePath))`. A função retorna uma mensagem de sucesso se o arquivo foi salvo com sucesso e retorna `null` se o arquivo não pôde ser salvo.

    ```java
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {

            String filePath=FOLDER_PATH+file.getOriginalFilename();

            FileData fileData=fileDataRepository.save(FileData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath).build());

            file.transferTo(new File(filePath));

            if (fileData != null) {
                return "file uploaded successfully : " + filePath;
            }
            return null;
        }
    ```
   - **Propósito:** Salvar um arquivo no sistema de arquivos do servidor.
   - **Funcionamento:** 
       - Define o caminho do arquivo no sistema de arquivos.
       - Salva os dados do arquivo no repositório `fileDataRepository`.
       - Transfere o arquivo para o sistema de arquivos.
       - Retorna uma mensagem de sucesso se a operação for bem-sucedida.

6. **Método `downloadImageFromFileSystem`**

   - Este método é usada para ler um arquivo do sistema de arquivos do servidor e retornar seu conteúdo como um array de bytes. A função recebe um parâmetro `String` chamado `fileName`, que representa o nome do arquivo a ser lido.

   - A função começa procurando o arquivo no banco de dados usando o método `fileDataRepository.findByName(fileName)`. O método `findByName` retorna um `Optional<FileData>` que contém o objeto `FileData` associado ao arquivo com o nome especificado.

   - Em seguida, a função extrai o caminho do arquivo do objeto `FileData` usando o método `fileData.get().getFilePath()`.

   - Em seguida, a função lê o conteúdo do arquivo usando o método `Files.readAllBytes(new File(filePath).toPath())`. Esse método lê o conteúdo do arquivo no sistema de arquivos especificado pelo caminho `filePath` e retorna um array de bytes que representa o conteúdo do arquivo.

   - Por fim, a função retorna o array de bytes que representa o conteúdo do arquivo.

   - Em resumo, o script `downloadImageFromFileSystem` é uma função Java que é usada para ler um arquivo do sistema de arquivos do servidor e retornar seu conteúdo como um array de bytes. A função recebe um parâmetro `String` chamado `fileName`, que representa o nome do arquivo a ser lido. A função procura o arquivo no banco de dados usando o método `fileDataRepository.findByName(fileName)`, extrai o caminho do arquivo do objeto `FileData` usando o método `fileData.get().getFilePath()`, lê o conteúdo do arquivo usando o método `Files.readAllBytes(new File(filePath).toPath())` e retorna o array de bytes que representa o conteúdo do arquivo.

    ```java
    public byte[] downloadImageFromFileSystem(String fileName)throws IOException {
            Optional<FileData> fileData = fileDataRepository.findByName(fileName);
            String filePath=fileData.get().getFilePath();
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        }
    ```

   - **Propósito:** Ler um arquivo do sistema de arquivos do servidor e retornar seu conteúdo como um array de bytes.
   - **Funcionamento:** 
       - Busca os dados do arquivo no repositório `fileDataRepository` com base no nome do arquivo.
       - Lê o conteúdo do arquivo como um array de bytes.
       - Retorna o array de bytes que representa o conteúdo do arquivo.

Esta classe serve como um intermediário entre a camada de controle (provavelmente controladores Spring) e a camada de acesso a dados (repositório de armazenamento). Ele encapsula a lógica de manipulação de imagens, como upload e download, e utiliza a injeção de dependência do Spring para interagir com o repositório de armazenamento.

Esses métodos demonstram a funcionalidade de upload e download de imagens, tanto para armazenamento em um banco de dados quanto no sistema de arquivos do servidor, em um aplicativo Spring Boot. Cada método executa operações específicas para manipular os dados das imagens de acordo com o tipo de armazenamento escolhido.

---

# Autor
## Feito por: `Daniel Penelva de Andrade`