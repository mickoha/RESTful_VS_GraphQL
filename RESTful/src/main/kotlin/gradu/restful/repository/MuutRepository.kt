package gradu.restful.repository

import gradu.restful.model.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class MuutRepository(private val db: JdbcTemplate) {

    fun haeKaikkiOsastot(): List<Osasto> {
        return db.query(
            "SELECT * FROM core.osasto"
        ) { rs, _ ->
            Osasto(
                nimi = rs.getString("nimi"),
                toimistoId = rs.getLong("toimisto_id"),
                yhteyshenkiloId = rs.getLong("yhteyshenkilo_id"),
                id = rs.getLong("id")
            )
        }
    }

    fun haeKaikkiHuoneet(): List<Huone> {
        return db.query(
            "SELECT * FROM core.huone"
        ) { rs, _ ->
            Huone(
                nimi = rs.getString("nimi"),
                tyyppi = HuoneTyyppi.valueOf(rs.getString("tyyppi")),
                osastoId = rs.getLong("osasto_id"),
                id = rs.getLong("id")
            )
        }
    }

    fun haeKaikkiAutopaikat(): List<Autopaikka> {
        return db.query(
            "SELECT * FROM core.autopaikka"
        ) { rs, _ ->
            Autopaikka(
                toimistoId = rs.getLong("toimisto_id"),
                tyontekijaId = rs.getLong("tyontekija_id"),
                id = rs.getLong("id")
            )
        }
    }

     fun haeKaikkiAsiakkaat(): List<Asiakas> {
         return db.query(
             "SELECT * FROM core.asiakas"
         , asiakasRowMapper)
     }

    fun haeAsiakasById(id: Long): Asiakas {
        return db.query("SELECT * FROM core.asiakas WHERE id = ?", asiakasRowMapper, id)[0]
    }

    fun haeOsaamisvaatimuksetByAsiakasId(id: Long): List<String> {
        val sql = """
            SELECT osaaminen FROM core.osaaminen o 
            LEFT JOIN core.asiakas_osaamisvaatimus ao ON o.id = ao.osaaminen_id
            WHERE ao.asiakas_id = ?
        """.trimIndent()
        return db.query(sql, osaaminenRowMapper, id)
    }

    private val asiakasRowMapper = RowMapper {rs, _ ->
        Asiakas(
            nimi = rs.getString("nimi"),
            puhelinnumero = rs.getString("puhelinnumero"),
            toimiala = rs.getString("toimiala"),
            id = rs.getLong("id")
        )
    }

    private val osaaminenRowMapper = RowMapper { rs, _ ->
        rs.getString("osaaminen")
    }
}