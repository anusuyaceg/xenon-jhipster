package com.xenon.life.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xenon.life.domain.Xen;
import com.xenon.life.service.XenService;
import com.xenon.life.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Xen.
 */
@RestController
@RequestMapping("/api")
public class XenResource {

    private final Logger log = LoggerFactory.getLogger(XenResource.class);

    private static final String ENTITY_NAME = "xen";
        
    private final XenService xenService;

    public XenResource(XenService xenService) {
        this.xenService = xenService;
    }

    /**
     * POST  /xens : Create a new xen.
     *
     * @param xen the xen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new xen, or with status 400 (Bad Request) if the xen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/xens")
    @Timed
    public ResponseEntity<Xen> createXen(@RequestBody Xen xen) throws URISyntaxException {
        log.debug("REST request to save Xen : {}", xen);
        if (xen.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new xen cannot already have an ID")).body(null);
        }
        Xen result = xenService.save(xen);
        return ResponseEntity.created(new URI("/api/xens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /xens : Updates an existing xen.
     *
     * @param xen the xen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated xen,
     * or with status 400 (Bad Request) if the xen is not valid,
     * or with status 500 (Internal Server Error) if the xen couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/xens")
    @Timed
    public ResponseEntity<Xen> updateXen(@RequestBody Xen xen) throws URISyntaxException {
        log.debug("REST request to update Xen : {}", xen);
        if (xen.getId() == null) {
            return createXen(xen);
        }
        Xen result = xenService.save(xen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, xen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /xens : get all the xens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of xens in body
     */
    @GetMapping("/xens")
    @Timed
    public List<Xen> getAllXens() {
        log.debug("REST request to get all Xens");
        return xenService.findAll();
    }

    /**
     * GET  /xens/:id : get the "id" xen.
     *
     * @param id the id of the xen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the xen, or with status 404 (Not Found)
     */
    @GetMapping("/xens/{id}")
    @Timed
    public ResponseEntity<Xen> getXen(@PathVariable Long id) {
        log.debug("REST request to get Xen : {}", id);
        Xen xen = xenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(xen));
    }

    /**
     * DELETE  /xens/:id : delete the "id" xen.
     *
     * @param id the id of the xen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/xens/{id}")
    @Timed
    public ResponseEntity<Void> deleteXen(@PathVariable Long id) {
        log.debug("REST request to delete Xen : {}", id);
        xenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
