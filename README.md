# Insightify
Insightify é um aplicativo moderno para Android que oferece uma maneira fácil de se manter atualizado com as últimas notícias. Utiliza a arquitetura MVVM e o Jetpack Compose para uma interface de usuário agradável.
Este aplicativo resgata os artigos consumindo a [News API](https://newsapi.org/).

## Screenshots

## Recursos
- **Tema dinâmico:** Experimente uma interface visualmente atraente com tematização dinâmica baseada no modo escuro/claro do sistema.
- **Pesquisa e conversão de voz para Texto:** Encontre artigos sem esforço pesquisando ou usando o recurso de conversão de voz para texto para uma experiência sem uso das mãos.
- **Principais manchetes:** Mantenha-se informado com os principais artigos de várias fontes de notícias.
- **Detalhes da notícia:** Aprofunde-se nos artigos com uma visualização detalhada que inclui uma visualização da web para uma experiência de leitura imersiva.

## Tecnologias
- **Jetpack Compose:** Um toolkit de UI moderno para Android para construir interfaces nativas.
- **MVVM Architecture:** Uma arquitetura robusta para separação de preocupações e manutenibilidade.
- **Retrofit:** Um cliente HTTP seguro para fazer solicitações de rede.
- **Speech Recognition API:** Integração para funcionalidade de conversão de voz para texto.

## Rode o projeto no seu ambiente
1. Clone o repositório: `git clone https://github.com/darayve/news-app.git`
2. Vá até o site https://newsapi.org/ e cadastre-se. Uma API Key será gerada, coloque-a dentro do seu arquivo global `gradle.properties`. Geralmente este arquivo fica localizado dentro da pasta `.gradle`.
3. Abra o projeto no Android Studio.
4. Execute o aplicativo em um emulador ou dispositivo físico.
