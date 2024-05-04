package gradu.kotlingraphql.repository

import gradu.kotlingraphql.model.Asiakas
import gradu.kotlingraphql.model.Mappers
import gradu.kotlingraphql.model.Osaaminen
import gradu.kotlingraphql.model.Tyontekija
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.rmi.UnexpectedException


@Repository
class TyontekijaRepository(private val db: JdbcTemplate, private val mappers: Mappers) {

    fun haeKaikkiTyontekijat(): List<Tyontekija> {
        return db.query("SELECT * FROM core.tyontekija", mappers.tyontekijaRowMapper)
    }

    fun haeTyontekijaOsaaminen(id: Long): List<Osaaminen> {
        val sqlString = """
            SELECT * FROM core.osaaminen o 
            LEFT JOIN core.tyontekija_osaaminen ot ON o.id = ot.osaaminen_id
            WHERE ot.tyontekija_id = ?
        """.trimIndent()
        return db.query(sqlString, mappers.osaaminenRowMapper, id)
    }

    fun haeAsiakkaatByTyontekija(id: Long): List<Asiakas> {
        val sqlString = """
            SELECT * FROM core.asiakas a 
            LEFT JOIN core.tyontekija_asiakas ta ON a.id = ta.asiakas_id
            WHERE ta.tyontekija_id = ?
        """.trimIndent()
        return db.query(sqlString, mappers.asiakasRowMapper, id)
    }

    fun haeTyontekijaById(id: Long): Tyontekija? {
        if (id.toInt() != 0) {
            val sql = "SELECT * FROM core.tyontekija WHERE id = ?"
            return db.query(sql, mappers.tyontekijaRowMapper, id)[0]
        }
        return null
    }

    fun haeTyontekijatByHuone(id: Long): List<Tyontekija> {
        val sql = """
            SELECT t.* FROM core.tyontekija t
            JOIN core.tyontekija_huone th ON t.id = th.tyontekija_id
            WHERE th.huone_id = ?
            """.trimIndent()

        return db.query(sql, mappers.tyontekijaRowMapper, id)
    }

    fun lisaaTyontekija(nimi: String, puhelinnumero: String, sahkoposti: String): Tyontekija {
        val sqlString = "INSERT INTO core.tyontekija (nimi, puhelinnumero, sahkoposti) VALUES(?, ?, ?)"
        val affectedRows = db.update(sqlString, nimi, puhelinnumero, sahkoposti)

        if (affectedRows == 1) {
            return Tyontekija(nimi, puhelinnumero, sahkoposti)
        }

        throw UnexpectedException("Jotain meni vikaan")
    }

    fun muokkaaTyontekija(id: Long, nimi: String, puhelinnumero: String, sahkoposti: String): Tyontekija {
        val sqlString = "UPDATE core.tyontekija SET nimi = ?, sahkoposti = ?, puhelinnumero = ? WHERE id = ?"
        val affectedRows = db.update(sqlString, nimi, puhelinnumero, sahkoposti, id)

        if (affectedRows == 1) {
            return Tyontekija(nimi, puhelinnumero, sahkoposti, null, null, id)

        }
        throw UnexpectedException("Jotain meni vikaan")
    }


}