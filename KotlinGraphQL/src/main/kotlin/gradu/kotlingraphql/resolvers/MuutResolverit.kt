package gradu.kotlingraphql.resolvers

import gradu.kotlingraphql.model.Asiakas
import gradu.kotlingraphql.model.Autopaikka
import gradu.kotlingraphql.model.Huone
import gradu.kotlingraphql.model.Osasto
import gradu.kotlingraphql.repository.MuutRepository
import gradu.kotlingraphql.service.MuutService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class MuutResolverit(
    private val services: MuutService
) {

    @QueryMapping
    fun osastot(): List<Osasto> = services.haeKaikkiOsastot()

    @QueryMapping
    fun huoneet(@Argument huoneId: Long?): List<Huone> = services.haeKaikkiHuoneet(huoneId)

    @QueryMapping
    fun autopaikat(): List<Autopaikka> = services.haeAutopaikat()

    @QueryMapping
    fun asiakkaat(): List<Asiakas> =  services.haeAsiakkaat()

}

