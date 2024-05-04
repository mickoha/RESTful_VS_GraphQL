package gradu.kotlingraphql.service

import gradu.kotlingraphql.model.Tyontekija
import gradu.kotlingraphql.repository.TyontekijaRepository
import org.springframework.stereotype.Service

@Service
class TyontekijaService(private val tyontekijaRepository: TyontekijaRepository) {
    
    fun haeKaikkiTyontekijat(): List<Tyontekija> = tyontekijaRepository.haeKaikkiTyontekijat()
    
    fun lisaaTyontekija(nimi: String, puhelinnumero: String, sahkoposti: String): Tyontekija {
        return tyontekijaRepository.lisaaTyontekija(nimi, puhelinnumero, sahkoposti)
    }
    
    fun muokkaaTyontekija(id: Long, nimi: String, puhelinnumero: String, sahkoposti: String): Tyontekija {
        return tyontekijaRepository.muokkaaTyontekija(id, nimi, puhelinnumero, sahkoposti)
    }
}