package gradu.kotlingraphql.model

data class Autopaikka(
    val toimisto: Toimisto,
    val tyontekija: Tyontekija?,
    val id: Long
)
