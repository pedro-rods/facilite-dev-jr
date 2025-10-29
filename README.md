# Implementações realizadas

## Back-end

* **Integração com a API ViaCEP**
  Implementado o consumo da API pública do ViaCEP para busca de endereços a partir do CEP informado.

* **Normalização e pré-processamento do CEP**
  Criação de uma rotina de validação e limpeza do CEP antes da requisição, garantindo apenas dígitos válidos e tratamento adequado dos possíveis erros HTTP:

  * `400` → CEP inválido
  * `404` → CEP não encontrado
  * `502` → Serviço de CEP indisponível

* **DTO personalizado (`ViaCepDTO`)**
  Definido um DTO específico para representar a resposta da API ViaCEP, com suporte ao `@JsonIgnoreProperties` para tolerância a mudanças na estrutura da resposta.

* **Uso do Lombok**
  Adoção de anotações do Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para eliminar boilerplate e manter o código mais limpo e legível.

* **Mapeamento com MapStruct**
  Implementado o mapeamento automático de `ViaCepDTO` → `AddressDTO` com uso de *helpers* (`digitsOnly`, `toUf`) para tratar campos que exigem transformação, como normalização do CEP e conversão da sigla de UF para o enum `Uf`.

* **Endpoint dedicado**
  Conectado o novo fluxo ao endpoint já existente `api/cep/{cep}`, garantindo respostas consistentes e mensagens de erro claras.

---

## Front-end

* **Método `cepLookup`**
  Implementado o método de consumo da API de CEP no `AddressService`, com interface dedicada (`CepLookup`) para tipagem e segurança do mapeamento.

* **Máscara e validações para o campo de CEP**
  Aplicada máscara dinâmica (`00000-000`) e validações de formato antes da busca.

* **Automação na busca de CEP**
  Substituição do botão de busca por automação ao sair do campo (evento `blur`), tornando o fluxo mais intuitivo para o usuário.

* **Tratamento de erros amigável**
  Mapeamento completo dos erros HTTP recebidos do back-end com mensagens personalizadas, exibidas de forma não intrusiva diretamente no formulário.

* **Preservação do campo “number”**
  O campo **número** do endereço permanece de preenchimento manual, garantindo que o usuário informe corretamente o número do imóvel.

---

✅ **Resultado final:**
Integração completa entre front-end e back-end para pesquisa automática de endereços via CEP, com tratamento de erros robusto, código limpo (Lombok + MapStruct) e experiência de usuário fluida.
