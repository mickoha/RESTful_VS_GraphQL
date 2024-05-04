package gradu.restful.model

enum class HuoneTyyppi {
    tyotila,
    neuvottelu
}
data class Huone(
    val nimi: String,
    val tyyppi: HuoneTyyppi,
    val osastoId: Long,
    val id: Long
)
