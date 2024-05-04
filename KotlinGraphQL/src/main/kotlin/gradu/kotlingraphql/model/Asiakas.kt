package gradu.kotlingraphql.model

data class Asiakas(
    val nimi: String,
    val puhelinnumero: String,
    val toimiala: String,
    val osaamisvaatimukset: List<Osaaminen>,
    val id: Long
)
