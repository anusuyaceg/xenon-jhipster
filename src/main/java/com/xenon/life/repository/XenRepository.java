package com.xenon.life.repository;

import com.xenon.life.domain.Xen;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Xen entity.
 */
@SuppressWarnings("unused")
public interface XenRepository extends JpaRepository<Xen,Long> {

}
