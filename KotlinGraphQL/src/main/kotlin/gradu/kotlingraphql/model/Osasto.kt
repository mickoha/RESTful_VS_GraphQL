package gradu.kotlingraphql.model

data class Osasto(
    val nimi: String,
    val toimisto_id: Long,
    val yhteyshenkilo: Tyontekija,
    val id: Long
)


