package gradu.kotlingraphql.model

import gradu.kotlingraphql.repository.MuutRepository
import gradu.kotlingraphql.repository.TyontekijaRepository
import org.springframework.context.annotation.Lazy
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

@Component
class Mappers (
    @Lazy
    private val tyontekijaRepository: TyontekijaRepository,
    @Lazy
    private val muutRepository: MuutRepository
) {
    val tyontekijaRowMapper = RowMapper { rs, _ ->
        Tyontekija(
            id = rs.getLong("id"),
            nimi = rs.getString("nimi"),
            puhelinnumero = rs.getString("puhelinnumero"),
            sahkoposti = rs.getString("sahkoposti"),
            osaaminen =  tyontekijaRepository.haeTyontekijaOsaaminen(rs.getLong("id")),
            asiakkaat = tyontekijaRepository.haeAsiakkaatByTyontekija(rs.getLong("id")),
        )
    }

    val osaaminenRowMapper = RowMapper { rs, _ ->
        Osaaminen(
            osaaminen = rs.getString("osaaminen"),
            id = rs.getLong("id")
        )
    }

    val asiakasRowMapper = RowMapper {rs, _ ->
        Asiakas(
            nimi = rs.getString("nimi"),
            puhelinnumero = rs.getString("puhelinnumero"),
            toimiala = rs.getString("toimiala"),
            osaamisvaatimukset = muutRepository.haeAsiakkaanOsaamisVaatimukset(rs.getLong("id")),
            id = rs.getLong("id")
        )
    }

    val autopaikkaRowMapper = RowMapper {rs, _ ->
        Autopaikka(
            id = rs.getLong("id"),
            toimisto = muutRepository.haeToimistoById(rs.getLong("toimisto_id")),
            tyontekija = tyontekijaRepository.haeTyontekijaById(rs.getLong("tyontekija_id"))
        )
    }

    val toimistoRowMapper = RowMapper { rs, _ ->
        Toimisto(
            id = rs.getLong("id"),
            nimi = rs.getString("nimi"),
            kaupunki = rs.getString("kaupunki"),
            maa = rs.getString("maa")
        )
    }

    val osastoRowMapper = RowMapper { rs, _ ->
        Osasto(
            id = rs.getLong("id"),
            nimi = rs.getString("nimi"),
            toimisto_id = rs.getLong("toimisto_id"),
            yhteyshenkilo = Tyontekija(
                id = rs.getLong("tyontekija_id"),
                nimi = rs.getString("tyontekija_nimi"),
                sahkoposti = rs.getString("sahkoposti"),
                puhelinnumero = rs.getString("puhelinnumero")
            )
        )
    }

    val huoneRowMapper = RowMapper { rs, _ ->
        Huone(
            id = rs.getLong("id"),
            tyyppi = HuoneTyyppi.valueOf(rs.getString("tyyppi")),
            osasto = muutRepository.haeOsastoById(rs.getLong("osasto_id")),
            tyontekijat = tyontekijaRepository.haeTyontekijatByHuone(rs.getLong("id"))
        )
    }
}