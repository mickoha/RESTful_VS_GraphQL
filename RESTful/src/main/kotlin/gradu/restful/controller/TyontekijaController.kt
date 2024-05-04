package gradu.restful.controller

import gradu.restful.model.Tyontekija
import gradu.restful.service.TyontekijaService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/api/tyontekija")
class TyontekijaController(
    private val tyontekijaService: TyontekijaService
) {
    @GetMapping("/kaikki")
    @ResponseBody
    fun haeKaikkiTyontekijat(): List<Tyontekija> {
        return tyontekijaService.haeKaikkiTyontekijat()
    }

    @PostMapping("/lisaa")
    fun lisaaTyontekija(@RequestBody tyontekija: Tyontekija): ResponseEntity<String> {
        tyontekijaService.lisaaTyontekija(tyontekija)
        return ResponseEntity.ok().body("Employee added successfully")
    }

    @PutMapping("/muokkaa/{id}")
    fun muokkaaTyontekija(
        @PathVariable(required = true) id: Long,
        @RequestBody tyontekija: Tyontekija): ResponseEntity<String> {
        tyontekijaService.muokkaaTyontekij(id, tyontekija)
        return ResponseEntity.ok().body("Employee modified successfully")
    }
}

