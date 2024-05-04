package gradu.kotlingraphql.repository

import gradu.kotlingraphql.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class MuutRepository(
    private val db: JdbcTemplate,
    private val mappers: Mappers
) {

    fun haeKaikkiOsastot(): List<Osasto> {
        val sql = """
            SELECT o.id, o.nimi, o.toimisto_id, t.id as tyontekija_id, t.nimi as tyontekija_nimi, t.sahkoposti, t.puhelinnumero
            FROM core.osasto o
            JOIN core.tyontekija t ON o.yhteyshenkilo_id = t.id
        """.trimIndent()

        return db.query(sql, mappers.osastoRowMapper)
    }

    fun haeOsastoById(id: Long): Osasto {
        val sql = """
            SELECT o.id, o.nimi, o.toimisto_id, t.id as tyontekija_id, t.nimi as tyontekija_nimi, t.sahkoposti, t.puhelinnumero
            FROM core.osasto o
            JOIN core.tyontekija t ON o.yhteyshenkilo_id = t.id
            WHERE o.id = ?
        """.trimIndent()
        return db.query(sql, mappers.osastoRowMapper, id)[0]
    }

    fun haeKaikkiHuoneet(huoneId: Long?): List<Huone> {
        val sql = "SELECT * FROM core.huone"
        val condition = if (huoneId != null) " WHERE id = ?" else ""
        return db.query(sql.plus(condition), mappers.huoneRowMapper, huoneId)
    }

    fun haeToimistoById(id: Long): Toimisto {
        val sql = "SELECT * FROM core.toimisto WHERE id = ?"
        return db.query(sql, mappers.toimistoRowMapper, id)[0]
    }

    fun haeKaikkiAutopaikat(): List<Autopaikka> {
        val sql = "SELECT * FROM core.autopaikka"
        return db.query(sql, mappers.autopaikkaRowMapper)
    }

    fun haeAsiakkaat(): List<Asiakas> {
        val sql = "SELECT * FROM core.asiakas"
        return db.query(sql, mappers.asiakasRowMapper)
    }

    fun haeAsiakkaanOsaamisVaatimukset(id: Long): List<Osaaminen> {
        val sqlString = """
            SELECT * FROM core.osaaminen o
            LEFT JOIN core.asiakas_osaamisvaatimus ao ON o.id = ao.osaaminen_id
            WHERE ao.asiakas_id = ?
        """.trimIndent()
        return db.query(sqlString, mappers.osaaminenRowMapper, id)
    }
}