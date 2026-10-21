# Deploy Asaas (produção)

Projeto Firebase: **b3-insights-5f19e**

## 1. No computador (com Firebase CLI)

```bash
npm install -g firebase-tools
firebase login
cd functions
npm i
firebase use b3-insights-5f19e
firebase deploy --only functions
```

A chave está em `asaas.secret.json` (só nesta pasta functions — **não** vai para o APK).

## 2. URLs depois do deploy

- Checkout: `https://us-central1-b3-insights-5f19e.cloudfunctions.net/asaasCreateProCheckout`
- Webhook: `https://us-central1-b3-insights-5f19e.cloudfunctions.net/asaasWebhook`

(Já configurado no app em ProConfig.)

## 3. Webhook no painel Asaas

1. Asaas → Integrações → Webhooks → Criar
2. URL: a do `asaasWebhook` acima
3. Token de autenticação (asaas-access-token):

```
p2fhu7f4I0kB2cLfERMUORC23IEp1RyPSlnXyX7Cogc
```

4. Eventos recomendados:
   - PIX_AUTOMATIC_RECURRING_AUTHORIZATION_ACTIVATED
   - PIX_AUTOMATIC_RECURRING_AUTHORIZATION_CANCELLED
   - PIX_AUTOMATIC_RECURRING_AUTHORIZATION_EXPIRED
   - PAYMENT_RECEIVED
   - PAYMENT_CONFIRMED
   - PAYMENT_OVERDUE

## 4. Segurança

Você colou a **API Key de produção** no chat. Depois que o deploy funcionar, o ideal é:
1. Gerar uma **nova** chave no Asaas
2. Atualizar `functions/asaas.secret.json`
3. Fazer deploy de novo
4. Apagar a chave antiga no painel Asaas
