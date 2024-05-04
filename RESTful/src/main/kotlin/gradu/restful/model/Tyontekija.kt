package gradu.restful.model

data class Tyontekija(
    val nimi: String,
    val puhelinnumero: String,
    val sahkoposti: String,
)

data class TyontekijaOsaamiset(
    val id: Long,
    val nimi: String,
    val osaamiset: List<String>
)