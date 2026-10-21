# Proxy de mercado (brapi no backend)

## Por quê
O token da brapi **não** deve ir no APK. O Android chama só as Cloud Functions.

## Configurar token
```bash
firebase functions:config:set brapi.token="SEU_TOKEN_BRAPI"
firebase deploy --only functions:marketQuote,functions:marketSearch,functions:health
```

## Android
Em `local.properties` (não commitado):
```
MARKET_API_BASE=https://US-CENTRAL1-SEU-PROJECT.cloudfunctions.net/
```

No `app/build.gradle` já existe (ou adicione):
```
buildConfigField "String", "MARKET_API_BASE", "\"${marketBase}\""
```

Enquanto MARKET_API_BASE estiver vazio, o app usa o fluxo atual (Yahoo + brapi direto se houver token local de desenvolvimento).

## CVM / Gemini
Próximas functions (não implementadas nesta fase):
- `cvmFundamentals` — dados oficiais quando houver endpoint estável
- `geminiExplain` — recebe JSON estruturado, não inventa números
