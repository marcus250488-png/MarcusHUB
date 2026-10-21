# Firebase — o que é e ONDE fazer (passo a passo)

## O que é isso?

Hoje o celular (APK) às vezes usa o token da brapi **dentro do app**.
Isso é ruim para publicar na Play Store.

A ideia do **backend** é:

```
Seu celular  →  Firebase (nuvem do Google)  →  brapi
                    ↑
              token fica SÓ aqui
```

O Firebase do seu app já existe:
- **Project ID:** `b3-insights-5f19e`

Você **não precisa criar outro projeto**. Só publicar as Functions que já estão na pasta `functions/` do zip.

---

## ONDE exatamente (3 lugares)

### A) No seu computador (ou no AndroidIDE, se tiver terminal)

Pasta do projeto:

```
B3Insights/
  functions/     ← código do backend (já veio no zip)
  app/           ← código do celular
```

### B) No site do Firebase (navegador)

1. Abra: https://console.firebase.google.com/
2. Entre com o **mesmo Google** da conta B3 Insights
3. Clique no projeto **b3-insights-5f19e**
4. Menu lateral esquerdo → **Functions** (Funções)
   - É aqui que, depois do deploy, aparecem: `health`, `marketQuote`, `marketSearch`

### C) Token da brapi (site brapi)

1. Abra: https://brapi.dev/
2. Entre na conta → copie o **token**
3. Esse token vai **só** no Firebase, nunca no código do app publicado

---

## O QUE VOCÊ FAZ (uma vez)

### 1. Instalar ferramentas (só se ainda não tiver)

No PC (não no celular):

```bash
npm install -g firebase-tools
firebase login
```

### 2. Entrar na pasta functions do projeto

```bash
cd B3Insights/functions
npm install
cd ..
firebase use b3-insights-5f19e
```

### 3. Guardar o token da brapi NO SERVIDOR (não no APK)

```bash
firebase functions:config:set brapi.token="COLE_SEU_TOKEN_AQUI"
```

Exemplo (falso):

```bash
firebase functions:config:set brapi.token="abc123xyz"
```

### 4. Publicar (deploy)

```bash
firebase deploy --only functions
```

Espere terminar. No final aparece um link tipo:

```
https://us-central1-b3-insights-5f19e.cloudfunctions.net/marketQuote
```

### 5. Colocar o link no app (local.properties)

Arquivo: `B3Insights/local.properties` (não sobe no Git)

```
sdk.dir=...
BRAPI_API_TOKEN=
MARKET_API_BASE=https://us-central1-b3-insights-5f19e.cloudfunctions.net/
```

- `BRAPI_API_TOKEN` pode ficar **vazio** em produção (token só no Firebase)
- `MARKET_API_BASE` = URL base que o deploy mostrou (sem o nome da function no final; com barra no fim)

### 6. Rebuild do APK no AndroidIDE

Pronto. O app chama o Firebase; o Firebase chama a brapi com o token.

---

## Como testar se deu certo

No navegador do celular ou PC:

```
https://us-central1-b3-insights-5f19e.cloudfunctions.net/health
```

Deve responder algo como: `B3 Insights functions OK...`

```
https://us-central1-b3-insights-5f19e.cloudfunctions.net/marketQuote?ticker=PETR4
```

Deve devolver JSON da cotação (se o token estiver certo).

---

## O que EU (código) já fiz no zip

- Arquivo `functions/index.js` com `health`, `marketQuote`, `marketSearch`
- App preparado com `MARKET_API_BASE` no BuildConfig
- Motor `ValuationEngine` + status de campos
- Aba Valuation no detalhe do ativo

## O que SÓ VOCÊ pode fazer

1. Login no Firebase
2. Colar o token da brapi no `functions:config:set`
3. `firebase deploy --only functions`
4. Colar a URL em `local.properties`

Sem isso, o app **continua funcionando** no modo atual (Yahoo + brapi local se tiver token no local.properties de desenvolvimento).

---

## Gemini e CVM

- **Gemini:** mesma ideia — chave só no Firebase, numa function futura. Ainda não deployamos porque precisa da sua chave da Google AI.
- **CVM:** integração oficial é fase seguinte (não inventamos endpoint).

Quando o deploy do marketProxy estiver no ar, avise — aí ligamos o Android 100% no proxy e tiramos o token do APK de produção.
