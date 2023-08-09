# API para raspagem e classificação de notícias

Este repositório contém o código-fonte desenvolvido para o Trabalho de Conclusão de Curso intitulado "Desenvolvimento de uma API destinada a scraping e classificação de textos de sites de notícias".

## Descrição do projeto

O objetivo deste projeto foi desenvolver uma API capaz de realizar raspagem de dados em sites de notícias, extrair o conteúdo textual das matérias e classificá-lo utilizando técnicas de Processamento de Linguagem Natural.

A API recebe como entrada um termo de busca e retorna os resultados encontrados no Google filtrados para a categoria de notícias. O conteúdo dessas notícias é então extraído e analisado para identificar conceitos, entidades, palavras-chave e sentimentos presentes no texto.


## Tecnologias utilizadas

- [Java](https://www.java.com/)
- [Spring Framework](https://spring.io/)
- [Selenium](https://www.selenium.dev/)
- [Jsoup](https://jsoup.org/)
- [Watson Natural Language Understanding](https://www.ibm.com/watson/services/natural-language-understanding/)  
- [MySQL](https://www.mysql.com/)
- [Scale SERP API](https://www.scaleserp.com/)

## Como utilizar

O código-fonte da API está disponível neste repositório. Para executá-la localmente, siga os passos:

1. Clone este repositório
```bash 
git clone https://github.com/lucferreira-27/news-backend
```
2. Configure o banco de dados MySQL   
3. Obtenha chaves de acesso para o Watson NLU e Scale SERP API  


A API conta com endpoints REST para busca, cadastro, atualização e exclusão de sites de notícias. As funcionalidades podem ser testadas via [Swagger UI](https://swagger.io/tools/swagger-ui/) ou ferramentas como [Postman](https://www.postman.com/).

## Resultados  

Este projeto resultou em uma API REST capaz de realizar raspagem web, extração de conteúdo e análise semântica de textos de forma automatizada. Os dados extraídos podem ser utilizados para fins de mineração de opinião, detecção de viés político, análise de sentimento, entre outras aplicações.

O código desenvolvido está estruturado e documentado visando facilitar futuras manutenções e implementação de novas funcionalidades.

### Exemplo de resposta

```json
{
"status": "FINISHED",
"data": [
   {
      "entities": [
         {
            "text": "Banco Central",
            "type": "Organization",
            "sentiment": {
               "score": 0.6179
            }
         }
      ],
      "keywords": [ 
         {
            "text": "juros",
            "sentiment": {
               "score": 0.128
            }
         }
      ],
      "concepts": [
         {
            "text": "Política monetária",
            "relevance": 0.912,
            "sentiment": {
               "score": -0.124
            }
         }
      ]
   }
]
}
```
### Considerações finais
Este projeto foi desenvolvido como Trabalho de Conclusão de Curso na Faculdade de Tecnologia de Ribeirão Preto e obteve resultado satisfatório, atingindo os objetivos propostos inicialmente.

O código-fonte está disponível neste repositório para consulta e contribuições da comunidade.
