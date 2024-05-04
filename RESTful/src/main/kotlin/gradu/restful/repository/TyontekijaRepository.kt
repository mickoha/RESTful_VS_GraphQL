package gradu.restful.repository

import gradu.restful.model.AsiakasVaatimukset
import gradu.restful.model.Tyontekija
import gradu.restful.model.TyontekijaOsaamiset
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

@Repository
class TyontekijaRepository(
    private val db: JdbcTemplate
) {

    fun haeKaikkiTyontekijat(): List<Tyontekija> {
        return db.query("SELECT * FROM core.tyontekija", tyontekijaRowMapper)
    }

    fun haeTyontekijatOsaamisetHuoneessa(id: Long): List<TyontekijaOsaamiset> {
        val sqlString = """
            SELECT 
                t.id,
                t.nimi,
                array_agg(DISTINCT o.osaaminen) AS osaamiset
            FROM core.tyontekija t
            JOIN core.tyontekija_huone th ON t.id = th.tyontekija_id
            JOIN core.tyontekija_osaaminen tto ON t.id = tto.tyontekija_id
            JOIN core.osaaminen o ON o.id = tto.osaaminen_id
            WHERE th.huone_id = ?
            GROUP BY t.id
        """.trimIndent()
        return db.query(sqlString, tyontekijaOsaamisetRowMapper, id)
    }

    fun haeTyontekijanAsiakkaidenVaatimukset(id: Long): List<AsiakasVaatimukset> {
        val sqlString = """
            SELECT 
                a.id,
                a.nimi,
                array_agg(DISTINCT o.osaaminen) AS osaamisvaatimukset
            FROM core.asiakas a
            JOIN core.tyontekija_asiakas ta ON a.id = ta.asiakas_id
            JOIN core.asiakas_osaamisvaatimus ao ON a.id = ao.asiakas_id
            JOIN core.osaaminen o ON o.id = ao.osaaminen_id
            WHERE ta.tyontekija_id = ?
            GROUP BY a.id
        """.trimIndent()
        return db.query(sqlString, asiakasVaatimuksetRowMapper, id)

    }


    fun lisaaTyontekija(employee: Tyontekija) {
        val sqlString = "INSERT INTO core.tyontekija (nimi, puhelinnumero, sahkoposti) VALUES(?, ?, ?)"
        db.update(sqlString, employee.nimi, employee.puhelinnumero, employee.sahkoposti)
    }

    fun muokkaaTyontekija(id: Long, employee: Tyontekija) {
        val sqlString = "UPDATE core.tyontekija SET nimi = ?, sahkoposti = ?, puhelinnumero = ? WHERE id = ?"
        db.update(sqlString, employee.nimi, employee.puhelinnumero, employee.sahkoposti, id)
    }

    val tyontekijaRowMapper = RowMapper { rs, _ ->
         Tyontekija(
            nimi = rs.getString("nimi"),
            puhelinnumero = rs.getString("puhelinnumero"),
            sahkoposti = rs.getString("sahkoposti"),
        )
    }

    val tyontekijaOsaamisetRowMapper = RowMapper { rs, _ ->
        // Oletetaan, että "osaamiset" on tallennettu tietokantaan ARRAY-tietotyyppinä
        val osaamisetArray = rs.getArray("osaamiset")
        val osaamisetList: List<String> = if (osaamisetArray != null) {
            // Muunnetaan SQL Array Java Arrayksi
            val array = osaamisetArray.array as Array<*>
            // Muunnetaan Java Array Listaksi ja varmistetaan, että kaikki elementit ovat String-tyyppisiä
            array.filterIsInstance<String>()
        } else {
            emptyList()
        }

        TyontekijaOsaamiset(
            id = rs.getLong("id"),
            nimi = rs.getString("nimi"),
            osaamiset = osaamisetList
        )
    }

    val asiakasVaatimuksetRowMapper = RowMapper { rs, _ ->
        val osaamisetArray = rs.getArray("osaamisvaatimukset")
        val osaamisetList: List<String> = if (osaamisetArray != null) {
            val array = osaamisetArray.array as Array<*>
            array.filterIsInstance<String>()
        } else {
            emptyList()
        }

        AsiakasVaatimukset(
            asiakasId = rs.getLong("id"),
            nimi = rs.getString("nimi"),
            osaamisvaatimukset = osaamisetList
        )
    }
}