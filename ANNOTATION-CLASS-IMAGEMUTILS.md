# Classes `Deflater` e `ByteArrayOutputStream`

As classes `Deflater` e `ByteArrayOutputStream` são classes importantes na plataforma Java, especialmente quando se trata de compressão de dados.

## 1. **Deflater**:
A classe `Deflater` faz parte do pacote `java.util.zip` e é usada para realizar compressão de dados em formato zlib. Ela implementa um algoritmo de compressão sem perdas, que reduz o tamanho dos dados através da eliminação de redundâncias. Algumas características principais:

- **Compressão de Dados**: A principal função da classe `Deflater` é comprimir dados. Ela aceita uma matriz de bytes de entrada e produz uma saída compactada.
- **Configurações de Compressão**: Você pode configurar diferentes níveis de compressão usando as constantes fornecidas pela classe `Deflater`, como `BEST_SPEED`, `BEST_COMPRESSION` e `DEFAULT_COMPRESSION`.
- **Fluxo de Compressão**: Ela opera em um modelo de fluxo, o que significa que você alimenta os dados para compressão e obtém os dados compactados conforme necessário, sem a necessidade de carregar todos os dados na memória de uma vez.


## 2. **ByteArrayOutputStream**:
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

# Autor
## Feito por: `Daniel Penelva de Andrade`