package gradu.restful.service

import gradu.restful.model.Tyontekija
import gradu.restful.repository.TyontekijaRepository
import org.springframework.stereotype.Service

@Service
class TyontekijaService(
    private val tyontekijaRepository: TyontekijaRepository
) {
    fun haeKaikkiTyontekijat(): List<Tyontekija> = tyontekijaRepository.haeKaikkiTyontekijat()

    fun lisaaTyontekija(employee: Tyontekija) = tyontekijaRepository.lisaaTyontekija(employee)

    fun muokkaaTyontekij(id: Long, employee: Tyontekija) = tyontekijaRepository.muokkaaTyontekija(id, employee)
}