package gradu.restful.model

data class Asiakas(
    val nimi: String,
    val puhelinnumero: String,
    val toimiala: String,
    val id: Long
)

data class AsiakasOsaamisvaatimukset(
    val nimi: String,
    val osaamisvaatimukset: List<String>,
    val id: Long
)
