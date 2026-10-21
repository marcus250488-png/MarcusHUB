# Motor de dados B3 Insights — fase 1

## Arquitetura atual (antes)
- Kotlin + Jetpack Compose + Room + Retrofit
- RemoteDataSource: brapi.dev + Yahoo Finance + Coinbase (cripto)
- Token brapi opcional em local.properties → BuildConfig (dev)
- Valuation.kt: Graham, Bazin, Lynch, Barsi, DCF no app
- Firebase Functions: praticamente só health (billing desativado)

## Arquitetura alvo (prompt)
Android → Backend → BRAPI / CVM → cálculos → Gemini → Android

## O que esta fase entrega
1. `FieldValue` + `FieldStatus` (AVAILABLE | CALCULATED | UNAVAILABLE | …)
2. `ValuationEngine` — métodos com fórmula, inputs e status
3. Cloud Functions `marketQuote` / `marketSearch` — token só no servidor
4. `DEPLOY-MARKET.md` — como configurar
5. BuildConfig `MARKET_API_BASE` para o app apontar ao proxy

## O que NÃO está nesta fase (precisa de mais ciclo)
- CVM estruturada (dados oficiais em massa)
- Gemini (chave só no backend + prompts)
- UI completa "ANALISAR" com todas as seções do prompt
- Remoção total do token do APK em produção (só após proxy deployado e testado)
- Cache backend intraday vs fundamental

## Uso no AndroidIDE
1. Continua compilando como hoje
2. Opcional: configure MARKET_API_BASE após deploy das functions
3. Valuation de Ações e telas de ativo usam os mesmos dados de mercado atuais
