package gradu.kotlingraphql.resolvers

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import gradu.kotlingraphql.model.Tyontekija
import gradu.kotlingraphql.service.TyontekijaService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping

@Controller
class TyontekijaResolveri (private val tyontekijaService: TyontekijaService) {

    @QueryMapping
    fun tyontekijat(): List<Tyontekija> = tyontekijaService.haeKaikkiTyontekijat()

    @MutationMapping
    fun lisaaTyontekija(@Argument nimi: String, @Argument puhelinnumero: String, @Argument sahkoposti: String): Tyontekija {
        return tyontekijaService.lisaaTyontekija(nimi, puhelinnumero, sahkoposti)
    }

    @MutationMapping
    fun muokkaaTyontekija(@Argument id: Long, @Argument nimi: String, @Argument puhelinnumero: String, @Argument sahkoposti: String): Tyontekija {
        return tyontekijaService.muokkaaTyontekija(id, nimi, puhelinnumero, sahkoposti)
    }
}
