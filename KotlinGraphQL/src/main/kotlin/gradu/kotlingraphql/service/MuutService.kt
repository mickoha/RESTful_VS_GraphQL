package gradu.kotlingraphql.service

import gradu.kotlingraphql.model.Asiakas
import gradu.kotlingraphql.model.Autopaikka
import gradu.kotlingraphql.model.Huone
import gradu.kotlingraphql.model.Osasto
import gradu.kotlingraphql.repository.MuutRepository
import org.springframework.stereotype.Service

@Service
class MuutService(private val muutRepository: MuutRepository) {

    fun haeKaikkiOsastot(): List<Osasto> = muutRepository.haeKaikkiOsastot()

    fun haeKaikkiHuoneet(huoneId: Long?): List<Huone> = muutRepository.haeKaikkiHuoneet(huoneId)

    fun haeAutopaikat(): List<Autopaikka> = muutRepository.haeKaikkiAutopaikat()

    fun haeAsiakkaat(): List<Asiakas> = muutRepository.haeAsiakkaat()
}