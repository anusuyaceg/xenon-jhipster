package com.xenon.life.service;

import com.xenon.life.domain.Xen;
import java.util.List;

/**
 * Service Interface for managing Xen.
 */
public interface XenService {

    /**
     * Save a xen.
     *
     * @param xen the entity to save
     * @return the persisted entity
     */
    Xen save(Xen xen);

    /**
     *  Get all the xens.
     *  
     *  @return the list of entities
     */
    List<Xen> findAll();

    /**
     *  Get the "id" xen.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Xen findOne(Long id);

    /**
     *  Delete the "id" xen.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
