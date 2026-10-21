@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.theme.TextPrimary

enum class LegalDoc { PRIVACY, TERMS }

@Composable
fun LegalScreen(doc: LegalDoc, onBack: () -> Unit) {
    val title = if (doc == LegalDoc.PRIVACY) "Política de privacidade" else "Termos de uso"
    val body = if (doc == LegalDoc.PRIVACY) privacyText else termsText

    PaperBackground {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = MaterialTheme.colorScheme.onBackground)
            }
            Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(8.dp))
            Text(
                "Última atualização: 16 de setembro de 2026",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))
            Text(body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(48.dp))
        }
    }
}

private val privacyText = """
1. Quem somos
O B3 Insights é um aplicativo de acompanhamento de investimentos (ações, FIIs, ETFs e criptomoedas). Esta política descreve como tratamos dados quando você usa o app.

2. Dados que coletamos
• Conta Google: nome, e-mail e foto de perfil, quando você faz login.
• Carteira e uso: ativos, quantidades, preços médios, datas de operação, favoritos, alertas, metas e preferências que você cadastra.
• Dados técnicos: identificadores de dispositivo necessários para notificações e funcionamento básico.
Não pedimos senha do Google; a autenticação é feita pelo serviço do Google.

3. Como usamos
• Sincronizar sua carteira e configurações na nuvem (Firebase/Google) associada à sua conta.
• Exibir cotações e indicadores de mercado obtidos de fontes públicas ou de terceiros.
• Enviar notificações que você autorizar (ex.: alertas de preço ou de valuation).

4. Compartilhamento
• Google (login e Firebase).
• Fontes de cotação (apenas consultas de mercado, sem enviar seus dados pessoais de carteira).
Não vendemos seus dados pessoais.

5. Pagamentos
Nesta versão do app não processamos pagamentos nem dados de cartão. Se no futuro houver compra na Google Play, o pagamento será tratado pela Google conforme as políticas da loja; o B3 Insights não armazena número completo de cartão.

6. Armazenamento e segurança
Dados da carteira ficam no aparelho (banco local) e, se você estiver logado, na nuvem da sua conta. Adote boas práticas no dispositivo (bloqueio de tela, biometria).

7. Seus direitos
Você pode solicitar exclusão da conta e dos dados sincronizados saindo do app e excluindo os dados no Firebase/Google conforme as ferramentas da plataforma, ou entrando em contato pelo canal de suporte indicado na página da Play Store quando o app estiver publicado.

8. Crianças
O app não é destinado a menores de 13 anos.

9. Alterações
Podemos atualizar esta política; a data no topo será revisada. O uso contínuo após mudanças relevantes indica ciência.

10. Contato
Dúvidas sobre privacidade: canal de suporte da conta B3 Insights / página do app na Play Store quando publicada.
""".trimIndent()

private val termsText = """
1. Aceitação
Ao usar o B3 Insights, você concorda com estes Termos de Uso e com a Política de Privacidade.

2. O serviço
O app oferece ferramentas de organização de carteira, consulta de cotações e indicadores de mercado (ações, FIIs, ETFs e criptomoedas), rankings e recursos de acompanhamento. Conteúdo é informativo.

3. Conta
Você é responsável pelo uso da sua conta Google e pelas informações lançadas na carteira. Mantenha o login seguro.

4. Acesso ao app
Nesta versão, os recursos do aplicativo estão disponíveis conforme disponibilizado no app, sem cobrança de assinatura dentro do aplicativo. Se no futuro houver compra ou assinatura pela Google Play, aplicam-se os termos da Google Play e a descrição do produto na loja.

5. Conteúdo de mercado
Cotações, indicadores, notícias e cálculos (incluindo métodos como Graham e Bazin) são informativos, podem conter atrasos ou imprecisões e NÃO constituem recomendação de investimento, consultoria de valores mobiliários ou garantia de resultado.

6. Uso aceitável
É proibido usar o app de forma ilegal, sobrecarregar servidores ou copiar de forma abusiva bases de dados de terceiros via o app.

7. Propriedade intelectual
Marca, layout e código do B3 Insights pertencem aos titulares do projeto. Fontes de dados de mercado pertencem aos respectivos provedores.

8. Limitação de responsabilidade
Na máxima extensão permitida pela lei, o B3 Insights não se responsabiliza por decisões de investimento, perdas financeiras, indisponibilidade temporária de cotações ou erros de terceiros (APIs de mercado, corretoras, Google).

9. Rescisão
Podemos suspender o acesso em caso de violação destes termos. Você pode parar de usar o app a qualquer momento.

10. Lei aplicável
Estes termos são interpretados conforme as leis brasileiras, sem prejuízo de direitos do consumidor.

11. Contato
Questões sobre os termos: canal de suporte da conta B3 Insights / página do app na Play Store quando publicada.
""".trimIndent()
