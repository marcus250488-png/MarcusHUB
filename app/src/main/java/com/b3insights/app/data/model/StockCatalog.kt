package com.b3insights.app.data.model

/**
 * Universo de ações B3 para o radar de valuation.
 * Reutiliza setores já usados no Explore e amplia a lista.
 */
data class StockCatalogEntry(
    val ticker: String,
    val name: String,
    val sector: String
)

object StockCatalog {
    private val bySector: List<Pair<String, List<Pair<String, String>>>> = listOf(
        "Energia" to listOf(
            "PETR3" to "Petrobras ON", "PETR4" to "Petrobras PN", "PRIO3" to "Prio",
            "RECV3" to "PetroRecôncavo", "RRRP3" to "3R Petroleum", "CSAN3" to "Cosan",
            "UGPA3" to "Ultrapar", "VBBR3" to "Vibra", "ENAT3" to "Enauta",
            "ELET3" to "Eletrobras ON", "ELET6" to "Eletrobras PNB", "EQTL3" to "Equatorial",
            "ENGI11" to "Energisa Unit", "CPFE3" to "CPFL Energia", "CMIG3" to "Cemig ON",
            "CMIG4" to "Cemig PN", "CPLE3" to "Copel ON", "CPLE6" to "Copel PNB",
            "EGIE3" to "Engie Brasil", "TAEE11" to "Taesa Unit", "AURE3" to "Auren",
            "NEOE3" to "Neoenergia", "SRNA3" to "Serena"
        ),
        "Saneamento" to listOf(
            "SBSP3" to "Sabesp", "SAPR3" to "Sanepar ON", "SAPR4" to "Sanepar PN",
            "SAPR11" to "Sanepar Unit", "CSMG3" to "Copasa", "ORVR3" to "Orizon", "AMBP3" to "Ambipar"
        ),
        "Bancos" to listOf(
            "ITUB3" to "Itaú ON", "ITUB4" to "Itaú PN", "BBDC3" to "Bradesco ON",
            "BBDC4" to "Bradesco PN", "BBAS3" to "Banco do Brasil", "SANB11" to "Santander Unit",
            "BPAC11" to "BTG Unit", "ITSA4" to "Itaúsa", "ABCB4" to "ABC Brasil",
            "BRSR6" to "Banrisul", "BPAN4" to "Banco Pan", "BMGB4" to "BMG"
        ),
        "Seguros" to listOf(
            "BBSE3" to "BB Seguridade", "PSSA3" to "Porto Seguro", "SULA11" to "SulAmérica",
            "WIZC3" to "Wiz"
        ),
        "Mineração" to listOf(
            "VALE3" to "Vale", "CSNA3" to "CSN", "GGBR3" to "Gerdau ON", "GGBR4" to "Gerdau PN",
            "GOAU4" to "Metalúrgica Gerdau", "USIM5" to "Usiminas", "CMIN3" to "CSN Mineração"
        ),
        "Varejo" to listOf(
            "MGLU3" to "Magazine Luiza", "LREN3" to "Lojas Renner", "AMER3" to "Americanas",
            "ARZZ3" to "Arezzo", "SOMA3" to "Grupo Soma", "ASAI3" to "Assaí",
            "CRFB3" to "Carrefour", "PCAR3" to "Pão de Açúcar", "VIVA3" to "Vivara",
            "CEAB3" to "C&A", "GUAR3" to "Guararapes"
        ),
        "Consumo" to listOf(
            "ABEV3" to "Ambev", "NTCO3" to "Natura", "HYPE3" to "Hypera", "RADL3" to "Raia Drogasil",
            "MDIA3" to "M. Dias Branco", "SMTO3" to "São Martinho", "BEEF3" to "Minerva",
            "JBSS3" to "JBS", "BRFS3" to "BRF", "MRFG3" to "Marfrig", "CAML3" to "Camil"
        ),
        "Telecom" to listOf(
            "VIVT3" to "Telefônica Brasil", "TIMS3" to "TIM", "OIBR3" to "Oi ON", "OIBR4" to "Oi PN"
        ),
        "Saúde" to listOf(
            "HAPV3" to "Hapvida", "RDOR3" to "Rede D'Or", "FLRY3" to "Fleury",
            "ONCO3" to "Oncoclínicas", "QUAL3" to "Qualicorp", "MATD3" to "Mater Dei"
        ),
        "Tecnologia" to listOf(
            "TOTS3" to "Totvs", "LWSA3" to "Locaweb", "POSI3" to "Positivo",
            "CASH3" to "Méliuz", "IFCM3" to "Infracommerce"
        ),
        "Construção" to listOf(
            "CYRE3" to "Cyrela", "EZTC3" to "EZTEC", "MRVE3" to "MRV", "DIRR3" to "Direcional",
            "TEND3" to "Tenda", "CURY3" to "Cury", "PLPL3" to "Plano&Plano", "JHSF3" to "JHSF"
        ),
        "Indústria" to listOf(
            "WEGE3" to "WEG", "RAIL3" to "Rumo", "EMBR3" to "Embraer", "RANI3" to "Irani",
            "TUPY3" to "Tupy", "MYPK3" to "Iochpe-Maxion", "POMO4" to "Marcopolo"
        ),
        "Transportes" to listOf(
            "CCRO3" to "CCR", "ECOR3" to "Ecorodovias", "TPIS3" to "Triunfo",
            "RENT3" to "Localiza", "MOVI3" to "Movida", "VAMO3" to "Vamos", "LOGN3" to "Log-In"
        ),
        "Agronegócio" to listOf(
            "SLCE3" to "SLC Agrícola", "AGRO3" to "BrasilAgro", "SOJA3" to "Boa Safra",
            "TTEN3" to "3tentos", "SMTO3" to "São Martinho"
        ),
        "Outros" to listOf(
            "B3SA3" to "B3", "SUZB3" to "Suzano", "KLBN11" to "Klabin Unit", "KLBN4" to "Klabin PN",
            "DXCO3" to "Dexco", "BRAP4" to "Bradespar", "CMIN3" to "CSN Mineração"
        )
    )

    val all: List<StockCatalogEntry> by lazy {
        val seen = linkedSetOf<String>()
        val out = mutableListOf<StockCatalogEntry>()
        for ((sector, pairs) in bySector) {
            for ((t, n) in pairs) {
                val key = t.uppercase()
                if (key in seen) continue
                seen += key
                out += StockCatalogEntry(key, n, sector)
            }
        }
        out.sortedBy { it.ticker }
    }

    val sectors: List<String> = bySector.map { it.first }

    fun bySector(name: String?): List<StockCatalogEntry> =
        if (name.isNullOrBlank() || name == "Todos") all
        else all.filter { it.sector == name }
}
