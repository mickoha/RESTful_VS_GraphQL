package gradu.restful.service

import gradu.restful.model.*
import gradu.restful.repository.MuutRepository
import gradu.restful.repository.TyontekijaRepository
import org.springframework.stereotype.Service

@Service
class MuutService(
    private val muutRepository: MuutRepository,
    private val tyontekijaRepository: TyontekijaRepository
) {

    fun haeKaikkiOsastot(): List<Osasto> = muutRepository.haeKaikkiOsastot()

    fun haeKaikkiHuoneet(): List<Huone> = muutRepository.haeKaikkiHuoneet()

    fun haeKaikkiAutopaikat(): List<Autopaikka> = muutRepository.haeKaikkiAutopaikat()

    fun haeKaikkiAsiakkaat(): List<Asiakas> = muutRepository.haeKaikkiAsiakkaat()

    fun haeAsiakasOsaamisvaatimusByAsiakasId(id: Long): AsiakasOsaamisvaatimukset {
        val asiakas = muutRepository.haeAsiakasById(id)
        val osaamiset = muutRepository.haeOsaamisvaatimuksetByAsiakasId(id)
        return AsiakasOsaamisvaatimukset(
            nimi = asiakas.nimi,
            osaamisvaatimukset = osaamiset,
            id =  asiakas.id
        )
    }

    fun haeHuoneenTyontekijaAsiakasOsaamiset(id: Long): List<TyontekijaAsiakasOsaamiset> {
        val tyontekijat = tyontekijaRepository.haeTyontekijatOsaamisetHuoneessa(id)
        return tyontekijat.map {
            TyontekijaAsiakasOsaamiset(
                tyontekijaId = it.id,
                nimi = it.nimi,
                osaamiset = it.osaamiset,
                asiakasvaatimukset = tyontekijaRepository.haeTyontekijanAsiakkaidenVaatimukset(it.id)
            )
        }
    }
 }