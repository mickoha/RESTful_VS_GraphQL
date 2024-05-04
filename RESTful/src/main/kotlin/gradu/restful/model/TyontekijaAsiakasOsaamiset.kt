package gradu.restful.model

data class TyontekijaAsiakasOsaamiset(
    val tyontekijaId: Long,
    val nimi: String,
    val osaamiset: List<String>,
    val asiakasvaatimukset: List<AsiakasVaatimukset>
)

data class AsiakasVaatimukset(
    val asiakasId: Long,
    val nimi: String,
    val osaamisvaatimukset: List<String>
)
