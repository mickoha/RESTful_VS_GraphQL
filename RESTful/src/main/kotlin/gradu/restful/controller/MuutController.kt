package gradu.restful.controller

import gradu.restful.model.*
import gradu.restful.service.MuutService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/api")
class MuutController(
    private val muutService: MuutService
) {

    @GetMapping("/osasto/kaikki")
    @ResponseBody
    fun haeKaikkiOsastot(): List<Osasto> {
        return muutService.haeKaikkiOsastot()
    }

    @GetMapping("/huone/kaikki")
    @ResponseBody
    fun haeKaikkiHuoneet(): List<Huone> {
        return muutService.haeKaikkiHuoneet()
    }

    @GetMapping("/autopaikat/kaikki")
    @ResponseBody
    fun haeKaikkiAutopaikat(): List<Autopaikka> {
        return muutService.haeKaikkiAutopaikat()
    }

    @GetMapping("/asiakas/kaikki")
    @ResponseBody
    fun haeKaikkiAsiakkaat(): List<Asiakas> {
        return muutService.haeKaikkiAsiakkaat()
    }

    @GetMapping("/asiakas/{id}/osaamisvaatimukset")
    @ResponseBody
    fun asiakasOsaamisvaatimukset(@PathVariable(required = true) id: Long): AsiakasOsaamisvaatimukset {
        return muutService.haeAsiakasOsaamisvaatimusByAsiakasId(id)
    }

    @GetMapping("/huone/{id}/osaamiset")
    @ResponseBody
    fun huoneenTyontekijoidenOsaamiset(@PathVariable(required = true) id: Long): List<TyontekijaAsiakasOsaamiset> {
        return muutService.haeHuoneenTyontekijaAsiakasOsaamiset(id)
    }


}