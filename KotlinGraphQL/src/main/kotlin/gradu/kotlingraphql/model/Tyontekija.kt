package gradu.kotlingraphql.model

data class Tyontekija(
    val nimi: String,
    val puhelinnumero: String,
    val sahkoposti: String,
    val asiakkaat: List<Asiakas>? = null,
    val osaaminen: List<Osaaminen>? = null,
    val id: Long? = null
)
