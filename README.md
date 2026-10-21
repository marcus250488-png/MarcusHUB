# B3 Insights

App Android nativo (Kotlin + Jetpack Compose) para consultar ações e FIIs da B3:
busca por ticker, cotação, gráfico, dividendos, carteira e favoritos.

## Como abrir no AndroidIDE

1. Copie a pasta `B3Insights` inteira para o aparelho.
2. Abra o AndroidIDE → **Open Project** → pasta `B3Insights`.
3. Confirme que `local.properties` tem o token da brapi (já vem preenchido neste zip).
   O IDE deve preencher `sdk.dir` sozinho.
4. Sincronize o Gradle e compile (**Build → Assemble Debug APK**).
5. O projeto precisa de **JDK 17**. No terminal: `java -version`. Se aparecer 8, rode o `idesetup` do AndroidIDE.

O app funciona **sem login**. Use “Continuar sem login” até o Google Sign-In estar 100% no Firebase.

## PRO vitalício (R$ 59,90)

Pagamento único via Stripe — sem mensalidade. O webhook em `functions/` grava `status: lifetime` no Firestore. Depois de alterar o código, publique de novo as Cloud Functions (`firebase deploy --only functions`) e compile o APK no AndroidIDE.

## Fonte de dados (grátis)

| O quê | De onde |
|---|---|
| Cotação, gráfico, dividendos de **qualquer** ticker | Yahoo Finance (`PETR4.SA`, `HGLG11.SA`…) |
| Busca por nome/ticker da B3, logo, P/L, LPA, perfil da empresa | brapi.dev (seu token no `local.properties`) |

O plano gratuito da brapi **não inclui dividendos**. Se o app pedir `dividends=true` lá, a API responde 403 e a tela quebra. Por isso os proventos vêm do Yahoo.

Não precisa pagar nada para pesquisar WEGE3, BBAS3, MXRF11, HGLG11 etc.

## Login com Google (Firebase)

O SHA-1 do debug já deve estar no app Android `com.b3insights.app`.

Ainda falta, se o botão Google não entrar:

1. Firebase → Authentication → Sign-in method → ative **Google**.
2. Baixe de novo o `google-services.json` (o campo `oauth_client` precisa deixar de ser `[]`).
3. Substitua `app/google-services.json`.

Enquanto isso, use **Continuar sem login**. Cotação e carteira não dependem do Firebase.

Para ver o SHA-1 de novo no terminal do AndroidIDE (sem Gradle):

```bash
mkdir -p $HOME/.android
keytool -list -v -keystore $HOME/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

Se o arquivo não existir, crie-o:

```bash
keytool -genkey -v -keystore $HOME/.android/debug.keystore -alias androiddebugkey -keyalg RSA -keysize 2048 -validity 10000 -storepass android -keypass android -dname "CN=Android Debug,O=Android,C=US"
```

Não use `./gradlew signingReport` — o plugin do Firebase quebra nesse comando no AndroidIDE.

## Estrutura

```
app/src/main/java/com/b3insights/app/
├── data/remote/     YahooFinanceService + BrapiService + RemoteDataSource
├── data/repository/ MarketDataRepository
├── data/local/      Room (carteira, favoritos, buscas)
├── ui/screens/      home, asset, favorites, portfolio, compare, settings, login
└── auth/            Login Google / Firebase
```

Nenhuma senha de keystore está no código. O token da brapi fica só em `local.properties`.

## PRO via Asaas (Pix Automático)

1. No Asaas: chave Pix ativa + API Key (Sandbox ou Produção).
2. Deploy das Functions:
   ```bash
   cd functions
   npm i
   firebase functions:config:set asaas.api_key="SUA_CHAVE_ASAAS" asaas.webhook_token="UM_TOKEN_SECRETO"
   # Opcional sandbox:
   # firebase functions:config:set asaas.base_url="https://api-sandbox.asaas.com/v3"
   firebase deploy --only functions
   ```
3. Em `ProConfig.CREATE_CHECKOUT_URL`, coloque a URL de `asaasCreateProCheckout`.
4. No Asaas → Integrações → Webhooks:
   - URL: `https://REGION-PROJECT.cloudfunctions.net/asaasWebhook`
   - Token: o mesmo `asaas.webhook_token`
   - Eventos: autorização Pix Automático + pagamentos.
5. No app: **VIRAR PRO** → formulário (nome, CPF, e-mail, celular) → Pix copia e cola.

Valor: **R$ 19,90/mês**.

## Marcus Hub — ligação com o Financeiro

Ao adicionar uma compra/venda (tela **Adicionar à carteira**), o switch **Lançar no Financeiro** (ligado por padrão) publica o aporte em
`users/{uid}/b3_hub/aportes/itens/{id}` (`processado=false`). O app **Financeiro** importa ao abrir/voltar ao app e cria a transferência
**Nubank → Banco Inter (Investimento)** (compra) ou **Inter → Nubank** (venda), marcando `processado=true`.

- Importações em lote (CSV / nota de corretagem) **não** movem dinheiro.
- Editar ou excluir uma posição no B3 **não** altera o que o Financeiro já lançou.
- Sem login/internet o aporte fica numa fila local e é reenviado no próximo sync.
- **Importante:** o B3 precisa usar o **mesmo projeto Firebase** dos demais apps (`moto-control-a2086`) — ver `google-services.json`.
