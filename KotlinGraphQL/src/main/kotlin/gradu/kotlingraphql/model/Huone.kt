package gradu.kotlingraphql.model

enum class HuoneTyyppi {
    tyotila,
    neuvottelu
}

data class Huone(
    val tyyppi: HuoneTyyppi,
    val osasto: Osasto,
    val tyontekijat: List<Tyontekija>,
    val id: Long
)
