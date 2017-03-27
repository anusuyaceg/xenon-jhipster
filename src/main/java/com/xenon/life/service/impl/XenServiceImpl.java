package com.xenon.life.service.impl;

import com.xenon.life.service.XenService;
import com.xenon.life.domain.Xen;
import com.xenon.life.repository.XenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Xen.
 */
@Service
@Transactional
public class XenServiceImpl implements XenService{

    private final Logger log = LoggerFactory.getLogger(XenServiceImpl.class);
    
    private final XenRepository xenRepository;

    public XenServiceImpl(XenRepository xenRepository) {
        this.xenRepository = xenRepository;
    }

    /**
     * Save a xen.
     *
     * @param xen the entity to save
     * @return the persisted entity
     */
    @Override
    public Xen save(Xen xen) {
        log.debug("Request to save Xen : {}", xen);
        Xen result = xenRepository.save(xen);
        return result;
    }

    /**
     *  Get all the xens.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Xen> findAll() {
        log.debug("Request to get all Xens");
        List<Xen> result = xenRepository.findAll();

        return result;
    }

    /**
     *  Get one xen by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Xen findOne(Long id) {
        log.debug("Request to get Xen : {}", id);
        Xen xen = xenRepository.findOne(id);
        return xen;
    }

    /**
     *  Delete the  xen by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Xen : {}", id);
        xenRepository.delete(id);
    }
}
