package com.computicsolutions.openfashion.repository;

import com.computicsolutions.openfashion.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * File Repository
 */
@Repository
public interface FileRepository extends JpaRepository<File, String> {
}
